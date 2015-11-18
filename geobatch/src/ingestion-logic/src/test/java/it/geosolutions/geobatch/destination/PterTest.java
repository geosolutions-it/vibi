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
import it.geosolutions.geobatch.destination.ingestion.PterIngestionProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class PterTest  extends DestinationMemoryTest {

	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"pters_test_data"});
	}

	@Override
	protected void checkData(SimpleFeature feature) {
		
	}
	
	@Test
	public void testImport() throws IOException {
		PterIngestionProcess pterIngestion = createProcess("RP_PTER_20131212");
		pterIngestion.importPter(null, false);		
		checkFeature("siig_geo_pl_pter", 5);
	}
	
	/**
	 * @return
	 */
	private PterIngestionProcess createProcess(String inputTypeName) {
		PterIngestionProcess process = new PterIngestionProcess(inputTypeName,
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

}
