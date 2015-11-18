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
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.vulnerability.RiskComputation;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class RiskTest extends DestinationMemoryTest{

	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"risk_test_data"}, new RiskTestMemoryMockDataStore());
	}

	/**
	 * Test the prefetchRiskAtLevel() method
	 * The "risk_test_data" contains records related to partner ID = 1
	 * We load the test data setting the partner code to "RP" ("RP" = 1)
	 * @throws Exception
	 */
	@Test
	public void testPrefetchRiskAtLevel() throws Exception {
	    // Setup
		RiskComputation riskComputation = createProcess("RP_C_Grafo_20130917");	
		// Run 
		riskComputation.prefetchRiskAtLevel(3, 1, 1, 26, 100, "1,2,3,4,5,6,7,8,9,10", "1,2,3,4,5,6,7,8,9,10,11", "0,1", "1,2,3,4,5", "fp_scen_centrale", "UPDATE", null, false, false);
		// Test
		checkFeature("siig_t_elab_standard_1", 12);
	}

	private RiskComputation createProcess(String inputTypeName) {
		RiskComputation riskComputation = new RiskComputation(inputTypeName,
				new ProgressListenerForwarder(null),
				metadataHandler, dataStore);
		return riskComputation;
		
	}

	@Override
	protected void checkData(SimpleFeature feature) {

	}

}
