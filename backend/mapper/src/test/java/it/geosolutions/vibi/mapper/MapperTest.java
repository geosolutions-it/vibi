package it.geosolutions.vibi.mapper;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.service.Calculations;
import it.geosolutions.vibi.mapper.service.VibiService;
import org.apache.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.junit.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

public class MapperTest {

    private final static Logger LOGGER = Logger.getLogger(MapperTest.class);

    private static final String VIBI_TEST_BD_PROPERTY = "VIBI_TEST_DB";

    @Test
    public void testMapper() throws Exception {
        Map<String, Object> dbParameters = getVibiTestDbParameters();
        if (dbParameters == null) {
            LOGGER.warn("To activate VIBI mapper tests setup a database for VIBI and define its connection parameters" +
                    " using OS environment or Java property VIBI_TEST_DB. " +
                    "As an example: 'host=localhost;port=5432;schema=public;database=postgres;user=postgres;passwd=postgres'");
        }
        assumeThat(dbParameters, notNullValue());
        assertThat(dbParameters.get("host"), notNullValue());
        assertThat(dbParameters.get("port"), notNullValue());
        assertThat(dbParameters.get("schema"), notNullValue());
        assertThat(dbParameters.get("database"), notNullValue());
        assertThat(dbParameters.get("user"), notNullValue());
        assertThat(dbParameters.get("passwd"), notNullValue());
        DataStore store = DataStoreFinder.getDataStore(dbParameters);
        String dbUrl = String.format("jdbc:postgresql://%s:%s/%s/%s", dbParameters.get("host"),
                dbParameters.get("port"), dbParameters.get("database"), dbParameters.get("schema"));
        String user = dbParameters.get("user").toString();
        String pass = dbParameters.get("passwd").toString();
        URL resourceA = MapperTest.class.getClassLoader().getResource("2011_PCAP_DATA_1101-1130_mod.xls");
        assertThat(resourceA, notNullValue());
        parseWorkBook(store, resourceA.getFile(), dbUrl, user, pass);
        checkDataA(dbUrl, user, pass);
        // check that parsing the same workbook is idempotent
        parseWorkBook(store, resourceA.getFile(), dbUrl, user, pass);
        checkDataA(dbUrl, user, pass);
        // test the submission of a spreadsheet that only contains plot info
        URL resourceB = MapperTest.class.getClassLoader().getResource("2011_PCAP_DATA_1101-1130_mod_empty.xls");
        assertThat(resourceB, notNullValue());
        parseWorkBook(store, resourceB.getFile(), dbUrl, user, pass);
        checkDataB(dbUrl, user, pass);
    }

    private void checkDataA(String dbUrl, String user, String pass) {
        assertThat(count("CLASS_CODE_MOD_NATURESERVE", dbUrl, user, pass), is(154));
        assertThat(count("COVER_MIDPOINT_LOOKUP", dbUrl, user, pass), is(11));
        assertThat(count("PLOT", dbUrl, user, pass), is(30));
        assertThat(count("PLOT_MODULE_HERBACEOUS_INFO", dbUrl, user, pass), is(760));
        assertThat(count("PLOT_MODULE_HERBACEOUS", dbUrl, user, pass), is(10480));
        assertThat(count("PLOT_MODULE_WOODY_RAW", dbUrl, user, pass), is(1865));
        assertThat(count("BIOMASS_RAW", dbUrl, user, pass), is(9));
        assertThat(count("BIOMASS_ACCURACY", dbUrl, user, pass), is(4));
        assertThat(count("METRIC_CALCULATIONS", dbUrl, user, pass), is(150));
        checkPlotScores(1101, 66.0, 63.0, 70.0, 61.0, dbUrl, user, pass);
        checkPlotScores(1106, 20.0, 23.0, 3.0, 0.0, dbUrl, user, pass);
        checkPlotScores(1120, 0.0, 0.0, 0.0, 0.0, dbUrl, user, pass);
    }

