package it.geosolutions.geobatch.destination;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;
import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.datamigration.ProductionUpdater;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.data.memory.MemoryDataStore;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

public class MigrationTest  extends DestinationMemoryTest{

	private Ds2dsConfiguration productionUpdaterConfiguration;
	private MemoryDataStore outputDatastore;

	@Override
	protected void checkData(SimpleFeature feature) {
		// TODO Auto-generated method stub
	}

	@Before
	public void before() throws Exception { 	
		this.productionUpdaterConfiguration = new Ds2dsConfiguration("migrationtest", "migrationtest", "migrationtest");
		this.productionUpdaterConfiguration.setFailIgnored(true);
		this.productionUpdaterConfiguration.setPurgeData(true);
		
		FeatureConfiguration source = new FeatureConfiguration();
		Map<String,Serializable> sourceDataStore = new HashMap<String,Serializable>();
		sourceDataStore.put("dbtype", "mockmemory");
		sourceDataStore.put("name", "input");
		sourceDataStore.put("dbdatasfiles", "migration_test_data");	
		source.setDataStore(sourceDataStore);
		this.productionUpdaterConfiguration.setSourceFeature(source);
		
		FeatureConfiguration destination = new FeatureConfiguration();
		Map<String,Serializable> destinationDataStore = new HashMap<String,Serializable>();
		destinationDataStore.put("dbtype", "mockmemory");
		destinationDataStore.put("name", "output");
		destinationDataStore.put("dbdatasfiles", "migration_test_data");	
		destination.setDataStore(destinationDataStore);
		this.productionUpdaterConfiguration.setOutputFeature(destination);

		outputDatastore = (MemoryDataStore)DataStoreFinder.getDataStore(destinationDataStore);
		metadataHandler = new MockMetadataIngestionHandler(outputDatastore);
	}

	@Test
	public void migrateBNU() throws Exception {
		this.productionUpdaterConfiguration.getSourceFeature().setTypeName("RP_BNU-AAGR_C_20130904_02");
		ProductionUpdater productionUpdater = createProcess(productionUpdaterConfiguration.getSourceFeature().getTypeName());		
		productionUpdater.setDs2DsConfiguration(productionUpdaterConfiguration);
		productionUpdater.execute(null, false);
		
		checkFeature("siig_geo_bers_non_umano_ln", 1, this.outputDatastore);
		checkFeature("siig_geo_bers_non_umano_pl", 1, this.outputDatastore);
		checkFeature("siig_geo_bers_non_umano_pt", 1, this.outputDatastore);
		checkFeature("siig_t_bersaglio_non_umano", 1, this.outputDatastore);
	}
	
	@Test
	public void migrateBU() throws Exception {
		this.productionUpdaterConfiguration.getSourceFeature().setTypeName("RP_BU-ACOMM_C_20130906_02");
		ProductionUpdater productionUpdater = createProcess(productionUpdaterConfiguration.getSourceFeature().getTypeName());		
		productionUpdater.setDs2DsConfiguration(productionUpdaterConfiguration);
		productionUpdater.execute(null, false);
		
		checkFeature("siig_geo_bersaglio_umano_pl", 1, this.outputDatastore);
		checkFeature("siig_geo_bersaglio_umano_pt", 1, this.outputDatastore);
		checkFeature("siig_t_bersaglio_umano", 1, this.outputDatastore);
	}
	
	@Test
	public void migrateArcs() throws Exception {
		this.productionUpdaterConfiguration.getSourceFeature().setTypeName("RP_C_Grafo_20130917");
		ProductionUpdater productionUpdater = createProcess(productionUpdaterConfiguration.getSourceFeature().getTypeName());		
		productionUpdater.setDs2DsConfiguration(productionUpdaterConfiguration);
		productionUpdater.execute(null, false);
		
		checkFeature("siig_r_arco_1_dissesto", 1, this.outputDatastore);
		checkFeature("siig_r_arco_1_scen_tipobers", 1, this.outputDatastore);
		checkFeature("siig_r_arco_1_sostanza", 1, this.outputDatastore);		
		checkFeature("siig_r_tipovei_geoarco1", 1, this.outputDatastore);
		checkFeature("siig_r_arco_2_dissesto", 1, this.outputDatastore);
		checkFeature("siig_r_arco_2_scen_tipobers", 1, this.outputDatastore);
		checkFeature("siig_r_arco_2_sostanza", 1, this.outputDatastore);		
		checkFeature("siig_r_tipovei_geoarco2", 1, this.outputDatastore);
		checkFeature("siig_r_arco_3_dissesto", 1, this.outputDatastore);
		checkFeature("siig_r_arco_3_scen_tipobers", 1, this.outputDatastore);
		checkFeature("siig_r_arco_3_sostanza", 1, this.outputDatastore);		
		checkFeature("siig_r_tipovei_geoarco3", 1, this.outputDatastore);		
		checkFeature("siig_geo_ln_arco_1", 1, this.outputDatastore);
		checkFeature("siig_geo_ln_arco_2", 1, this.outputDatastore);
		checkFeature("siig_geo_ln_arco_3", 1, this.outputDatastore);		
		checkFeature("siig_geo_pl_arco_3", 1, this.outputDatastore);

	}
	
	@Test
	public void migrateVulnerability() throws Exception {
		this.productionUpdaterConfiguration.getSourceFeature().setTypeName("RP_C_Grafo_20130917");
		ProductionUpdater productionUpdater = createProcess(productionUpdaterConfiguration.getSourceFeature().getTypeName());		
		productionUpdater.setDs2DsConfiguration(productionUpdaterConfiguration);
		productionUpdater.execute(null, false);
		
		checkFeature("siig_t_vulnerabilita_1", 1, this.outputDatastore);
		checkFeature("siig_t_vulnerabilita_2", 1, this.outputDatastore);
		checkFeature("siig_t_vulnerabilita_3", 1, this.outputDatastore);		
	}
	
	@Test
	public void migratePreComputation() throws Exception {
		this.productionUpdaterConfiguration.getSourceFeature().setTypeName("RP_C_Grafo_20130917");
		ProductionUpdater productionUpdater = createProcess(productionUpdaterConfiguration.getSourceFeature().getTypeName());		
		productionUpdater.setDs2DsConfiguration(productionUpdaterConfiguration);
		productionUpdater.execute(null, false);
		
		checkFeature("siig_t_elab_standard_1", 1, this.outputDatastore);
		checkFeature("siig_t_elab_standard_2", 1, this.outputDatastore);
		checkFeature("siig_t_elab_standard_3", 1, this.outputDatastore);		

	}

	/**
	 * @return
	 */
	private ProductionUpdater createProcess(String inputTypeName) {		
		ProductionUpdater process = new ProductionUpdater(inputTypeName,
				new ProgressListenerForwarder(null),
				metadataHandler,
				this.outputDatastore);
		process.setDs2DsConfiguration(this.productionUpdaterConfiguration);
		return process;
	}

}
