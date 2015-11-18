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
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.destination.datamigration.ProductionUpdater;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FilenameUtils;
import org.geotools.data.DataStore;
import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = MigrationConfiguration.class)
public class MigrationAction extends BaseAction<EventObject> {

	private MigrationConfiguration configuration;

	public MigrationAction(final MigrationConfiguration configuration) throws IOException {
		super(configuration);
		this.configuration = configuration;
	}

	@Override
	@CheckConfiguration
	public boolean checkConfiguration() {
		return true;
	}

	private void checkInit() {
		MigrationConfiguration cfg = getConfiguration();
		if (cfg == null) {
			throw new IllegalStateException("ActionConfig is null.");
		}
	}

	/**
	 *
	 */
	public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {

		listenerForwarder.setTask("Check config");
		checkInit();
		final LinkedList<EventObject> ret = new LinkedList<EventObject>();
		try {
			listenerForwarder.started();
			while (!events.isEmpty()) {
				EventObject event = events.poll();
				if (event instanceof FileSystemEvent) {
					FileSystemEvent fse = (FileSystemEvent) event;
					File file = fse.getSource();
					doProcess(configuration,FilenameUtils.getBaseName(file.getName()));
				}
				
				// pass the feature config to the next action
				ret.add(new FileSystemEvent(((FileSystemEvent)event).getSource(), FileSystemEventType.FILE_ADDED));
			}
			listenerForwarder.completed();
			return ret;
		} catch (Exception t) {
			listenerForwarder.failed(t);
			throw new ActionException(this, t.getMessage(), t);
		}
	}

	private void doProcess(MigrationConfiguration cfg, String typeName) throws ActionException {
		FeatureConfiguration sourceFeature = cfg.getSourceFeature();
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
			ProductionUpdater updater = new ProductionUpdater(typeName, 
					listenerForwarder, metadataHandler, dataStore);
			updater.setDs2DsConfiguration(cfg);    
			updater.setFilterByTarget(cfg.isFilterByTarget());
			updater.execute(cfg.getClosePhase(), cfg.isNewImport());
		} catch (Exception ex) {
			LOGGER.error("Error in importing arcs", ex);
			throw new ActionException(this, "Error in importing arcs", ex);
		} finally {

		}

	}

}
