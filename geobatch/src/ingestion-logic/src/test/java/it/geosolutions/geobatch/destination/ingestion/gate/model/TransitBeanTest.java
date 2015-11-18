/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.ingestion.gate.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

/**
 * Transit bean unit test
 * 
 * @author adiaz
 */
public class TransitBeanTest {

/**
 * Test xml code
 */
public static String TEST_XML = ""
        + "<ExportData xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
        + "    <Transits>" 
        + "    <Transit>" 
        + "      <GateId>2004</GateId>"
        + "      <TransitId>81831</TransitId>"
        + "      <Timestamp>2013-10-23T10:34:00+01:00</Timestamp>"
        + "      <Lane>0</Lane>" 
        + "      <Direction>0</Direction>"
        + "      <KemlerCode />" 
        + "      <OnuCode />" 
        + "    </Transit>"
        + "    <Transit>" 
        + "      <GateId>2004</GateId>"
        + "      <TransitId>81832</TransitId>"
        + "      <Timestamp>2013-10-23T10:34:00+01:00</Timestamp>"
        + "      <Lane>0</Lane>" 
        + "      <Direction>1</Direction>"
        + "      <KemlerCode />" 
        + "      <OnuCode />" 
        + "    </Transit>"
        + "  </Transits>" 
        + "</ExportData>";

private JAXBContext context;

private Marshaller marshaller;

private Unmarshaller unmarshaller;

@Before
public void setUp() throws Exception {
    context = JAXBContext.newInstance(getClass().getPackage().getName());
    marshaller = context.createMarshaller();
    unmarshaller = context.createUnmarshaller();
}

/**
 * Marshal from code and unmarshal from example xml file and test if the result
 * it's the same
 */
@Test
public void testTransitMarshalUnmarshal() {
    Transits fromCode = new Transits();
    Transit transit = new Transit();
    transit.setIdGate(new Long(2004));
    transit.setIdTransito(new Long(81831));
    transit.setDataRilevamento("2013-10-23T10:34:00+01:00");
    transit.setCorsia(new Integer(0));
    transit.setDirezione("0");
    fromCode.getTransit().add(transit);
    transit = new Transit();
    transit.setIdGate(new Long(2004));
    transit.setIdTransito(new Long(81832));
    transit.setDataRilevamento("2013-10-23T10:34:00+01:00");
    transit.setCorsia(new Integer(0));
    transit.setDirezione("1");
    fromCode.getTransit().add(transit);
    StringWriter writer = new StringWriter();
    try {
        marshaller.marshal(fromCode, writer);
        assertNotNull(writer);
        assertNotNull(writer.toString());
        Transits fromXml = ((ExportData) unmarshaller
                .unmarshal(new StringReader(TEST_XML))).getTransits().get(0);
        assertNotNull(fromXml);
        assertEquals(fromXml.getTransit().size(), fromCode.getTransit().size());
        for (int i = 0; i < fromXml.getTransit().size(); i++) {
            Transit transitFromXml = fromXml.getTransit().get(i);
            Transit transitFromCode = fromCode.getTransit().get(i);
            check(transitFromXml.getCorsia(), transitFromCode.getCorsia());
            check(transitFromXml.getDataRilevamento(),
                    transitFromCode.getDataRilevamento());
            check(transitFromXml.getDirezione(), transitFromCode.getDirezione());
            check(transitFromXml.getIdGate(), transitFromCode.getIdGate());
            check(transitFromXml.getIdTransito(),
                    transitFromCode.getIdTransito());
            check(transitFromXml.getKemlerCode(),
                    transitFromCode.getKemlerCode());
            check(transitFromXml.getOnuCode(), transitFromCode.getOnuCode());
        }
    } catch (JAXBException e) {
        // fail
        e.printStackTrace();
        fail("Error on marshal or unmarshal");
    }
}

/**
 * Generate sql code for insert kemler and onu codes
 */
@Test
public void testGenerateInsert() {
    try {
        ExportData ed = (ExportData) unmarshaller.unmarshal(new File("src/test/resources/00_20131218_141116.xml"));

        Transits fromXml = ed.getTransits().get(0);
        
        String kemlerT = "insert into siig_d_kemler (codice_kemler) values (':kemler');";
        String onuT = "insert into siig_d_onu (codice_onu, codice_kemler) values (':onu', ':kemler');";


        Map<String, String> insertK = new HashMap<String, String>();
        Map<String, String> insertC = new HashMap<String, String>();
        for (int i = 0; i < fromXml.getTransit().size(); i++) {
            String kemler = fromXml.getTransit().get(i).getKemlerCode();
            String onu = fromXml.getTransit().get(i).getOnuCode();
            insertK.put(kemler, kemlerT.replace(":kemler", kemler));
            insertC.put(onu, onuT.replace(":kemler", kemler).replace(":onu", onu));
        }
        
        String insertAll = "";
        insertAll +="-- Kemler code missed data";
        for(String kemler: insertK.keySet()){
            insertAll += "\n" + insertK.get(kemler);
        }
        insertAll += "\n"; 
        insertAll += "-- Onu code missed data (we suppose if one transit have onu and kemler code, the relation exists between those codes)";
        for(String onu: insertC.keySet()){
            insertAll += "\n" + insertC.get(onu);
        }
        // show sql code in console
        System.out.println("### GENERATED SQL CODE  ###");
        System.out.println(insertAll);
        System.out.println("### EoF SQL CODE  ###");
        
    } catch (JAXBException e) {
        // fail
        e.printStackTrace();
        fail("Error on marshal or unmarshal");
    }
}

/**
 * If o1 or o2 are empty Strings an the other one it's null, don't assertEquals.
 * Otherwise do an assertEquals between o1 and o2
 * 
 * @param o1
 * @param o2
 */
private void check(Object o1, Object o2) {
    if (o1 != null && o2 == null) {
        if (o1 instanceof String && "".equals(o1)) {
        } else {
            assertEquals(o1, o2);
        }
    } else if (o2 != null && o1 == null) {
        check(o2, o1);
    } else {
        assertEquals(o1, o2);
    }
}

}
