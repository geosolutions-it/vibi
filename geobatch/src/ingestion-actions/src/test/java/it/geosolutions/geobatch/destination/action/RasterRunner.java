package it.geosolutions.geobatch.destination.action;

import it.geosolutions.geobatch.destination.datamigration.RasterMigration;
import it.geosolutions.geobatch.destination.rasterize.TargetRasterizeProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class RasterRunner {
	public static void main(String[] args) {
		

		String shpName = "RP_BNU-ASUP_C_20130424_03";
		File configDir = new File("J:\\Develop\\destination\\dati_temp");
		File outputDir = new File("J:\\Develop\\destination\\rasters_temp");
		File xmlFile = new File("J:\\rasterizeBNU.xml");

		//Create rasterizeBU.xml file content
		String xmlSource = "" +
				"<GdalRasterize>" +
				"	<shapefilename>"+shpName+"</shapefilename>" +
				"	<shapefilepath>"+configDir.getAbsolutePath()+"\\"+shpName+"\\</shapefilepath>" +
				"	<baseOutputPath>"+outputDir.getAbsolutePath()+"\\</baseOutputPath>" +
				"	<targetValue>1</targetValue>" +
				"	<attributeToNormalize>(N_ADDETTI+N_UTENTI)</attributeToNormalize>" +
				"</GdalRasterize>";

		try {
			writeXmlConfig(xmlSource,xmlFile);
			TargetRasterizeProcess targetRasterizeProcess = new TargetRasterizeProcess(
					shpName, new ProgressListenerForwarder(null),
					null, null);
			targetRasterizeProcess.execute(configDir,new File(System.getProperty("java.io.tmpdir")),outputDir,xmlFile, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RasterMigration migration = new RasterMigration("ALL", "J:\\Develop\\destination\\rasters_out",
				"J:\\Develop\\destination\\rasters_prod_lose", null, null, new ProgressListenerForwarder(null));
        try {
			migration.execute("C", false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		
	}
	
	private static void writeXmlConfig(String xmlSource, File output) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

		// Write the parsed document to an xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		DOMSource source = new DOMSource(doc);

		StreamResult result =  new StreamResult(output);
		transformer.transform(source, result);
	}
}