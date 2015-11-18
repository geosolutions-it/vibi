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
import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.destination.common.utils.SequenceManager;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.streetuser.StreetUserComputation;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class StreetUserTest extends DestinationMemoryTest{

	private static final String sampleInput = "RP_C_Grafo_20130917";
	
	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"streetuser_test_data"});
	}
	
	@Ignore
	public void testLevel1() throws IOException {
		StreetUserComputation streetUserComputation = createProcess(sampleInput);	
		streetUserComputation.executeArc(1, false, null, false);
		checkFeature("siig_r_scen_vuln_1", 8);
	}

	@Ignore
	public void testLevel2() throws IOException {
		StreetUserComputation streetUserComputation = createProcess(sampleInput);	
		streetUserComputation.executeArc(2, false, null, false);
		checkFeature("siig_r_scen_vuln_2", 8);
	}

	@Ignore
	public void testGridLevel3() throws IOException {
		StreetUserComputation streetUserComputation = createProcess(sampleInput);	
		streetUserComputation.executeCell(3, false, null, false);
		checkFeature("siig_r_scen_vuln_3", 8);
	}

	/**
	 * @return
	 */
	private StreetUserComputation createProcess(String inputTypeName) {
		StreetUserComputation process = new StreetUserComputation(inputTypeName,
				new ProgressListenerForwarder(null),metadataHandler,dataStore);
		process.setSequenceManager(new SequenceManager(dataStore, "") {
			int value = 1;
			@Override
			public long retrieveValue() throws IOException {
				return value++;
			}

		});
		return process;
	}
	@Test
	public void test() {
		assertTrue(true);
	}

	@Override
	protected void checkData(SimpleFeature feature) {
		// TODO Auto-generated method stub
		
	}

}
