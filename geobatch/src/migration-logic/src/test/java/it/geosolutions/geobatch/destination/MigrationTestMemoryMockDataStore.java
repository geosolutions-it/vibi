package it.geosolutions.geobatch.destination;

import java.sql.Connection;

import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockDatabaseMetaData;
import com.mockrunner.mock.jdbc.MockResultSet;

public class MigrationTestMemoryMockDataStore extends MemoryDataStore{

	protected static final Logger LOGGER = LoggerFactory.getLogger(MigrationTestMemoryMockDataStore.class);

	private Connection connection = new MockConnection();

	public Connection getConnection(Transaction transaction){
		return connection;
	}

	public MigrationTestMemoryMockDataStore(){
		try{
			
			MockDatabaseMetaData metaData = (MockDatabaseMetaData) ((MockConnection)connection).getMetaData();
			
			MockResultSet importedKeys = new MockResultSet("siig_t_bersaglio_non_umano");			
			importedKeys.addColumn("FKTABLE_NAME");
			importedKeys.addColumn("FKCOLUMN_NAME");
			importedKeys.addColumn("PKTABLE_NAME");
			importedKeys.addColumn("PKCOLUMN_NAME");				
			importedKeys.addRow(new String[]{"siig_t_bersaglio_non_umano","fk_bers_non_umano_pl","siig_geo_bers_non_umano_pl","idgeo_bers_non_umano_pl"});
			importedKeys.addRow(new String[]{"siig_t_bersaglio_non_umano","fk_bers_non_umano_ln","siig_geo_bers_non_umano_ln","idgeo_bers_non_umano_ln"});
			importedKeys.addRow(new String[]{"siig_t_bersaglio_non_umano","fk_bers_non_umano_pt","siig_geo_bers_non_umano_pt","idgeo_bers_non_umano_pt"});
			metaData.setImportedKeys(connection.getCatalog(), null, "siig_t_bersaglio_non_umano", importedKeys);
			
			importedKeys = new MockResultSet("siig_t_bersaglio_umano");
			importedKeys.addColumn("FKTABLE_NAME");
			importedKeys.addColumn("FKCOLUMN_NAME");
			importedKeys.addColumn("PKTABLE_NAME");
			importedKeys.addColumn("PKCOLUMN_NAME");	
			importedKeys.addRow(new String[]{"siig_t_bersaglio_umano","fk_bersaglio_umano_pl","siig_geo_bersaglio_umano_pl","idgeo_bersaglio_umano_pl"});
			importedKeys.addRow(new String[]{"siig_t_bersaglio_umano","fk_bersaglio_umano_pt","siig_geo_bersaglio_umano_pt","idgeo_bersaglio_umano_pt"});
			metaData.setImportedKeys(connection.getCatalog(), null, "siig_t_bersaglio_umano", importedKeys);
			
			MockResultSet exportedKeys = new MockResultSet("siig_geo_ln_arco_1");			
			exportedKeys.addColumn("FKTABLE_NAME");
			exportedKeys.addColumn("FKCOLUMN_NAME");
			exportedKeys.addColumn("PKTABLE_NAME");
			exportedKeys.addColumn("PKCOLUMN_NAME");			
			exportedKeys.addRow(new String[]{"siig_r_arco_1_dissesto","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_1_scen_tipobers","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_1_sostanza","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_tipovei_geoarco1","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_vulnerabilita_1","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_elab_standard_1","id_geo_arco","siig_geo_ln_arco_1","id_geo_arco"});
			metaData.setExportedKeys(connection.getCatalog(), null, "siig_geo_ln_arco_1", exportedKeys);
			
			exportedKeys = new MockResultSet("siig_geo_ln_arco_2");			
			exportedKeys.addColumn("FKTABLE_NAME");
			exportedKeys.addColumn("FKCOLUMN_NAME");
			exportedKeys.addColumn("PKTABLE_NAME");
			exportedKeys.addColumn("PKCOLUMN_NAME");			
			exportedKeys.addRow(new String[]{"siig_r_arco_2_dissesto","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_2_scen_tipobers","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_2_sostanza","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_tipovei_geoarco2","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_vulnerabilita_2","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_elab_standard_2","id_geo_arco","siig_geo_ln_arco_2","id_geo_arco"});
			metaData.setExportedKeys(connection.getCatalog(), null, "siig_geo_ln_arco_2", exportedKeys);
			
			exportedKeys = new MockResultSet("siig_geo_pl_arco_3");			
			exportedKeys.addColumn("FKTABLE_NAME");
			exportedKeys.addColumn("FKCOLUMN_NAME");
			exportedKeys.addColumn("PKTABLE_NAME");
			exportedKeys.addColumn("PKCOLUMN_NAME");			
			exportedKeys.addRow(new String[]{"siig_r_arco_3_dissesto","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_3_scen_tipobers","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_arco_3_sostanza","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_r_tipovei_geoarco3","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_vulnerabilita_3","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			exportedKeys.addRow(new String[]{"siig_t_elab_standard_3","id_geo_arco","siig_geo_pl_arco_3","id_geo_arco"});
			metaData.setExportedKeys(connection.getCatalog(), null, "siig_geo_pl_arco_3", exportedKeys);

		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}

	}


}