/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.action.datamigration;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.actions.ds2ds.util.FeatureConfigurationUtil;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.datamigration.RasterMigration;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;

import java.io.File;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FilenameUtils;
import org.geotools.data.DataStore;
import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = RasterMigrationConfiguration.class)
public class RasterMigrationAction extends BaseAction<EventObject> {

    /** 
     * Path of the final directory
     */
    private String finalDirectory;

    /** 
     * Path of the input directory
     */
    private String inputDirectory;
    
    RasterMigrationConfiguration configuration = null;

    public RasterMigrationAction(final RasterMigrationConfiguration configuration) {
        super(configuration);
        this.configuration = configuration;
        this.inputDirectory = System.getProperty("SIIG_RASTERS_PATH");
        this.finalDirectory = System.getProperty("SIIG_RASTERS_PATH_FINAL");
    }

    @Override
    public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {
        listenerForwarder.setTask("Check configuration");
        checkConfiguration();
        // Creation of a List containing the event objects
        final LinkedList<EventObject> ret = new LinkedList<EventObject>();
        try {
            // Start of the operations
            listenerForwarder.started();
            // Until the events are present, the input files are elaborated
            while (!events.isEmpty()) {
                // Each event is polled from the list
                EventObject event = events.poll();
                if (event instanceof FileSystemEvent) {
                    FileSystemEvent fse = (FileSystemEvent) event;
                    File file = fse.getSource();
                    // Copy
                    doProcess(FilenameUtils.getBaseName(file.getName()));
                }
                // pass the feature config to the next action
                ret.add(new FileSystemEvent(((FileSystemEvent) event).getSource(),
                        FileSystemEventType.FILE_ADDED));
            }
            listenerForwarder.completed();
            return ret;
        } catch (Exception t) {
            listenerForwarder.failed(t);
            throw new ActionException(this, t.getMessage(), t);
        }
    }

    /**
     * Copies files from inputDirectory to the finalDirectory. The files to copy are indicated by the input String.
     * 
     * @param fileName
     * @throws ActionException
     */
    private void doProcess(String fileName) throws ActionException {
        try {
        	FeatureConfiguration sourceFeature = configuration.getSourceFeature();
			DataStore ds = FeatureConfigurationUtil
					.createDataStore(sourceFeature);
			if (ds == null) {
				throw new ActionException(this, "Can't find datastore ");
			}
			try {
				if (!(ds instanceof JDBCDataStore)) {
					throw new ActionException(this, "Bad Datastore type "
							+ ds.getClass().getName());
				}
				JDBCDataStore dataStore = (JDBCDataStore) ds;
				dataStore.setExposePrimaryKeyColumns(true);
				MetadataIngestionHandler metadataHandler = new MetadataIngestionHandler(
						dataStore);
				RasterMigration migration = new RasterMigration(fileName, inputDirectory,
	                    finalDirectory, metadataHandler, dataStore, listenerForwarder);
	            
	            migration.execute(configuration.getClosePhase(), configuration.isNewImport());
				
				
			} finally {
				ds.dispose();
			}
        	
            
        } catch (Exception ex) {
            LOGGER.error("Error in copying rasters", ex);
            throw new ActionException(this, "Error in copying rasters", ex);
        }
    }

    @Override
    public boolean checkConfiguration() {
        RasterMigrationConfiguration cfg = getConfiguration();
        if (cfg == null) {
            throw new IllegalStateException("ActionConfig is null.");
        }
        return true;
    }

}
