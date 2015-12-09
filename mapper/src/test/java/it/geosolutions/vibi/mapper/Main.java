package it.geosolutions.vibi.mapper;

import it.geosolutions.vibi.mapper.service.VibiService;
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
        params.put("database", "postgres");
        params.put("user", "postgres");
        params.put("passwd", "postgres");
        DataStore store = DataStoreFinder.getDataStore(params);
        VibiService.submit(System.getProperty("user.dir") + "/mapper/src/test/resources/2011_PCAP_DATA_1101-1130_mod.xls", store);
    }
}
