package it.geosolutions.geobatch.destination.rasterize;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.catalog.Identifiable;
import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.IProgressListener;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.task.TaskExecutor;
import it.geosolutions.geobatch.task.TaskExecutorConfiguration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.geotools.jdbc.JDBCDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class TargetRasterizeProcess extends InputObject{

	private final static Logger LOGGER = LoggerFactory.getLogger(TargetRasterizeProcess.class);

	//private static Pattern TYPE_NAME_PARTS_ARCS = Pattern.compile("^([A-Z]{2})_([A-Z]{1})-([A-Za-z]+)_([0-9]{8})$");
	private static Pattern TYPE_NAME_PARTS_TARGETS  = Pattern.compile("^([A-Z]{2})[_-]([A-Z]{2,3})[_-]([A-Z]+)([_-][C|I])?[_-]([0-9]{8})[_-]([0-9]{2})$");
	private static Properties targetTypes = new Properties();
	private static Properties targetNameTypes = new Properties();

	private String inputTypeName;
	private String codicePartner;
	private int partner;
	private int targetType;
	private int priority;
	private final static String fs = "/";

	static {	
		// load mappings from resources
		try {			
			targetTypes.load(TargetRasterizeProcess.class.getResourceAsStream(fs+"targets.properties"));	
			targetNameTypes.load(TargetRasterizeProcess.class.getResourceAsStream(fs+"targets_name.properties"));	
		} catch (IOException e) {
			LOGGER.error("Unable to load configuration: "+e.getMessage(), e);
		}
	}

	public TargetRasterizeProcess(String inputTypeName,
			ProgressListenerForwarder listenerForwarder,
			MetadataIngestionHandler metadataHandler, JDBCDataStore dataStore) {
		super(inputTypeName, listenerForwarder, metadataHandler, dataStore);
		this.inputTypeName = inputTypeName;
	}

	@Override
	protected boolean parseTypeName(String typeName) {
		Matcher m = TYPE_NAME_PARTS_TARGETS.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());		
			targetType = Integer.parseInt(targetTypes.get(m.group(3)).toString());		
			priority = Integer.parseInt(m.group(6).toString());		
			return true;
		}
		return false;
	}

	private List<String> receivedEvents = new ArrayList<String>();

	private IProgressListener listener = new IProgressListener() {

		@Override
		public void terminated() {
			receivedEvents.add("terminated");				
		}

		@Override
		public void started() {
			receivedEvents.add("started");				
		}

		@Override
		public void setTask(String currentTask) {

		}

		@Override
		public void setProgress(float progress) {

		}

		@Override
		public void resumed() {
			receivedEvents.add("resumed");			
		}

		@Override
		public void progressing() {
			receivedEvents.add("progressing");		
		}

		@Override
		public void paused() {
			receivedEvents.add("paused");			
		}

		@Override
		public String getTask() {				
			return null;
		}

		@Override
		public float getProgress() {
			return 0;
		}

		@Override
		public Identifiable getOwner() {				
			return null;
		}

		@Override
		public void failed(Throwable exception) {
			receivedEvents.add("failed");			
		}

		@Override
		public void completed() {
			receivedEvents.add("completed");			
		}
	};

	public void execute(File configDir, File tempDir, File baseTifOutputDir, File eventFile, String processPhase) throws ActionException, IOException{
		LOGGER.debug("Execute Task Executor Action for partner : " + partner);
		
		Queue<FileSystemEvent> events = new LinkedList<FileSystemEvent>();
		FileSystemEvent event = new FileSystemEvent(eventFile, FileSystemEventType.FILE_ADDED);
		events.add(event);

		XStream xstream = new XStream();
		xstream.alias("TaskExecutorConfiguration", TaskExecutorConfiguration.class);

		TaskExecutor action = initAction(configDir,tempDir);

		//Check priority
		File outputFile = new File(baseTifOutputDir.getAbsolutePath() + fs + codicePartner + fs + targetNameTypes.getProperty(targetType+"") + ".tif");
		/*if(outputFile.exists() && priority == 1){
			return;
		}*/		
		clearOutput(baseTifOutputDir);		
		
		int process = -1;
		int trace = -1;

		int errors = 0;
		
		// existing process
		MetadataIngestionHandler.Process importData = getProcessData();
		if (importData != null) {
			process = importData.getId();
			trace = importData.getMaxTrace();
			errors = importData.getMaxError();			
		}
		int startErrors = errors;
		if (metadataHandler != null && process == -1) {
			LOGGER.error("Cannot find process for input file");
			throw new IOException("Cannot find process for input file");
		}
		try {
			//No human target
			if(targetType>=10){
				LOGGER.debug("NO HUMAN TARGET");
				listenerForwarder.progressing(0, "Creating Raster");
				//EXECUTE rasterize		
				events = rasterizeBNU(action,events);
				listenerForwarder.progressing(50, "Creating Overviews");
	
				//Execute overview on output TIF
				events = overview(action,events);
	
			}
	
			if(targetType>0 && targetType<10){
				LOGGER.debug("HUMAN TARGET");
	
				listenerForwarder.progressing(0, "Creating Temporary Shapefile");
				//Create temp SHP
				events = createTempBU(action,events);
	
				listenerForwarder.progressing(20, "Adding Normalized Field");
				//Alter temp SHP: create NORM field
				events = alterTempBU(action,events);
	
				listenerForwarder.progressing(40, "Filling Normalized Field");
				//Fill NORM field of temp SHP
				events = fillTempBU(action,events);
	
				listenerForwarder.progressing(60, "Creating Raster");
				//Execute rasterize on temporary SHP
				events = rasterizeBU(action,events);
				listenerForwarder.progressing(80, "Creating Overviews");
				//Execute overview on output TIF
				events = overview(action,events);
				listenerForwarder.progressing(99, "Removing Temporary Shapefile");
				//Clear normalized SHP
				clearNormalized(baseTifOutputDir);
	
			}
			
			//Rename output tif
			{
				File inputFile = new File(baseTifOutputDir.getAbsolutePath() + fs + codicePartner + fs + inputTypeName + ".tif");
				
				if(outputFile.exists()){
					outputFile.delete();
				}
				inputFile.renameTo(outputFile);
			}
		}
        catch(Exception e){
        	errors++;
        	metadataHandler
				.logError(trace, errors, "Error occurred on rasterize", getError(e), 0);                        
            LOGGER.error("Error occurred on rasterize: " + e.getMessage(), e);
        } finally {
        	if((errors - startErrors) == 0) {
				listenerForwarder.progressing(100, "Raster Creation Completed");
			} else {
				listenerForwarder.progressing(100, "Raster Creation Failed");
			}
        }
		
		if(process != -1 && processPhase != null) {
			// close current process phase
			metadataHandler.closeProcessPhase(process, processPhase);
		}
		

	}

	private Queue<FileSystemEvent> fillTempBU(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("ogrinfo");
		File xslFile = (new File(action.getConfigDir() + fs + "updateNormalizedTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}

	private Queue<FileSystemEvent> alterTempBU(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("ogrinfo");
		File xslFile = (new File(action.getConfigDir() + fs + "alterNormalizedTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}

	private Queue<FileSystemEvent> createTempBU(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("ogr2ogr");
		File xslFile = (new File(action.getConfigDir() + fs + "createNormalizedTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}

	private Queue<FileSystemEvent> rasterizeBU(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{		
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("gdal_rasterize");
		File xslFile = (new File(action.getConfigDir() + fs + "rasterizeBUTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}


	private void clearOutput(File outputDir){
		File partnerOutput = new File(outputDir.getAbsolutePath()+ fs +codicePartner);
		if(partnerOutput.exists()){			
			FileFilter fileFilter = new WildcardFileFilter(targetNameTypes.getProperty(targetType+"") + ".tif");
			File[] files = partnerOutput.listFiles(fileFilter);
			for (File file : files) {
				file.delete();
			}
		}else{
			partnerOutput.mkdir();		
		}
	}
	
	private void clearNormalized(File outputDir){
		File partnerOutput = new File(outputDir.getAbsolutePath()+fs+codicePartner);
		if(partnerOutput.exists()){
			FileFilter fileFilter = new WildcardFileFilter(this.inputTypeName+"_normalized*.*");
			File[] files = partnerOutput.listFiles(fileFilter);
			for (File file : files) {
				file.delete();
			}
		}else{
			partnerOutput.mkdir();		
		}
	}

	private Queue<FileSystemEvent> overview(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("gdaladdo");
		File xslFile = (new File(action.getConfigDir() + fs + "overviewTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}

	private Queue<FileSystemEvent> rasterizeBNU(TaskExecutor action, Queue<FileSystemEvent> events) throws ActionException{
		TaskExecutorConfiguration taskExecutorConfiguration = action.getConfiguration();
		taskExecutorConfiguration.setExecutable("gdal_rasterize");
		File xslFile = (new File(action.getConfigDir() + fs + "rasterizeBNUTaskExecutorConfiguration.xsl"));
		taskExecutorConfiguration.setXsl(xslFile.getAbsolutePath());
		Queue<FileSystemEvent> outEvents = action.execute(events);
		return outEvents;
	}

	private TaskExecutor initAction(File configDir, File tempDir) throws ActionException{
		TaskExecutor action = null;
		try {
			TaskExecutorConfiguration taskExecutorConfiguration = new TaskExecutorConfiguration("rasterizeTask", "rasterizeTask", "rasterizeTask");
			taskExecutorConfiguration.setTimeOut(1200000L);				

			taskExecutorConfiguration.setErrorFile(tempDir+fs+"errorlog.txt");			

			action = new TaskExecutor(taskExecutorConfiguration);

			if(action.getConfigDir() == null){
				action.setConfigDir(configDir);
			}
			action.setTempDir(tempDir);
			action.setFailIgnored(true);
			action.addListener(listener);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return action;
	}
}
