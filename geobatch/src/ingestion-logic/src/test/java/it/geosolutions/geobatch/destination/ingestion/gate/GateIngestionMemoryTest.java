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
package it.geosolutions.geobatch.destination.ingestion.gate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.geosolutions.geobatch.catalog.impl.TimeFormat;
import it.geosolutions.geobatch.catalog.impl.configuration.TimeFormatConfiguration;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.ingestion.GateIngestionProcess;
import it.geosolutions.geobatch.destination.ingestion.gate.dao.TransitDao;
import it.geosolutions.geobatch.destination.ingestion.gate.dao.impl.TransitDaoMemoryImpl;
import it.geosolutions.geobatch.destination.ingestion.gate.model.ExportData;
import it.geosolutions.geobatch.destination.ingestion.gate.model.Transit;
import it.geosolutions.geobatch.destination.ingestion.gate.model.TransitBeanTest;
import it.geosolutions.geobatch.destination.ingestion.gate.model.Transits;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXB;

import org.apache.commons.io.FileUtils;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Gate ingestion test:
 * <ol>
 * <li>Insert a gate for testing proposal</li>
 * <li>Generate a xml test file with {@link GateIngestionMemoryTest#numberOrTransits} transits</li>
 * <li>Execute a {@link GateIngestionProcess} with the xml file</li>
 * <li>Check if data it's correctly inserted</li>
 * <li> Remove test data</li>
 * </ol>
 * 
 * @author adiaz
 */
public class GateIngestionMemoryTest extends DestinationMemoryTest {

	/**
	 * Separator
	 */
	public static final String SEPARATOR = System.getProperty("file.separator");
	
	/**
	 * Random to generate magic keys and random strings
	 */
	private static final Random RANDOM = new Random();
	
	/**
	 * Magic key for the id of the inserted data
	 */
	private Long magicKey;
	
	/**
	 * Test gate id
	 */
	private Long FAKE_GATE_ID;
	
	/**
	 * Description for the fake gate
	 */
	private String FAKE_GATE_KEY = "TEST_GATE";
	
	/**
	 * Number of transits to be inserted
	 */
	private Integer numberOrTransits = new Integer(50);
	
	/**
	 * DAO to store transit objects
	 */
	private TransitDao transitDao;
	
	/**
	 * Dummy time format. Use default configuration
	 */
	private TimeFormat timeFormat = new TimeFormat(null, null, null, null);
	
	/**
	 * Initialization of data store
	 * 
	 * @throws Exception if create fake gate throws an exception 
	 */
	@Before
	public void init() throws Exception {
	    String [] extraData = {};
	    initTestWithData(extraData);
	    transitDao = new TransitDaoMemoryImpl((MemoryDataStore) dataStore);
	    createFakeGate();
	}
	
	protected String getFixtureId() {
	    return "destination";
	}
	
	/**
	 * Test import gates process
	 */
	@Test
	public void testImportGatesProcess() {
	
	    File file;
	    List<Long> ids = null;
	    boolean failCleanning = false;
	    try {
	
	        // Prepare process
	        file = getTestFile(FAKE_GATE_ID, numberOrTransits);
	        GateIngestionProcess gateIngestion = new GateIngestionProcess("A00_20131016-180030",
	                new ProgressListenerForwarder(null), metadataHandler,
	                dataStore, file, new TimeFormatConfiguration(null, null, null));
	
	        // process execution
	        ids = gateIngestion.importGates(false, false, "", "");
	
	        // Check if was inserted correctly
	        checkData(ids, file);
	
	    } catch (Exception e) {
	        fail("Exception on gates ingestion");
	        e.printStackTrace();
	    } finally {
	        // clean inserted data
	        if(ids != null){
	            try {
	                cleanUp(ids);
	            } catch (Exception e) {
	                failCleanning = true;
	            }
	        }
	        if (metadataHandler != null) {
	            metadataHandler.dispose();
	        }
	
	        if (dataStore != null) {
	            dataStore.dispose();
	        }
	    }
	    
	    if(failCleanning){
	        fail("Fail on clean up");
	    }
	}
	
	/**
	 * Create a fake gate to make the test
	 * @throws Exception 
	 */
	private void createFakeGate() throws Exception {
	    // it's numeric(3,0)
	    FAKE_GATE_ID = new Long(RANDOM.nextInt(1000));
	    // create test gate
	    transitDao.createGate(FAKE_GATE_ID, FAKE_GATE_KEY);
	}
	
	/**
	 * Check if the data inserted its correct
	 * 
	 * @param ids of the inserted data
	 * @param file used to execute the process
	 * @throws Exception 
	 */
	private void checkData(List<Long> ids, File file) throws Exception {
	    // the number of inserts it's OK
	    assertEquals(ids.size(), (int) numberOrTransits);
	    // check one by one
	    int index = 0;
	    ExportData exportedData = JAXB.unmarshal(file, ExportData.class);
	    for(Transit transit: exportedData.getTransits().get(0).getTransit()){
	        checkData(ids.get(index++), transit);
	    }
	}
	
	/**
	 * Check if exists one transit with all data in the data store
	 * 
	 * @param idTransit id of the transit in the data store
	 * @param transit bean with the data
	 * @throws Exception
	 */
	private void checkData(Long idTransit, Transit transit) throws Exception {
	    @SuppressWarnings("rawtypes")
	    FeatureCollection fc = dataStore.getFeatureSource("siig_gate_t_dato")
	            .getFeatures();
	    @SuppressWarnings("unchecked")
	    FeatureIterator<Feature> fi = fc.features();
	    boolean found = false;
	    while (fi.hasNext()) {
	        Feature feature = fi.next();
	        // check common data
	        if (feature.getProperty("id_dato").getValue().toString()
	                .equals(idTransit.toString())
	                && feature.getProperty("flg_corsia").getValue().toString()
	                        .equals(transit.getCorsia().toString())
	                && feature.getProperty("flg_direzione").getValue().toString()
	                        .equals(transit.getDirezione())	               
	                && feature.getProperty("codice_onu").getValue().toString()
	                        .equals(transit.getOnuCode())) {
	            // check dates
	            Timestamp arriveDate = (Timestamp) feature.getProperty(
	                    "data_rilevamento").getValue();
	            Object receiptDate = feature.getProperty("data_ricezione")
	                    .getValue();
	            if (timeFormat.getTimeStamp(transit.getDataRilevamento()).getTime() == arriveDate
	                    .getTime() && timeFormat.isToday(receiptDate.toString())) {
	                found = true;
	                break;
	            }
	        }
	    }
	    assertTrue(found);
	}
	
	/**
	 * Clean up the inserted data for the test
	 * 
	 * @param insertedTransitsIds
	 * @throws Exception
	 */
	private void cleanUp(List<Long> insertedTransitsIds) throws Exception {
	    transitDao.cleanAll(insertedTransitsIds);
	    // clean fake gate
	    transitDao.deleteGate(FAKE_GATE_ID);
	}
	
	/**
	 * Generate a test file with the content tested on
	 * {@link TransitBeanTest#TEST_XML}
	 * 
	 * @return
	 * @throws IOException
	 */
	private File getTestFile(long idGate, int transitNumbers) throws IOException {
	    File file = new File(FileUtils.getTempDirectory() + SEPARATOR
	            + "A00_20131016-180030.xml");
	
	    if (!file.exists())
	        file.createNewFile();
	
	    ExportData exportData = generateTestData(idGate, transitNumbers);
	
	    JAXB.marshal(exportData, file);
	
	    return file;
	}
	
	private ExportData generateTestData(long idGate, int transitNumber) {
	
	    // Magic key for the id
	    magicKey = RANDOM.nextLong();
	
	    // Generate transits
	    Transits transits = new Transits();
	
	    // parameters to be generated in this function
	    boolean inverse = false;
	    String kemler = null;
	    String onu = null;
	
	    for (int i = 0; i < transitNumber; i++) {
	        if (!inverse) {
	            // Generate different data each 2 transits (enter and return)
	            kemler = RANDOM.nextInt(100) + "_k";
	            onu = RANDOM.nextInt(100) + "_o";
	        }
	
	        // create a new transit
	        Transit transit = new Transit();
	        transit.setIdGate(idGate);
	        transit.setIdTransito(magicKey + i);
	
	        // Time it's different each time
	        transit.setDataRilevamento(timeFormat.getDate(new Date()));
	
	        // direction
	        transit.setDirezione(inverse ? "1" : "0");
	
	        // lane (always 0)
	        transit.setCorsia(0);
	
	        // genrated
	        transit.setKemlerCode(kemler);
	        transit.setOnuCode(onu);
	
	        transits.getTransit().add(transit);
	
	        // inverse direction
	        inverse = !inverse;
	    }
	
	    // Generate the export fakeData
	    ExportData eD = new ExportData();
	    eD.getTransits().add(transits);
	
	    return eD;
	}
	
	@Override
	protected void checkData(SimpleFeature feature) {
	
	}

}
