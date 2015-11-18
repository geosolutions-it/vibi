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
import it.geosolutions.geobatch.destination.common.utils.FeatureLoaderUtils;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.zeroremoval.ZeroRemovalComputation;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class ZeroRemovalTest extends DestinationMemoryTest{

	@Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"zero_test_data"});
	}

	@Test
	public void test() throws IOException {
		ZeroRemovalComputation zeroRemoval = createProcess("RP_C_ZURB_20130613");
		zeroRemoval.removeZeros(null, 1, false, null);	
		checkFeature("siig_geo_ln_arco_1", 12);
		checkAllData();
	}

	/**
	 * @return
	 */
	private ZeroRemovalComputation createProcess(String inputTypeName) {
		ZeroRemovalComputation process = new ZeroRemovalComputation(
				inputTypeName, new ProgressListenerForwarder(null),
				metadataHandler, dataStore);
		return process;
	}
	
	@Override
	protected void checkData(SimpleFeature feature) {
		
	}
	
	protected void checkAllData() {
		List<SimpleFeature> features = FeatureLoaderUtils.loadFeatures(dataStore, "siig_geo_ln_arco_1");
		Double nr_incidenti_sum = 0d;
		Double nr_incidenti_elab_sum = 0d;
		for(SimpleFeature f : features){
			LOGGER.info(f.toString());
			BigDecimal nr_incidenti = (BigDecimal) f.getAttribute("nr_incidenti");
			BigDecimal nr_incidenti_elab = new BigDecimal((Double)f.getAttribute("nr_incidenti_elab"));
			BigDecimal length = (BigDecimal) f.getAttribute("lunghezza");
			nr_incidenti_sum = nr_incidenti_sum + nr_incidenti.doubleValue() * length.doubleValue();
			nr_incidenti_elab_sum = nr_incidenti_elab_sum + nr_incidenti_elab.doubleValue() * length.doubleValue();
		}
		LOGGER.info("nr_incidenti_sum = " + Math.rint(nr_incidenti_sum));
		LOGGER.info("nr_incidenti_elab_sum = " + Math.rint(nr_incidenti_elab_sum));
		Assert.assertTrue(Math.rint(nr_incidenti_sum.doubleValue()) == Math.rint(nr_incidenti_elab_sum.doubleValue()));		
	}

}
