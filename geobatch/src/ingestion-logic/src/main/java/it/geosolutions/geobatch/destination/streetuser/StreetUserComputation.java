package it.geosolutions.geobatch.destination.streetuser;

import it.geosolutions.destination.utils.BufferUtils;
import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.common.utils.FeatureLoaderUtils;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureStore;
import org.geotools.data.Query;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.PropertyIsEqualTo;
import org.opengis.filter.sort.SortBy;
import org.opengis.filter.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;

public class StreetUserComputation extends InputObject {

	private final static Logger LOGGER = LoggerFactory.getLogger(StreetUserComputation.class);
	private Double SECONDS_IN_HOUR = 3600d;
	private Double KM_IN_METER = 1/1000d;
	private Double HOUR_IN_DAY = 24d;
	private String codicePartner;
	private int partner;
	private String date;
	private boolean removeFeatures = true;
	private boolean sorted = false;

	private String siig_r_scen_vuln_X_type;
	private Integer startId = null;

	//private final static String EXTERNAL_PROP_DIR_PATH = "EXTERNAL_PROP_DIR_PATH";
	//private static final String STREET_USERS_PROP = "/streetusers.properties";

	private static final Integer MAX_VEICLE_QUEUE_SIZE = 150;

	//private static SimpleFeatureType siig_r_scen_vuln_X = null;

	private static Pattern TYPE_NAME_PARTS = Pattern
			.compile("^([A-Z]{2})_([A-Z]{1})_([A-Za-z]+)_([0-9]{8})(_.*?)?$");

	public StreetUserComputation(String inputTypeName, ProgressListenerForwarder listenerForwarder, MetadataIngestionHandler metadataHandler, DataStore dataStore) {
		super(inputTypeName, listenerForwarder, metadataHandler, dataStore);
	}
	
	
	
	/**
	 * @return the sorted
	 */
	public boolean isSorted() {
		return sorted;
	}



