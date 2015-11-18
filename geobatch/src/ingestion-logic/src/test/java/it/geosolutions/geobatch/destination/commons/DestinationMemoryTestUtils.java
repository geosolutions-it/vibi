package it.geosolutions.geobatch.destination.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.util.FileUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.SchemaException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationMemoryTestUtils {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DestinationMemoryTestUtils.class);

	public static MemoryDataStore initTestWithData(String[] strings, MemoryDataStore inDataStore){
		MemoryDataStore dataStore = null;
		try{
			dataStore = initTestDataStore(strings, inDataStore);	
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		return dataStore;
	}

	protected static DataStore initTestWithData(String[] strings) throws IOException, SchemaException {
		return initTestWithData(strings, null);
	}

	private static MemoryDataStore initTestDataStore(String[] extraData, MemoryDataStore inDataStore) throws IOException, SchemaException {
		MemoryDataStore dataStore = null;
		if(inDataStore != null){
			dataStore = inDataStore;
		}else{
			dataStore = new MemoryDataStore();
		}
		Map<String, SimpleFeatureType> model = loadTestModel();
		for(SimpleFeatureType schema : model.values()) {
			dataStore.createSchema(schema);
		}
		loadTestData(dataStore, model, "test_data");
		for(String data : extraData) {
			loadTestData(dataStore, model, data);
		}
		return dataStore;
	}

	/**
	 * 
	 * @param dataStore
	 * @param model
	 * @param name
	 * @throws IOException
	 */
	private static void loadTestData(MemoryDataStore dataStore,
			Map<String, SimpleFeatureType> model, String name) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(DestinationMemoryTestUtils.class.getResourceAsStream("/" + name + ".txt")));
			String line = null;
			while((line = reader.readLine()) != null) {
				String typeName = line.substring(0, line.indexOf('='));
				String data = line.substring(line.indexOf('=') + 1);
				SimpleFeature feature = DataUtilities.createFeature(model.get(typeName), data);
				dataStore.addFeature(feature);
			}
		} finally {
			FileUtils.close(reader);			
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws SchemaException
	 */
	private static Map<String, SimpleFeatureType> loadTestModel() throws IOException, SchemaException {
		Map<String, SimpleFeatureType> model = new HashMap<String, SimpleFeatureType>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(DestinationMemoryTestUtils.class.getResourceAsStream("/test_model.txt")));
			String line = null;
			while((line = reader.readLine()) != null) {
				String typeName = line.substring(0, line.indexOf('='));
				String typeSpec = line.substring(line.indexOf('=') + 1);
				model.put(typeName, DataUtilities.createType(typeName, typeSpec));
			}
		} finally {
			FileUtils.close(reader);			
		}
		return model;
	};
}
