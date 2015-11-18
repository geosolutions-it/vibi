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

import it.geosolutions.geobatch.destination.common.ImportType;
import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.common.OutputObject;
import it.geosolutions.geobatch.destination.common.utils.DbUtils;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Query;
import org.geotools.data.Transaction;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;


/**
 * Handle the vector targets ingestion processes.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class TargetIngestionProcess extends InputObject {
		
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TargetIngestionProcess.class);

        private static final double POINTS_BUFFER = 20;

        private static final double LINES_BUFFER = 1;
	
	private static Pattern typeNameParts  = Pattern.compile("^([A-Z]{2})[_-]([A-Z]{2,3})[_-]([A-Z]+)([_-][C|I])?[_-]([0-9]{8})[_-]([0-9]{2})$");
	
	private int partner;
	private String codicePartner;
	private String targetMacroType;
	private int targetType;
	private String date;
	private String geometryType;
	private ImportType importType;
	
	private static Properties targetTypes = new Properties();
	private static Properties geometryTypes = new Properties();						
	
	private static Map attributeMappings = null;
	
	private static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
	
	String geoSuffix;
	String geoTypeName;
	String geoId;
	String fkGeoId;

	String outTypeName;
		
	
	static {	
		// load mappings from resources
		try {			
			targetTypes.load(TargetIngestionProcess.class.getResourceAsStream("/targets.properties"));	
			geometryTypes.load(TargetIngestionProcess.class.getResourceAsStream("/geometries.properties"));
			attributeMappings = (Map) readResourceFromXML("/targets.xml");							
		} catch (IOException e) {
			LOGGER.error("Unable to load configuration: "+e.getMessage(), e);
		}
	}
	
	
	/**
	 * Initializes a VectorTarget handler for the given input feature.
	 * 
	 * @param inputTypeName
	 */
	public TargetIngestionProcess(String inputTypeName,
			ProgressListenerForwarder listenerForwarder,
			MetadataIngestionHandler metadataHandler, DataStore dataStore) {
		super(inputTypeName, listenerForwarder, metadataHandler, dataStore);		
	}



	/**
	 * Parse input feature typeName and extract useful information from it. 
	 */
	@Override
	protected boolean parseTypeName(String inputTypeName) {
		Matcher m = typeNameParts.matcher(inputTypeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			// target macro type (bu or bnu)
			targetMacroType = m.group(2);
			// target detailed type id (from siig_t_bersaglio)
			targetType = Integer.parseInt(targetTypes.get(m.group(3)).toString());
			// import type (completa / integrativa)
			if(m.group(4) != null) {
				importType = m.group(4).equalsIgnoreCase("I") ? ImportType.INTEGRATIVA : ImportType.COMPLETA;
			} else {
				importType = ImportType.COMPLETA;
			}
			// file date identifier
			date = m.group(5);
			// geometry type (pt, pl or ln)
			geometryType = geometryTypes.get(m.group(6)).toString();			
			
			// final part of the target geo table name (siig_geo_<suffix>) 
			//geoSuffix = (targetMacroType.equals("BU") ? "bersaglio_umano" : "bers_non_umano") + "_" + geometryType;
			geoSuffix = (targetMacroType.equals("BU") ? "bersaglio_umano" : "bers_non_umano") + "_pl";
			// target geo table name (siig_geo_<suffix>)
			geoTypeName = "siig_geo_" + geoSuffix;
			// target geo id name (idgeo_<suffix>)
			geoId = "idgeo_" + geoSuffix;
			// target non-geo table name (siig_t_bersaglio_<non geo suffix>)
			outTypeName = "siig_t_bersaglio_" + (targetMacroType.equals("BU") ? "umano" : "non_umano");
			// target non-geo to geo foreign key name
			fkGeoId = "fk_" + geoSuffix;
			
			return true;
		}
		return false;
	}

	/**
	 * Imports the target feature from the original Feature to the SIIG
	 * targets tables (in staging).
	 * 
	 * @param datastoreParams
	 * @param crs
	 * @throws IOException
	 */
	public void importTarget(CoordinateReferenceSystem crs, boolean dropInput) throws IOException {
		
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
				trace = logFile(process, targetType,
						partner, codicePartner, date, false);

				// setup input reader								
				createInputReader(dataStore, Transaction.AUTO_COMMIT, null);	
				
									
				// is it an update (alternative geo shapefile for an already imported one)
				boolean update =isAnUpdate(dataStore, Transaction.AUTO_COMMIT);
				
				// is it an update with new records (currently acque superficiali supports this
				// modality)
				boolean hasMoreData = update && canHaveMoreData(targetType);									
				
				Transaction transaction = new DefaultTransaction();
				
				OutputObject geoObject = new OutputObject(dataStore, transaction, geoTypeName, geoId);
				OutputObject nonGeoObject = new OutputObject(dataStore, transaction, outTypeName, "");
							
				OutputObject[] outputObjects = new OutputObject[] {nonGeoObject, geoObject};
				
				//BigDecimal maxId = null;
				
				try {

					// remove previous data for the given partner - target couple
					Filter removeFilter = filterFactory.and(
						filterFactory.equals(
							filterFactory.property("id_bersaglio"), filterFactory.literal(targetType)
						),
						filterFactory.equals(
							filterFactory.property("id_partner"), filterFactory.literal(partner)
						)
					);
					if(!update) {
						// first shapefile of a series, we can remove all previous data for the
						// target - partner couple
						nonGeoObject.getWriter().removeFeatures(removeFilter);
					} else {
						// on update we only reset the foreign key column
					        // not needed anymore
						// nonGeoObject.getWriter().modifyFeatures(nonGeoObject.getSchema().getDescriptor(fkGeoId).getName(), null, removeFilter);
					}						
					
					if(hasMoreData) {
						// remove records with no more geometry bound
						String otherGeo = geometryType.equals("pl") ? "ln" : "pl";
						Filter extraRemoveFilter = filterFactory.and(
							removeFilter,
							filterFactory.isNull(
								filterFactory.property("fk_bers_non_umano_" + otherGeo)
							)
							
						); 
						// not needed anymore
						// nonGeoObject.getWriter().removeFeatures(extraRemoveFilter);
					} else {
        					// remove geo records
        					geoObject.getWriter().removeFeatures(removeFilter);
					}
					
					//maxId = (BigDecimal)getOutputId(geoObject);
					
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
						int idTematico = normalizeIdTematico(getMapping(inputFeature, (Map)attributeMappings.get(targetType), "id_tematico"));
						
						Transaction rowTransaction = new DefaultTransaction();
						setTransaction(outputObjects, rowTransaction);
						
						try {	
						        if(hasMoreData) {
						            boolean added = addOrUpdateGeoFeature(nonGeoObject, geoObject, id, inputFeature);
						            if(added) {
						                addOrUpdateFeature(nonGeoObject, id, inputFeature);
						            }
						        } else {
						            addGeoFeature(geoObject, id, inputFeature);
							
        						    if(update) {
        							updateFeature(nonGeoObject, id, inputFeature);
        						    } else {
        							addFeature(nonGeoObject, id, inputFeature);
        						    }
						        }
							
							rowTransaction.commit();
							
							updateImportProgress(total, errors, "Importing data in " + geoTypeName + "/" + outTypeName);
						} catch(Exception e) {						
							errors++;
							rowTransaction.rollback();
							metadataHandler.logError(trace, errors,
									"Error writing output feature", getError(e),
									idTematico);
						} finally {				
							rowTransaction.close();							
						}
																
					}
					importFinished(total, errors, "Data imported in " + geoTypeName + "/" + outTypeName);
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
         * Add a new record or update the existing one for non-geo table 
         *  
         * @param targetType
         * @param partner
         * @param geometryType
         * @param fkGeoId
         * @param schema
         * @param featureBuilder
         * @param featureWriter
         * @param id
         * @param inputFeature
         * @throws IOException
         */
        private boolean addOrUpdateGeoFeature(OutputObject object, OutputObject geoObject,
                        int id, SimpleFeature inputFeature) throws IOException {
                Map<String,String> mappings = (Map<String,String>)attributeMappings.get(targetType);
                
                // key filter
                Filter filter = filterFactory.and(
                                filterFactory.and(
                                        filterFactory.equals(
                                                filterFactory.property("id_tematico"),
                                                filterFactory.literal(getMapping(inputFeature, mappings, "id_tematico"))
                                        ),
                                        filterFactory.equals(
                                                filterFactory.property("id_bersaglio"),
                                                filterFactory.literal(targetType)
                                        )
                                ),                              
                                filterFactory.equals(
                                        filterFactory.property("id_partner"),
                                        filterFactory.literal(partner)
                                )
                );
                Query existing = new Query(object.getSchema().getName().getLocalPart(), filter);
                FeatureCollection<SimpleFeatureType, SimpleFeature> features = object.getWriter().getFeatures(existing);
                if(features.size() > 0) {
                        FeatureIterator<SimpleFeature> iterator = features.features();
                        Number geoId = (Number)iterator.next().getAttribute(fkGeoId);
                        try {
                            return updateGeoFeature(geoObject, id, geoId.intValue(), inputFeature);
                        } finally {
                            iterator.close();
                        }
                } else {
                        addGeoFeature(geoObject, id, inputFeature);
                        return true;
                }               
        }
	
	
	private boolean updateGeoFeature(OutputObject object, int id,  int geoIdValue, SimpleFeature inputFeature) throws IOException {
	    Filter filter = filterFactory.equals(
                    filterFactory.property(geoId),
                    filterFactory.literal(geoIdValue)
            );
	    FeatureCollection<SimpleFeatureType, SimpleFeature> features = object.getReader().getFeatures(filter);
	    if(features.size() > 0) {
	        Geometry geometry = null;
	        FeatureIterator<SimpleFeature> iterator = features.features();
	        try {
                    geometry = (Geometry)iterator.next().getAttribute("geometria");
                    Geometry newGeometry = (Geometry)inputFeature.getDefaultGeometry();
                    if(geometry == null) {
                        geometry = newGeometry;
                    } else if(newGeometry != null) {
                        geometry = geometry.union(transformGeometry(newGeometry, geometryType));
                    }
                    object.getWriter().modifyFeatures(object.getSchema().getDescriptor("geometria").getName(), geometry, filter);
	        } finally {
	            iterator.close();
	        }
	        return false;
	    } else {
	        addGeoFeature(object, id, inputFeature);
	        return true;
	    }
        
        }



    /**
	 * Add a new record or update the existing one for non-geo table 
	 *  
	 * @param targetType
	 * @param partner
	 * @param geometryType
	 * @param fkGeoId
	 * @param schema
	 * @param featureBuilder
	 * @param featureWriter
	 * @param id
	 * @param inputFeature
	 * @throws IOException
	 */
	private void addOrUpdateFeature(OutputObject object,
			int id, SimpleFeature inputFeature) throws IOException {
		Map<String,String> mappings = (Map<String,String>)attributeMappings.get(targetType);
		
		// key filter
		Filter filter = filterFactory.and(
				filterFactory.and(
					filterFactory.equals(
						filterFactory.property("id_tematico"),
						filterFactory.literal(getMapping(inputFeature, mappings, "id_tematico"))
					),
					filterFactory.equals(
						filterFactory.property("id_bersaglio"),
						filterFactory.literal(targetType)
					)
				),				
				filterFactory.equals(
					filterFactory.property("id_partner"),
					filterFactory.literal(partner)
				)
		);
		Query existing = new Query(object.getSchema().getName().getLocalPart(), filter);
		if(object.getWriter().getCount(existing) > 0) {
			updateFeature(object, id, inputFeature);
		} else {
			addFeature(object, id, inputFeature);
		}		
	}


	/**
	 * Update the non-geo table with foreign keys of the imported geos.
	 * 
	 * @param targetType
	 * @param partner
	 * @param geometryType
	 * @param fkGeoId
	 * @param schema
	 * @param featureBuilder
	 * @param featureWriter
	 * @param id
	 * @param inputFeature
	 * @throws IOException
	 */
	private void updateFeature(OutputObject object,
			int id, SimpleFeature inputFeature) throws IOException {
		Map<String,String> mappings = (Map<String,String>)attributeMappings.get(targetType);
		
		// key filter
		Filter filter = filterFactory.and(
				filterFactory.and(
					filterFactory.equals(
						filterFactory.property("id_tematico"),
						filterFactory.literal(getMapping(inputFeature, mappings, "id_tematico"))
					),
					filterFactory.equals(
						filterFactory.property("id_bersaglio"),
						filterFactory.literal(targetType)
					)
				),				
				filterFactory.equals(
					filterFactory.property("id_partner"),
					filterFactory.literal(partner)
				)
		);
		
		object.getWriter().modifyFeatures(object.getSchema().getDescriptor(fkGeoId).getName(), id, filter);			
	}



	



	/**
	 * Checks if the current import is an update, so we only need
	 * to import geo data and insert foreign keys in non-geo table.
	 * 
	 * @param dataStore
	 * @param transaction
	 * @return
	 * @throws IOException
	 */
	private boolean isAnUpdate(DataStore dataStore, Transaction transaction) throws IOException {		
		// targets having multi geometry types
		if(hasAlternativeGeo(targetType)) {					
			String alternativeGeo = ""; 
			// this is a particular case, we can have records to insert
			if(canHaveMoreData(targetType)) {
				if(geometryType.equals("pl")) {
					alternativeGeo = "03";
				} else {
					alternativeGeo = "02";
				}
			} else {
				if(geometryType.equals("pl")) {
					alternativeGeo = "01";
				} else {
					alternativeGeo = "02";
				}
			}
			// check if an import for the other existing geo has already been executed
			String alternativeTypeName = getAlternativeTypeName(alternativeGeo);
			try {
				if(dataStore instanceof JDBCDataStore){
					return !DbUtils.executeScalar((JDBCDataStore)dataStore, transaction, "SELECT COUNT(*) FROM siig_t_tracciamento where nome_file='"+alternativeTypeName+"'").equals(new Long(0));
				}
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}
		return false;
	}



	



	/**
	 * The target can have different records in the various alternative geos.
	 * 
	 * @param targetType
	 * @return
	 */
	private boolean canHaveMoreData(int targetType) {		
		return targetType == 15;
	}



	/**
	 * The target has multiple alternative geometries
	 * @param targetType
	 * @return
	 */
	private boolean hasAlternativeGeo(int targetType) {
	    return targetType == 15;
	    //return targetType == 4 || targetType == 6 || targetType >= 14;
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
			if(attr.getLocalName().equals(geoId)) {
				geoFeatureBuilder.add(id);
			}
			if(attr.getLocalName().equals("id_bersaglio")) {
				geoFeatureBuilder.add(targetType);
			}
			if(attr.getLocalName().equals("id_partner")) {
				geoFeatureBuilder.add(partner+"");
			}
			if(attr.getLocalName().equals("geometria")) {
				geoFeatureBuilder.add(transformGeometry((Geometry)inputFeature.getDefaultGeometry(), geometryType));
			}
		}
		
		SimpleFeature geoFeature = geoFeatureBuilder.buildFeature("" + id);
		geoFeature.getUserData().put(Hints.USE_PROVIDED_FID, true); 
		geoObject.getWriter().addFeatures(DataUtilities
				.collection(geoFeature));
	}
	
	private Geometry transformGeometry(Geometry geometry, String type) {
            if(type.equalsIgnoreCase("pt")) {
                return geometry.buffer(POINTS_BUFFER);
            } else if(type.equalsIgnoreCase("ln")) {
                return geometry.buffer(LINES_BUFFER);
            } else {
                return geometry;
            }
        }



    /**
	 * Adds a new non-geo feature.
	 * 
	 * @param targetType
	 * @param partner
	 * @param geometryTypeName
	 * @param geoId
	 * @param schema
	 * @param featureBuilder
	 * @param featureWriter
	 * @param id
	 * @param inputFeature
	 * @throws IOException
	 */
	private void addFeature(OutputObject object, int id, SimpleFeature inputFeature)
			throws IOException {
		Map<String,String> mappings = (Map<String,String>)attributeMappings.get(targetType);
		SimpleFeatureBuilder featureBuilder = object.getBuilder();
		// compiles the attributes from target and read feature data, using mappings
		// to match input attributes with output ones
		for(AttributeDescriptor attr : object.getSchema().getAttributeDescriptors()) {
			if(attr.getLocalName().equals(fkGeoId)) {
				featureBuilder.add(id);
			} else if(attr.getLocalName().equals("id_bersaglio")) {
				featureBuilder.add(targetType);
			} else if(attr.getLocalName().equals("id_partner")) {
				featureBuilder.add(partner+"");
			} else if(mappings.containsKey(attr.getLocalName())) {
				featureBuilder.add(getMapping(inputFeature,mappings, attr.getLocalName()));
			} else {
				featureBuilder.add(null);
			}
		}
		
		// compiles the fid to be sure the feature is correctly inserted
		// the fid should be "id_tematico.id_bersaglio.id_partner"
		String featureid = normalizeIdTematico(getMapping(inputFeature, mappings, "id_tematico")) + "." + targetType + "." + partner;
		SimpleFeature feature = featureBuilder.buildFeature(featureid);
		feature.getUserData().put(Hints.USE_PROVIDED_FID, true);
		object.getWriter().addFeatures(DataUtilities
				.collection(feature));		
	}



	/**
	 * @param attribute
	 * @return
	 */
	private int normalizeIdTematico(Object attribute) {
		if(attribute == null) {
			return 0;
		}
		if(attribute instanceof Number) {
			return ((Number)attribute).intValue();
		}
		return Integer.parseInt(attribute.toString());
	}
}
