/*

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
package it.geosolutions.geobatch.destination.ingestion.gate.statistics;

import it.geosolutions.geobatch.catalog.impl.TimeFormat;
import it.geosolutions.geobatch.catalog.impl.configuration.TimeFormatConfiguration;
import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.common.utils.SequenceManager;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle the gate statistics processes. This processor reads all data in
 * 'siig_gate_t_dato' for the last year and insert on
 * 'siig_gate_t_dato_statistico' table
 * 
 * @author adiaz
 */
public class GateStatisticsProcess extends InputObject {

private final static Logger LOGGER = LoggerFactory
        .getLogger(GateStatisticsProcess.class);

/**
 * No target specified
 */
private static final Integer DEFAULT_GATE_TYPE = NO_TARGET;

/**
 * Pattern: [Server]_[date]_[time].run
 */
private static Pattern typeNameParts = Pattern
        .compile("^([A-Z])([0-9]{2})[_-]([0-9]{8})[_-]([0-9]{6})$");

/**
 * Partner always 1
 */
private static int PARTNER = 1;

/**
 * Always 'A'
 */
private static String PARTNER_CODE = "A";

/**
 * File with the data to be inserted
 */
private File file = null;

/**
 * Date in the stored in the file name
 */
private String date;

/**
 * Outout type it's siig_gate_t_dato_statistico
 */
private String outputType = "siig_gate_t_dato_statistico";



private StatisticsExtractor statisticsExtractor;

/**
 * Time format component
 */
private TimeFormat timeFormat;

/**
 * Parametrized constructor
 * 
 * @param typeName
 * @param listenerForwarder
 * @param metadataHandler
 * @param dataStore for the output
 * @param inputDataStore to read statistics
 */
public GateStatisticsProcess(String typeName,
        ProgressListenerForwarder listenerForwarder,
        MetadataIngestionHandler metadataHandler, DataStore dataStore,
        JDBCDataStore inputDataStore,
        TimeFormatConfiguration timeFormatConfiguration) {

    super(typeName, listenerForwarder, metadataHandler, dataStore);
        // create time format
    this.timeFormat = new TimeFormat(null, null, null, timeFormatConfiguration);

    // init from file to be inserted
    initFromNow();

    // init extractor and sequence manager
    this.statisticsExtractor = new StatisticsJDBCExtractor(
            (JDBCDataStore) inputDataStore, this.timeFormat);
    this.setSequenceManager(new SequenceManager(inputDataStore,
            "gate_statistic_seq"));
}

/**
 * Parametrized constructor
 * 
 * @param typeName
 * @param listenerForwarder
 * @param metadataHandler
 * @param dataStore for the output
 * @param file that launched this process
 * @param inputDataStore to read statistics
 */
public GateStatisticsProcess(String typeName,
        ProgressListenerForwarder listenerForwarder,
        MetadataIngestionHandler metadataHandler, DataStore dataStore,
        File file, JDBCDataStore inputDataStore,
        TimeFormatConfiguration timeFormatConfiguration) {

    super(typeName, listenerForwarder, metadataHandler, dataStore);
    // create time format
    this.timeFormat = new TimeFormat(null, null, null, timeFormatConfiguration);

    // init from file to be inserted
    this.file = file;

    // init extractor and sequence manager
    this.statisticsExtractor = new StatisticsJDBCExtractor(
            (JDBCDataStore) inputDataStore, this.timeFormat);
    this.setSequenceManager(new SequenceManager(inputDataStore,
            "gate_statistic_seq"));
}

/**
 * Read data from the file name
 */
protected boolean parseTypeName(String inputTypeName) {
    Matcher m = typeNameParts.matcher(inputTypeName);

    if (m.matches()) {
        // file date identifier
        date = m.group(4);
        this.inputTypeName = inputTypeName;

        return true;
    } else {
        return initFromNow();
    }
}

private boolean initFromNow() {

    this.date = new SimpleDateFormat("yyyyMMDD").format(new Date());
    this.inputTypeName = "Stats_" + this.date;

    return true;
}

/**
 * Generate statistics for the gate transits.
 * 
 * @param purgeData indicates if remove previous data or not
 * @throws IOException
 */
public List<Long> generateStatistics(boolean purgeData) throws IOException {

    List<Long> ids = new ArrayList<Long>();
    reset();
    if (isValid()) {

        int process = -1;
        int trace = -1;
        int errors = 0;
        int total = 0;
        int processed = 0;
        float percent = 0;
        Transaction transaction = null;

        try {

            process = createProcess();

            // write log for the imported file
            trace = logFile(process, DEFAULT_GATE_TYPE, PARTNER, PARTNER_CODE,
                    date, false);

            // Read statistics
            List<StatisticsBean> statistics = null;
            try {
                updateProgress(0.1f, "Generating statistics");
                statistics = statisticsExtractor.getStatistics();
                updateProgress(49f, "All statistics generated");
            } catch (Exception e) {
                String msg = "Can't generate statistics";
                updateProgress(90, msg);
                metadataHandler.logError(trace, errors, msg, getError(e), 0);
                throw new IOException(msg);
            }

            // create one transaction for all the operation
            transaction = new DefaultTransaction();
            int idTematico = 0;
            // clean old statistics
            if (purgeData) {
                updateProgress(49.1f, "Cleaning old statistics");
                cleanStatistics(transaction);
                updateProgress(50f, "Old statistics cleaned");                
            }

            if (statistics != null && !statistics.isEmpty()) {
                total = statistics.size();
                float ftot = new Float(total);
                
                // Insert one by one
                for (StatisticsBean statisticBean : statistics) {
                    Long id = null;
                    try {

                        // Update status
                        inputCount++;
                        float fproc = new Float(++processed);
                        String msg = "Importing data in statistic table: "
                                + (processed) + "/" + total;
                        percent = (fproc++ / ftot);
                        if(processed % 100 == 0) {
	                        updateProgress((percent * 50) + 50f, msg);
	                        if (LOGGER.isInfoEnabled()) {
	                            LOGGER.info(msg);
	                        }
                        }
                        if(purgeData) {
                        	idTematico++;
                        } else {
                        	idTematico = nextId();
                        }
                        // insert
                        id = createStatistic(idTematico, statisticBean.getFk_gate(),
                                statisticBean.getFk_interval(),
                                statisticBean.getData_stat_inizio(),
                                statisticBean.getData_stat_fine(),
                                statisticBean.getFlg_corsia(),
                                statisticBean.getDirezione(),
                                statisticBean.getCodice_kemler(),
                                statisticBean.getCodice_onu(),
                                statisticBean.getQuantita(), transaction);

                        // add to result
                        if (id != null) {
                            // Trace insert
                            if (LOGGER.isTraceEnabled()) {
                                LOGGER.trace("Correctly insert id " + id);
                            }
                            ids.add(id);
                        } else {
                            LOGGER.error("Error on gate statistics for element "
                                    + inputCount);
                        }

                    } catch (Exception e) {
                        errors++;
                        if (id != null) {
                            metadataHandler.logError(trace, errors,
                                    "Error on gate statistics", getError(e),
                                    id.intValue());
                            String msg = "Error on gate statistics for id "
                                    + id.intValue();
                            updateProgress((percent * 50) + 50f, msg);
                            LOGGER.error(msg);
                        } else {
                            metadataHandler.logError(trace, errors,
                                    "Error importing element " + inputCount,
                                    getError(e), 0);
                            String msg = "Error on gate statistics for element "
                                    + inputCount;
                            updateProgress((percent * 50) + 50f, msg);
                            LOGGER.error(msg);
                        }
                    }
                }

                // commit transaction
                transaction.commit();
            } else {
                LOGGER.error("Can't generate statistics");
            }

            // all complete
            importFinished(total, errors, "Data imported in statistics table");
            metadataHandler.updateLogFile(trace, total, errors, true);

        } catch (IOException e) {
            errors++;
            metadataHandler.logError(trace, errors, "Error generating data",
                    getError(e), 0);

            // close current process phase
            process = closeProcess(process);

            // roll back transaction
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        } finally {
            if (process != -1) {
                // close current process phase
                metadataHandler.closeProcessPhase(process, "A");
            }

            // close transaction
            if (transaction != null) {
                transaction.close();
            }

        }

        // close current process phase
        process = closeProcess(process);
    }

    return ids;
}

/**
 * Clean old statistics
 * 
 * @throws IOException
 */
private void cleanStatistics(Transaction transaction) throws IOException {
    Filter filter;
    try {
        SimpleFeatureSource featureSource = dataStore
                .getFeatureSource(outputType);
        if (featureSource instanceof SimpleFeatureStore) {
            SimpleFeatureStore store = (SimpleFeatureStore) featureSource; // write
                                                                           // access!
            store.setTransaction(transaction);
            filter = CQL.toFilter("id_dato is not null");
            store.removeFeatures(filter); // remove all statistics
        } else {
            String msg = "'"
                    + featureSource.getClass().getSimpleName()
                    + "' is an unknown format for the data source. Couldn't clean statistics";
            LOGGER.error(msg);
            throw new IOException(msg);
        }
    } catch (Exception e) {
        throw new IOException(e);
    }
}

private void updateProgress(float progress, String msg) {
    listenerForwarder.setProgress(progress);
    listenerForwarder.setTask(msg);
    listenerForwarder.progressing();
}

private int closeProcess(int process) throws IOException {
    if (process != -1) {
        metadataHandler.closeProcessPhase(process, "A");
        process = -1;
    }
    return process;
}

/**
 * Create and insert an statistic element
 * 
 * @param fk_gate the gate we are generating stats
 * @param fk_interval values 1,2,3,4 (for each gate we will have a minimum of 4
 *        records each with one value in the 1..4 range)
 * @param data_stat_inizio start date
 * @param data_stat_fine end date
 * @param flg_corsia of the transit
 * @param direzione of the transit
 * @param codice_kemler of the transit
 * @param codice_onu of the transit
 * @param quantita calculated value
 * @param transaction for save the new statistics
 * @return auto generated key for the statistic insert
 * @throws Exception
 */
public Long createStatistic(int idTematico, BigDecimal fk_gate, int fk_interval,
        Timestamp data_stat_inizio, Timestamp data_stat_fine,
        BigDecimal flg_corsia, BigDecimal direzione, String codice_kemler,
        String codice_onu, BigDecimal quantita, Transaction transaction)
        throws Exception {

    Long idLong = new Long(idTematico);

    // Create feature
    SimpleFeatureSource featureSource = dataStore.getFeatureSource(outputType);

    SimpleFeatureType featureType = featureSource.getSchema();
    SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);

