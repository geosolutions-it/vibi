package it.geosolutions.geobatch.destination.action.streetuser;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.streetuser.StreetUserComputation;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;

import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = StreetUserConfiguration.class)
public class StreetUserAction extends DestinationBaseAction<StreetUserConfiguration> {


	public StreetUserAction(final StreetUserConfiguration configuration) throws IOException {
		super(configuration);
	}

	@Override
	protected void doProcess(StreetUserConfiguration cfg,
			FeatureConfiguration featureCfg, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException {
		
		try {
			
			StreetUserComputation computation = new StreetUserComputation(
					featureCfg.getTypeName(),
					listenerForwarder,
					metadataHandler,
					dataStore);
			computation.setSorted(true);
			computation.execute(cfg.getAggregationLevel(), cfg.isDropInput(), cfg.getClosePhase(), cfg.isNewImport());
		} catch (IOException ex) {
            // TODO: what shall we do here??
            // log and rethrow for the moment, but a rollback should be implementened somewhere
            LOGGER.error("Error in calculating street users", ex);
            throw new ActionException(this, "Error in importing targets", ex);
        }
	}
}