    private void checkDataB(String dbUrl, String user, String pass) {
        assertThat(count("CLASS_CODE_MOD_NATURESERVE", dbUrl, user, pass), is(154));
        assertThat(count("COVER_MIDPOINT_LOOKUP", dbUrl, user, pass), is(11));
        assertThat(count("PLOT", dbUrl, user, pass), is(30));
        assertThat(count("PLOT_MODULE_HERBACEOUS_INFO", dbUrl, user, pass), is(0));
        assertThat(count("PLOT_MODULE_HERBACEOUS", dbUrl, user, pass), is(0));
        assertThat(count("PLOT_MODULE_WOODY_RAW", dbUrl, user, pass), is(0));
        assertThat(count("BIOMASS_RAW", dbUrl, user, pass), is(0));
        assertThat(count("BIOMASS_ACCURACY", dbUrl, user, pass), is(4));
        assertThat(count("METRIC_CALCULATIONS", dbUrl, user, pass), is(0));
        checkPlotScores(1101, 0.0, 0.0, 0.0, 0.0, dbUrl, user, pass);
        checkPlotScores(1106, 0.0, 0.0, 0.0, 0.0, dbUrl, user, pass);
        checkPlotScores(1120, 0.0, 0.0, 0.0, 0.0, dbUrl, user, pass);
    }

    private void checkPlotScores(int plotNo, double eIndex, double ecstIndex, double fIndex,
                                 double shIndex, String dbUrl, String user, String pass) {
        assertThat(getScore(plotNo, "vibi_ecst_index", dbUrl, user, pass), is(ecstIndex));
        assertThat(getScore(plotNo, "vibi_f_index", dbUrl, user, pass), is(fIndex));
        assertThat(getScore(plotNo, "vibi_e_index", dbUrl, user, pass), is(eIndex));
        assertThat(getScore(plotNo, "vibi_sh_index", dbUrl, user, pass), is(shIndex));
    }

    private Map<String, Object> getVibiTestDbParameters() {
        String rawParameters = System.getenv(VIBI_TEST_BD_PROPERTY);
        if (rawParameters == null) {
            rawParameters = System.getProperty(VIBI_TEST_BD_PROPERTY);
            if (rawParameters == null) {
                return null;
            }
        }
        Map<String, Object> parameters = new HashMap<>();
        for (String rawParameter : rawParameters.split(";")) {
            String[] rawParameterParts = rawParameter.split("=");
            if (rawParameterParts[0].equals("port")) {
                parameters.put(rawParameterParts[0], Integer.parseInt(rawParameterParts[1]));
            } else {
                parameters.put(rawParameterParts[0], rawParameterParts[1]);
            }
        }
        parameters.put("dbtype", "postgis");
        return parameters;
    }

    private static void parseWorkBook(DataStore store, String workBookPath, String dbUrl, String user, String pass) throws Exception {
        Transaction transaction = new DefaultTransaction(UUID.randomUUID().toString());
        try {
            LOGGER.info("Start parsing workbook: " + workBookPath);
            VibiService.submit(workBookPath, store, transaction);
            Calculations.initJdbcDriver("org.postgresql.Driver");
        } finally {
            transaction.commit();
            transaction.close();
        }
        Calculations.refresh(dbUrl, user, pass);
    }

    private int count(String tableName, String dbUrl, String user, String pass) {
        String query = String.format("SELECT COUNT(1) FROM %s", tableName);
        return new ExecuteQuery(dbUrl, user, pass, query) {
            int result;

            @Override
            public void extract(ResultSet resultSet) throws Exception {
                resultSet.next();
                result = resultSet.getInt(1);
            }
        }.result;
    }

    private double getScore(int plotNo, String scoreName, String dbUrl, String user, String pass) {
        String query = String.format("SELECT score FROM metric_calculations WHERE plot_no = '%d' AND vibi_type = '%s';",
                plotNo, scoreName);
        return new ExecuteQuery(dbUrl, user, pass, query) {
            double result;

            @Override
            public void extract(ResultSet resultSet) throws Exception {
                resultSet.next();
                result = resultSet.getDouble(1);
            }
        }.result;
    }

    private static abstract class ExecuteQuery {

        public abstract void extract(ResultSet resultSet) throws Exception;

        public ExecuteQuery(String dbUrl, String user, String pass, String query) {
            try (Connection connection = DriverManager.getConnection(dbUrl, user, pass);
                 PreparedStatement statement = connection.prepareStatement(query)) {
                extract(statement.executeQuery());
            } catch (Exception exception) {
                throw new VibiException(exception, "Something bad happen when doing work on data base.");
            }
        }
    }
}