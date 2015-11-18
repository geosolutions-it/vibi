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
package it.geosolutions.geobatch.destination;

//import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.destination.common.utils.SequenceManager;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class TargetTest extends DestinationMemoryTest{

	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"targets_test_data"});
	}

	@Test
	public void test_BNU_AAGR() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-AAGR_C_20130904_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_ABOSC() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-ABOSC_C_20130904_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_APROT() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-APROT_C_20130906_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_ASOTT() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-ASOTT_C_20130521_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_ASUP() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-ASUP_C_20130904_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_BCULT() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-BCULT_C_20130524_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BNU_ZURB() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BNU-ZURB_C_20130911_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bers_non_umano_pl", 5);
		checkFeature("siig_t_bersaglio_non_umano", 5);
	}

	@Test
	public void test_BU_ACOMM() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-ACOMM_C_20130906_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 2);
		checkFeature("siig_t_bersaglio_umano", 2);
	}

	@Test
	public void test_BU_AIND() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-AIND_C_20130903_01");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 5);
		checkFeature("siig_t_bersaglio_umano", 5);
	}

	@Test
	public void test_BU_ASAN() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-ASAN_C_20130606_01");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 5);
		checkFeature("siig_t_bersaglio_umano", 5);
	}

	@Test
	public void test_BU_ASCOL() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-ASCOL_C_20130531_01");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 5);
		checkFeature("siig_t_bersaglio_umano", 5);
	}

	@Test
	public void test_BU_PRES() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-PRES_C_20130911_01");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 5);
		checkFeature("siig_t_bersaglio_umano", 5);
	}

	@Test
	public void test_BU_PTUR() throws IOException {
		TargetIngestionProcess targetIngestion = createProcess("BZ_BU-PTUR_C_20130604_02");
		targetIngestion.importTarget(null, false);		
		checkFeature("siig_geo_bersaglio_umano_pl", 5);
		checkFeature("siig_t_bersaglio_umano", 5);
	}

	/**
	 * @return
	 */
	private TargetIngestionProcess createProcess(String inputTypeName) {
		TargetIngestionProcess process = new TargetIngestionProcess(inputTypeName,
				new ProgressListenerForwarder(null), metadataHandler, dataStore);
		process.setSequenceManager(new SequenceManager(dataStore, "") {
			int value = 1;
			@Override
			public long retrieveValue() throws IOException {
				return value++;
			}

		});
		return process;
	}

	@Override
	protected void checkData(SimpleFeature feature) {
		// TODO Auto-generated method stub
		
	}

}
