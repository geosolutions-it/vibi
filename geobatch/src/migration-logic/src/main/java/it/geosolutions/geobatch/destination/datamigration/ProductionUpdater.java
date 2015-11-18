package it.geosolutions.geobatch.destination.datamigration;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.actions.ds2ds.Ds2dsAction;
import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;
import it.geosolutions.geobatch.catalog.Identifiable;
import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.datamigration.UpdaterFeatures.UpdaterFeature;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.IProgressListener;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureReader;
import org.geotools.data.Query;
import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.Id;
import org.opengis.filter.identity.FeatureId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductionUpdater extends InputObject{

	private final static Logger LOGGER = LoggerFactory.getLogger(ProductionUpdater.class);

	private static Pattern TYPE_NAME_PARTS_ARCS = Pattern.compile("^([A-Z]{2})_([A-Z]{1})_([A-Za-z]+)_([0-9]{8})(_.*?)?$");
	private static Pattern TYPE_NAME_PARTS_TARGETS  = Pattern.compile("^([A-Z]{2})[_-]([A-Z]{2,3})[_-]([A-Z]+)([_-][C|I])?[_-]([0-9]{8})[_-]([0-9]{2})$");
	private static Pattern TYPE_NAME_PARTS_ALL = Pattern.compile("^([A-Z]{2})_([0-9]{8})(_.*?)?$");
	private static Properties targetTypes = new Properties();

	private Ds2dsConfiguration ds2dsConfiguration;
	private String codicePartner;
	private Integer partner;
	private Integer targetType;
	private String date;
	private boolean filterByTarget = true;
	private boolean removeFeatures = true;
	private boolean migrateAll;

	static {	
		// load mappings from resources
		try {			
			targetTypes.load(TargetIngestionProcess.class.getResourceAsStream("/targets.properties"));						
		} catch (IOException e) {
			LOGGER.error("Unable to load configuration: "+e.getMessage(), e);
		}
	}



	/**
	 * @param filterByTarget the filterByTarget to set
	 */
	public void setFilterByTarget(boolean filterByTarget) {
		this.filterByTarget = filterByTarget;
	}

	/**
	 * @param removeFeatures the removeFeatures to set
	 */
	public void setRemoveFeatures(boolean removeFeatures) {
		this.removeFeatures = removeFeatures;
	}



	public ProductionUpdater(String inputTypeName,
			ProgressListenerForwarder listenerForwarder,
			MetadataIngestionHandler metadataHandler, DataStore dataStore) {
		super(inputTypeName, listenerForwarder, metadataHandler, dataStore);
	}

	@Override
	protected boolean parseTypeName(String typeName) {
		Matcher m = TYPE_NAME_PARTS_ARCS.matcher(typeName);
		migrateAll = false;
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());			
			//targetType = Integer.parseInt(targetTypes.get(m.group(3)).toString());
			date = m.group(4);
			return true;
		}
		m = TYPE_NAME_PARTS_TARGETS.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			// target detailed type id (from siig_t_bersaglio)
			targetType = Integer.parseInt(targetTypes.get(m.group(3)).toString());
			date = m.group(5);
			return true;
		}
		m = TYPE_NAME_PARTS_ALL.matcher(typeName);
		if(m.matches()) {
			// partner alphanumerical abbreviation (from siig_t_partner)
			codicePartner = m.group(1);
			// partner numerical id (from siig_t_partner)
			partner = Integer.parseInt(partners.get(codicePartner).toString());
			date = m.group(2);
			migrateAll = true;
			return true;
		}
		return false;
	}

	//private List<String> receivedEvents = new ArrayList<String>();

			/*
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
	};*/

	private Queue<EventObject> getEvents() throws URISyntaxException {
		Queue<EventObject> events = new LinkedList<EventObject>();
		FileSystemEvent event = new FileSystemEvent(new File("dummyInput.run") , FileSystemEventType.FILE_ADDED);
		events.add(event);
		return events;
	}

	public void execute(String closePhase, boolean newProcess) throws Exception {
		UpdaterFeatures updaterFeatures  = UpdaterFeatures.fromXML(this.getClass().getClassLoader().getResourceAsStream("datamigration.xml"));
		UpdaterFeatures targetFeature = new UpdaterFeatures();
		UpdaterFeatures arcFeature = new UpdaterFeatures();
		for(UpdaterFeature f : updaterFeatures.getFeatures()){
			if(f.getTarget()!=null && f.getTarget()){
				targetFeature.getFeatures().add(f);
			}else{
				arcFeature.getFeatures().add(f);
			}
		}
		
		int process = -1;
		int trace = -1;

		int errors = 0;
		int startErrors = 0;
		
		// create or retrieve metadata for ingestion
		if(newProcess) {
			removeOldImports();
			// new process
			process = createProcess();
			// write log for the imported file
			trace = logFile(process, NO_TARGET,
					partner, codicePartner, date, false);
		} else {
			// existing process
			MetadataIngestionHandler.Process importData = getProcessData();
			if (importData != null) {
				process = importData.getId();
				trace = importData.getMaxTrace();
				errors = importData.getMaxError();
				startErrors = errors;
			}
		}
		
		try {
			MultiProgressListenerForwarder listener = null;
			if(migrateAll) {
				listener = new MultiProgressListenerForwarder(
						listenerForwarder.getOwner(), listenerForwarder,
						updaterFeatures.getFeatures().size());
				executeTarget(targetFeature, listener);
				executeArc(arcFeature, listener);
			}
			else if(targetType!=null){
				listener = new MultiProgressListenerForwarder(
						listenerForwarder.getOwner(), listenerForwarder,
						targetFeature.getFeatures().size());
				executeTarget(targetFeature, listener);
			}else{
				listener = new MultiProgressListenerForwarder(
						listenerForwarder.getOwner(), listenerForwarder,
						arcFeature.getFeatures().size());
				executeArc(arcFeature, listener);
			}
			metadataHandler.updateLogFile(trace, listener.getTotalRecords(), startErrors, true);
		} catch(Exception e){
        	errors++;
        	metadataHandler
				.logError(trace, errors, "Error occurred on migration", getError(e), 0);                        
            LOGGER.error("Error occurred on migration" + e.getMessage(), e);
	    } finally {
	    
	        if (process != -1 && closePhase != null) {
				// close current process phase
				metadataHandler.closeProcessPhase(process, closePhase);
			}
	        finalReport("Data migration completed", errors - startErrors);
	    }

	}

	public void executeTarget(UpdaterFeatures targetFeature, MultiProgressListenerForwarder listener) throws Exception {
		LOGGER.info("Execute TARGET migration");
		Map<String, Serializable> destinationDataStoreConfig = this.ds2dsConfiguration.getOutputFeature().getDataStore();
		DataStore destinationDataStore = DataStoreFinder.getDataStore(destinationDataStoreConfig);
		if(destinationDataStore instanceof JDBCDataStore){
			((JDBCDataStore)destinationDataStore).setExposePrimaryKeyColumns(true);
		}
		if(filterByTarget) {
			this.ds2dsConfiguration.setEcqlFilter("id_partner = " + partner + " AND id_bersaglio = " + targetType);
		} else {
			this.ds2dsConfiguration.setEcqlFilter("id_partner = " + partner);
		}

		/*
		 * Delete all features
		 */
		if(removeFeatures) {
			LOGGER.info("Cleaning output DS ...");
			for(UpdaterFeature f : targetFeature.getFeatures()){

				String fn = f.getFeatureName();
				String pr = f.getFeatureParentRelation();

				Filter filter = CQL.toFilter(this.ds2dsConfiguration.getEcqlFilter());
				// Feature hasn't id_partner, join with the parent relation is needed to delete entry 
				if(pr != null){

					Connection connection = getConnection(destinationDataStore,Transaction.AUTO_COMMIT);

					//Connection connection = DriverManager.getConnection("jdbc:postgresql://"+destinationDataStoreConfig.get("host")+":"+destinationDataStoreConfig.get("port")+"/"+destinationDataStoreConfig.get("database"),destinationDataStoreConfig.get("user").toString(), destinationDataStoreConfig.get("passwd").toString());

					DatabaseMetaData metaData = connection.getMetaData();

					ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, pr);
					boolean fkFound = false;
					String fkTableName = "";
					String fkColumnName = "";
					String pkTableName = "";
					String pkColumnName = "";
					if(foreignKeys != null){
						foreignKeys.beforeFirst();					

						//Retrieve information about FK
						while (foreignKeys.next()) {
							fkTableName = foreignKeys.getString("FKTABLE_NAME");
							fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
							pkTableName = foreignKeys.getString("PKTABLE_NAME");
							pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
							if(pkTableName.equals(fn) && fkTableName.equals(pr)){
								fkFound = true;
								break;

							}
						}
					}
					//connection.close();

					if(fkFound){
						listenerForwarder.setTask("Removing data from " + pkTableName);
						//Retrieve IDs of primary feature to delete related to those extracted by EcqlFilter on related feature
						FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);
						
						//Delete features by IDs

						SimpleFeatureStore store = (SimpleFeatureStore) destinationDataStore.getFeatureSource(pkTableName);
						store.setTransaction(Transaction.AUTO_COMMIT);		
						
						Query query = new Query(fkTableName,filter);
						FeatureReader<SimpleFeatureType, SimpleFeature> reader = destinationDataStore.getFeatureReader(query, Transaction.AUTO_COMMIT);
						Set<FeatureId> ids = new HashSet<FeatureId>();
						int count = 0;
						while (reader.hasNext()) {
							SimpleFeature feature = reader.next();
							if(feature.getAttribute(fkColumnName) != null){
								ids.add(ff.featureId(pkTableName + "." + feature.getAttribute(fkColumnName).toString()));
								
							}
							count++;
							if(count % 100 == 0) {
								store.removeFeatures(ff.id(ids));
								ids.clear();
							}
						}
						if(ids.size() > 0) {
							store.removeFeatures(ff.id(ids));
						}
						reader.close();

									
						
					}
				}

				//Delete parent records
				else{
					listenerForwarder.setTask("Removing data from " + fn);
					SimpleFeatureStore store = (SimpleFeatureStore) destinationDataStore.getFeatureSource(fn);
					store.setTransaction(Transaction.AUTO_COMMIT);					
					store.removeFeatures(filter);
				}
			}
		}

		LOGGER.info("Copying to output DS ...");
		/*
		 * Populate all features (first the ones constraint relation)
		 */
		for(UpdaterFeature f : targetFeature.getFeatures()){
			String fn = f.getFeatureName();
			String pr = f.getFeatureParentRelation();
			if(pr != null){
				runDs2Ds(fn, listener);
			}			
		}

		for(UpdaterFeature f : targetFeature.getFeatures()){
			String fn = f.getFeatureName();
			String pr = f.getFeatureParentRelation();
			if(pr == null){					
				runDs2Ds(fn, listener);
			}			
		}

	}

	public void executeArc(UpdaterFeatures arcFeature, MultiProgressListenerForwarder listener) throws Exception {
		LOGGER.info("Execute ARC migration");
		Map<String, Serializable> destinationDataStoreConfig = this.ds2dsConfiguration.getOutputFeature().getDataStore();
		DataStore destinationDataStore = DataStoreFinder.getDataStore(destinationDataStoreConfig);
		if(destinationDataStore instanceof JDBCDataStore){
			((JDBCDataStore)destinationDataStore).setExposePrimaryKeyColumns(true);
		}
		this.ds2dsConfiguration.setEcqlFilter("fk_partner = " + partner);
		/*
		 * Delete all features
		 */
		if(removeFeatures) {
			LOGGER.info("Cleaning output DS ...");
			Transaction transaction = new DefaultTransaction("removeFeature");
			try {
				for(UpdaterFeature f : arcFeature.getFeatures()){
					String fn = f.getFeatureName();
					String pr = f.getFeatureParentRelation();
					Filter filter = CQL.toFilter(this.ds2dsConfiguration.getEcqlFilter());
					//Delete all using cascade on FK
					if(pr == null){
						Query query = new Query(fn,filter);
						Connection connection = getConnection(destinationDataStore,transaction);
						DatabaseMetaData metaData = connection.getMetaData();
						FeatureReader<SimpleFeatureType, SimpleFeature> reader = destinationDataStore.getFeatureReader(query, transaction);
						if (reader.hasNext()) {
							SimpleFeature feature = reader.next();			
							

							ResultSet foreignKeys = metaData.getExportedKeys(connection.getCatalog(), null, fn);
							if(foreignKeys != null){
								foreignKeys.beforeFirst();
								//Retrieve information about FK
								while (foreignKeys.next()) {
									String fkTableName = foreignKeys.getString("FKTABLE_NAME");
									String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
									String pkTableName = foreignKeys.getString("PKTABLE_NAME");
									String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
									if(pkTableName.equals(fn)){
										listenerForwarder.setTask("Removing data from " + fkTableName);
										//Delete on related table										
										Filter relatedFilter = CQL.toFilter(fkColumnName+"="+feature.getAttribute(pkColumnName).toString());
										//Delete features by IDs
										SimpleFeatureStore store = (SimpleFeatureStore) destinationDataStore.getFeatureSource(fkTableName);
										store.setTransaction(Transaction.AUTO_COMMIT);					
										store.removeFeatures(relatedFilter);									
									}
								}
							}
							//
						}
						reader.close();		
						listenerForwarder.setTask("Removing data from " + fn);
						SimpleFeatureStore store = (SimpleFeatureStore) destinationDataStore.getFeatureSource(fn);
						store.setTransaction(transaction);					
						store.removeFeatures(filter);
						transaction.commit();
					}
				}
			} catch (Exception eek) {
				LOGGER.error(eek.getMessage(),eek);
				transaction.rollback();
			}finally{
				transaction.close();
			}
		}

		/*
		 * Populate all features (first the ones without constraint relation)
		 */
		LOGGER.info("Copying to output DS ...");
		for(UpdaterFeature f : arcFeature.getFeatures()){
			String fn = f.getFeatureName();
			String pr = f.getFeatureParentRelation();
			if(pr == null){
				runDs2Ds(fn, listener);
			}
		}
		for(UpdaterFeature f : arcFeature.getFeatures()){
			String fn = f.getFeatureName();
			String pr = f.getFeatureParentRelation();
			if(pr != null){
				runDs2Ds(fn, listener);
			}
		}

	}


	private void runDs2Ds(String featureName, final MultiProgressListenerForwarder listener) throws Exception{
		LOGGER.info("Execute Ds2Ds on " + featureName + " feature");
		this.ds2dsConfiguration.getSourceFeature().setTypeName(featureName);
		//Force purge to false
		this.ds2dsConfiguration.setPurgeData(false);
		this.ds2dsConfiguration.setFailIgnored(true);
		Ds2dsAction action = new Ds2dsAction(this.ds2dsConfiguration) {
			protected void updateImportProgress(int progress, int total, String message) {
		        float f = total == 0 ? 0 : (float) progress*100 / total;
		        listenerForwarder.progressing(f, message);
		        listener.addTotalRecords(total);
		        if (LOGGER.isInfoEnabled()) {
		            LOGGER.info("Importing data: " + progress + "/" + total);
		        }
		    }
		};		
		action.setTempDir(new File(System.getProperty("java.io.tmpdir")));
		action.setFailIgnored(true);
		listener.setCurrentName(featureName);
		action.addListener(listener);
		action.execute(getEvents());
		listener.incrementCurrent();
	}

	public void setDs2DsConfiguration(Ds2dsConfiguration productionUpdaterConfiguration) {
		this.ds2dsConfiguration = productionUpdaterConfiguration;		
	}

}
