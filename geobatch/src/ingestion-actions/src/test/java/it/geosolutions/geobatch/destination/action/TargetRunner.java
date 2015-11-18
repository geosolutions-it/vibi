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
package it.geosolutions.geobatch.destination.action;

import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.jdbc.JDBCDataStore;
import org.geotools.referencing.CRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class TargetRunner{

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetRunner.class);
    

    public static void main(String [] args) {
        Map<String, Serializable> datastoreParams = new HashMap<String, Serializable>();
        datastoreParams.put("port", 5432);
        datastoreParams.put("schema", "siig_p");
        datastoreParams.put("passwd", "siig_p");
        datastoreParams.put("dbtype", "postgis");
        datastoreParams.put("host", "localhost");
        datastoreParams.put("Expose primary keys", "true");
        datastoreParams.put("user", "siig_p");
        datastoreParams.put("database", "destination_staging");
        
        JDBCDataStore dataStore = null;        
        MetadataIngestionHandler metadataHandler = null;
        
        try {
        	dataStore = (JDBCDataStore)DataStoreFinder.getDataStore(datastoreParams);
	        metadataHandler = new MetadataIngestionHandler(dataStore);
	        
	        TargetIngestionProcess targetIngestion = new TargetIngestionProcess("BZ_BNU-ASOTT_C_20130521_01",
	                new ProgressListenerForwarder(null), metadataHandler, dataStore);        
	        
	        
	        targetIngestion.importTarget(CRS.decode("EPSG:32632"), false);
	        
	       
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
