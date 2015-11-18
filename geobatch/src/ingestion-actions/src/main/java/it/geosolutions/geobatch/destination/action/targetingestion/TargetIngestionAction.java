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
package it.geosolutions.geobatch.destination.action.targetingestion;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;

import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = TargetIngestionConfiguration.class)
public class TargetIngestionAction extends DestinationBaseAction<TargetIngestionConfiguration> {

    

    public TargetIngestionAction(final TargetIngestionConfiguration configuration) throws IOException {
        super(configuration);        
    }

    

    @Override
	protected void doProcess(TargetIngestionConfiguration cfg,
			FeatureConfiguration featureCfg, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException {
        
    		try {
		        TargetIngestionProcess computation = new TargetIngestionProcess(
		                featureCfg.getTypeName(),
		                listenerForwarder,
		                metadataHandler,
		                dataStore);
	        
	            computation.importTarget(null, cfg.isDropInput());
	
	        } catch (IOException ex) {
	            // TODO: what shall we do here??
	            // log and rethrow for the moment, but a rollback should be implementened somewhere
	            LOGGER.error("Error in importing targets", ex);
	            throw new ActionException(this, "Error in importing targets", ex);
	        }
        
    }
}
