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
package it.geosolutions.geobatch.destination.action.pteringestion;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.action.DestinationBaseAction;
import it.geosolutions.geobatch.destination.action.rasterize.RasterizeConfiguration;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.PterIngestionProcess;
import it.geosolutions.geobatch.flow.event.action.ActionException;

import java.io.File;
import java.io.IOException;

import org.geotools.jdbc.JDBCDataStore;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
@Action(configurationClass = PterIngestionConfiguration.class)
public class PterIngestionAction extends DestinationBaseAction<PterIngestionConfiguration> {

    

    public PterIngestionAction(final PterIngestionConfiguration configuration) throws IOException {
        super(configuration);        
    }


	@Override
	protected void doProcess(PterIngestionConfiguration configuration,
			FeatureConfiguration featureConfiguration, JDBCDataStore dataStore,
			MetadataIngestionHandler metadataHandler, File file)
			throws ActionException {
		try {
	        PterIngestionProcess computation = new PterIngestionProcess(
	        		featureConfiguration.getTypeName(),
	                listenerForwarder,
	                metadataHandler,
	                dataStore);
        
            computation.importPter(null, configuration.isDropInput());

        } catch (IOException ex) {
            // TODO: what shall we do here??
            // log and rethrow for the moment, but a rollback should be implementened somewhere
            LOGGER.error("Error in importing pter", ex);
            throw new ActionException(this, "Error in importing pter", ex);
        }
	}

}