	/**
	 * @param sorted the sorted to set
	 */
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}



	@Override
	protected String getInputTypeName(String inputTypeName) {
		return inputTypeName.replace("_ORIG", "").replace("_CALCS", "");
	}

	public int getPartner() {
		return partner;
	}

	@Override
	protected boolean parseTypeName(String typeName) {
		Matcher m = TYPE_NAME_PARTS.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			date = m.group(4);
			return true;
		}
		return false;
	}

	public void clearOutputFeature(Integer aggregationLevel){
		Transaction transaction = new DefaultTransaction("handle");
		try{									
			FeatureStore<SimpleFeatureType, SimpleFeature> outputFeatureStore = FeatureLoaderUtils.createFeatureSource(dataStore, transaction,this.siig_r_scen_vuln_X_type);				
			outputFeatureStore.removeFeatures(filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)));
			transaction.commit();
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(),ex);
			try {
				transaction.rollback();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		finally {
			try {
				transaction.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
	}

	private List<StreetScenario> getScenari() throws Exception{
		List<StreetScenario> scenari = new ArrayList<StreetScenario>();
		//Get "scenario" info
		FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_t_scenario");
		Query inputQuery = new Query("siig_t_scenario");
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			inputIterator = inputReader.getFeatures(inputQuery).features();

			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				
				//LOGGER.debug("Scenario <" + sf.getAttribute("tipologia") + "> found ");
				StreetScenario scenario = new StreetScenario();
				scenario.setIdScenario(getAttributeAsInt(sf.getAttribute("id_scenario")));
				scenario.setDescrizioneScenario((String) sf.getAttribute("tipologia"));
				double tempoDiCoda = getAttributeAsDouble(sf.getAttribute("tempo_di_coda"));
				scenario.setTempoDiCoda(tempoDiCoda / SECONDS_IN_HOUR);
				scenari.add(scenario);	
				
			}
			return scenari;
		} finally {
			if(inputIterator != null) {
				inputIterator.close();
			}
		}


	}

	public void execute(Integer aggregationLevel, boolean dropInput, String closePhase, boolean newProcess) throws IOException {
		
		if(aggregationLevel == 1 || aggregationLevel == 2){
			executeArc(aggregationLevel, dropInput, closePhase, newProcess);
		}
		if(aggregationLevel >= 3){
			executeCell(aggregationLevel, dropInput, closePhase, newProcess);
		}
		
	}

	public void executeCell(Integer aggregationLevel, boolean dropInput, String closePhase, boolean newProcess) throws IOException {
		LOGGER.info("Start execution for CELL with partner="+partner+" and aggregationLevel="+aggregationLevel);

		Transaction transaction = new DefaultTransaction("handle");
		
		int process = -1;
		int trace = -1;
		
		int errors = 0;
		int startErrors = 0;
		
		try{
			siig_r_scen_vuln_X_type = "siig_r_scen_vuln_"+aggregationLevel;

			// create or retrieve metadata for ingestion
			if(newProcess) {
				removeOldImports();
				// new process
				process = createProcess();
				// write log for the imported file
				trace = logFile(process, NO_TARGET,
						partner, codicePartner, date, false);
			} else {
				// existing process
				MetadataIngestionHandler.Process importData = getProcessData();
				if (importData != null) {
					process = importData.getId();
					trace = importData.getMaxTrace();
					errors = importData.getMaxError();
					startErrors = errors;
				}
			}
			
			if(removeFeatures) {
				clearOutputFeature(aggregationLevel);
			}

			List<StreetScenario> scenari = getScenari();

			Integer[] distanze = getDistanze();
			
			int maxDistanza = distanze[distanze.length - 1];
			
			Map<Integer, VehicleType> vehicleTypes = fetchVehicleTypeInfo();
			
			Map<Integer, List<StreetVeicle>> vehicles = fetchVehiclesInfo(aggregationLevel, vehicleTypes);

			SimpleFeatureStore featureStore = (SimpleFeatureStore) this.dataStore.getFeatureSource(this.siig_r_scen_vuln_X_type);
			

			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, transaction,"siig_geo_pl_arco_" + aggregationLevel);
			Query inputQuery = new Query("siig_geo_pl_arco_" + aggregationLevel);
			inputQuery.setFilter(filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)));
			if(sorted) {
				inputQuery.setSortBy(new SortBy[] {filterFactory.sort("id_geo_arco", SortOrder.ASCENDING)});
			}
			FeatureIterator<SimpleFeature> inputIterator = null;
			try {
				int total = inputReader.getCount(inputQuery);
				inputIterator = inputReader.getFeatures(inputQuery).features();

				while(inputIterator.hasNext()) {
					SimpleFeature sf = inputIterator.next();
					//Found record on pl_arco3
					Integer idGeoCell = getAttributeAsInt(sf.getAttribute("id_geo_arco"));
					Geometry geometry = (Geometry) sf.getDefaultGeometry();
					
					FeatureStore<SimpleFeatureType, SimpleFeature> inputReaderArc = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_geo_ln_arco_" + getLinearAggregationLevel(aggregationLevel));
					Query inputQueryArc = new Query("siig_geo_ln_arco_" + getLinearAggregationLevel(aggregationLevel));
					inputQueryArc.setFilter(filterFactory.and(
							filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)),
							filterFactory.intersects(filterFactory.property("geometria"), filterFactory.literal(geometry))
					));
					Map<Integer,Map<Integer, StreetUserResult>> cellResults = new HashMap<Integer, Map<Integer, StreetUserResult>>();
					FeatureIterator<SimpleFeature> inputIteratorArc = null;
					try {
						inputIteratorArc = inputReaderArc.getFeatures(inputQueryArc).features();
						boolean arcsFound = false;
						while(inputIteratorArc.hasNext()) {
						        arcsFound = true;
							SimpleFeature sfArc = inputIteratorArc.next();
							Integer idGeoArco = getAttributeAsInt(sfArc.getAttribute("id_geo_arco"));
							Geometry geometryArc = (Geometry) sfArc.getDefaultGeometry();
							
							List<StreetInfo> candidates = fetchCandidateStreets(
									idGeoArco, maxDistanza, geometryArc, vehicles, aggregationLevel);
							
							for(int distanza : distanze) {
								List<StreetInfo> distanceCandidates = new ArrayList<StreetInfo>();
								for(StreetInfo candidate : candidates) {
									if(candidate.getEffectiveGeometry().isWithinDistance(geometryArc, distanza)) {
										candidate.resetVehiclesData();
										distanceCandidates.add(candidate);
									}
								}
								Map<Integer, StreetUserResult> arcResults = computeArcoDistanza(
										idGeoArco, geometry, distanza, distanceCandidates, scenari,
										aggregationLevel);
								
								for(Integer scenarioId : arcResults.keySet()){
									StreetUserResult rArc = arcResults.get(scenarioId);
									Map<Integer, StreetUserResult> rCellMap = cellResults.get(scenarioId);
									if(rCellMap==null){
										rCellMap = new HashMap<Integer, StreetUserResult>();
										cellResults.put(scenarioId, rCellMap);
									}
									StreetUserResult rCell = rCellMap.get(distanza);
									if(rCell==null){
										rCell = new StreetUserResult(idGeoArco, distanza, scenarioId, rArc.getUtentiSede(), rArc.getUtentiBersaglio());
										rCellMap.put(distanza, rCell);
									}else{
										Double utentiSedeCell =  rArc.getUtentiSede() + rCell.getUtentiSede();
										Double utentiBersaglioCell = rArc.getUtentiBersaglio() + rCell.getUtentiBersaglio();
										rCell.setUtentiBersaglio(utentiBersaglioCell);
										rCell.setUtentiSede(utentiSedeCell);							
									}
								}
							}
						}
						if(!arcsFound) {
						    for(StreetScenario scenario : scenari) {
						        Map<Integer, StreetUserResult> cellMap = new HashMap<Integer, StreetUserResult>();
						        for(int idDistanza : distanze) {
						            cellMap.put(idDistanza, new StreetUserResult(idGeoCell, idDistanza, scenario.getIdScenario(), 0.0, 0.0));
						        }
						        cellResults.put(scenario.getIdScenario(), cellMap);
						    }
						}
						
					} catch(Exception e) {
						errors++;
						metadataHandler.logError(trace, errors,
								"Error calculating street users data for cell: " + idGeoCell, getError(e),
								idGeoCell);
					} finally {
						if(inputIteratorArc != null) {
							inputIteratorArc.close();
						}
					}
					Transaction rowTransaction = new DefaultTransaction();
					featureStore.setTransaction( rowTransaction );
					try {
						for(Integer idScenario : cellResults.keySet()){
							Map<Integer, StreetUserResult> cellMap = cellResults.get(idScenario);
							for(Integer distanza : cellMap.keySet()) {
								StreetUserResult celResult = cellMap.get(distanza);
								persistStreetUsersData(partner,idGeoCell, distanza, idScenario, celResult.getUtentiSede(), celResult.getUtentiBersaglio(), featureStore);									 
							}
						}
						rowTransaction.commit();

						inputCount++;
						updateImportProgress(inputCount, total, 1, errors - startErrors, "Calculating streets user data in " + siig_r_scen_vuln_X_type);
					} catch(Exception e) {
						errors++;
						rowTransaction.rollback();
						metadataHandler.logError(trace, errors,
								"Error writing street users data for cell: " + idGeoCell, getError(e),
								idGeoCell);
					} finally {				
						rowTransaction.close();							
					}
					
				}
				importFinished(total, errors - startErrors, "Data imported in " + siig_r_scen_vuln_X_type);
				transaction.commit();
			} finally {
				
				if(inputIterator != null) {
					inputIterator.close();
				}
			}
					
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(),ex);
			try {
				transaction.rollback();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		finally {
			if (process != -1 && closePhase != null) {
                // close current process phase
                if (metadataHandler != null) {
                    metadataHandler.closeProcessPhase(process, closePhase);
                }
            } 
            transaction.close();
            finalReport("Streets users computation completed", errors - startErrors);
		}
	}

	
	protected Map<Integer, Geometry> getBuffersForArc(Integer[] distanceValues, Geometry geometry) {
		Map<Integer, Geometry> bufferMap = new HashMap<Integer, Geometry>();
        double previousDistance = 0.0;
        for(int count = 0; count  < distanceValues.length; count++) {        	
        	int distanceValue = distanceValues[count];
        	
        	geometry = BufferUtils.iterativeBuffer(geometry, distanceValue - previousDistance, 200);
        	previousDistance = distanceValue;
        	
        	bufferMap.put(distanceValue, geometry);
        }
        
        return bufferMap;
	}
	
	public void executeArc(Integer aggregationLevel, boolean dropInput, String closePhase, boolean newProcess) throws IOException{

		LOGGER.info("Start execution with partner="+partner+" and aggregationLevel="+aggregationLevel);
		Transaction transaction = new DefaultTransaction("handle");		
		
		int process = -1;
		int trace = -1;

		int errors = 0;
		int startErrors = 0;
		
		try{
			
			// create or retrieve metadata for ingestion
			if(newProcess) {
				removeOldImports();
				// new process
				process = createProcess();
				// write log for the imported file
				trace = logFile(process, NO_TARGET,
						partner, codicePartner, date, false);
			} else {
				// existing process
				MetadataIngestionHandler.Process importData = getProcessData();
				if (importData != null) {
					process = importData.getId();
					trace = importData.getMaxTrace();
					errors = importData.getMaxError();
					startErrors = errors;
				}
			}
			
			siig_r_scen_vuln_X_type = "siig_r_scen_vuln_"+aggregationLevel;
						
			if(removeFeatures) {
				clearOutputFeature(aggregationLevel);
			}

			List<StreetScenario> scenari = getScenari();

			Integer[] distanze = getDistanze();
			int maxDistanza = distanze[distanze.length - 1];
			
			Map<Integer, VehicleType> vehicleTypes = fetchVehicleTypeInfo();
			
			Map<Integer, List<StreetVeicle>> vehicles = fetchVehiclesInfo(aggregationLevel, vehicleTypes);
			
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils
					.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,
							"siig_geo_ln_arco_" + aggregationLevel);
			Query inputQuery = new Query("siig_geo_ln_arco_" + aggregationLevel);
			Filter filter = filterFactory.equals(
					filterFactory.property("fk_partner"),
					filterFactory.literal(partner));
			if(startId != null) {
				filter = filterFactory.and(
						filter,
						filterFactory.greaterOrEqual(filterFactory.property("id_geo_arco"), filterFactory.literal(startId))
				);
			}
			inputQuery.setFilter(filter);
			if(sorted) {
				inputQuery.setSortBy(new SortBy[] {filterFactory.sort("id_geo_arco", SortOrder.ASCENDING)});
			}
			FeatureIterator<SimpleFeature> inputIterator = null;
			try {
				int total = inputReader.getCount(inputQuery);
				inputIterator = inputReader.getFeatures(inputQuery).features();


				SimpleFeatureStore featureStore = (SimpleFeatureStore) this.dataStore
						.getFeatureSource(this.siig_r_scen_vuln_X_type);
				//

				while(inputIterator.hasNext()) {
					SimpleFeature sf = inputIterator.next();
					Integer idGeoArco = getAttributeAsInt(sf.getAttribute("id_geo_arco"));
					Geometry geometry = (Geometry) sf.getDefaultGeometry();
					
					List<StreetInfo> candidates = fetchCandidateStreets(
							idGeoArco, maxDistanza, geometry, vehicles, aggregationLevel);
					
					for(int distanza : distanze) {
						Transaction rowTransaction = new DefaultTransaction();
						featureStore.setTransaction( rowTransaction );
						List<StreetInfo> distanceCandidates = new ArrayList<StreetInfo>();
						for(StreetInfo candidate : candidates) {
							if(candidate.getEffectiveGeometry().isWithinDistance(geometry, distanza)) {
								candidate.resetVehiclesData();
								distanceCandidates.add(candidate);
							}
						}
						try {
							Map<Integer, StreetUserResult> results = computeArcoDistanza(
									idGeoArco, geometry, distanza, distanceCandidates, scenari,
									aggregationLevel);
							for (Integer key : results.keySet()) {
								StreetUserResult r = results.get(key);
								persistStreetUsersData(partner, r.getIdArco(),
										r.getIdDistanza(), r.getIdScenario(),
										r.getUtentiSede(),
										r.getUtentiBersaglio(), featureStore);
								
							}
							rowTransaction.commit();
							
						} catch(Exception e) {
							errors++;
							rowTransaction.rollback();
							metadataHandler.logError(trace, errors,
									"Error writing output streets user data for arc " + idGeoArco, getError(e),
									idGeoArco);
						} finally {				
							rowTransaction.close();							
						}
					}
					inputCount++;
					updateImportProgress(inputCount, total, 1, errors - startErrors, "Calculating streets user data in " + siig_r_scen_vuln_X_type);
				}
				importFinished(total, errors - startErrors, "Streets user data calculated in " + siig_r_scen_vuln_X_type);
				transaction.commit(); 
			} finally {
				if(inputIterator != null) {
					inputIterator.close();
				}
			}
		}catch (Exception ex){
			LOGGER.error(ex.getMessage(), ex);
            transaction.rollback();
            errors++;
            if (metadataHandler != null) {
                metadataHandler.logError(trace, errors, "Error calculating streets user data", getError(ex), 0);
            }
            throw new IOException(ex);
		}
		finally {
			if (process != -1 && closePhase != null) {
                // close current process phase
                if (metadataHandler != null) {
                    metadataHandler.closeProcessPhase(process, closePhase);
                }
            } 
            transaction.close();
            finalReport("Streets users computation completed", errors - startErrors);
		}
	}


	private Map<Integer, StreetUserResult> computeArcoDistanza(
			Integer idGeoArco, Geometry geometry,
			int distanza, List<StreetInfo> streetsInfo, List<StreetScenario> scenari,
			Integer aggregationLevel) throws Exception {
		
		StreetUser streetUser = new StreetUser(idGeoArco,geometry,distanza);		
		computeVehicles(streetsInfo, scenari);		
		return computeUsers(streetsInfo, distanza, streetUser,scenari);		
	}
	
	private Integer[] getDistanze(){
		Set<Integer> distanze = new TreeSet<Integer>();
		FeatureIterator<SimpleFeature> inputIterator = null;
		try{
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_d_distanza");
			Query inputQuery = new Query("siig_d_distanza");
			
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				
				Integer distanza = getAttributeAsInt(sf.getAttribute("distanza"));
				distanze.add(distanza);				
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}
		return distanze.toArray(new Integer[0]);	
	}

	private Map<Integer, StreetUserResult> computeUsers(
			List<StreetInfo> streetsInfo, int distanza, StreetUser streetUser,
			List<StreetScenario> scenari) {
		Map<Integer,StreetUserResult> result = new HashMap<Integer,StreetUserResult>();

		Integer idArco = (Integer) streetUser.getIdArco();
		//Integer idDistanza = streetUser.getDistance().getIdDistanza();

		for(StreetScenario scenario : scenari){
			Integer idScenario = scenario.getIdScenario();
			Double utentiSede = 0d;
			Double utentiBersaglio = 0d;

			for(StreetInfo streetInfo  : streetsInfo){			

				for(StreetVeicle veicle : streetInfo.getVeicleTypes()){

					ComputedData computed = veicle.getComputedData(scenario);

					if(streetInfo.getOriginStreet()){
						Double nCoinvolti = correctCodaByStorage(computed.getN_territoriali(),computed.getN_coda(),streetInfo.getStorage());
						Double utentiVeicoli = nCoinvolti * veicle.getOccupationCoeff();
						utentiSede = utentiSede + utentiVeicoli;
					}else{
						Double utentiVeicoli = (computed.getN_territoriali() + computed.getN_transito())*veicle.getOccupationCoeff();
						utentiBersaglio = utentiBersaglio + utentiVeicoli;
					}

				}

			}
			result.put(idScenario,new StreetUserResult(idArco,distanza,idScenario,utentiSede,utentiBersaglio));
		}
		return result;

	}

	private void persistStreetUsersData(Integer partner, Integer idArco, Integer distanza, Integer idScenario, Double utentiSede, Double utentiBersaglio, SimpleFeatureStore featureStore) throws Exception {
		List<SimpleFeature> list = new ArrayList<SimpleFeature>();
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureStore.getSchema());
		String featureid = idScenario+"."+idArco + "." + distanza;
		builder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
		builder.set( "id_distanza", distanza );
		builder.set( "id_geo_arco", idArco );
		builder.set( "id_scenario", idScenario );
		builder.set( "utenti_carr_bersaglio", utentiBersaglio );
		builder.set( "utenti_carr_sede_inc", utentiSede );
		builder.set( "fk_partner", partner );
		final SimpleFeature feature = builder.buildFeature(featureid);
		feature.getUserData().put(Hints.USE_PROVIDED_FID, Boolean.TRUE);
		list.add(feature);
		SimpleFeatureCollection collection = new ListFeatureCollection(featureStore.getSchema(), list);
		//LOGGER.debug("Update street user data : " +feature.toString());
		featureStore.addFeatures(collection);
	}

	private void computeVehicles(List<StreetInfo> streetsInfo, List<StreetScenario> scenari) {
		for(StreetScenario scenario : scenari){
			for(StreetInfo streetInfo  : streetsInfo){
				List<StreetVeicle> vt = streetInfo.getVeicleTypes();
				for(int i = 0 ; i < vt.size() ; i++){
					StreetVeicle vehicle = vt.get(i);
					ComputedData computed = new ComputedData();
					Double nTerritoriali = computeVeicleT0(vehicle,streetInfo);
					computed.setN_territoriali(nTerritoriali);
					if(streetInfo.getOriginStreet()){
						Double nCoda = computeVeicleT1(vehicle,streetInfo,scenario);						
						computed.setN_coda(nCoda);
						computed.setN_transito(0d);
					}else{
						Double nTransito = computeVeicleT1(vehicle,streetInfo,scenario);
						computed.setN_transito(nTransito);
						computed.setN_coda(0d);
					}
					vehicle.addComputedData(scenario,computed);
				}

			}
		}
	}

	private Double correctCodaByStorage(Double nTerritoriali, Double nCoda, Double storage) {
		Double nCoinvolti = (nTerritoriali + nCoda);
		return (nCoinvolti > (storage/2) ? (storage/2) : nCoinvolti);		
	}

	private Double computeVeicleT1(StreetVeicle veicle, StreetInfo streetInfo, StreetScenario scenario) {
		return (veicle.getDensity() * veicle.getMeanVelocity() * scenario.getTempoDiCoda());		
	}

	private Double computeVeicleT0(StreetVeicle veicle, StreetInfo streetInfo) {
		return  (veicle.getDensity() * (streetInfo.getEffectiveGeometry().getLength() * KM_IN_METER));
	}

	private Double computeVeicleStorage(Double length, Integer nCorsie) {
		return  MAX_VEICLE_QUEUE_SIZE * nCorsie * (length * KM_IN_METER);
	}

	private List<StreetInfo> fetchCandidateStreets(int originalIdArco,
			int maxDistance, Geometry geometry, Map<Integer, List<StreetVeicle>> vehicles, int aggregationLevel)
			throws IOException {
		List<StreetInfo> streetsInfo = new ArrayList<StreetInfo>();
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader =FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_geo_ln_arco_" + getLinearAggregationLevel(aggregationLevel));
			Query inputQuery = new Query("siig_geo_ln_arco_" + getLinearAggregationLevel(aggregationLevel));
			inputQuery.setFilter(filterFactory.and(
					filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)),
					filterFactory.dwithin(filterFactory.property("geometria"), filterFactory.literal(geometry), (double)maxDistance, "m")
			));
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				Integer streetId = getAttributeAsInt(sf.getAttribute("id_geo_arco"));
				Geometry intersection = (Geometry)sf.getAttribute("geometria");
				Boolean isOriginStreet = (streetId.intValue() == originalIdArco);
				Integer nCorsie = getAttributeAsInt(sf.getAttribute("nr_corsie"));
				Double storage = computeVeicleStorage(intersection.getLength(),nCorsie);
				StreetInfo si = new StreetInfo(sf,intersection,isOriginStreet,storage);
				for(StreetVeicle vehicle : vehicles.get(streetId)) {
					si.addVeicleType(vehicle);
				}
				
				streetsInfo.add(si);
			}	
			return streetsInfo;
		}finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}
	}
	
	/*private void retrieveStreetInBuffer(int distance, StreetUser streetUser,
			List<StreetInfo> streetsInfo, int aggregationLevel) {
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			//LOGGER.debug("Look for geometry in buffer " + distance);	
			Geometry inputGeometry = (Geometry)streetUser.getGeoemtry();
			Geometry bufferGeometry = BufferUtils.iterativeBuffer(
					inputGeometry, (double) distance, 50);
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader =FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_geo_ln_arco_" + aggregationLevel);
			Query inputQuery = new Query("siig_geo_ln_arco_" + aggregationLevel);
			inputQuery.setFilter(filterFactory.and(
					filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)),
					filterFactory.intersects(filterFactory.property("geometria"), filterFactory.literal(bufferGeometry))
					));
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				Geometry intersection = bufferGeometry.intersection( (Geometry) sf.getDefaultGeometry() );
				Integer streetId = getAttributeAsInt(sf.getAttribute("id_geo_arco"));
				Boolean isOriginStreet = (streetId.intValue() == streetUser.getIdArco().intValue());
				Integer nCorsie = getAttributeAsInt(sf.getAttribute("nr_corsie"));
				Double storage = computeVeicleStorage(intersection.getLength(),nCorsie);
				StreetInfo si = new StreetInfo(sf,intersection,isOriginStreet,storage);	
				retrieveStreetInfo(si,aggregationLevel);
				streetsInfo.add(si);
			}			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}	
	}*/

	protected Map<Integer, List<StreetVeicle>> fetchVehiclesInfo(
			int aggregationLevel, Map<Integer, VehicleType> vehicleTypes)
			throws IOException {
		Map<Integer, List<StreetVeicle>> vehicles = new HashMap<Integer, List<StreetVeicle>>();
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils
					.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,
							"siig_r_tipovei_geoarco" + getLinearAggregationLevel(aggregationLevel));
			Query inputQuery = new Query("siig_r_tipovei_geoarco" + getLinearAggregationLevel(aggregationLevel));
			inputQuery.setFilter(
					filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner))
			);
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				StreetVeicle veicle = new StreetVeicle();
				int idArco = getAttributeAsInt(sf.getAttribute("id_geo_arco"));
				int vehicleType = getAttributeAsInt(sf.getAttribute("id_tipo_veicolo"));
				veicle.setType(vehicleType);
				int tgm = getAttributeAsInt(sf.getAttribute("densita_veicolare"));
				int meanVelocity = getAttributeAsInt(sf.getAttribute("velocita_media"));
				double densita;
				if(meanVelocity == 0) {
					densita = 0;
				} else {
					densita = tgm / (meanVelocity * HOUR_IN_DAY);
				}
				 						
				veicle.setDensity(densita);
				veicle.setMeanVelocity(meanVelocity);
				veicle.setTypeDescription(vehicleTypes.get(vehicleType).getDescription());
				veicle.setOccupationCoeff(vehicleTypes.get(vehicleType).getCoefficienteOccupazione());
				//retrieveVeicleInfo(veicle);
				List<StreetVeicle> vehiclesList;
				if(vehicles.containsKey(idArco)) {
					vehiclesList = vehicles.get(idArco); 
				} else {
					vehiclesList = new ArrayList<StreetVeicle>();
					vehicles.put(idArco, vehiclesList);
				}
				vehiclesList.add(veicle);
			}
			return vehicles;
		} finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}
	}
	
	/*private void retrieveStreetInfo(StreetInfo ss, int aggregationLevel) throws IOException{
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			Integer id_geo_arco = getAttributeAsInt(ss.getOriginFeature().getAttribute("id_geo_arco"));
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_r_tipovei_geoarco" + aggregationLevel);
			Query inputQuery = new Query("siig_r_tipovei_geoarco" + aggregationLevel);
			inputQuery.setFilter(filterFactory.and(
					filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner)),
					filterFactory.equals(filterFactory.property("id_geo_arco"), filterFactory.literal(id_geo_arco))
					));
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				StreetVeicle veicle = new StreetVeicle();
				veicle.setType(getAttributeAsInt(sf.getAttribute("id_tipo_veicolo")));
				int tgm = getAttributeAsInt(sf.getAttribute("densita_veicolare"));
				int meanVelocity = getAttributeAsInt(sf.getAttribute("velocita_media"));
				double densita;
				if(meanVelocity == 0) {
					densita = 0;
				} else {
					densita = tgm / (meanVelocity * HOUR_IN_DAY);
				}
				 						
				veicle.setDensity(densita);
				veicle.setMeanVelocity(meanVelocity);
				retrieveVeicleInfo(veicle);
				ss.addVeicleType(veicle);
			}
		} finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}
	}*/

	private int getLinearAggregationLevel(int aggregationLevel) {
            
            return Math.min(aggregationLevel, 3);
        }



    protected Map<Integer, VehicleType> fetchVehicleTypeInfo() throws IOException {
		Map<Integer, VehicleType> result = new HashMap<Integer, VehicleType>();
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_d_tipo_veicolo");
			Query inputQuery = new Query("siig_d_tipo_veicolo");
			
			inputIterator = inputReader.getFeatures(inputQuery).features();
			while(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				int typeId = getAttributeAsInt(sf.getAttribute("id_tipo_veicolo"));
				VehicleType type = new VehicleType(
						typeId,
						(String) sf.getAttribute("tipo_veicolo_it"),
						getAttributeAsDouble(sf.getAttribute("coeff_occupazione"))
				);
				
				result.put(typeId, type);
			}
			return result;
		} finally {
			if(inputIterator != null){
				inputIterator.close();
			}
		}
	}
	
	private void retrieveVeicleInfo(StreetVeicle veicle){
		FeatureIterator<SimpleFeature> inputIterator = null;
		try {
			FeatureStore<SimpleFeatureType, SimpleFeature> inputReader = FeatureLoaderUtils.createFeatureSource(dataStore, Transaction.AUTO_COMMIT,"siig_d_tipo_veicolo");
			Query inputQuery = new Query("siig_d_tipo_veicolo");
			inputQuery.setFilter(filterFactory.equals(filterFactory.property("id_tipo_veicolo"),filterFactory.literal(veicle.getType())));
			inputIterator = inputReader.getFeatures(inputQuery).features();
			if(inputIterator.hasNext()) {
				SimpleFeature sf = inputIterator.next();
				veicle.setTypeDescription((String) sf.getAttribute("tipo_veicolo_it"));
				veicle.setOccupationCoeff(getAttributeAsDouble(sf.getAttribute("coeff_occupazione")));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}finally{
			if(inputIterator != null){
				inputIterator.close();
			}
		}
	}

	public void setRemoveFeatures(boolean removeFeatures) {
		this.removeFeatures = removeFeatures;
	}

	private int getAttributeAsInt(Object value){
		if(value instanceof BigDecimal){
			return ((BigDecimal)value).intValue();
		}else{
			return ((Integer)value).intValue();
		}
	}

	private double getAttributeAsDouble(Object value){
		if(value == null) {
			return 0.0;
		}
		if(value instanceof BigDecimal){
			return ((BigDecimal)value).doubleValue();
		}else{
			return ((Double)value).doubleValue();
		}
	}



	public void setStartOriginId(Integer startId) {
		this.startId  = startId;
	}

}
