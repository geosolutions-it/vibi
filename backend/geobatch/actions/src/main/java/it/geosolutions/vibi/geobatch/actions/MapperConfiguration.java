package it.geosolutions.vibi.geobatch.actions;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.vibi.mapper.service.Calculations;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MapperConfiguration extends ActionConfiguration {

    private String dbtype = "postgis";
    private String host = "localhost";
    private int port = 5432;
    private String schema = "public";
    private String database = "postgres";
    private String user = "postgres";
    private String passwd = "postgres";
    private String outputPath = "/tmp";
    private String driver = "org.postgresql.Driver";
    private String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

    public MapperConfiguration(String id, String name, String description) {
        super(id, name, description);
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
        Calculations.initJdbcDriver(this.driver);
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public DataStore getStore() {
        Map<String, Object> params = new HashMap<>();
        params.put("dbtype", dbtype);
        params.put("host", host);
        params.put("port", port);
        params.put("schema", schema);
        params.put("database", database);
        params.put("user", user);
        params.put("passwd", passwd);
        try {
            return DataStoreFinder.getDataStore(params);
        } catch (IOException exception) {
            throw new RuntimeException("Error creating store.", exception);
        }
    }
}
