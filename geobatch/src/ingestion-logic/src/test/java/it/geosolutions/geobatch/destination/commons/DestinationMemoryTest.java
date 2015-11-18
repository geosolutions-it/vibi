package it.geosolutions.geobatch.destination.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.geosolutions.geobatch.destination.MockMetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;

import java.io.File;
import java.io.IOException;

import org.geotools.data.Query;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.SchemaException;
import org.geotools.util.logging.Logging;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DestinationMemoryTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DestinationMemoryTest.class);

	protected MemoryDataStore dataStore;
	protected MetadataIngestionHandler metadataHandler;

	static{
		try {
			Logging.ALL.setLoggerFactory("org.geotools.util.logging.Log4JLoggerFactory");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	protected abstract void checkData(SimpleFeature feature);

	protected void initTestWithData(String[] strings,  MemoryDataStore inDataStore) throws IOException, SchemaException {
		dataStore = DestinationMemoryTestUtils.initTestWithData(strings, inDataStore);
		initMetadata();		
	}

	protected void initTestWithData(String[] strings) throws IOException, SchemaException {
		initTestWithData(strings,null);
	}

	private void initMetadata() {
		metadataHandler = new MockMetadataIngestionHandler(dataStore);		
	}

	/**
	 * @param string
	 * @param i
	 * @throws IOException 
	 */
	protected void checkFeature(String typeName, int expectedSize) throws IOException {		
		checkFeature(typeName, expectedSize, this.dataStore);
	}

	protected void checkFeature(String typeName, int expectedSize, MemoryDataStore inDataStore) throws IOException {		
		SimpleFeatureSource featureSource = inDataStore.getFeatureSource(typeName);
		SimpleFeatureType schema = inDataStore.getSchema(typeName);
		assertNotNull(schema);
		assertEquals(expectedSize, featureSource.getCount(new Query(typeName)));
		SimpleFeatureIterator iterator = featureSource.getFeatures().features();
		try {
			while(iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				assertNotNull(feature);
				checkData(feature);
			}
		} finally {
			iterator.close();
		}
	}

	protected void checkFile(String filePath) throws IOException {
		assertEquals(new File(filePath).exists(), true);
	}

}
