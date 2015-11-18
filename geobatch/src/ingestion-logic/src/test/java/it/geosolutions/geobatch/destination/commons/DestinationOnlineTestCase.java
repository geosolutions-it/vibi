/*
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.commons;

import static org.junit.Assume.assumeTrue;
import it.geosolutions.geobatch.destination.common.OutputObject;
import it.geosolutions.geobatch.destination.vulnerability.VulnerabilityComputation;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.jdbc.JDBCDataStore;
import org.geotools.jdbc.JDBCDataStoreFactory;
import org.geotools.test.OnlineTestSupport;
import org.junit.After;
import org.junit.Before;
import org.opengis.feature.simple.SimpleFeatureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DamianoG
 * 
 * This class provide some feature to easy implement a geotools like online test case. Use the fixture mechanism.
 * Subclass this class if you want to implement an online test case
 * 
 * TODO Actually if a test don't require DB access this class force to setup it anyway
 *
 */
public abstract class DestinationOnlineTestCase extends OnlineTestSupport {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(DestinationOnlineTestCase.class);
    
    public String testTable;
    
    protected JDBCDataStore dataStore;
    protected OutputObject vulnerabilityObj;
    protected Transaction outTransaction;
    protected String tableName;
    protected Boolean removeAfter = true;
    /**
     * The table where to copy the schema
     */
    protected String originTable;
    
    /**
     * @param originTable the originTable to set
     */
    public void setOriginTable(String originTable) {
        this.originTable = originTable;
    }

    /**
     * @return the originTable
     */
    public String getOriginTable() {
        return originTable;
    }



    protected abstract String getFixtureId();

    @Before
    public void before() throws Exception {
        LOGGER.debug(" BaseTest:: <start_of_before()> Create temporary resources on DB...");
        super.before();
        this.testTable = (testTable == null)? "tmp_table_test" : this.testTable;
        try{
            connect();
        } catch(Exception e) {
            LOGGER.warn("connect() failed, skipping test ");
            assumeTrue(false);
        }
        outTransaction = new DefaultTransaction();
        // Remove the table... nel dubbio!
        removeTable();
        dataStore.createSchema(getTmpTable());
        try{
            
            vulnerabilityObj = new OutputObject(dataStore, outTransaction, testTable, VulnerabilityComputation.GEOID);
            loadFeature(vulnerabilityObj);
        }catch(Exception e) {
            LOGGER.error(e.getMessage(),e);
            assumeTrue(false);
        }
        finally{
            if(outTransaction != null){
                try {
                    outTransaction.commit();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        LOGGER.info("---------- Running Test " + getClass().getSimpleName());
    }
    
    @After
    public void after() throws Exception {
        LOGGER.debug(" BaseTest:: <start_of_after()> cleaning created resources...");
        super.after();
        if(outTransaction != null){
            outTransaction.close();
        }
        if(removeAfter){
        	removeTable();
        }
        if(dataStore != null){
            dataStore.dispose();
            dataStore = null;
        }
        LOGGER.info("---------- End Test " + getClass().getSimpleName());
    }
    
    protected void connect() throws Exception {
        if(dataStore != null){
            return;
        }
        dataStore = (JDBCDataStore)createDatastore();
    }
    
    protected void disconnect() throws Exception {
        
    }
    
    protected DataStore createDatastore() throws IOException {
        Map params = getPostgisParams();
        params.put("Expose primary keys","true");
        DataStore dataStore = DataStoreFinder.getDataStore(params);
        return dataStore;
    }
    
    @Override
    protected boolean isOnline() throws Exception {
        connect();
        if(dataStore == null){
            return false;
        }
        String [] names = dataStore.getTypeNames();
        for(String name : names){
            if(name.equals(getFixture().getProperty("src_table"))){
                return true;
            }
        }
        return false;
    }
    
    public Map<String,Serializable> getPostgisParams() {
        Map<String,Serializable> params = new HashMap<String,Serializable>();
        params.put(JDBCDataStoreFactory.DBTYPE.key, "postgis");
        params.put(JDBCDataStoreFactory.HOST.key, getFixture().getProperty("pg_host"));
        params.put(JDBCDataStoreFactory.PORT.key, getFixture().getProperty("pg_port"));
        params.put(JDBCDataStoreFactory.SCHEMA.key, getFixture().getProperty("pg_schema"));
        params.put(JDBCDataStoreFactory.DATABASE.key, getFixture().getProperty("pg_database"));
        params.put(JDBCDataStoreFactory.USER.key, getFixture().getProperty("pg_user"));
        params.put(JDBCDataStoreFactory.PASSWD.key, getFixture().getProperty("pg_password"));
        params.put(JDBCDataStoreFactory.EXPOSE_PK.key, getFixture().getProperty("expose_primary_keys"));
        params.put("src_table", getFixture().getProperty("src_table"));
        return params;
    }
    
    protected String getRasterParams(String key) {
        return getFixture().getProperty(key);
    }
    
    protected String getExternalPropDirPath() {
        return getFixture().getProperty("external_prop_dir_path");
    }
    
    protected Properties getExamplePostgisProps() {
        Properties ret = new Properties();
        ret.setProperty("pg_host", "localhost");
        ret.setProperty("pg_port", "5432");
        ret.setProperty("pg_database", "destination_staging");
        ret.setProperty("pg_schema", "siig_p");
        ret.setProperty("pg_user", "siig_p");
        ret.setProperty("pg_password", "siig_p");
        ret.setProperty("src_table", "siig_geo_ln_arco_1");
        ret.setProperty("ground_coverage_target", "<RASTER_FILE_ABSOLUTE_PATH>");
        ret.setProperty("not_human_target", "<RASTER_FILE_ABSOLUTE_PATH>");
        ret.setProperty("human_target", "<RASTER_FILE_ABSOLUTE_PATH>");
        ret.setProperty("external_prop_dir_path", "<EXTERNAL_PROP_DIR_PATH>");
        ret.setProperty("expose_primary_keys", "TRUE");

        return ret;
    }
    
    protected SimpleFeatureType getTmpTable() throws IOException{
        if(originTable == null){
            // TODO if a test don't use the DB but is implemented as online test don't force the input table setup
            LOGGER.info("You must setup the origin table to copy, call the method setOriginTable(tableName) in a @Before annotated method, where tableName is an existing table into the schema...");
            LOGGER.info("Set the temp table name to siig_geo_ln_arco_1");
            originTable = "siig_geo_ln_arco_1";
        }
        SimpleFeatureType schema = (SimpleFeatureType) dataStore.getSchema(originTable);
        SimpleFeatureTypeBuilder sftb = new SimpleFeatureTypeBuilder();
        sftb.setName(testTable);
        sftb.addAll(schema.getAttributeDescriptors());
        return sftb.buildFeatureType();
    }
    
    protected void loadFeature(OutputObject objOut) throws IOException{
    }
    
    private void removeTable() throws Exception{
        // Remove vunerabilityTestTable
        Statement stmt = null;
        Connection c = null;
        Transaction t = new DefaultTransaction();
        try{
            c = dataStore.getConnection(t);
            stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS " +  dataStore.getDatabaseSchema() + "." + testTable;
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
         // An exception occurs? Maybe the table is already been deleted... swallow the exception...
            LOGGER.warn("could not drop test table ...", e);
        }
        finally{
            if(t != null){
                try {
                    t.commit();
                    t.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}