    // values
    for (AttributeDescriptor attDesc : featureType.getAttributeDescriptors()) {
        // known columns
        String name = attDesc.getName().getLocalPart();
        if (name.equals("fk_gate")) {
            featureBuilder.add(fk_gate);
        } else if (name.equals("fk_interval")) {
            featureBuilder.add(fk_interval);
        } else if (name.equals("data_stat_inizio")) {
            featureBuilder.add(data_stat_inizio);
        } else if (name.equals("data_stat_fine")) {
            featureBuilder.add(data_stat_fine);
        } else if (name.equals("flg_corsia")) {
            featureBuilder.add(flg_corsia);
        } else if (name.equals("flg_direzione")) {
            featureBuilder.add(direzione);
        } else if (name.equals("codice_kemler")) {
            featureBuilder.add(codice_kemler);
        } else if (name.equals("codice_onu")) {
            featureBuilder.add(codice_onu);
        } else if (name.equals("quantita")) {
            featureBuilder.add(quantita);
        } else if (name.equals("id_dato")) {
            featureBuilder.add(new BigDecimal(idLong));
        } else {
            // unknown column, try to insert as null.
            featureBuilder.add(null);
        }
    }

    SimpleFeature feature = featureBuilder.buildFeature(idLong.toString());
    feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

    SimpleFeatureStore featureStore = null;
    if (featureSource instanceof SimpleFeatureStore) {
        featureStore = (SimpleFeatureStore) featureSource;

    } else if (featureSource.getDataStore() instanceof SimpleFeatureSource) {
        featureStore = (SimpleFeatureStore) featureSource.getDataStore();
    } else {
        String msg = "'"
                + featureSource.getClass().getSimpleName()
                + "' is an unknown format for the data source. Couldn't save feature";
        LOGGER.error(msg);
        throw new IOException(msg);
    }

    // save new features
    if (featureStore != null) {
        featureStore.setTransaction(transaction);
        try {
            featureStore.addFeatures(DataUtilities.collection(feature));
        } catch (Exception problem) {
            throw new IOException(problem);
        }
    }

    return idLong;
}

/**
 * Check if file and datastore is not null
 */
protected boolean isValid() throws IOException {
    return file != null && dataStore != null;
}

}
