/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geobatch.destination.ingestion;

import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.common.OutputObject;
import it.geosolutions.geobatch.destination.common.utils.DbUtils;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureReader;
import org.geotools.data.Query;
import org.geotools.data.Transaction;
import org.geotools.factory.Hints;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.expression.Function;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.linearref.LengthIndexedLine;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class OriginalArcsIngestionProcess extends InputObject {
	
	
	/** INCIDENTALITA_FEATURE */
	private static final String INCIDENTALITA_FEATURE = "siig_t_incidentalita";


	private final static Logger LOGGER = LoggerFactory.getLogger(ArcsIngestionProcess.class);

	private static final String PTER_GEO_FEATURE = "siig_geo_pl_pter";
	
	private static final String PROVINCE_GEO_FEATURE = "siig_geo_pl_province";


	private static final String PATRIMONIALITA_FEATURE = "siig_d_patrimonialita_strada";
	
	private static Pattern typeNameParts = Pattern
			.compile("^([A-Z]{2})_([A-Z]{1})_([A-Za-z]+)_([0-9]{8})_ORIG$");
	
	private int partner;
	private String codicePartner;
	private String date;
	private String outName;
	private String geoId = "fid";
	
	Map<Double, Integer> counters = new HashMap<Double, Integer>();
	Map<Integer, Integer> corsieStandard = new HashMap<Integer, Integer>();
	Map<Integer, String> velocitaStandard = new HashMap<Integer, String>();
	Map<String, Double> incidentalitaStandard = new HashMap<String, Double>();
	Map<String, Double> cumulativeData = new HashMap<String, Double>();
	
	private int lastYear = -1;

	private static NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.US); 
	
	static {
		doubleFormat.setGroupingUsed(false);
		doubleFormat.setMaximumFractionDigits(5);
	}
	
	private String padrStatisticoSplitted1 = null;
	private String padrStatisticoSplitted2 = null;
	private String padrStatisticoSplitted3 = null;


	private int years = 5;
	
	/**
	 * @param inputTypeName
	 * @param listener
	 * @param metadataHandler
	 * @param dataStore
	 * @throws IOException 
	 */
	public OriginalArcsIngestionProcess(String inputTypeName,
			ProgressListenerForwarder listener,
			MetadataIngestionHandler metadataHandler, DataStore dataStore, int lastYear, int years) throws IOException {
		super(inputTypeName, listener, metadataHandler, dataStore);
		if(lastYear > 0) {
			this.lastYear = lastYear;
		} else {
			this.lastYear = getMaxIncidentalitaYear();
		}
		if(years > 0) {
			this.years = years;
		}
	}
	
	
	
	/**
	 * @return the lastYear
	 */
	public int getLastYear() {
		return lastYear;
	}



	/**
	 * @return
	 * @throws IOException 
	 */
	private int getMaxIncidentalitaYear() throws IOException {
		Function max = filterFactory.function("Collection_Max", filterFactory.property("anno"));			
		
		Number maxValue = (Number)max.evaluate( dataStore.getFeatureSource(INCIDENTALITA_FEATURE).getFeatures());
		if(maxValue != null) {
			return maxValue.intValue();
		}
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	@Override
	protected boolean parseTypeName(String typeName) {
		Matcher m = typeNameParts.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			// extraction date
			date = m.group(4);			
			// output feature name
			outName = m.group(1) + "_" + m.group(2) + "_" + m.group(3) + "_" + m.group(4);
			// TODO: add other validity checks
			
			return true;
		}
		return false;
	}
	
	
	
	private void preloadDatiCategorieStrada() throws IOException {
		corsieStandard.clear();
		velocitaStandard.clear();
		
		
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(
						new Query("siig_d_categoria_strada"),
						Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				Integer id = ((Number)feature.getAttribute("id_categoria_strada")).intValue();
				Integer corsie = ((Number)feature.getAttribute("nr_medio_corsie")).intValue();
				Integer velocitaLeggeri = ((Number)feature.getAttribute("vel_media_vei_leggeri")).intValue();
				Integer velocitaPesanti = ((Number)feature.getAttribute("vel_media_vei_pesanti")).intValue();
				corsieStandard.put(id, corsie);
				velocitaStandard.put(id, velocitaLeggeri + "|" + velocitaPesanti);
			}
			
		} finally {
			featureReader.close();
		}
			
	}
	
	private void preloadIncidentalita() throws IOException {
		incidentalitaStandard.clear();
		
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(
						new Query(INCIDENTALITA_FEATURE),
						Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				String provincia = (String)feature.getAttribute("cod_provincia");
				Integer patrimonialita = ((Number)feature.getAttribute("id_patrimonialita")).intValue();
				Integer anno = ((Number)feature.getAttribute("anno")).intValue();
				Number incidenti = (Number)feature.getAttribute("nr_incidenti");
				if(incidenti != null) {
					if(cumulativeData.containsKey(provincia + "." + patrimonialita)) {
						incidenti = incidenti.doubleValue() / cumulativeData.get(provincia + "." + patrimonialita);
					}
					incidentalitaStandard.put(provincia + "." + patrimonialita + "." + anno, incidenti.doubleValue());
				}
			}
			
		} finally {
			featureReader.close();
		}
			
	}
	
	private void preloadPadrStatistico() throws IOException {
		Map<Integer, String> padrList = new TreeMap<Integer, String>();
		
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(
						new Query("siig_t_sostanza"),
						Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				Integer id = ((Number)feature.getAttribute("id_sostanza")).intValue();
				Double padr = ((Number)feature.getAttribute("padr_statistico")).doubleValue();
				padrList.put(id, doubleFormat.format(padr));
			}
			populateStatisticPadr(padrList);
			
		} finally {
			featureReader.close();
		}
			
	}



	/**
	 * Populate the 3 splitted default padr values
	 * The main padr string is splitted in chunks of 250 characters or less
	 * @param padrList
	 */
	private void populateStatisticPadr(Map<Integer, String> padrList) {
		if(padrList != null && !padrList.isEmpty()){
			
			// Data is coming from old type input files
			if(padrList.size() <= 12){
				padrStatisticoSplitted1 = StringUtils.join(padrList.values(), "|");
				padrStatisticoSplitted2 = "";
				padrStatisticoSplitted3 = "";
				return;
			}
			
			final List<Integer> idList = new ArrayList<Integer>();
			for(Integer i : padrList.keySet()){
				idList.add( i );
			}

			// ids should be ordered
			Collections.sort(idList);
			
			StringBuilder sb1 = new StringBuilder(250);
			StringBuilder sb2 = new StringBuilder(250);
			StringBuilder sb3 = new StringBuilder(250);
			
			int currentIdx = 0;
			String padrValue =null;
			for(Integer i : idList){
				padrValue = padrList.get(i);
				if(currentIdx < (250 - padrValue.length() - 1) ){
					sb1.append(padrValue).append("|");
				}else if(currentIdx < (500 - padrValue.length() - 1) ){
					sb2.append(padrValue).append("|");
				}else if(currentIdx < (750 - padrValue.length() - 1) ){
					sb3.append(padrValue).append("|");
				}else{
					// TODO: throw?
				}
				currentIdx += (padrValue.length()+1);
			}
			padrStatisticoSplitted1 = sb1.toString();
			padrStatisticoSplitted2 = sb1.toString();
			padrStatisticoSplitted3 = sb1.toString();
			
		}
	}
	
	public void importArcs(CoordinateReferenceSystem crs, 
			boolean dropInput)
			throws IOException {
		reset();
		if(isValid()) {								
			
			
			crs = checkCrs(crs);			
			
			int process = -1;
			int trace = -1;
			int errors = 0;
			
			try {												
								
				removeOldImports();
				// new process
				process = createProcess();
				// write log for the imported file
				trace = logFile(process, NO_TARGET,
						partner, codicePartner, date, false);
				
				// setup input reader								
				createInputReader(dataStore, Transaction.AUTO_COMMIT, null);						
				
				createOutputFeature(dataStore, outName);

				OutputObject mainGeoObject = new OutputObject(dataStore,
						Transaction.AUTO_COMMIT, outName, geoId);

				// calculates total objects to import				
				int total = getImportCount();												
				OutputObject[] outputObjects = new OutputObject[] {mainGeoObject};
				try {
					SimpleFeature inputFeature = null;
					while( (inputFeature = readInput()) != null) {
						
						Transaction rowTransaction = new DefaultTransaction();
						setTransaction(outputObjects, rowTransaction);
						
						try {		
							doSegmentation(inputFeature, outputObjects);
							
							rowTransaction.commit();
							
							updateImportProgress(total, errors, "Importing data in arcs");
						} catch(Exception e) {						
							errors++;
							rowTransaction.rollback();
							metadataHandler.logError(trace, errors,
									"Error writing output feature", getError(e),
									0);
						} finally {				
							rowTransaction.close();							
						}
																
					}
					importFinished(total, errors, "Data imported in arcs");
					metadataHandler.updateLogFile(trace, total, errors, true);
					
				} finally {
					closeInputReader();
				}
				
				metadataHandler.updateLogFile(trace, total, errors, true);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
				errors++;				
				metadataHandler.logError(trace, errors, "Error importing data", getError(e), 0);				
				throw e;
			} finally {
				if(dropInput) {
					dropInputFeature(dataStore);
				}
				finalReport(errors);
				if(process != -1) {
					// close current process phase
					metadataHandler.closeProcessPhase(process, "A");
				}				
							
			}
		}
	}

	
	
	@Override
	protected void reset() throws IOException {
		super.reset();
		counters.clear();
		counters.put(1000.0, 0);
		counters.put(500.0, 0);
		counters.put(100.0, 0);
		
		preloadCumulativeData();
		preloadDatiCategorieStrada();
		preloadPadrStatistico();
		preloadIncidentalita();
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private void preloadCumulativeData() throws IOException {
		cumulativeData.clear();
		List<Integer> patrimonialita = new ArrayList<Integer>();
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(
						new Query(PATRIMONIALITA_FEATURE),
						Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				patrimonialita.add(((Number)feature.getAttribute("id_patrimonialita")).intValue());
			}
		} finally {
			featureReader.close();
		}
		
		featureReader = dataStore
				.getFeatureReader(
						new Query(PROVINCE_GEO_FEATURE),
						Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				String provincia = (String)feature.getAttribute("cod_provincia");
				Geometry geometry = (Geometry)feature.getDefaultGeometry();
				if(geometry != null) {
					for(int idPatrimonialita : patrimonialita) {
						Filter filter = filterFactory.and(
							filterFactory.equals(filterFactory.property("FK_PATRIM"), filterFactory.literal(idPatrimonialita)),
							filterFactory.intersects(filterFactory.property("the_geom"), filterFactory.literal(geometry))
						);
						FeatureReader<SimpleFeatureType, SimpleFeature> geoReader = dataStore
								.getFeatureReader(
										new Query(inputTypeName, filter),
										Transaction.AUTO_COMMIT);
						double totalLen = 0.0;
						try {
							while(geoReader.hasNext()) {
								SimpleFeature geoFeature = geoReader.next();
								if(geoFeature.getDefaultGeometry() != null) {
									totalLen += ((Geometry)geoFeature.getDefaultGeometry()).getLength();
								}
							}
						} finally {
							geoReader.close();
						}
						if(totalLen != 0.0) {
							cumulativeData.put(provincia + "." + idPatrimonialita, totalLen);
						}
					}
					
				}
			}
			
		} finally {
			featureReader.close();
		}
	}



	/**
	 * Reads the file "roads_input_model.txt" to create the datastore schema
	 * @param dataStore
	 * @param outName2
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void createOutputFeature(DataStore dataStore, String featureName) throws IOException {
		try {
			DbUtils.dropFeatureType(dataStore, featureName);			
		} catch(SQLException e) {
			// check for "table does not exist" error and ignore it
			if(!e.getSQLState().equals("42P01")) {
				throw new IOException(e);
			}
		}
		try {
			SimpleFeatureType featureType = DataUtilities.createType(featureName, readModel("roads_input_model"));
			dataStore.createSchema(featureType);
		} catch (SchemaException e) {
			throw new IOException(e);
		} 
	}
	
	private String readModel(String model) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + model + ".txt")));
			return reader.readLine();
			
		} finally {
			if(reader != null) {
			    reader.close();
			}			
		}
	}

	/**
	 * Cuts the inputFeature in 100, 500 and 1000 length pieces and writes 
	 * the output in the {@link OutputObject} array first element 
	 * @param inputFeature
	 * @throws IOException 
	 */
	private void doSegmentation(SimpleFeature inputFeature, OutputObject[] outputObjects) throws IOException {
		Geometry geo = (Geometry)inputFeature.getDefaultGeometry();
		
		for(Geometry segmented1000 : doSegmentation(inputFeature, geo, 1000.0)) {			
			int counter1000 = incrementCounter(1000.0);
			for(Geometry segmented500 : doSegmentation(inputFeature, segmented1000, 500.0)) {
				int counter500 = incrementCounter(500.0);
				for(Geometry segmented100 : doSegmentation(inputFeature, segmented500, 100.0)) {					
					int counter100 = incrementCounter(100.0);
					
					writeGeoObject(outputObjects[0], inputFeature, segmented100, counter100, counter500, counter1000, geo.getLength());
				}
			}
		}
	}

	/**
	 * @param segmented100
	 * @return
	 * @throws IOException 
	 */
	private String calcDissesti(Geometry geometry) throws IOException {
		List<String> dissesti = new ArrayList<String>();
		Query query = new Query(PTER_GEO_FEATURE, filterFactory.and(
				filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner+"")),
				filterFactory.intersects(filterFactory.property("geometria"), filterFactory.literal(geometry))
		));
		
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(query, Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				Object dissesto = feature.getAttribute("fk_dissesto");
				if(dissesto != null) {
					dissesti.add(dissesto.toString());
				}
			}
			if(dissesti.size() > 0) {
				return StringUtils.join(dissesti, "|");
			} else {
				return null;
			}
		} finally {
			featureReader.close();
		}
		
	}

	/**
	 * @param outputObject
	 * @param inputFeature
	 * @param segmented100
	 * @param counter100
	 * @param counter500
	 * @param counter1000
	 * @throws IOException 
	 */
	private void writeGeoObject(OutputObject geoObject,
			SimpleFeature inputFeature, Geometry geometry, int counter100,
			int counter500, int counter1000, double originalGeometryLen) throws IOException {
		SimpleFeatureBuilder geoFeatureBuilder = geoObject.getBuilder();
		
		String dissesti = calcDissesti(geometry);
		
		Integer categoria = ((Number)inputFeature.getAttribute("FK_CATEGOR")).intValue();
		Integer patrimonialita = ((Number)inputFeature.getAttribute("FK_PATRIM")).intValue();
		String flagCorsie = (String)inputFeature.getAttribute("FLG_N_CORS");
		String flagVelocita = (String)inputFeature.getAttribute("FLG_VELOC");
		String flagTgm = (String)inputFeature.getAttribute("FLG_TGM");
		String flagIncidenti = (String)inputFeature.getAttribute("FLG_N_INC");
		String flagViadotto = (String)inputFeature.getAttribute("FLG_VDTT");
		String flagGalleria = (String)inputFeature.getAttribute("FLG_GLLR");
		Integer corsie = (Integer)inputFeature.getAttribute("N_CORSIE");
		if(corsie == null || corsie <=0) {			
			if(categoria != null) {
				corsie = corsieStandard.get(categoria);
				flagCorsie = "S";
			}
		}
		String[] defaultSpeeds = new String[] {"0", "0"};
		if(categoria != null) {
			defaultSpeeds = velocitaStandard.get(categoria).split("\\|");
		}
		Integer velocitaLeggeri = ((Number)inputFeature.getAttribute("VEL_LEGG")).intValue();
		if((velocitaLeggeri == null || velocitaLeggeri < 0) ) {
			velocitaLeggeri = Integer.parseInt(defaultSpeeds[0]);
			flagVelocita = "S";
		}
		Integer velocitaPesanti = ((Number)inputFeature.getAttribute("VEL_PES")).intValue();
		if((velocitaPesanti == null || velocitaPesanti < 0) ) {
			velocitaPesanti = Integer.parseInt(defaultSpeeds[1]);
			flagVelocita = "S";
		}
		String velocita = velocitaLeggeri + "|" + velocitaPesanti;
		
		Integer tgmLeggeri = ((Number)inputFeature.getAttribute("TGM_LEGG")).intValue();
		if(tgmLeggeri == null) {
			tgmLeggeri = 0;
		} else if(originalGeometryLen != 0){
			tgmLeggeri = (int)Math.round((double)tgmLeggeri / (double)originalGeometryLen * geometry.getLength());
		}
		Integer tgmPesanti = ((Number)inputFeature.getAttribute("TGM_PES")).intValue();
		if(tgmPesanti == null) {
			tgmPesanti = 0;
		} else if(originalGeometryLen != 0){
			tgmPesanti = (int)Math.round((double)tgmPesanti / (double)originalGeometryLen * geometry.getLength());
		}
		String tgm = tgmLeggeri + "|" + tgmPesanti;
		
		String provincia = getProvincia(geometry);
		String padr = (String)inputFeature.getAttribute("PADR");
		String padr2 = (String)inputFeature.getAttribute("PADR2");
		String padr3 = (String)inputFeature.getAttribute("PADR3");
		// Note: if the feature does not have a valid PADR attribute, PADR2 and PADR3 are not checked
		if(padr == null || padr.trim().isEmpty()) {
			padr = padrStatisticoSplitted1 ;
			padr2 = padrStatisticoSplitted2;
			padr3 = padrStatisticoSplitted3;
		}
		
		double[] incidentiPerYear = new double[years];
				
		for(int year = 1; year <= years; year++) {
			double incidenti = ((Number)inputFeature.getAttribute("INC_ANNO_" + year)).doubleValue();
			if(incidenti < 0) {
				String key = provincia + "." + patrimonialita + "." + (lastYear + 1 - year);
				if(incidentalitaStandard.containsKey(key)) {
					flagIncidenti = "S";
					incidenti = incidentalitaStandard.get(key) * geometry.getLength();
				} else {
					incidenti = 0;
				}
			}
			incidentiPerYear[year - 1] = incidenti;
		}
		double incidenti = 0;
		for(int year = 1; year <= years; year++) {
			incidenti += incidentiPerYear[year -1];
		}
		incidenti = (double)incidenti / (double)incidentiPerYear.length;

		for(AttributeDescriptor attr : geoObject.getSchema().getAttributeDescriptors()) {
			if(attr.getLocalName().equals(geoId) || attr.getLocalName().equals("ID_SEGM_01")) {
				geoFeatureBuilder.add(counter100);
			} else if(attr.getLocalName().equals("ID_SEGM_05")) {
				geoFeatureBuilder.add(counter500);
			} else if(attr.getLocalName().equals("ID_SEGM_10")) {
				geoFeatureBuilder.add(counter1000);
			} else if(attr.getLocalName().equals("ID_ORIG")) {
				geoFeatureBuilder.add(inputFeature.getAttribute("ID_STRADA"));
			} else if(attr.getLocalName().equals("LUNGHEZZA")) {
				geoFeatureBuilder.add(geometry.getLength());
			} else if(attr.getLocalName().equals("CFF")) {
				geoFeatureBuilder.add(inputFeature.getAttribute("CFF"));
			} else if(attr.getLocalName().equals("PTERR")) {
				geoFeatureBuilder.add(dissesti);
			} else if(attr.getLocalName().equals("N_CORSIE")) {				
				geoFeatureBuilder.add(corsie);
			} else if(attr.getLocalName().equals("FLG_N_CORS")) {				
				geoFeatureBuilder.add(flagCorsie);
			} else if(attr.getLocalName().equals("COD_PROVINCIA")) {				
				geoFeatureBuilder.add(provincia);
			} else if(attr.getLocalName().equals("PADR")) {				
				geoFeatureBuilder.add(padr);
			} else if(attr.getLocalName().equals("PADR2")) {				
				geoFeatureBuilder.add(padr2);
			} else if(attr.getLocalName().equals("PADR3")) {				
				geoFeatureBuilder.add(padr3);
			} else if(attr.getLocalName().equals("VELOCITA")) {				
				geoFeatureBuilder.add(velocita);
			} else if(attr.getLocalName().equals("FLG_VELOC")) {				
				geoFeatureBuilder.add(flagVelocita);
			} else if(attr.getLocalName().equals("TGM")) {				
				geoFeatureBuilder.add(tgm);
			} else if(attr.getLocalName().equals("FLG_TGM")) {				
				geoFeatureBuilder.add(flagTgm);
			} else if(attr.getLocalName().equals("INCIDENT")) {				
				geoFeatureBuilder.add(incidenti);
			} else if(attr.getLocalName().equals("FLG_N_INC")) {				
				geoFeatureBuilder.add(flagIncidenti);
			} else if(attr.getLocalName().equals("FLG_VDTT")) {
				geoFeatureBuilder.add(flagViadotto);
			} else if(attr.getLocalName().equals("FLG_GLLR")) {
				geoFeatureBuilder.add(flagGalleria);
			} else if(attr.getLocalName().equals("the_geom")) {
				geoFeatureBuilder.add(geometry);
			} else {
				geoFeatureBuilder.add(null);
			}
						
		}
		
		SimpleFeature geoFeature = geoFeatureBuilder.buildFeature("" + counter100);		
		geoFeature.getUserData().put(Hints.USE_PROVIDED_FID, true);
		geoObject.getWriter().addFeatures(DataUtilities
				.collection(geoFeature));
		
	}

	

	/**
	 * @param geometry
	 * @return
	 * @throws IOException 
	 */
	private String getProvincia(Geometry geometry) throws IOException {
		String codProvincia = null;
		double len = -1.0;
		
		Query query = new Query(PROVINCE_GEO_FEATURE,
				filterFactory.intersects(filterFactory.property("geometria"), filterFactory.literal(geometry))
		);
		
		FeatureReader<SimpleFeatureType, SimpleFeature> featureReader = dataStore
				.getFeatureReader(query, Transaction.AUTO_COMMIT);
		try {
			while(featureReader.hasNext()) {
				SimpleFeature feature = featureReader.next();
				Geometry currentGeo = (Geometry)feature.getDefaultGeometry();
				double currentLen = currentGeo.intersection(geometry).getLength();
				if(currentLen > len) {
					len = currentLen;
					codProvincia = (String)feature.getAttribute("cod_provincia");
				}
				
			}
			
		} finally {
			featureReader.close();
		}
		return codProvincia;
	}

	/**
	 * @param d
	 * @return
	 */
	private int incrementCounter(double key) {
		int current = counters.get(key);
		current++;
		counters.put(key, current);
		return current;
	}

	/**
	 * @param geo
	 * @param i
	 * @return
	 */
	private Iterable<Geometry> doSegmentation(SimpleFeature inputFeature, Geometry geo, double segmentLength) {
		List<Geometry> geometries = new ArrayList<Geometry>();
		LengthIndexedLine lineGeo = new LengthIndexedLine(geo);
		double len = geo.getLength();
		int parts = (int)Math.ceil(len / segmentLength);
		for(int part = 0; part < parts; part++) {
			Geometry partGeo = lineGeo.extractLine(part * segmentLength, (part + 1) * segmentLength);
			
			boolean isLast = part == parts -1;
			if(isLast && (geometries.size() > 0) && (partGeo.getLength() < segmentLength / 2)) {
				Geometry otherGeo = geometries.remove(geometries.size() -1);
				geometries.add(otherGeo.union(partGeo));
			} else {
				geometries.add(partGeo);
			}
			
		}
		return geometries;
	}

}
