package it.geosolutions.geobatch.destination.action.rasterize;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.rasterize.TargetRasterizeProcess;
import it.geosolutions.geobatch.destination.vulnerability.VulnerabilityComputation;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;

import org.geotools.jdbc.JDBCDataStore;

@Action(configurationClass = RasterizeConfiguration.class)
public class RasterizeAction extends DestinationBaseAction<RasterizeConfiguration> {

	
	public RasterizeAction(RasterizeConfiguration actionConfiguration) throws IOException {
		super(actionConfiguration);
	}

	@Override
	protected void doProcess(RasterizeConfiguration cfg,
			FeatureConfiguration featureCfg, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file) throws ActionException {
        try {
			TargetRasterizeProcess rasterize = new TargetRasterizeProcess(featureCfg.getTypeName(), listenerForwarder, metadataHandler, dataStore);
			
			File outputDir =  new File(System.getProperty(VulnerabilityComputation.RASTER_PATH_PROP, "")); 
			if(configuration.getBaseOutputPath() != null && !configuration.getBaseOutputPath().isEmpty()){
				outputDir = new File(configuration.getBaseOutputPath());
			}		
			File eventFile = new File(getTempDir().getParentFile().getAbsolutePath() + "/rasterize.xml");
			rasterize.execute(getConfigDir(), getTempDir(), outputDir, eventFile, cfg.getClosePhase());
		} catch (IOException ex) {
            // TODO: what shall we do here??
            // log and rethrow for the moment, but a rollback should be implementened somewhere
            LOGGER.error("Error in rasterize process", ex);
            throw new ActionException(this, "Error in rasterize process", ex);
        }
	}


}