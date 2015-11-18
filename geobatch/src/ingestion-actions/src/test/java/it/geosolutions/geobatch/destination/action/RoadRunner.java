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

import it.geosolutions.geobatch.destination.ingestion.ArcsIngestionProcess;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.OriginalArcsIngestionProcess;
import it.geosolutions.geobatch.destination.streetuser.StreetUserComputation;
import it.geosolutions.geobatch.destination.vulnerability.RiskComputation;
import it.geosolutions.geobatch.destination.vulnerability.TargetManager.TargetInfo;
import it.geosolutions.geobatch.destination.vulnerability.VulnerabilityComputation;
import it.geosolutions.geobatch.destination.vulnerability.VulnerabilityEnvironment;
import it.geosolutions.geobatch.destination.vulnerability.VulnerabilityUtils;
import it.geosolutions.geobatch.destination.zeroremoval.ZeroRemovalComputation;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;
import it.geosolutions.geobatch.settings.jai.JAISettings;
import it.geosolutions.jaiext.scheduler.JAIExtTileScheduler;

import java.awt.image.RenderedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.TileScheduler;

import org.geotools.data.DataStoreFinder;
import org.geotools.filter.function.RangedClassifier;
import org.geotools.jdbc.JDBCDataStore;
import org.geotools.resources.image.ImageUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class RoadRunner{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoadRunner.class);
    
    static {
    	JAISettings jai=new JAISettings();
    	// Setting up TileScheduler
        TileScheduler jaiScheduler = new JAIExtTileScheduler(); 
        
        jaiScheduler.setParallelism(jai.getTileThreads());
        jaiScheduler.setPrefetchParallelism(jai.getTileThreads());
        jaiScheduler.setPriority(jai.getTilePriority());
        jaiScheduler.setPrefetchPriority(jai.getTilePriority());

        JAI.getDefaultInstance().setTileScheduler(jaiScheduler);
    }

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
        ProgressListenerForwarder listenerForwarder = new ProgressListenerForwarder(null);
		try {
        	
        	//String inputFeature = "RL_C_Grafo_20131126";
        	//String inputFeature = "RP_C_Grafo_20131212";
        	//String inputFeature = "TI_C_Grafo_20140124";
        	String inputFeature = "AO_C_padr_20140108";
        	
		//String inputFeature = "RL_C_Grafo_20131126";
        	
        	dataStore = (JDBCDataStore)DataStoreFinder.getDataStore(datastoreParams);	        
	        metadataHandler = new MetadataIngestionHandler(dataStore);
	        /*OriginalArcsIngestionProcess arcIngestion = new OriginalArcsIngestionProcess(inputFeature,
	                new ProgressListenerForwarder(null), metadataHandler, dataStore, 2012, 5);
	        arcIngestion.importArcs(null, false);*/
	        ArcsIngestionProcess arcIngestion = new ArcsIngestionProcess(inputFeature,
	                listenerForwarder, metadataHandler, dataStore);
	        
	        /*arcIngestion.updateArcs(null, 2, false, false, false, null);
	        arcIngestion.updateArcs(null, 3, false, false, false, null);
	        arcIngestion.updateArcs(null, 3, true, false, false, null);*/
	        
	        
	        arcIngestion.importArcs(null, 1, false, false, true, null);	        
	        arcIngestion.importArcs(null, 2, false, false, true, null);	        
	        arcIngestion.importArcs(null, 3, false, false, true, null);
	        arcIngestion.importArcs(null, 3, true, false, true, "A");
	        arcIngestion.importArcs(null, 4, true, false, true, "A");
	        arcIngestion.importArcs(null, 5, true, false, true, "A");

            // Spalmatore
			ZeroRemovalComputation zeroComputation = new ZeroRemovalComputation(
					inputFeature, listenerForwarder,
					metadataHandler, dataStore);
	        
	        
	        /*zeroComputation.removeZeros(null, 1, null);
	        zeroComputation.removeZeros(null, 2, null);
	        zeroComputation.removeZeros(null, 3, null);*/
	        
	        JAI.getDefaultInstance().getTileCache().setMemoryCapacity(1024*1024*512);
	        
	        VulnerabilityComputation vulnerability = new VulnerabilityComputation(inputFeature, 
	        		listenerForwarder, metadataHandler, dataStore);
	        
	        // Initial operations on the input Rasters
            //
            // 1. Merging of the rasters into 2 images, human and notHuman
            //
            // 2. Saving of the indexes that link each image band to the related target

            Map<Integer, TargetInfo> bandPerTargetH = new TreeMap<Integer, TargetInfo>();
            Map<Integer, TargetInfo> bandPerTargetNH = new TreeMap<Integer, TargetInfo>();

            //RenderedImage[] images = vulnerability.rasterCalculation(bandPerTargetH,
            //        bandPerTargetNH);
            
            // Setting of the JAI memory capacity to 512 Mbyte
            JAI.getDefaultInstance().getTileCache().setMemoryCapacity(512 * 1024 * 1024);
            
            // Aggregation level 1 or 2
           
            // Selection of the X and Y block number used for creating Nx*Ny parallel threads each one calculates
            // vulnerability on its block
            int numXBlocks = 2;
            int numYBlocks = 2;
            
            // Operation called from the Vulnerability Environment object
            /*new VulnerabilityEnvironment(inputFeature, listenerForwarder, metadataHandler, dataStore).computeLevel12(null,
                    numXBlocks, numYBlocks,
                    images, bandPerTargetNH, bandPerTargetH,
                    "PURGE_INSERT", 1, false,
                    null, null, null, null,
                    null,null, true);*/
            
            /*new VulnerabilityEnvironment(listenerForwarder).computeLevel12(null,
                    numXBlocks, numYBlocks, inputFeature, dataStore,
                    metadataHandler, images, bandPerTargetNH, bandPerTargetH,
                    "PURGE_INSERT", 2, false,
                    null, null, null, null,
                    null,null);*/
            // Aggregation level 3
            
            // Selection of the thread number, used for dividing the input cells into N group, each one for one thread
            int threadMaxNumber = 1;
            // Group division of the input cells
            /*RangedClassifier groups = VulnerabilityUtils.computeIntervals(vulnerability,
                    threadMaxNumber, 4, null, false);
            // Operation called from the Vulnerability Environment object
            new VulnerabilityEnvironment(inputFeature, listenerForwarder, metadataHandler, dataStore).computeLevelGrid(4, null,
                    threadMaxNumber, groups, images, bandPerTargetNH, bandPerTargetH,
                    "PURGE_INSERT", false,null, false);
            */

            // Image Disposal
            //ImageUtilities.disposePlanarImageChain(PlanarImage.wrapRenderedImage(images[0]));
            // Image Disposal
            //ImageUtilities.disposePlanarImageChain(PlanarImage.wrapRenderedImage(images[1]));

			
	        RiskComputation riskComputation = new RiskComputation(
	        		inputFeature,
					listenerForwarder,
					metadataHandler, dataStore);
	    	
	        
	        //riskComputation.prefetchRiskAtLevel(15, 1, 1, 26, 100, "1,2,3,4,5,6,7,8,9,10,11,12", "1,2,3,4,5,6,7,8,9,10,11,12,13,14", "0,1", "1,2,3,4,5", "fp_scen_centrale", "PURGE_INSERT", null, false, false);
	        //riskComputation.prefetchRiskAtLevel(15, 2, 1, 26, 100, "1,2,3,4,5,6,7,8,9,10,11,12", "1,2,3,4,5,6,7,8,9,10,11,12,13,14", "0,1", "1,2,3,4,5", "fp_scen_centrale", "PURGE_INSERT", null, false);
	        //riskComputation.prefetchRiskAtLevel(4, 3, 1, 26, 100, "1,2,3,4,5,6,7,8,9,10,11,12", "1,2,3,4,5,6,7,8,9,10,11,12,13,14", "0,1", "1,2,3,4,5", "fp_scen_centrale", "PURGE_INSERT", "B", false, false);
	        //riskComputation.prefetchRiskAtLevel(4, 4, 1, 26, 100, "1,2,3,4,5,6,7,8,9,10,11,12", "1,2,3,4,5,6,7,8,9,10,11,12,13,14", "0,1", "1,2,3,4,5", "fp_scen_centrale", "PURGE_INSERT", "B", false, false);
	        
	        
	        StreetUserComputation streetUserComputation = new StreetUserComputation(inputFeature,
					listenerForwarder,
					metadataHandler,
					dataStore);
	        //streetUserComputation.setStartOriginId(832268);
	        //streetUserComputation.setRemoveFeatures(false);
	        //streetUserComputation.setSorted(true);
	        //streetUserComputation.execute(1, false, null, false);
	        //streetUserComputation.execute(2, false, null);
	        //streetUserComputation.execute(3, false, null, false);
	        //streetUserComputation.execute(4, false, null, false);
        } catch(Exception e) {
        	LOGGER.error(e.getMessage(), e);
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
