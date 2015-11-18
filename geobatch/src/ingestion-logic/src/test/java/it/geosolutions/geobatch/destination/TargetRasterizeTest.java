package it.geosolutions.geobatch.destination;

import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.rasterize.TargetRasterizeProcess;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TargetRasterizeTest extends DestinationMemoryTest{

	@Before
	public void before() throws Exception { 

	};

	private void writeXmlConfig(String xmlSource, File output) throws Exception{
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

	@Ignore
	@Test
	public void rasterizeBNU(){
		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String shpName = "BZ_BNU-ASOTT_C_20130521_01";
			String outputFilePath = System.getProperty("java.io.tmpdir")+"/RASTERIZE_OUTPUT/BZ/acque_sotterranee.tif";
			File configDir = new File(classLoader.getResource("test-data/raster_processing/").getFile());
			File outputDir = new File(System.getProperty("java.io.tmpdir")+"/RASTERIZE_OUTPUT/");
			File xmlFile = new File(classLoader.getResource("test-data/raster_processing/rasterizeBNU.xml").getFile());

			//Create rasterizeBNU.xml file content
			String xmlSource = "" +
					"<GdalRasterize>" +
					"<shapefilename>"+shpName+"</shapefilename>" +
					"<shapefilepath>"+configDir.getAbsolutePath()+"\\"+shpName+"\\</shapefilepath>" +
					"<baseOutputPath>"+outputDir.getAbsolutePath()+"\\</baseOutputPath>" +
					"<targetValue>1</targetValue>" +
					"</GdalRasterize>";

			writeXmlConfig(xmlSource,xmlFile);			

			if(outputDir.exists()){
				FileUtils.deleteDirectory(outputDir);
			}
			outputDir.mkdir();

			TargetRasterizeProcess targetRasterizeProcess = new TargetRasterizeProcess(
					shpName, new ProgressListenerForwarder(null),
					null, null);
			targetRasterizeProcess.execute(configDir,new File(System.getProperty("java.io.tmpdir")),outputDir,xmlFile, null);

			checkFile(outputFilePath);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			Assert.assertFalse(true);
		}
	}
	
	@Ignore
	@Test
	public void rasterizeBU(){
		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String shpName = "BZ_BU-ACOMM_C_20130906_02";
			String outputFilePath = System.getProperty("java.io.tmpdir")+"/RASTERIZE_OUTPUT/BZ/strutture_commerciali.tif";
			File configDir = new File(classLoader.getResource("test-data/raster_processing/").getFile());
			File outputDir = new File(System.getProperty("java.io.tmpdir")+"/RASTERIZE_OUTPUT/");
			File xmlFile = new File(classLoader.getResource("test-data/raster_processing/rasterizeBU.xml").getFile());

			//Create rasterizeBU.xml file content
			String xmlSource = "" +
					"<GdalRasterize>" +
					"	<shapefilename>"+shpName+"</shapefilename>" +
					"	<shapefilepath>"+configDir.getAbsolutePath()+"\\"+shpName+"\\</shapefilepath>" +
					"	<baseOutputPath>"+outputDir.getAbsolutePath()+"\\</baseOutputPath>" +
					"	<attributeToNormalize>N_ADDETTI</attributeToNormalize>" +
					"</GdalRasterize>";

			writeXmlConfig(xmlSource,xmlFile);			

			if(outputDir.exists()){
				FileUtils.deleteDirectory(outputDir);
			}
			outputDir.mkdir();

			TargetRasterizeProcess targetRasterizeProcess = new TargetRasterizeProcess(
					shpName, new ProgressListenerForwarder(null),
					null, null);
			targetRasterizeProcess.execute(configDir,new File(System.getProperty("java.io.tmpdir")),outputDir,xmlFile, null);

			checkFile(outputFilePath);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			Assert.assertFalse(true);
		}

	}

	@Override
	protected void checkData(SimpleFeature feature) {
		
	}

}
