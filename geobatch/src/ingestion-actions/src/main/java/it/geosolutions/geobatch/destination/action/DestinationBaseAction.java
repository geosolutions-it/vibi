package it.geosolutions.geobatch.destination.action;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.actions.ds2ds.util.FeatureConfigurationUtil;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FilenameUtils;
import org.geotools.data.DataStore;
import org.geotools.jdbc.JDBCDataStore;

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
/**
 * Basic action behaviours for all destination project actions.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public abstract class DestinationBaseAction<T extends ActionConfiguration> extends BaseAction<EventObject> {
	
	protected final T configuration;
	
	public DestinationBaseAction(final T configuration) throws IOException {
        super(configuration);       
        this.configuration = configuration;
    }

	@Override
    @CheckConfiguration
    public boolean checkConfiguration() {
        if(getConfiguration().isFailIgnored()) {
            LOGGER.warn("FailIgnored is true. This is a multi-step action, and can't proceed when errors are encountered");
            return false;
        }

        return true;
    }

    protected void checkInit() {
        if (getConfiguration() == null) {
            throw new IllegalStateException("ActionConfig is null.");
        }
    }
    
    public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {
        listenerForwarder.setTask("Check config");
        checkInit();
        
        final LinkedList<EventObject> ret = new LinkedList<EventObject>();
        try {
			listenerForwarder.started();
			while (!events.isEmpty()) {
				EventObject event = events.poll();
				File file = null;
				if (event instanceof FileSystemEvent) {
					FileSystemEvent fse = (FileSystemEvent) event;
					file = fse.getSource();					
				}
				FeatureConfiguration featureConfiguration = unwrapFeatureConfig(event);
				DataStore ds = FeatureConfigurationUtil
						.createDataStore(featureConfiguration);
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
					doProcess((T) getConfiguration(), featureConfiguration,
							dataStore, metadataHandler, file);

					// pass the feature config to the next action
					ret.add(createOutputEvent(event));
				} finally {
					ds.dispose();
				}
			}
			listenerForwarder.completed();
	        return ret;
        } catch (Exception t) {
			listenerForwarder.failed(t);
			throw new ActionException(this, t.getMessage(), t);
		}
    }

	/**
	 * @param event
	 * @return
	 */
	protected FileSystemEvent createOutputEvent(EventObject event) {
		return new FileSystemEvent(((FileSystemEvent) event)
				.getSource(), FileSystemEventType.FILE_ADDED);
	}

    private FeatureConfiguration unwrapFeatureConfig(EventObject event) throws ActionException {
        if (event instanceof FileSystemEvent) {
            FileSystemEvent fse = (FileSystemEvent) event;
            File file = fse.getSource();
            try {
                return FeatureConfiguration.fromXML(new FileInputStream(file));
            } catch (FileNotFoundException ex) {
                throw new ActionException(this, ex.getMessage(), ex);
            }
        } else {
            throw new ActionException(this, "EventObject not handled " + event);
        }               
    }
    
	/**
	 * @param configuration
	 * @param featureConfiguration
	 */
	protected abstract void doProcess(T configuration,
			FeatureConfiguration featureConfiguration, JDBCDataStore ds,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException;
}
