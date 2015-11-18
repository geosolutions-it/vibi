package it.geosolutions.geobatch.destination.ingestion;

import it.geosolutions.geobatch.destination.common.OutputObject;

import java.io.IOException;

import org.geotools.data.DataStore;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeValueProcess extends OutputObject{

	protected final static Logger LOGGER = LoggerFactory.getLogger(ChangeValueProcess.class);

	public ChangeValueProcess(DataStore dataStore, Transaction transaction,
			String name) throws IOException {
		super(dataStore, transaction, name, "");
	}

	public void execute(String filter, String attribute, String value) throws Exception{
		Filter ecqlFilter = ECQL.toFilter(filter);
		SimpleFeatureStore store = (SimpleFeatureStore) this.getSource();
		store.modifyFeatures(attribute, value, ecqlFilter );
	}


}
