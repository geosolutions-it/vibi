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

import it.geosolutions.geobatch.destination.common.utils.SequenceManager;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.ingestion.OriginalArcsIngestionProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class OriginalRoadArcTest extends DestinationMemoryTest{

	private static final String sampleInput = "RP_C_Grafo_20131212_ORIG";
	private static final int SAMPLE_DATA_LAST_YEAR = 2012;

	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"original_arcs_test_data"});
	}

	@Test
	public void testIngestion() throws IOException {
		OriginalArcsIngestionProcess arcIngestion = createProcess();

		arcIngestion.importArcs(null, false);

		checkFeature("RP_C_Grafo_20131212", 27);
	}
	
	@Test
	public void testLastYear() throws IOException {
		OriginalArcsIngestionProcess arcIngestion = createProcess();
		assertEquals(SAMPLE_DATA_LAST_YEAR, arcIngestion.getLastYear());
	}
	
	/**
	 * @return
	 * @throws IOException 
	 */
	private OriginalArcsIngestionProcess createProcess() throws IOException {
		OriginalArcsIngestionProcess process = new OriginalArcsIngestionProcess(sampleInput,
				new ProgressListenerForwarder(null), metadataHandler, dataStore, -1, -1);
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
		assertNotNull(feature.getDefaultGeometry());
		assertNotNull(feature.getAttribute("ID_SEGM_01"));		
		assertNotNull(feature.getAttribute("ID_SEGM_05"));
		assertNotNull(feature.getAttribute("ID_SEGM_10"));
		assertNotNull(feature.getAttribute("ID_ORIG"));
		assertNotNull(feature.getAttribute("LUNGHEZZA"));
		assertNotNull(feature.getAttribute("CFF"));
		assertNotNull(feature.getAttribute("PTERR"));
		assertNotNull(feature.getAttribute("N_CORSIE"));
		assertNotNull(feature.getAttribute("FLG_N_CORS"));
		assertNotNull(feature.getAttribute("COD_PROVINCIA"));
		assertNotNull(feature.getAttribute("PADR"));
		assertNotNull(feature.getAttribute("VELOCITA"));
		assertNotNull(feature.getAttribute("FLG_VELOC"));
		assertNotNull(feature.getAttribute("TGM"));
		assertNotNull(feature.getAttribute("FLG_TGM"));	
		assertNotNull(feature.getAttribute("INCIDENT"));
		assertNotNull(feature.getAttribute("FLG_N_INC"));	
	}
}
