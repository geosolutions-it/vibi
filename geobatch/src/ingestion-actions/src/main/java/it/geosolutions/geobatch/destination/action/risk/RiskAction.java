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
package it.geosolutions.geobatch.destination.action.risk;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.vulnerability.RiskComputation;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;

import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = RiskConfiguration.class)
public class RiskAction extends DestinationBaseAction<RiskConfiguration> {

    public RiskAction(final RiskConfiguration configuration) throws IOException {
        super(configuration);
    }

    @Override
	protected void doProcess(RiskConfiguration cfg,
			FeatureConfiguration featureCfg, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException {
        
        try {
	        
	        RiskComputation computation = new RiskComputation(
	                featureCfg.getTypeName(),
	                listenerForwarder,
	                metadataHandler,
	                dataStore);
        
            computation.prefetchRiskAtLevel(
                    cfg.getPrecision(),
                    cfg.getAggregationLevel(),
                    cfg.getProcessing(),
                    cfg.getFormula(),
                    cfg.getTarget(),
                    cfg.getMaterials(),
                    cfg.getScenarios(),
                    cfg.getEntities(),
                    cfg.getSevereness(),
                    cfg.getFpfield(),
                    cfg.getWriteMode(),
                    cfg.getClosePhase(),
                    cfg.isDropInput(),
                    cfg.isNewImport());

        } catch (IOException ex) {
            // TODO: what shall we do here??
            // log and rethrow for the moment, but a rollback should be implementened somewhere
            LOGGER.error("Error in risk computation", ex);
            throw new ActionException(this, "Error in risk computation", ex);
        }
    }
}
