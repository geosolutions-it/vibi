package it.geosolutions.geobatch.destination;

import it.geosolutions.geobatch.destination.commons.DestinationMemoryTestUtils;
import it.geosolutions.geobatch.destination.commons.MemoryMockDataStore;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.geotools.data.AbstractDataStoreFactory;
import org.geotools.data.DataStore;
import org.geotools.data.memory.MemoryDataStore;

public class MemoryMockDataStoreFactory extends AbstractDataStoreFactory implements org.geotools.data.DataStoreFactorySpi{

	public static final Param DBTYPE = new Param("dbtype", String.class, "Type", true, "mockmemory");
	public static final Param DBNAME = new Param("name", String.class, "Name", true);
	public static final Param DBDATASFILE = new Param("dbdatasfiles", String.class, "CSV db data files", false);

	private static final Map<String,MemoryDataStore> memoryStores = new HashMap<String, MemoryDataStore>();

	@Override
	public DataStore createDataStore(Map<String, Serializable> params)
			throws IOException {
		// build the store
		String dbName = (String)DBNAME.lookUp(params);
		if(!memoryStores.containsKey(dbName)){
			MemoryDataStore store = new MigrationTestMemoryMockDataStore();
			String[] dbdatasfiles = DBDATASFILE.lookUp(params)!=null ? ((String)(DBDATASFILE.lookUp(params))).split(",") : new String[0];
			DestinationMemoryTestUtils.initTestWithData(dbdatasfiles, store);
			memoryStores.put(dbName, store);
		}
		return memoryStores.get(dbName);
	}

	protected boolean checkDBType(Map<String, ?> params) {
		return checkDBType(params, "mockmemory");
	}

	protected final boolean checkDBType(Map<String, ?> params, String dbtype) {
		String type;

		try {
			type = (String) DBTYPE.lookUp(params);

			if (dbtype.equals(type)) {
				return true;
			}

			return false;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public DataStore createNewDataStore(Map<String, Serializable> params)
			throws IOException {
		return createDataStore(params);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Param[] getParametersInfo() {
		LinkedHashMap<String, Param> map = new LinkedHashMap<String, Param>();
		setupParameters(map);
		return (Param[]) map.values().toArray(new Param[map.size()]);
	}

	protected void setupParameters(Map<String, Param> parameters) {
		parameters.put(DBTYPE.key,DBTYPE);
		parameters.put(DBNAME.key,DBNAME);
		parameters.put(DBDATASFILE.key, DBDATASFILE);
	}


	public boolean canProcess(Map params) {
		if (!super.canProcess(params)) {
			return false; // was not in agreement with getParametersInfo
		}

		return checkDBType(params);
	}


}
