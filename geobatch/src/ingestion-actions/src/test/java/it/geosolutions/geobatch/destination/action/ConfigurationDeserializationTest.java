package it.geosolutions.geobatch.destination.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.geobatch.configuration.flow.file.FileBasedFlowConfiguration;
import it.geosolutions.geobatch.destination.action.arcingestion.ArcIngestionConfiguration;
import it.geosolutions.geobatch.destination.action.rasterize.RasterizeConfiguration;
import it.geosolutions.geobatch.destination.action.risk.RiskConfiguration;
import it.geosolutions.geobatch.destination.action.streetuser.StreetUserConfiguration;
import it.geosolutions.geobatch.destination.action.targetingestion.TargetIngestionConfiguration;
import it.geosolutions.geobatch.destination.action.vulnerability.VulnerabilityConfiguration;
import it.geosolutions.geobatch.destination.action.zeroremoval.ZeroRemovalConfiguration;
import it.geosolutions.geobatch.registry.AliasRegistry;
import it.geosolutions.geobatch.xstream.Alias;

import java.io.File;
import java.io.StringWriter;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thoughtworks.xstream.XStream;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:test-context.xml"})

public class ConfigurationDeserializationTest extends BaseTest{

//	@Autowired
	private AliasRegistry aliasRegistry;

//	@Configuration
//	static class ContextConfiguration {
//
//	}

    protected static ClassPathXmlApplicationContext ctx = null;

    public ConfigurationDeserializationTest() {

        synchronized (ConfigurationDeserializationTest.class) {
            if (ctx == null) {
                String[] paths = {
//                    "classpath*:applicationContext.xml"
//                         "applicationContext-test.xml"
                        "classpath:test-context.xml",
                        "classpath*:applicationContext-listeners.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

            }
        }
        aliasRegistry = (AliasRegistry)ctx.getBean("aliasRegistry");
        if(aliasRegistry == null)
            throw new IllegalStateException("AliasRegistry not found");

    }



	@Test
	public void testRoadsDeserialization() throws Exception{
		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);
		File configFile = loadFile("data/roadrunner.xml");

        FileBasedFlowConfiguration configuration = (FileBasedFlowConfiguration)xstream.fromXML(configFile);

        int arccnt = 0;
        int vulncnt = 0;
        int zerocnt = 0;
        int riskcnt = 0;
        int streetusercnt = 0;

		for(ActionConfiguration actionConfiguration : configuration.getEventConsumerConfiguration().getActions()){
			if(actionConfiguration != null) {
                if (actionConfiguration instanceof ArcIngestionConfiguration ){
                    arccnt++;
                } else if (actionConfiguration instanceof VulnerabilityConfiguration ){
                    vulncnt++;
                } else if (actionConfiguration instanceof ZeroRemovalConfiguration ){
                    zerocnt++;
                } else if (actionConfiguration instanceof RiskConfiguration ){
                    riskcnt++;
                } else if (actionConfiguration instanceof StreetUserConfiguration ){
                	streetusercnt++;
                }
            }
		}

        assertEquals(4, arccnt);
        assertEquals(3, zerocnt);
        assertEquals(3, vulncnt);
        assertEquals(3, riskcnt);
        assertEquals(3, streetusercnt);
	}
	
	@Test
	public void testTargetsDeserialization() throws Exception{
		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);
		File configFile = loadFile("data/targetrunner.xml");

        FileBasedFlowConfiguration configuration = (FileBasedFlowConfiguration)xstream.fromXML(configFile);

        int targetcnt = 0;
        int rastercnt = 0;
        
		for(ActionConfiguration actionConfiguration : configuration.getEventConsumerConfiguration().getActions()){
			if(actionConfiguration != null) {
                if (actionConfiguration instanceof TargetIngestionConfiguration ){
                	targetcnt++;
                } else if (actionConfiguration instanceof RasterizeConfiguration ){
                	rastercnt++;
                }
            }
		}

        assertEquals(1, targetcnt);
        assertEquals(1, rastercnt);
	}


    @Test
	public void testArgIngestionSerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        ArcIngestionConfiguration cfg = new ArcIngestionConfiguration("id", "name", "descr");
        cfg.setAggregationLevel(1);
        cfg.setDropInput(false);
        cfg.setOnGrid(false);
        cfg.setClosePhase("A");

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }

    @Test
	public void testVulnerabilitySerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        VulnerabilityConfiguration cfg = new VulnerabilityConfiguration("id", "name", "descr");
        cfg.setAggregationLevel(1);
        cfg.setWriteMode("PURGE_INSERT");
        cfg.setClosePhase("B");

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }

    @Test
	public void testZeroRemovalSerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        ZeroRemovalConfiguration cfg = new ZeroRemovalConfiguration("id", "name", "descr");
        cfg.setAggregationLevel(1);
        cfg.setClosePhase("B");

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }

    @Test
	public void testRiskSerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        RiskConfiguration cfg = new RiskConfiguration("id", "name", "descr");

        cfg.setPrecision(3);
        cfg.setAggregationLevel(3);
        cfg.setProcessing(1);
        cfg.setFormula(29);
        cfg.setTarget(100);
        cfg.setMaterials("1,2,3,4,5,6,7,8,9,10");
        cfg.setScenarios("1,2,3,4,5,6,7,8,9,10,11");
        cfg.setEntities("0,1");
        cfg.setSevereness("1,2,3,4,5");
        cfg.setFpfield("fp_scen_centrale");
        cfg.setWriteMode("PURGE_INSERT");
        cfg.setClosePhase("B");

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }

    @Test
	public void testTargetIngestionSerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        TargetIngestionConfiguration cfg = new TargetIngestionConfiguration("id", "name", "descr");
        cfg.setDropInput(true);

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }
    
    @Test
	public void testTargetRasterizeIngestionSerialization() throws Exception {

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

		RasterizeConfiguration cfg = new RasterizeConfiguration("id", "name", "descr");
        cfg.setBaseOutputPath("basePath");
        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }
    
    @Test
	public void testStreetUserSerialization() throws Exception {

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

		StreetUserConfiguration cfg = new StreetUserConfiguration("id", "name", "descr");
        cfg.setAggregationLevel(1);

        StringWriter writer = new StringWriter();
        xstream.toXML(cfg, writer);
        assertNotNull(writer.toString());
        assertTrue(writer.toString().length() > 0);
    }

}
