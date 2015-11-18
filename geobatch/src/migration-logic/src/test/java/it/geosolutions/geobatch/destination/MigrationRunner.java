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
package it.geosolutions.geobatch.destination;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;
import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.destination.datamigration.ProductionUpdater;
import it.geosolutions.geobatch.destination.datamigration.RasterMigration;
import it.geosolutions.geobatch.destination.datamigration.UpdaterFeatures;
import it.geosolutions.geobatch.destination.datamigration.UpdaterFeatures.UpdaterFeature;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.jdbc.JDBCDataStore;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class MigrationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationRunner.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, Serializable> datastoreParams = new HashMap<String, Serializable>();
        datastoreParams.put("port", 5432);
        datastoreParams.put("schema", "siig_p");
        datastoreParams.put("passwd", "siig_p");
        datastoreParams.put("dbtype", "postgis");
        datastoreParams.put("host", "192.168.1.31");
        datastoreParams.put("Expose primary keys", "true");
        datastoreParams.put("user", "siig_p");
        datastoreParams.put("database", "lose_ingestion");
        
        Map<String, Serializable> outDatastoreParams = new HashMap<String, Serializable>();
        outDatastoreParams.put("port", 5432);
        outDatastoreParams.put("schema", "siig_p");
        outDatastoreParams.put("passwd", "siig_p");
        outDatastoreParams.put("dbtype", "postgis");
        outDatastoreParams.put("host", "192.168.1.31");
        outDatastoreParams.put("Expose primary keys", "true");
        outDatastoreParams.put("user", "siig_p");
        outDatastoreParams.put("database", "lose_production");
        
        Ds2dsConfiguration cfg = new Ds2dsConfiguration("miragte", "migrate", "migrate");
        
        try {
			CoordinateReferenceSystem crs = CRS.decode("EPSG:3003");
	        FeatureConfiguration outputFeatureCfg = new FeatureConfiguration();
	        outputFeatureCfg.setCoordinateReferenceSystem(crs);
	        outputFeatureCfg.setDataStore(outDatastoreParams);
	        cfg.setOutputFeature(outputFeatureCfg);
	        FeatureConfiguration inputFeatureCfg = new FeatureConfiguration();
	        inputFeatureCfg.setCoordinateReferenceSystem(crs);
	        inputFeatureCfg.setDataStore(datastoreParams);
	        cfg.setSourceFeature(inputFeatureCfg);
        
        } catch (NoSuchAuthorityCodeException e1) {
			LOGGER.error(e1.getMessage(), e1);
		} catch (FactoryException e1) {
			LOGGER.error(e1.getMessage(), e1);
		}
        JDBCDataStore dataStore = null;        
        MetadataIngestionHandler metadataHandler = null;
        try {
        	/*
        	String inputFeature = "BZ_BNU-AAGR_C_20130904_02";
        	//String inputFeature = "AO_C_Grafo_20130704";
        	*/
        	dataStore = (JDBCDataStore)DataStoreFinder.getDataStore(datastoreParams);	        
	        metadataHandler = new MetadataIngestionHandler(dataStore);
	        ProductionUpdater updater = new ProductionUpdater("LU_20141201", 
	                new ProgressListenerForwarder(null), metadataHandler, dataStore);
	        updater.setFilterByTarget(false);
	        updater.setRemoveFeatures(true);
	        updater.setDs2DsConfiguration(cfg);
	        UpdaterFeatures updaterFeatures  = UpdaterFeatures.fromXML(MigrationRunner.class.getClassLoader().getResourceAsStream("datamigration.xml"));
			UpdaterFeatures targetFeature = new UpdaterFeatures();
			UpdaterFeatures arcFeature = new UpdaterFeatures();
			
			for(UpdaterFeature f : updaterFeatures.getFeatures()){
				if(f.getTarget()!=null && f.getTarget()){
					targetFeature.getFeatures().add(f);
				} else {
					arcFeature.getFeatures().add(f);
				}
			}
	        /*Map<String, Serializable> attributeMappings = new HashMap<String, Serializable>();
	        attributeMappings.put("idgeo_bers_non_umano_pl", "#{idgeo_bers_non_umano_pl + 1867753}");
	        attributeMappings.put("fk_bers_non_umano_pl", "#{fk_bers_non_umano_pl == null ? null : (fk_bers_non_umano_pl + 1867753)}");	        	       
	        
	        attributeMappings.put("idgeo_bersaglio_umano_pl", "#{idgeo_bersaglio_umano_pl + 1207276}");
	        attributeMappings.put("fk_bersaglio_umano_pl", "#{fk_bersaglio_umano_pl == null ? null : (fk_bersaglio_umano_pl + 1207276)}");
	        
	        attributeMappings.put("idgeo_bersaglio_umano_pt", "#{idgeo_bersaglio_umano_pt + 257996}");
	        attributeMappings.put("fk_bersaglio_umano_pt", "#{fk_bersaglio_umano_pt == null ? null : (fk_bersaglio_umano_pt + 257996)}");
	        
	        cfg.setAttributeMappings(attributeMappings);*/
	        /*updater.executeTarget(targetFeature);
	        updater.executeArc(arcFeature);*/
			//updater.execute(null, true);
	        
	        RasterMigration rasterMig = new RasterMigration("LU_20141201", "J:\\Develop\\destination\\rasters_out", "J:\\Develop\\destination\\rasters_prod_lose", metadataHandler, dataStore, null);
	        // Copying file
	        rasterMig.execute("C", false);
	        
        } catch(Exception e) {
        	LOGGER.error(e.getMessage());
        } finally {
        	if(metadataHandler != null) {
        		metadataHandler.dispose();
        	}
        	
        	if(dataStore != null) {
        		dataStore.dispose();
        	}        	
        }
		
		
		
	}

}
