/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geobatch.destination.common.utils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.jdbc.JDBCDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database / DataStore utilities.
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class DbUtils {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);
	
	/**
	 * Drops a feature from the given DataStore.
	 * 
	 * @param datastoreParams
	 * @param typeName
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void dropFeatureType(DataStore dataStore, String typeName) throws IOException, SQLException {
		// the GeoTools DataStore interface doesn't implement an action to drop a feature,
		// so we need to use the sql connection directly
		if(dataStore instanceof JDBCDataStore){
			executeSql((JDBCDataStore)dataStore, null, "DROP TABLE \"" + typeName + "\" CASCADE", true);
		}
	}
	
	/**
	 * Drops a feature from the given DataStore.
	 * 
	 * @param datastoreParams
	 * @param typeName
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void dropFeatureType(Map<String, Serializable> datastoreParams, String typeName) throws IOException, SQLException {
		// the GeoTools DataStore interface doesn't implement an action to drop a feature,
		// so we need to use the sql connection directly
		executeSql(datastoreParams, "DROP TABLE \"" + typeName + "\" CASCADE");
	}

	/**
	 * Executes an sql query on the given datastore connection.
	 * 
	 * @param datastoreParams 
	 * @param string
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void executeSql(Map<String, Serializable> datastoreParams, String sql) throws IOException, SQLException {		
		Transaction transaction = null;
		JDBCDataStore dataStore = null;
		try {			
			dataStore = (JDBCDataStore)DataStoreFinder.getDataStore(datastoreParams);
			transaction = new DefaultTransaction();
			executeSql(dataStore, transaction, sql, true);			
		} finally {			
			if(dataStore != null) {
				dataStore.dispose();
			}
			if(transaction != null) {
				transaction.close();
			}
		}
		
	}
	
	/**
	 * Executes an sql query on the given datastore connection.
	 */
	public static void executeSql(JDBCDataStore dataStore, Transaction transaction, String sql, boolean commit) throws IOException, SQLException {
		Connection conn = null;
		Transaction queryTransaction = transaction;
		if(queryTransaction == null) {
			queryTransaction = new DefaultTransaction();
		}
		try {						
			conn = dataStore.getConnection(queryTransaction);
			
			if(conn == null || conn.isClosed()) {
				throw new IOException("Unable to connect to datastore");
			}
			executeSql(conn, queryTransaction, sql, commit);							
		} finally {						
			if(transaction == null && queryTransaction != null) {
				queryTransaction.close();
			}
		}
		
	}
	
	/**
         * Executes an sql query on the given datastore connection.
         */
	public static void executeSql(Connection conn, Transaction transaction, String sql, boolean commit) throws IOException, SQLException {
	    executeSql(conn, transaction, sql, commit, false);
	}
	
	public static void executeSql(Connection conn, Transaction transaction, String sql, boolean commit, boolean silent) throws IOException, SQLException {		
		Statement stmt = null;
		
		try {						
			stmt = conn.createStatement();
			stmt.execute(sql);
			if(commit) {
				transaction.commit();
			}
		} catch (IOException e) {
			transaction.rollback();
			LOGGER.error(e.getMessage(), e);
			throw e;
		} catch (SQLException e) {
			transaction.rollback();		
			if(!silent){
			    LOGGER.error(e.getMessage(), e);
			}
			throw e;
			
		} finally {
			if(stmt != null) {
				stmt.close();
			}			
		}
		
	}

	/**
	 * @param dataStore
	 * @param transaction
	 * @param string
	 * @param string2
	 * @return
	 * @throws IOException 
	 */
	public static int getNewId(Connection conn,
			Transaction transaction, String tableName, String idName) throws IOException {
		Integer newId = null;
		try {
			Object result = executeScalar(conn, transaction, "SELECT MAX(\""+idName+"\") FROM \""+tableName+"\"");
			if(result != null) {
				newId = Integer.parseInt(result.toString());
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
		if(newId == null)
			return 1;
		return newId + 1;
	}

	/**
	 * @param dataStore
	 * @param transaction
	 * @param string
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static Object executeScalar(JDBCDataStore dataStore,
			Transaction transaction, String sql) throws IOException, SQLException {
		Connection conn = null;
		Transaction queryTransaction = transaction;
		if(queryTransaction == null) {
			queryTransaction = new DefaultTransaction();
		}
		try {						
			conn = dataStore.getConnection(queryTransaction);
			if(conn == null) {
				throw new IOException("Unable to connect to datastore");
			}
			return executeScalar(conn, queryTransaction, sql);							
		} finally {							
			if(transaction == null && queryTransaction != null) {
				queryTransaction.close();
			}
		}
	}

	/**
	 * @param conn
	 * @param transaction
	 * @param sql
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private static Object executeScalar(Connection conn,
			Transaction transaction, String sql) throws IOException, SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {						
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return rs.getObject(1);
			}
			return null;
		} catch (SQLException e) {				
			LOGGER.error(e.getMessage(), e);
			throw e;
			
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}			
		}
	}
}
