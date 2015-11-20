package it.geosolutions.vibi.mapper;

import it.geosolutions.vibi.mapper.VibiService;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("dbtype", "postgis");
        params.put("host", "localhost");
        params.put("port", 5432);
        params.put("schema", "public");
        params.put("database", "nuno");
        params.put("user", "nuno");
        params.put("passwd", "nuno");
        DataStore store = DataStoreFinder.getDataStore(params);
        VibiService.submit(System.getProperty("user.dir") + "/mapper/src/test/resources/2010_PCAP_DATA_NC.xls", store);
    }
}
