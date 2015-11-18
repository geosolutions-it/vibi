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
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class PterIngestionProcess extends InputObject {
	/**
	 * @param inputTypeName
	 * @param listener
	 * @param metadataHandler
	 * @param dataStore
	 */
	public PterIngestionProcess(String inputTypeName,
			ProgressListenerForwarder listener,
			MetadataIngestionHandler metadataHandler, DataStore dataStore) {
		super(inputTypeName, listener, metadataHandler, dataStore);
	}
	private final static Logger LOGGER = LoggerFactory.getLogger(PterIngestionProcess.class);

	private static final String PTER_GEO_NAME = "siig_geo_pl_pter";

	private static final String PTER_GEO_ID = "id_geo_pter";
	
	private static Pattern typeNameParts = Pattern
			.compile("^([A-Z]{2})_PTER_([0-9]{8})$");
	
	private int partner;
	private String codicePartner;
	private String date;
	
	private static Map attributeMappings = null;
	
	static {	
		// load mappings from resources					
		attributeMappings = (Map) readResourceFromXML("/pters.xml");							
	}
	
	@Override
	protected boolean parseTypeName(String typeName) {
		Matcher m = typeNameParts.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			// target macro type (bu or bnu)			
			// file date identifier
			date = m.group(2);			
			
			return true;
		}
		return false;
	}
	
	/**
	 * Imports the pter feature from the original Feature into on of the SIIG
	 * pter tables (in staging).
	 * 
	 * @param crs
	 * @param dropInput drop input table after import
	 * 
	 * @throws IOException
	 */
	public void importPter(CoordinateReferenceSystem crs, boolean dropInput)
			throws IOException {
		reset();
		if(isValid()) {								
			
			
			crs = checkCrs(crs);			
			
			int process = -1;
			int trace = -1;
			int errors = 0;
			
			
			try {	
				removeOldImports();
				process = createProcess();
				// write log for the imported file
				trace = logFile(process, NO_TARGET,
						partner, codicePartner, date, false);

				// setup input reader								
				createInputReader(dataStore, Transaction.AUTO_COMMIT, null);
				
				Transaction transaction = new DefaultTransaction();
				
				OutputObject geoObject = new OutputObject(dataStore, transaction, PTER_GEO_NAME, PTER_GEO_ID);
				OutputObject[] outputObjects = new OutputObject[] { geoObject };
				try {

					// remove previous data for the given partner
					Filter removeFilter = filterFactory.equals(
							filterFactory.property("fk_partner"),
							filterFactory.literal(partner));
					
					
					// remove geo records
					geoObject.getWriter().removeFeatures(removeFilter);
					
					transaction.commit();	
				} catch (IOException e) {
					errors++;	
					metadataHandler.logError(trace, errors, "Error removing old data", getError(e), 0);					
					transaction.rollback();					
					throw e;
				} finally {
					transaction.close();
				}
				
				// calculates total objects to import				
				int total = getImportCount();	
				
				try {
					SimpleFeature inputFeature = null;
					while( (inputFeature = readInput()) != null) {
										
						int id = nextId();	
						
						
						Transaction rowTransaction = new DefaultTransaction();
						setTransaction(outputObjects, rowTransaction);
						
						try {							
							addGeoFeature(geoObject, id, inputFeature);
							
							rowTransaction.commit();
							
							updateImportProgress(total, errors, "Importing data in " + PTER_GEO_NAME);
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
					importFinished(total, errors, "Data imported in " + PTER_GEO_NAME);
					metadataHandler.updateLogFile(trace, total, errors, true);
					
				} finally {
					closeInputReader();
				}	
			} catch (IOException e) {
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
			
	/**
	 * Adds a new geo target feature.
	 * 
	 * @param geoSchema
	 * @param geoFeatureBuilder
	 * @param geoFeatureWriter
	 * @param inputFeature
	 * @param id
	 * @throws IOException
	 */
	private void addGeoFeature(OutputObject geoObject, int id,
			SimpleFeature inputFeature) throws IOException {
		SimpleFeatureBuilder geoFeatureBuilder = geoObject.getBuilder();
		// compiles the attributes from target and read feature data
		for(AttributeDescriptor attr : geoObject.getSchema().getAttributeDescriptors()) {
			if(attr.getLocalName().equals(PTER_GEO_ID)) {
				geoFeatureBuilder.add(id);
			} else if(attr.getLocalName().equals("fk_partner")) {
				geoFeatureBuilder.add(partner+"");
			} else if(attributeMappings.containsKey(attr.getLocalName())) {
				geoFeatureBuilder.add(getMapping(inputFeature,attributeMappings, attr.getLocalName()));
			} else if(attr.getLocalName().equals("geometria")) {
				geoFeatureBuilder.add(inputFeature.getDefaultGeometry());
			}
		}
		
		SimpleFeature geoFeature = geoFeatureBuilder.buildFeature("" + id);
		geoFeature.getUserData().put(Hints.USE_PROVIDED_FID, true); 
		geoObject.getWriter().addFeatures(DataUtilities
				.collection(geoFeature));
	}
	
}
