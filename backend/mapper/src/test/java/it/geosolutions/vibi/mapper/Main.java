package it.geosolutions.vibi.mapper;

import it.geosolutions.vibi.mapper.service.Calculations;
import it.geosolutions.vibi.mapper.service.VibiService;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, Object> dbParams = new HashMap<>();
        dbParams.put("dbtype", "postgis");
        dbParams.put("host", "localhost");
        dbParams.put("port", 5432);
        dbParams.put("schema", "public");
        dbParams.put("database", "nuno");
        dbParams.put("user", "nuno");
        dbParams.put("passwd", "nuno");
        DataStore store = DataStoreFinder.getDataStore(dbParams);
        //VibiService.submit(System.getProperty("user.dir") + "/mapper/src/test/resources/2011_PCAP_DATA_1101-1130_mod.xls", store);
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2011_PCAP_DATA_1101-1130_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2011_PCAP_DATA_1131-1160_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2011_PCAP_DATA_1161-1190_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2011_PCAP_DATA_1191-3564_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2012_PCAP_DATA_1201-1230_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2012_PCAP_DATA_1231-1260_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2012_PCAP_DATA_1261-1290_mod.xls");
        parseWoorkBook(store, "/home/nuno/Work/doc/vibi/vibi_data_original/2012_PCAP_DATA_1291-1300_mod.xls");
        Calculations.initJdbcDriver("org.postgresql.Driver");
        Calculations.refresh("jdbc:postgresql://localhost:5432/nuno", "nuno", "nuno");
    }

    private static void parseWoorkBook(DataStore store, String workBookPath) {
        System.out.println("PARSING: " + workBookPath);
        try {
            VibiService.submit(workBookPath, store);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}