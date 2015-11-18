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
package it.geosolutions.geobatch.destination.action.arcingestion;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.ingestion.ArcsIngestionProcess;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.OriginalArcsIngestionProcess;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;

import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = ArcIngestionConfiguration.class)
public class ArcIngestionAction extends DestinationBaseAction<ArcIngestionConfiguration> {


    public ArcIngestionAction(final ArcIngestionConfiguration configuration) throws IOException {
        super(configuration);       
    }

	@Override
	protected void doProcess(ArcIngestionConfiguration cfg,
			FeatureConfiguration featureCfg, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException {

		try {
			if(cfg.isSegmentation()) {
				OriginalArcsIngestionProcess arcIngestion = new OriginalArcsIngestionProcess(
						featureCfg.getTypeName(), listenerForwarder,
						metadataHandler, dataStore, cfg.getLastYear(), cfg.getYears());
				arcIngestion.importArcs(null, cfg.isDropInput());
				
			} else {
				ArcsIngestionProcess arcIngestion = new ArcsIngestionProcess(
						featureCfg.getTypeName(), listenerForwarder,
						metadataHandler, dataStore);
	
				arcIngestion.importArcs(null, cfg.getAggregationLevel(),
						cfg.isOnGrid(), cfg.isDropInput(), cfg.isNewProcess(), cfg.getClosePhase());
			}
		} catch (IOException ex) {
			// TODO: what shall we do here??
			// log and rethrow for the moment, but a rollback should be
			// implementened somewhere
			LOGGER.error("Error in importing arcs", ex);
			throw new ActionException(this, "Error in importing arcs", ex);
		}
	}
	
}
