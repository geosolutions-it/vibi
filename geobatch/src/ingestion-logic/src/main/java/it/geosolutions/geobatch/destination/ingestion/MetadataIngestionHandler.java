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
package it.geosolutions.geobatch.destination.ingestion;


import it.geosolutions.geobatch.destination.common.utils.DbUtils;
import it.geosolutions.geobatch.destination.common.utils.SequenceManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.geotools.data.DataStore;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.jdbc.JDBCDataStore;

/**
 * Handles the ingestion metadata structures on database.
 * 
 * TODO: use Hibernate ?
 * 
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class MetadataIngestionHandler {
	
	private SequenceManager processSequenceManager;
	private SequenceManager traceSequenceManager;
	
	JDBCDataStore dataStore;
	
	public class Process {
		int id = 0;
		int maxTrace = 0;
		int maxError = 0;
		
		/**
		 * @param id
		 * @param maxTrace
		 * @param maxError
		 */
		public Process(int id, int maxTrace, int maxError) {
			super();
			this.id = id;
			this.maxTrace = maxTrace;
			this.maxError = maxError;
		}
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @return the maxTrace
		 */
		public int getMaxTrace() {
			return maxTrace;
		}
		/**
		 * @return the maxError
		 */
		public int getMaxError() {
			return maxError;
		}
		
		
	}
	
	
	
	/**
	 * @param dataStore
	 */
	public MetadataIngestionHandler(DataStore dataStore) {
		super();
		if(dataStore instanceof JDBCDataStore){
			this.dataStore =  (JDBCDataStore)dataStore;	
			processSequenceManager = new SequenceManager(this.dataStore, "process_seq");
			traceSequenceManager = new SequenceManager(this.dataStore, "trace_seq");		
		}
	}

	/**	
	 * Creates a new process in the metadata table.
	 * 
	 * @param dataStore
	 * @return
	 * @throws IOException
	 */
	public int createProcess() throws IOException {
		
		Transaction transaction = null;	
		Connection conn = null;
		try {			
			transaction = new DefaultTransaction();
			conn = dataStore.getConnection(transaction);
			int processo = (int)processSequenceManager.retrieveValue();
			//DbUtils.getNewId(conn, transaction, "siig_t_processo", "id_processo");		
			String sql = "INSERT INTO siig_t_processo(id_processo,data_creazione) values(" +processo+ ",now())";
			
			DbUtils.executeSql(conn, transaction, sql, true);
			return processo;
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
			if(transaction != null) {
				transaction.close();
			}
		}
		
		
	}
	
	/**	
	 * Gets an existing process in the metadata table.
	 * 
	 * @param dataStore
	 * @return
	 * @throws IOException
	 */
	public Process getProcessData(String inputTypeName) throws IOException {
		Transaction transaction = null;	
		
		try {			
			transaction = new DefaultTransaction();
					
			String sql = "SELECT fk_processo FROM siig_t_tracciamento where nome_file='"
					+ inputTypeName + "'";
			
			int  process = ((Number)DbUtils.executeScalar(dataStore, transaction, sql)).intValue();
			
			sql = "SELECT id_tracciamento FROM siig_t_tracciamento where nome_file='"
					+ inputTypeName + "'";
			
			int  trace = ((Number)DbUtils.executeScalar(dataStore, transaction, sql)).intValue();
			
			sql = "SELECT coalesce(max(progressivo),0) FROM siig_t_log where id_tracciamento=" + trace;
			
			int  errors = ((Number)DbUtils.executeScalar(dataStore, transaction, sql)).intValue();
			
			return new Process(process, trace, errors);
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {			
			if(transaction != null) {
				transaction.close();
			}
		}			
	}		
	
	/**
	 * Close the given phase of a process.
	 * 
	 * @param dataStore
	 * @param processo
	 * @param phase
	 * @throws IOException
	 */
	public void closeProcessPhase(int processo, String phase) throws IOException {		
		try {												
			String sql = "UPDATE siig_t_processo SET data_chiusura_"+phase.toLowerCase()+"=now() where id_processo="+processo;
			
			DbUtils.executeSql(dataStore, null, sql, true);			
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Creates the log metadata for an imported file.
	 *  
	 * @param dataStore
	 * @param processo
	 * @param bersaglio
	 * @param partner
	 * @param codicePartner
	 * @param typeName
	 * @param date
	 * @param total
	 * @param update
	 * @return
	 * @throws IOException
	 */
	public int logFile(int processo, int bersaglio, int partner, String codicePartner, String typeName, String date, boolean update) throws IOException {
		Transaction transaction = null;	
		Connection conn = null;
		try {			
			transaction = new DefaultTransaction();
			conn = dataStore.getConnection(transaction);
			//int tracciamento = DbUtils.getNewId(conn, transaction, "siig_t_tracciamento", "id_tracciamento");
			int tracciamento = (int)traceSequenceManager.retrieveValue();
						
			String sql = "INSERT INTO siig_t_tracciamento(id_tracciamento,fk_processo,fk_bersaglio,fk_partner,codice_partner,nome_file,data,";
			sql       += "nr_rec_shape,nr_rec_storage,nr_rec_scartati,nr_rec_scartati_siig,data_imp_storage,data_elab,flg_tipo_imp)";
			sql       += "values("+tracciamento+","+processo+","+(bersaglio == -1 ? "null" : (bersaglio+""))+",'"+partner+ "','"+codicePartner+"','"+typeName+"', to_date('"+date+"', 'YYYYMMDD'),0,0,0,0,now(),now(),'"+(update ? "I" : "C")+"')";
			
			DbUtils.executeSql(conn, transaction, sql, true);
			return tracciamento;
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
			if(transaction != null) {
				transaction.close();
			}
		}
	}

	/**
	 * Logs a new error for an imported file.
	 * 
	 * @param dataStore
	 * @param trace
	 * @param errors
	 * @param message
	 * @param message2
	 * @param idTematico
	 * @throws IOException 
	 */
	public void logError(int trace, int progr,
			String codiceLog, String error, int idTematico) throws IOException {
		Transaction transaction = null;	
		Connection conn = null;
		try {			
			transaction = new DefaultTransaction();
			conn = dataStore.getConnection(transaction);				
						
			String sql = "INSERT INTO siig_t_log(id_tracciamento,progressivo,codice_log,descr_errore,id_tematico_shape_orig)";			
			sql       += "values("+trace+","+progr+",'"+codiceLog+"','"+error.replace("'", "''")+ "',"+idTematico+")";
			
			DbUtils.executeSql(conn, transaction, sql, true);
			
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
			if(transaction != null) {
				transaction.close();
			}
		}
	}

	/**
	 * @param dataStore
	 * @param trace
	 * @param errors
	 * @throws IOException 
	 */
	public void updateLogFile(int trace, int total,
			int errors, boolean updateTotals) throws IOException {
		Transaction transaction = null;	
		Connection conn = null;
		try {			
			transaction = new DefaultTransaction();
			conn = dataStore.getConnection(transaction);				
		
			String sql;
			if(updateTotals) {
				sql= "UPDATE siig_t_tracciamento SET nr_rec_shape=" + total			
					+ ",nr_rec_storage=" + (total - errors)
					+ ",nr_rec_scartati=" + errors + "WHERE id_tracciamento="
					+ trace;
			} else {
				sql= "UPDATE siig_t_tracciamento SET nr_rec_scartati=nr_rec_scartati+" + errors + ",nr_rec_storage=nr_rec_storage-" + errors + " WHERE id_tracciamento="
						+ trace;
			}
			
			DbUtils.executeSql(conn, transaction, sql, true);
			
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}
			if(transaction != null) {
				transaction.close();
			}
		}
	}

	/**
	 * 
	 */
	public void dispose() {
		if(processSequenceManager != null) {
			processSequenceManager.disposeManager();
			processSequenceManager = null;
		}
		
		if(traceSequenceManager != null) {
			traceSequenceManager.disposeManager();
			traceSequenceManager = null;
		}
	}

	public void removeImports(String inputTypeName) throws IOException {
		Transaction transaction = null;	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Set<Integer> idTracciamento = new HashSet<Integer>();
		Set<Integer> idProcesso = new HashSet<Integer>();
		try {
			transaction = new DefaultTransaction();
			conn = dataStore.getConnection(transaction);				
		
			String sql = "SELECT id_tracciamento,fk_processo FROM siig_t_tracciamento where nome_file='"
					+ inputTypeName + "'";
			
									
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				idTracciamento.add(rs.getInt(1));
				idProcesso.add(rs.getInt(2));
			}
			rs.close();
			stmt.close();
			for(int id : idTracciamento) {
				DbUtils.executeSql(conn, transaction, "delete from siig_t_log where id_tracciamento="+id, false);
				DbUtils.executeSql(conn, transaction, "delete from siig_t_tracciamento where id_tracciamento="+id, false);
			}
			for(int id : idProcesso) {
				DbUtils.executeSql(conn, transaction, "delete from siig_t_processo where id_processo="+id, false);
			}
			transaction.commit();
		} catch(SQLException e) {
			transaction.rollback();
			throw new IOException(e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				if(transaction != null) {
					transaction.close();
				}
			} catch (SQLException e) {
				throw new IOException(e);
			}
			
		}
		
	}
	
	
	
	
}
