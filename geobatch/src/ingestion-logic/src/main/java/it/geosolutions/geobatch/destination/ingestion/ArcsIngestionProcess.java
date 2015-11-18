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
package it.geosolutions.geobatch.destination.ingestion;

import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.common.OutputObject;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 * 
 */
public class ArcsIngestionProcess extends InputObject {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArcsIngestionProcess.class);

    private static Pattern typeNameParts = Pattern
            .compile("^([A-Z]{2})_([A-Z]{1})_([A-Za-z]+)_([0-9]{8})(_ORIG)?$");

    private int partner;

    private String codicePartner;

    private String date;

    public static Properties aggregation = new Properties();

    public static Properties bersaglio = new Properties();


    private static Map<Integer, String> gridTypeNames = new HashMap<Integer, String>();
    
    //private String gridIdName per recuperare le primary key delle tabelle da gestire;
    private static Map<Integer, String> gridIdNames = new HashMap<Integer, String>();

    private String geoTypeName = "siig_geo_ln_arco_X";

    private String geoTypeNamePl = "siig_geo_pl_arco_X";

    private String geoId = "id_geo_arco";

    private String byVehicleTypeName = "siig_r_tipovei_geoarcoX";

    private String dissestoTypeName = "siig_r_arco_X_dissesto";

    private String tipobersTypeName = "siig_r_arco_X_scen_tipobers";

    private String sostanzaTypeName = "siig_t_sostanza";

    private String sostanzaArcoTypeName = "siig_r_arco_X_sostanza";

    private static float PADDR_WORKAROUTD_VALUE = .5f;

    private static Map attributeMappings = null;

    static {
        // load mappings from resources
        attributeMappings = (Map) readResourceFromXML("/roadarcs.xml");

        InputStream aggregationStream = null;
        InputStream bersaglioStream = null;
        try {
            aggregationStream = ArcsIngestionProcess.class
                    .getResourceAsStream("/aggregation.properties");
            bersaglioStream = ArcsIngestionProcess.class
                    .getResourceAsStream("/bersaglio.properties");
            aggregation.load(aggregationStream);
            bersaglio.load(bersaglioStream);
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration: " + e.getMessage(), e);
        } finally {
            try {
                if (bersaglioStream != null) {
                    bersaglioStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (aggregationStream != null) {
                    aggregationStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        
        gridTypeNames.put(3, "siig_geo_grid_2_clip");
        gridTypeNames.put(4, "siig_geo_pl_comuni");
        gridTypeNames.put(5, "siig_geo_pl_province");
        //le primary Key
        gridIdNames.put(3, "gid");
        gridIdNames.put(4, "cod_comune");
        gridIdNames.put(5, "cod_provincia");
    }
    
    private static String[] padrSostanzaMapping = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", // original values
        "100", "101", "102", "103", "104", "105", "106", "107", "108", "109",
        "110", "111", "112", "113", "114", "115", "116", "117", "118", "119",
        "120", "121", "122", "123", "124", "125", "126", "127", "128", "129",
        "130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
        "140", "141", "142", "143", "144", "145", "146", "147", "148", // 149 does not exists
        "150", "151", "152", "153", "154", "155", "156", "157", "158", "159",
        "160", "161", "162", "163", "164", "165", "166", "167"};

    /**
     * Initializes a VectorTarget handler for the given input feature.
     * 
     * @param inputTypeName
     */
    public ArcsIngestionProcess(String inputTypeName, ProgressListenerForwarder listenerForwarder,
            MetadataIngestionHandler metadataHandler, DataStore dataStore) {
        super(inputTypeName, listenerForwarder, metadataHandler, dataStore);
    }

    @Override
    protected String getInputTypeName(String inputTypeName) {
        return inputTypeName.replace("_ORIG", "");
    }

    /**
     * Parse input feature typeName and extract useful information from it.
     */
    protected boolean parseTypeName(String typeName) {
        Matcher m = typeNameParts.matcher(typeName);
        if (m.matches()) {
            // partner alphanumerical abbreviation (from siig_t_partner)
            codicePartner = m.group(1);
            // partner numerical id (from siig_t_partner)
            partner = Integer.parseInt(partners.get(codicePartner).toString());
            // target macro type (bu or bnu)
            // file date identifier
            date = m.group(4);

            // TODO: add other validity checks

            return true;
        }
        return false;
    }

    public int getPartner() {
        return partner;
    }

    /**
     * @param geoTypeName2
     * @param aggregationLevel
     * @return
     */
    private String getTypeName(String typeName, int aggregationLevel) {
        return typeName.replace("X", aggregationLevel + "");
    }

    /**
     * Imports the arcs feature from the original Feature into on of the SIIG arcs tables (in staging).
     * 
     * @param datastoreParams
     * @param crs
     * @param aggregationLevel level to import (1, 2, 3)
     * @param onGrid aggregate on cells or not (for level 3)
     * @param dropInput drop input table after import
     * @param closePhase phase to close at the end of the import (A, B or C; null means no closing)
     * @throws IOException
     */
    public void importArcs(CoordinateReferenceSystem crs, int aggregationLevel, boolean onGrid,
            boolean dropInput, boolean newProcess, String closePhase) throws IOException {
        reset();
        if (isValid()) {

            crs = checkCrs(crs);

            int process = -1;
            int trace = -1;
            int errors = 0;

            String processPhase = closePhase;

            try {

                // create or retrieve metadata for ingestion
                if (newProcess) {
                    removeOldImports();
                    // new process
                    process = createProcess();
                    // write log for the imported file
                    trace = logFile(process, NO_TARGET, partner, codicePartner, date, false);
                } else {
                    // existing process
                    MetadataIngestionHandler.Process importData = getProcessData();
                    process = importData.getId();
                    trace = importData.getMaxTrace();
                    errors = importData.getMaxError();
                }
                int startErrors = errors;

                // setup input reader
                //fra --> stabilisce la tabella di ingresso da usare
                createInputReader(dataStore, Transaction.AUTO_COMMIT, onGrid ? gridTypeNames.get(aggregationLevel) : null);

                Transaction transaction = new DefaultTransaction();

                // setup the MAIN geo output object
                // The mainGeoObject is that one is used for compute also the other outputObjects
                // For aggregation level 1 and 2 is that one related to table siig_geo_ln_arco_X but for aggregation 3 on grid is siig_geo_pl_arco_X
                String geoName = getTypeName((onGrid ? geoTypeNamePl : geoTypeName),
                        aggregationLevel);
                OutputObject mainGeoObject = new OutputObject(dataStore, transaction, geoName,
                        geoId);

                // setup vehicle output object
                String vehicleName = getTypeName(byVehicleTypeName, aggregationLevel);
                OutputObject vehicleObject = new OutputObject(dataStore, transaction, vehicleName,
                        "");

                // setup dissesto output object
                String dissestoName = getTypeName(dissestoTypeName, aggregationLevel);
                OutputObject dissestoObject = new OutputObject(dataStore, transaction,
                        dissestoName, "");

                // setup CFF output object
                String tipobersName = getTypeName(tipobersTypeName, aggregationLevel);
                OutputObject tipobersObject = new OutputObject(dataStore, transaction,
                        tipobersName, "");

                // setup sostanza output object
                String tiposostName = getTypeName(sostanzaArcoTypeName, aggregationLevel);
                OutputObject tiposostObject = new OutputObject(dataStore, transaction,
                        tiposostName, "");

                // list of all the output objects
                OutputObject[] outputObjects = new OutputObject[] { vehicleObject, dissestoObject,
                        tipobersObject, tiposostObject, mainGeoObject };

                try {
                    // remove previous data for the given partner
                    Filter removeFilter = filterFactory.equals(
                            filterFactory.property("fk_partner"), filterFactory.literal(partner));
                    if (aggregationLevel >= 3) {
                        if (onGrid) {
                            removeObjects(new OutputObject[] { dissestoObject, tipobersObject,
                                    tiposostObject, mainGeoObject }, removeFilter);
                        } else {
                            // remove only geo and vehicle data for ln_3
                            removeObjects(new OutputObject[] { vehicleObject, mainGeoObject },
                                    removeFilter);
                        }
                    } else {
                        removeObjects(outputObjects, removeFilter);
                    }

                    transaction.commit();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    errors++;
                    metadataHandler.logError(trace, errors, "Error removing old data", getError(e),
                            0);
                    transaction.rollback();
                    throw e;
                } finally {
                    transaction.close();
                }
                if(onGrid) {
                    setInputFilter(filterFactory.equals(filterFactory.property("fk_partner"),filterFactory.literal(partner+"")));
                }
                // calculates total objects to import
                int total = getImportCount();

                if (onGrid) {
                    // aggregate arcs on grid and compute also the other tables with that aggregation
                    errors = aggregateArcsOnGrid(trace, dataStore, outputObjects, total, errors,
                            startErrors, geoName, aggregationLevel, false);
                } else if (aggregationLevel == 1) {
                    // no aggregation
                    errors = importWithoutAggregation(trace, dataStore, outputObjects, total,
                            errors, geoName);
                } else {
                    // aggregation on input field
                    errors = aggregateArcs(trace, dataStore, outputObjects, total, errors,
                            startErrors, geoName, aggregationLevel, aggregationLevel == 3, false);
                }
                metadataHandler.updateLogFile(trace, total, errors, aggregationLevel == 1);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                errors++;
                metadataHandler.logError(trace, errors, "Error importing data", getError(e), 0);
                throw e;
            } finally {
                if (dropInput) {
                    dropInputFeature(dataStore);
                }
                finalReport(errors);
                if (process != -1 && processPhase != null) {
                    // close current process phase
                    metadataHandler.closeProcessPhase(process, processPhase);
                }

            }
        }
    }

    /**
     * Imports the arcs feature from the original Feature into on of the SIIG arcs tables (in staging).
     * 
     * @param datastoreParams
     * @param crs
     * @param aggregationLevel level to import (1, 2, 3)
     * @param onGrid aggregate on cells or not (for level 3)
     * @param dropInput drop input table after import
     * @param closePhase phase to close at the end of the import (A, B or C; null means no closing)
     * @throws IOException
     */
    public void updateArcs(CoordinateReferenceSystem crs, int aggregationLevel, boolean onGrid,
            boolean dropInput, boolean newProcess, String closePhase) throws IOException {
        reset();
        if (isValid()) {

            crs = checkCrs(crs);

            int process = -1;
            int trace = -1;
            int errors = 0;

            String processPhase = closePhase;

            try {

                // create or retrieve metadata for ingestion
                if (newProcess) {
                    // new process
                    process = createProcess();
                    // write log for the imported file
                    trace = logFile(process, NO_TARGET, partner, codicePartner, date, false);
                } else {
                    // existing process
                    MetadataIngestionHandler.Process importData = getProcessData();
                    process = importData.getId();
                    trace = importData.getMaxTrace();
                    errors = importData.getMaxError();
                }
                int startErrors = errors;

                // setup input reader
                createInputReader(dataStore, Transaction.AUTO_COMMIT, onGrid ? gridTypeNames.get(aggregationLevel) : null);

                Transaction transaction = new DefaultTransaction();

                // setup the MAIN geo output object
                // The mainGeoObject is that one is used for compute also the other outputObjects
                // For aggregation level 1 and 2 is that one related to table siig_geo_ln_arco_X but for aggregation 3 on grid is siig_geo_pl_arco_X
                String geoName = getTypeName((onGrid ? geoTypeNamePl : geoTypeName),
                        aggregationLevel);
                OutputObject mainGeoObject = new OutputObject(dataStore, transaction, geoName,
                        geoId);

                // setup vehicle output object
                String vehicleName = getTypeName(byVehicleTypeName, aggregationLevel);
                OutputObject vehicleObject = new OutputObject(dataStore, transaction, vehicleName,
                        "");

                // setup dissesto output object
                String dissestoName = getTypeName(dissestoTypeName, aggregationLevel);
                OutputObject dissestoObject = new OutputObject(dataStore, transaction,
                        dissestoName, "");

                // setup CFF output object
                String tipobersName = getTypeName(tipobersTypeName, aggregationLevel);
                OutputObject tipobersObject = new OutputObject(dataStore, transaction,
                        tipobersName, "");

                // setup sostanza output object
                String tiposostName = getTypeName(sostanzaArcoTypeName, aggregationLevel);
                OutputObject tiposostObject = new OutputObject(dataStore, transaction,
                        tiposostName, "");

                // list of all the output objects
                OutputObject[] outputObjects = new OutputObject[] { vehicleObject, dissestoObject,
                        tipobersObject, tiposostObject, mainGeoObject };

                // calculates total objects to import
                int total = getImportCount();

                if (onGrid) {

                    // aggregate arcs on grid and compute also the other tables with that aggregation
                    errors = aggregateArcsOnGrid(trace, dataStore, outputObjects, total, errors,
                            startErrors, geoName, aggregationLevel, true);

                } else if (aggregationLevel == 1) {
                    /*
                     * // no aggregation errors = importWithoutAggregation(trace, dataStore, outputObjects, total, errors, geoName);
                     */
                } else {
                    // aggregation on input field
                    errors = aggregateArcs(trace, dataStore, outputObjects, total, errors,
                            startErrors, geoName, aggregationLevel, aggregationLevel == 3, true);
                }
                metadataHandler.updateLogFile(trace, total, errors, aggregationLevel == 1);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                errors++;
                metadataHandler.logError(trace, errors, "Error importing data", getError(e), 0);
                throw e;
            } finally {
                if (dropInput) {
                    dropInputFeature(dataStore);
                }

                if (process != -1 && processPhase != null) {
                    // close current process phase
                    metadataHandler.closeProcessPhase(process, processPhase);
                }

            }
        }
    }

    /**
     * @param trace
     * @param dataStore
     * @param outputObjects
     * @param intValue
     * @param total
     * @param errors
     * @param geoName
     * @param aggregationLevel
     * @return
     * @throws IOException
     */
    private int aggregateArcsOnGrid(int trace, DataStore dataStore, OutputObject[] outputObjects,
            int total, int errors, int startErrors, String outputName, int aggregationLevel,
            boolean update) throws IOException {
        try {
            String inputGeometryName = getInputGeometryName(dataStore);
            
            SimpleFeature gridFeature = null;
            String gridIdName =  gridIdNames.get(aggregationLevel);
            while ((gridFeature = readInput()) != null) {

                int id = nextId();
                int idTematico = getIdTematicoForGrid(gridFeature, gridIdName);

                Geometry cell = (Geometry) gridFeature.getDefaultGeometry();

                FeatureSource<SimpleFeatureType, SimpleFeature> reader = createInputReader(
                        dataStore, Transaction.AUTO_COMMIT, null);

                FeatureIterator<SimpleFeature> iterator = reader.getFeatures(
                        filterFactory.intersects(filterFactory.property(inputGeometryName),
                                filterFactory.literal(cell))).features();

                try {
                    errors = aggregateStep(trace, dataStore, outputObjects, total, errors,
                            startErrors, outputName, id, idTematico, iterator, cell, false, true,
                            update, true);
                } finally {
                    iterator.close();
                }

            }
            importFinished(total, errors - startErrors, "Data imported in " + outputName);

        } finally {
            closeInputReader();
        }

        return errors;
    }

	private int getIdTematicoForGrid(SimpleFeature gridFeature,
			String gridIdName) {
		Object primaryKey = gridFeature.getAttribute(gridIdName);
		
		int idTematico;
		if(primaryKey instanceof String){
			if(gridIdName.equalsIgnoreCase("cod_provincia")){
				if(((String) primaryKey).startsWith("CH"))
					idTematico = 10007;
				else
					idTematico = Integer.parseInt(primaryKey.toString());	
			}else{
				//vuol dire che e la tabella dei comuni
				idTematico = Integer.parseInt(primaryKey.toString());
			}
		}else{
			idTematico = ((BigDecimal) gridFeature.getAttribute(gridIdName)).intValue();
		}
		return idTematico;
	}

    /**
     * @throws IOException
     * 
     */
    private int aggregateArcs(int trace, DataStore dataStore, OutputObject[] outputObjects,
            int total, int errors, int startErrors, String outputName, int aggregationLevel,
            boolean computeOnlyGeoFeature, boolean update) throws IOException {
        String aggregationAttribute = aggregation.getProperty(aggregationLevel + "");
        // get unique aggregation values
        Set<Number> aggregationValues = getAggregationValues(aggregationAttribute);

        for (Number aggregationValue : aggregationValues) {

            int id = nextId();
            int idTematico = aggregationValue.intValue();

            setInputFilter(filterFactory.equals(filterFactory.property(aggregationAttribute),
                    filterFactory.literal(aggregationValue)));
            try {
                errors = aggregateStep(trace, dataStore, outputObjects, total, errors, startErrors,
                        outputName, id, idTematico, null, null, computeOnlyGeoFeature, false,
                        update, false);
            } finally {
                closeInputReader();
            }

        }
        importFinished(total, errors - startErrors, "Data imported in " + outputName);
        return errors;
    }

    /**
     * Execute an aggregation step. Aggregates all feature from iterator into a single output record.
     * 
     * @param trace
     * @param dataStore
     * @param outputObjects output objects descriptors
     * @param total total input objects
     * @param errors current errors count
     * @param startErrors initial error values (errors can sum up in various phases)
     * @param outputName main output table name
     * @param id main output table id value
     * @param idTematico original object id value
     * @param iterator list of objects to aggregate
     * @param aggregateGeo optional alternative aggregate geo (for grid cells)
     * @param computeOnlyGeoFeature write only main output table
     * @return
     * @throws IOException
     */
    private int aggregateStep(int trace, DataStore dataStore, OutputObject[] outputObjects,
            int total, int errors, int startErrors, String outputName, int id, int idTematico,
            FeatureIterator<SimpleFeature> iterator, Geometry aggregateGeo,
            boolean computeOnlyGeoFeature, boolean dontComputeVehicle, boolean update, boolean writeEmpty)
            throws IOException {

        SimpleFeature inputFeature;
        Geometry geo = null;
        double lunghezza = 0;
        double incidenti = 0;
        int corsie = 0;
        int[] tgm = new int[] { 0, 0 };
        int[] velocita = new int[] { 0, 0 };
        double[] cff = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        double[] padr = new double [79];
        Arrays.fill(padr, 0);
        int padrSize = 79;
        
        ElementsCounter flgTgmCounter = new ElementsCounter();
        ElementsCounter flgVelocCounter = new ElementsCounter();
        ElementsCounter flgCorsieCounter = new ElementsCounter();
        ElementsCounter flgIncidentiCounter = new ElementsCounter();
        Set<Integer> pterr = new HashSet<Integer>();
        int idOrigin = -1;
        Boolean isViadotto = true;
        Boolean isGalleria = true;

        while ((inputFeature = readInput(iterator)) != null) {
            try {
                if (aggregateGeo == null) {
                    if (geo == null) {
                        geo = (Geometry) inputFeature.getDefaultGeometry();
                    } else if (inputFeature.getDefaultGeometry() != null) {
                        geo = geo.union((Geometry) inputFeature.getDefaultGeometry());
                    }
                } else {
                    geo = aggregateGeo;
                }
                idOrigin = (idOrigin == -1) ? ((Number) getMapping(inputFeature, attributeMappings,
                        "id_origine")).intValue() : idOrigin;

                Number currentLunghezza = (Number) getMapping(inputFeature, attributeMappings,
                        "lunghezza");
                if (currentLunghezza != null) {
                    lunghezza += currentLunghezza.doubleValue();
                }
                Number currentIncidenti = (Number) getMapping(inputFeature, attributeMappings,
                        "nr_incidenti");
                if (currentIncidenti != null) {
                    incidenti += Math.max(0, currentIncidenti.doubleValue());
                }
                Number currentCorsie = (Number) getMapping(inputFeature, attributeMappings,
                        "nr_corsie");
                if (currentCorsie != null && currentLunghezza != null) {
                    corsie += currentCorsie.intValue() * currentLunghezza.doubleValue();
                }

                String currentFlgCorsie = (String) getMapping(inputFeature, attributeMappings,
                        "flg_nr_corsie");

                String currentFlgIncidenti = (String) getMapping(inputFeature, attributeMappings,
                        "flg_nr_incidenti");
                flgCorsieCounter.addElement(currentFlgCorsie);
                flgIncidentiCounter.addElement(currentFlgIncidenti);
                
                // Boolean check, every input features must have the same flag for the output feature to be flagged
                isViadotto = isViadotto && ((Integer) getMapping(inputFeature, attributeMappings, "flg_viadotto")>0);
                isGalleria = isGalleria && ((Integer) getMapping(inputFeature, attributeMappings, "flg_galleria")>0);
                
                if (!dontComputeVehicle) {
                    // by vehicle
                    int[] tgms = extractMultipleValues(inputFeature, "TGM");
                    int[] velocitas = extractMultipleValues(inputFeature, "VELOCITA");
                    for (int i = 0; i < tgms.length; i++) {
                        if (currentLunghezza != null) {
                            tgm[i] += tgms[i] * currentLunghezza.doubleValue();
                            velocita[i] += velocitas[i] * currentLunghezza.doubleValue();
                        }
                    }
                    String currentFlgTGM = (String) getMapping(inputFeature, attributeMappings,
                            "flg_densita_veicolare");

                    String currentFlgVeloc = (String) getMapping(inputFeature, attributeMappings,
                            "flg_velocita");
                    flgTgmCounter.addElement(currentFlgTGM);
                    flgVelocCounter.addElement(currentFlgVeloc);
                }

                if (!computeOnlyGeoFeature) {
                    // dissesto
                    String[] pterrs = inputFeature.getAttribute("PTERR") == null ? new String[0]
                            : inputFeature.getAttribute("PTERR").toString().split("\\|");
                    for (int j = 0; j < pterrs.length; j++) {
                        try {
                            int dissesto = Integer.parseInt(pterrs[j]);
                            pterr.add(dissesto);
                        } catch (NumberFormatException e) {

                        }
                    }

                    // cff
                    // We are supposing that cff.length < cffs.length
                    double[] cffs = extractMultipleValuesDouble(inputFeature, "CFF", cff.length);
                    for (int i = 0; i < cff.length; i++) {
                        cff[i] += cffs[i] * currentLunghezza.doubleValue();
                    }
                    // padr
                    if(inputFeature.getProperty("PADR2") == null){
                        // Set the PADR values number to 12, this is an old input type
                        padrSize = 12;
                        //Input feature has the old schema, revert to 12 padr list
                        double[] padrs = extractMultipleValuesDouble(inputFeature, "PADR", 12);
                        for (int i = 0; i < padrs.length; i++) {
                            padr[i] += padrs[i] * currentLunghezza.doubleValue();
                        }

                    }else{
                        
                        double[] padrs = extractMultipleValuesDouble(inputFeature, "PADR", 0);
                        double[] padrs2 = extractMultipleValuesDouble(inputFeature, "PADR2", 0);
                        double[] padrs3 = extractMultipleValuesDouble(inputFeature, "PADR3", 0);
                        
                        double[] composedPadr = ArrayUtils.addAll(
                                                ArrayUtils.addAll(padrs , padrs2),
                                                padrs3);
                        int composedLenght = composedPadr.length;
                        if(composedLenght > padr.length){
                            LOGGER.warn("Input Error: Too many PADR values, truncating");
                            composedLenght = padr.length;
                        }
                        for (int i = 0; i < composedLenght; i++) {
                            padr[i] = composedPadr[i] * currentLunghezza.doubleValue();
                        }
                    }
                   
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                errors++;
                metadataHandler.logError(trace, errors, "Error writing output feature",
                        getError(e), idTematico);
            }
        }
        if (lunghezza <= 0 && geo != null) {
            lunghezza = (int) geo.getLength();
        }
        if ((int) lunghezza <= 0) {
            lunghezza = 1.0;
        }
        if(geo == null && writeEmpty) {
            geo = aggregateGeo;
        }
        if(idOrigin == -1){
            // There was no aggregation, setting flags to false
            isViadotto = false;
            isGalleria = false;
        }
        if (geo != null) {
            Transaction rowTransaction = new DefaultTransaction();
            setTransaction(outputObjects, rowTransaction);

            try {
                if (update) {
                    updateAggregateGeoFeature(outputObjects[4], id, idTematico, geo,
                            (int) lunghezza, corsie, incidenti, inputFeature, idOrigin,
                            flgCorsieCounter.getMax("C"), flgIncidentiCounter.getMax("C"));
                } else {
                    addAggregateGeoFeature(outputObjects[4], id, idTematico, geo, (int) lunghezza,
                            corsie, incidenti, inputFeature, idOrigin, flgCorsieCounter.getMax("C"),
                            flgIncidentiCounter.getMax("C"), isViadotto, isGalleria);
                    if (!dontComputeVehicle) {
                        addAggregateVehicleFeature(outputObjects[0], id, (int) lunghezza, tgm,
                                velocita, flgTgmCounter.getMax("C"), flgVelocCounter.getMax("C"),
                                inputFeature);
                    }

                    if (!computeOnlyGeoFeature) {
                        addAggregateDissestoFeature(outputObjects[1], id, (int) lunghezza, pterr,
                                inputFeature);
                        addAggregateCFFFeature(outputObjects[2], id, (int) lunghezza, cff,
                                inputFeature);
                        if(padr.length > padrSize){
                            // We are aggregating an old type of input, truncate to original size
                            padr = Arrays.copyOf(padr, padrSize);
                        }
                        addAggregatePADRFeature(outputObjects[3], id, (int) lunghezza, padr,
                                inputFeature);

                    }
                }

                rowTransaction.commit();

                updateImportProgress(total, errors - startErrors, "Importing data in " + outputName);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                errors++;
                rowTransaction.rollback();
                metadataHandler.logError(trace, errors, "Error writing output feature",
                        getError(e), idTematico);
            } finally {
                rowTransaction.close();
            }
        }
        return errors;
    }

    /**
     * Inserts padr records relative to the given inputFeature in the padrObject
     * (Example table: siig_arco_1_sostanza)
     * @param padrObject
     * @param id
     * @param lunghezza
     * @param padr
     * @param inputFeature
     * @throws IOException
     */
    private void addAggregatePADRFeature(OutputObject padrObject, int id, int lunghezza,
            double padr[], SimpleFeature inputFeature) throws IOException {

        SimpleFeatureBuilder featureBuilder = padrObject.getBuilder();
        for (int count = 0; count < padr.length; count++) {
            try {
                double padrElement = padr[count];
                featureBuilder.reset();
                // compiles the attributes from target and read feature data,
                // using mappings
                // to match input attributes with output ones
                for (AttributeDescriptor attr : padrObject.getSchema().getAttributeDescriptors()) {
                    if (attr.getLocalName().equals("padr")) {
                        // compute the aritmetic average
                        featureBuilder.add(padrElement / lunghezza);
                    } else if (attr.getLocalName().equals("fk_partner")) {
                        featureBuilder.add(partner + "");
                    } else {
                        featureBuilder.add(null);
                    }
                }

                if(count >= padrSostanzaMapping.length){
                    LOGGER.warn("Found bad padr index: "+count+ ". Skipping..");
                    return;
                }
                //String idSostanza = (count + 1) + "";
                String idSostanza = padrSostanzaMapping[count];
                // GeoTools will split the featureid to populate "id_geo_arco" and "id_sostanza"
                // that are both primary keys and foreign keys of the table
                String featureid = id + "." + idSostanza;
                SimpleFeature feature = featureBuilder.buildFeature(featureid);
                feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

                padrObject.getWriter().addFeatures(DataUtilities.collection(feature));
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * @param outputObject
     * @param id
     * @param lunghezza
     * @param pterr
     * @param inputFeature
     * @throws IOException
     */
    private void addAggregateDissestoFeature(OutputObject outputObject, int id, int lunghezza,
            Set<Integer> pterr, SimpleFeature inputFeature) throws IOException {
        SimpleFeatureBuilder dissestoFeatureBuilder = outputObject.getBuilder();

        for (int dissesto : pterr) {
            // compiles the attributes from target and read feature data, using mappings
            // to match input attributes with output ones
            for (AttributeDescriptor attr : outputObject.getSchema().getAttributeDescriptors()) {
                if (attr.getLocalName().equals(geoId)) {
                    dissestoFeatureBuilder.add(id);
                } else if (attr.getLocalName().equals("id_dissesto")) {
                    dissestoFeatureBuilder.add(dissesto);
                } else if (attr.getLocalName().equals("fk_partner")) {
                    dissestoFeatureBuilder.add(partner + "");
                } else {
                    dissestoFeatureBuilder.add(null);
                }
            }
            String featureid = dissesto + "." + id;
            SimpleFeature feature = dissestoFeatureBuilder.buildFeature(featureid);
            feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

            outputObject.getWriter().addFeatures(DataUtilities.collection(feature));
        }
    }

    /**
     * @param outputObject
     * @param id
     * @param idTematico
     * @param tgm
     * @param velocita
     * @param inputFeature
     * @throws IOException
     */
    private void addAggregateVehicleFeature(OutputObject outputObject, int id, int lunghezza,
            int[] tgm, int[] velocita, String flgTgm, String flgVeloc, SimpleFeature inputFeature)
            throws IOException {
        SimpleFeatureBuilder byvehicleFeatureBuilder = outputObject.getBuilder();

        for (int type = 0; type <= 1; type++) {
            for (AttributeDescriptor attr : outputObject.getSchema().getAttributeDescriptors()) {
                if (attr.getLocalName().equals(geoId)) {
                    byvehicleFeatureBuilder.add(id);
                } else if (attr.getLocalName().equals("densita_veicolare")) {
                    if (lunghezza == 0) {
                        byvehicleFeatureBuilder.add(0);
                    } else {
                        byvehicleFeatureBuilder.add(tgm[type] / lunghezza);
                    }
                } else if (attr.getLocalName().equals("id_tipo_veicolo")) {
                    byvehicleFeatureBuilder.add(type + 1);
                } else if (attr.getLocalName().equals("flg_velocita")) {
                    byvehicleFeatureBuilder.add(flgVeloc);
                } else if (attr.getLocalName().equals("flg_densita_veicolare")) {
                    byvehicleFeatureBuilder.add(flgTgm);
                } else if (attr.getLocalName().equals("velocita_media")) {
                    if (lunghezza == 0) {
                        byvehicleFeatureBuilder.add(0);
                    } else {
                        byvehicleFeatureBuilder.add(velocita[type] / lunghezza);
                    }
                } else if (attr.getLocalName().equals("fk_partner")) {
                    byvehicleFeatureBuilder.add(partner + "");
                } else {
                    byvehicleFeatureBuilder.add(null);
                }
            }
            String featureid = (type + 1) + "." + id;
            SimpleFeature feature = byvehicleFeatureBuilder.buildFeature(featureid);
            feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

            outputObject.getWriter().addFeatures(DataUtilities.collection(feature));
        }
    }

    /**
     * @param outputObject
     * @param id
     * @param geo
     * @param lunghezza
     * @param corsie
     * @param inputFeature
     * @throws IOException
     */
    private void addAggregateGeoFeature(OutputObject outputObject, int id, int idTematico,
            Geometry geo, int lunghezza, int corsie, double incidenti, SimpleFeature inputFeature,
            int idOrigin, String flgCorsie, String flgIncidenti, boolean flgViadotto, boolean flgGalleria) throws IOException {
        SimpleFeatureBuilder geoFeatureBuilder = outputObject.getBuilder();
        //ricavo i codici provinciali e comunali 
        CodiceProvincialeComunale codiciProvincialiComunali = getCodiceProvincialeComune(geo);
        for (AttributeDescriptor attr : outputObject.getSchema().getAttributeDescriptors()) {
            if (attr.getLocalName().equals(geoId)) {
                geoFeatureBuilder.add(id);
            } else if (attr.getLocalName().equals("fk_partner")) {
                geoFeatureBuilder.add(partner + "");
            } else if (attr.getLocalName().equals("id_tematico_shape")) {
                geoFeatureBuilder.add(idTematico + "");
            } else if (attr.getLocalName().equals("geometria")) {
                geoFeatureBuilder.add(geo);
            } else if (attr.getLocalName().equals("cod_comune")) {
                if (codiciProvincialiComunali != null
                        && codiciProvincialiComunali.getCodiceComune() != null) {
                    geoFeatureBuilder.add(codiciProvincialiComunali.getCodiceComune());
                } else {
                    geoFeatureBuilder.add(null);
                }
            } else if (attr.getLocalName().equals("cod_provincia")) {
                if (codiciProvincialiComunali != null
                        && codiciProvincialiComunali.getCodiceProvincia() != null) {
                    geoFeatureBuilder.add(codiciProvincialiComunali.getCodiceProvincia());
                } else {
                    geoFeatureBuilder.add(null);
                }
            } else if (attr.getLocalName().equals("lunghezza")) {
                geoFeatureBuilder.add(lunghezza);
            } else if (attr.getLocalName().equals("nr_incidenti")) {
                geoFeatureBuilder.add(incidenti);
            } else if (attr.getLocalName().equals("nr_incidenti_elab")) {
                geoFeatureBuilder.add(incidenti);
            } else if (attr.getLocalName().equals("flg_nr_corsie")) {
                geoFeatureBuilder.add(flgCorsie);
            } else if (attr.getLocalName().equals("flg_nr_incidenti")) {
                geoFeatureBuilder.add(flgIncidenti);
            } else if (attr.getLocalName().equals("flg_viadotto")) {
                geoFeatureBuilder.add(flgViadotto?1:0);
            } else if (attr.getLocalName().equals("flg_galleria")) {
                geoFeatureBuilder.add(flgGalleria?1:0);
            } else if (attr.getLocalName().equals("id_origine")) {
                geoFeatureBuilder.add(idOrigin);
            } else if (attr.getLocalName().equals("nr_corsie")) {
                if (lunghezza == 0) {
                    geoFeatureBuilder.add(0);
                } else {
                    geoFeatureBuilder.add(corsie / lunghezza);
                }
            } else {
                geoFeatureBuilder.add(null);
            }
        }
        SimpleFeature geoFeature = geoFeatureBuilder.buildFeature("" + id);
        geoFeature.getUserData().put(Hints.USE_PROVIDED_FID, true);
        outputObject.getWriter().addFeatures(DataUtilities.collection(geoFeature));
    }

    /**
     * @param outputObject
     * @param id
     * @param geo
     * @param lunghezza
     * @param corsie
     * @param inputFeature
     * @throws IOException
     */
    private void updateAggregateGeoFeature(OutputObject outputObject, int id, int idTematico,
            Geometry geo, int lunghezza, int corsie, double incidenti, SimpleFeature inputFeature,
            int idOrigin, String flgCorsie, String flgIncidenti) throws IOException {
        Filter filter = filterFactory.and(
                filterFactory.equals(filterFactory.property("fk_partner"),
                        filterFactory.literal(partner + "")),
                filterFactory.equals(filterFactory.property("id_tematico_shape"),
                        filterFactory.literal(idTematico + "")));
        outputObject.getWriter()
                .modifyFeatures(outputObject.getSchema().getDescriptor("nr_incidenti").getName(),
                        incidenti, filter);
        outputObject.getWriter().modifyFeatures(
                outputObject.getSchema().getDescriptor("nr_incidenti_elab").getName(), incidenti,
                filter);

    }

    private void addAggregateCFFFeature(OutputObject cffObject, int id, int lunghezza,
            double cff[], SimpleFeature inputFeature) throws IOException {

        SimpleFeatureBuilder featureBuilder = cffObject.getBuilder();
        for (int count = 0; count < cff.length; count++) {
            try {
                double cffElement = cff[count];
                featureBuilder.reset();
                // compiles the attributes from target and read feature data, using mappings
                // to match input attributes with output ones
                for (AttributeDescriptor attr : cffObject.getSchema().getAttributeDescriptors()) {
                    if (attr.getLocalName().equals("cff")) {
                        // compute the aritmetic average
                        featureBuilder.add(cffElement / lunghezza);
                    } else if (attr.getLocalName().equals("fk_partner")) {
                        featureBuilder.add(partner + "");
                    } else {
                        featureBuilder.add(null);
                    }
                }
                String idBersaglio = bersaglio.getProperty(Integer.toString(count + 1));
                String featureid = id + "." + idBersaglio;
                SimpleFeature feature = featureBuilder.buildFeature(featureid);
                feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

                cffObject.getWriter().addFeatures(DataUtilities.collection(feature));
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * @param trace
     * @param dataStore
     * @param outputObjects
     * @param total
     * @param errors
     * @param outputName
     * @param inputFeature
     * @param id
     * @param idTematico
     * @return
     * @throws IOException
     */
    private int writeOutputObjects(int trace, DataStore dataStore, OutputObject[] outputObjects,
            int total, int errors, String outputName, SimpleFeature inputFeature, int id,
            int idTematico) throws IOException {
        Transaction rowTransaction = new DefaultTransaction();
        setTransaction(outputObjects, rowTransaction);

        try {
            addGeoFeature(outputObjects[4], id, inputFeature);
            addVehicleFeature(outputObjects[0], id, inputFeature);
            addDissestoFeature(outputObjects[1], id, inputFeature);
            addCFFFeature(outputObjects[2], id, inputFeature);
            addSostanzaFeature(outputObjects[3], id, inputFeature, dataStore);

            rowTransaction.commit();

            updateImportProgress(total, errors, "Importing data in " + outputName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            errors++;
            rowTransaction.rollback();
            metadataHandler.logError(trace, errors, "Error writing output feature", getError(e),
                    idTematico);
        } finally {
            rowTransaction.close();
        }
        return errors;
    }

    private CodiceProvincialeComunale getCodiceProvincialeComune(Geometry geometry) throws IOException {
        CodiceProvincialeComunale codici = new CodiceProvincialeComunale();
        FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2();

        SimpleFeatureSource tabComuni = dataStore.getFeatureSource("siig_geo_pl_comuni");
        Filter filtroComuni = filterFactory.intersects(filterFactory.property("geometria"),
                filterFactory.literal(geometry));
        SimpleFeatureCollection elencoComuni = tabComuni.getFeatures(filtroComuni);
        SimpleFeatureIterator iterComuni = elencoComuni.features();
        try {
            if (iterComuni != null) {
                double lunghezza = 0.0;
                while (iterComuni.hasNext()) {
                    SimpleFeature rec = iterComuni.next();
                    Geometry geomComune = (Geometry) rec.getDefaultGeometry();
                    Geometry intersezione = geomComune.intersection(geometry);
                    if (intersezione != null && intersezione.getLength() > lunghezza) {
                        lunghezza = intersezione.getLength();
                        codici.setCodiceComune((String) rec.getAttribute("cod_comune"));
                        codici.setCodiceProvincia((String) rec.getAttribute("cod_provincia"));
                    }
                }
            }
        } finally {
            iterComuni.close();
        }

        return codici;
    }

    /**
     * @throws IOException
     * 
     */
    private int importWithoutAggregation(int trace, DataStore dataStore,
            OutputObject[] outputObjects, int total, int errors, String outputName)
            throws IOException {
        try {
            SimpleFeature inputFeature = null;
            while ((inputFeature = readInput()) != null) {

                int id = nextId();
                int idTematico = getIdTematico(inputFeature, attributeMappings);

                errors = writeOutputObjects(trace, dataStore, outputObjects, total, errors,
                        outputName, inputFeature, id, idTematico);
            }
            importFinished(total, errors, "Data imported in " + outputName);

        } finally {
            closeInputReader();
        }

        return errors;
    }

    /**
     * Adds arc - vehicletype data feature.
     * 
     */
    private void addVehicleFeature(OutputObject vehicleObject, int id, SimpleFeature inputFeature)
            throws IOException {

        SimpleFeatureBuilder featureBuilder = vehicleObject.getBuilder();

        int[] tgm = extractMultipleValues(inputFeature, "TGM");
        int[] velocita = extractMultipleValues(inputFeature, "VELOCITA");

        for (int type = 0; type <= 1; type++) {
            featureBuilder.reset();
            // compiles the attributes from target and read feature data, using mappings
            // to match input attributes with output ones
            for (AttributeDescriptor attr : vehicleObject.getSchema().getAttributeDescriptors()) {
                if (attr.getLocalName().equals(geoId)) {
                    featureBuilder.add(id);
                } else if (attr.getLocalName().equals("densita_veicolare")) {
                    featureBuilder.add(tgm[type]);
                } else if (attr.getLocalName().equals("id_tipo_veicolo")) {
                    featureBuilder.add(type + 1);
                } else if (attr.getLocalName().equals("velocita_media")) {
                    featureBuilder.add(velocita[type]);
                } else if (attr.getLocalName().equals("fk_partner")) {
                    featureBuilder.add(partner + "");
                } else if (attributeMappings.containsKey(attr.getLocalName())) {
                    featureBuilder.add(getMapping(inputFeature, attributeMappings,
                            attr.getLocalName()));
                } else {
                    featureBuilder.add(null);
                }
            }
            String featureid = (type + 1) + "." + id;
            SimpleFeature feature = featureBuilder.buildFeature(featureid);
            feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

            vehicleObject.getWriter().addFeatures(DataUtilities.collection(feature));
        }
    }

    /**
     * @param inputFeature
     * @return
     */
    private int[] extractMultipleValues(SimpleFeature inputFeature, String attributeName) {
        if (inputFeature.getAttribute(attributeName) == null) {
            return new int[0];
        }
        String[] svalues = inputFeature.getAttribute(attributeName).toString().split("\\|");
        int[] values = new int[] { 0, 0 };

        for (int count = 0; count < svalues.length; count++) {
            try {
                values[count] = Integer.parseInt(svalues[count]);
            } catch (NumberFormatException e) {

            }
        }
        return values;
    }
    
    /**
     * Returns an array containing all the values found in the attribute of the given feature.
     * Values are split by | character
     * Values must be dot-noted double numbers
     * If no attribute is found, an empty array with the given default_size will be returned
     * @param inputFeature
     * @return
     */
    private double[] extractMultipleValuesDouble(SimpleFeature inputFeature, String attributeName,
            int default_size) {
    	    	
    	// Input feature has no Attribute, return an empty array
        if (inputFeature.getAttribute(attributeName) == null) {
            return new double[default_size];
        }
        
        String[] svalues = inputFeature.getAttribute(attributeName).toString().split("\\|");
        double[] values = new double[svalues.length];
        Arrays.fill(values, 0);
        
        for (int count = 0; count < svalues.length; count++) {
            try {
                String el = svalues[count].replace(",", ".");
                values[count] = Double.parseDouble(el);
            } catch (NumberFormatException e) {
                // Skipping malformed value, default to zero.
                values[count] = 0;
            }
        }
        return values;
    }

    /**
     * Adds arc - dissesto data feature.
     * 
     */
    private void addDissestoFeature(OutputObject dissestoObject, int id, SimpleFeature inputFeature)
            throws IOException {

        SimpleFeatureBuilder featureBuilder = dissestoObject.getBuilder();

        Set<Integer> pterrs = removePTerrDuplicates(inputFeature.getAttribute("PTERR") == null ? new String[] {}
                : inputFeature.getAttribute("PTERR").toString().split("\\|"));

        for (int dissesto : pterrs) {
            try {

                featureBuilder.reset();
                // compiles the attributes from target and read feature data, using mappings
                // to match input attributes with output ones
                for (AttributeDescriptor attr : dissestoObject.getSchema()
                        .getAttributeDescriptors()) {
                    if (attr.getLocalName().equals(geoId)) {
                        featureBuilder.add(id);
                    } else if (attr.getLocalName().equals("id_dissesto")) {
                        featureBuilder.add(dissesto);
                    } else if (attr.getLocalName().equals("fk_partner")) {
                        featureBuilder.add(partner + "");
                    } else {
                        featureBuilder.add(null);
                    }
                }
                String featureid = id + "." + dissesto;
                SimpleFeature feature = featureBuilder.buildFeature(featureid);
                feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

                dissestoObject.getWriter().addFeatures(DataUtilities.collection(feature));
            } catch (NumberFormatException e) {

            }
        }

    }

    /**
     * @param strings
     * @return
     */
    private Set<Integer> removePTerrDuplicates(String[] pterrs) {
        Set<Integer> result = new HashSet<Integer>();
        for (String pterr : pterrs) {
            try {
                result.add(Integer.parseInt(pterr));
            } catch (NumberFormatException e) {

            }
        }
        return result;
    }

    private void addCFFFeature(OutputObject cffObject, int id, SimpleFeature inputFeature)
            throws IOException {

        SimpleFeatureBuilder featureBuilder = cffObject.getBuilder();
        Object cffAttribute = inputFeature.getAttribute("CFF");
        String[] cffAttributeSplitted = cffAttribute == null ? new String[] {} : cffAttribute
                .toString().split("\\|");

        for (int count = 0; count < cffAttributeSplitted.length; count++) {
            try {
                String el = cffAttributeSplitted[count].replace(",", ".");
                double cffElement = Double.parseDouble(el);
                featureBuilder.reset();

                String idBersaglio = bersaglio.getProperty(Integer.toString(count + 1));

                // compiles the attributes from target and read feature data,
                // using mappings
                // to match input attributes with output ones
                for (AttributeDescriptor attr : cffObject.getSchema().getAttributeDescriptors()) {
                    if (attr.getLocalName().equals(geoId)) {
                        featureBuilder.add(id);
                    } else if (attr.getLocalName().equals("cff")) {
                        featureBuilder.add(cffElement);
                    } else if (attr.getLocalName().equals("id_bersaglio")) {
                        featureBuilder.add(idBersaglio);
                    } else if (attr.getLocalName().equals("fk_partner")) {
                        featureBuilder.add(partner + "");
                    } else {
                        featureBuilder.add(null);
                    }
                }
                // String fid2 = el.replaceAll("\\,", "");
                // fid2 = el.replaceAll("\\.", "");
                String featureid = id + "." + idBersaglio;
                SimpleFeature feature = featureBuilder.buildFeature(featureid);
                feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

                cffObject.getWriter().addFeatures(DataUtilities.collection(feature));
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * Populate the padr table splitting the padr properties of the input feature
     * @param sostanzaObject
     * @param id
     * @param inputFeature
     * @param datastore
     * @throws IOException
     */
    private void addSostanzaFeature(OutputObject sostanzaObject, int id,
            SimpleFeature inputFeature, DataStore datastore) throws IOException {

        SimpleFeatureBuilder featureBuilder = sostanzaObject.getBuilder();
        Object padrAttribute = inputFeature.getAttribute("PADR");
        String[] padrAttributeSplitted = padrAttribute == null ? new String[] {} : padrAttribute
                .toString().split("\\|");
        
        Object padr2Attribute = inputFeature.getAttribute("PADR2");
        String[] padr2AttributeSplitted = padr2Attribute == null ? new String[] {} : padr2Attribute
                .toString().split("\\|");
        
        Object padr3Attribute = inputFeature.getAttribute("PADR3");
        String[] padr3AttributeSplitted = padr3Attribute == null ? new String[] {} : padr3Attribute
                .toString().split("\\|");
        
        String[] composedPadr = ArrayUtils.addAll(
                ArrayUtils.addAll(padrAttributeSplitted , padr2AttributeSplitted),
                padr3AttributeSplitted);

        for (int count = 0; count < composedPadr.length; count++) {
            try {
                String el = composedPadr[count].replace(",", ".");
                double padrElement = Double.parseDouble(el);
                featureBuilder.reset();
                
                if(count >= padrSostanzaMapping.length){
                    LOGGER.warn("Found bad padr index: "+count+ ". Skipping..");
                    return;
                }
                //String id_sostanza = (count + 1) + "";
                String id_sostanza = padrSostanzaMapping[count];
                
                // for (String id_sostanza : sostanze) {
                for (AttributeDescriptor attr : sostanzaObject.getSchema()
                        .getAttributeDescriptors()) {
                    if (attr.getLocalName().equals(geoId)) {
                        featureBuilder.add(id);
                    } else if (attr.getLocalName().equals("id_sostanza")) {
                        featureBuilder.add(id_sostanza);
                    } else if (attr.getLocalName().equals("padr")) {
                        featureBuilder.add(padrElement);
                    } else if (attr.getLocalName().equals("fk_partner")) {
                        featureBuilder.add(partner + "");
                    } else {
                        featureBuilder.add(null);
                    }
                }
                String featureid = id + "." + id_sostanza;
                SimpleFeature feature = featureBuilder.buildFeature(featureid);
                feature.getUserData().put(Hints.USE_PROVIDED_FID, true);

                sostanzaObject.getWriter().addFeatures(DataUtilities.collection(feature));
                // }
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * Adds a new geo arc feature.
     * 
     * @param geoSchema
     * @param geoFeatureBuilder
     * @param geoFeatureWriter
     * @param inputFeature
     * @param id
     * @throws IOException
     */
    private void addGeoFeature(OutputObject geoObject, int id, SimpleFeature inputFeature)
            throws IOException {
        SimpleFeatureBuilder geoFeatureBuilder = geoObject.getBuilder();
        CodiceProvincialeComunale codici = null;
        Geometry geometry = (Geometry) inputFeature.getDefaultGeometry();
        codici = getCodiceProvincialeComune(geometry);
        // compiles the attributes from target and read feature data
        for (AttributeDescriptor attr : geoObject.getSchema().getAttributeDescriptors()) {
            if (attr.getLocalName().equals(geoId)) {
                geoFeatureBuilder.add(id);
            } else if (attr.getLocalName().equals("fk_partner")) {
                geoFeatureBuilder.add(partner + "");
            } else if (attr.getLocalName().equals("geometria")) {
                geoFeatureBuilder.add(geometry);
            } else if (attr.getLocalName().equals("lunghezza")) {
                Number lunghezza = (Number) getMapping(inputFeature, attributeMappings,
                        attr.getLocalName());
                if (lunghezza == null || lunghezza.intValue() <= 0) {
                    lunghezza = 1;
                }
                geoFeatureBuilder.add(lunghezza);
            } else if (attr.getLocalName().equals("cod_comune")) {
                if (codici != null && codici.getCodiceComune() != null) {
                    geoFeatureBuilder.add(codici.getCodiceComune());
                } else {
                    geoFeatureBuilder.add(null);
                }
            } else if (attr.getLocalName().equals("cod_provincia")) {
                if (codici != null && codici.getCodiceProvincia() != null) {
                    geoFeatureBuilder.add(codici.getCodiceProvincia());
                } else {
                    geoFeatureBuilder.add(null);
                }
            } else if (attributeMappings.containsKey(attr.getLocalName())) {
                geoFeatureBuilder.add(getMapping(inputFeature, attributeMappings,
                        attr.getLocalName()));
            } else {
                geoFeatureBuilder.add(null);
            }

        }

        SimpleFeature geoFeature = geoFeatureBuilder.buildFeature("" + id);
        geoFeature.getUserData().put(Hints.USE_PROVIDED_FID, true);
        geoObject.getWriter().addFeatures(DataUtilities.collection(geoFeature));
    }

    /**
     * Drops the input feature.
     * 
     * @param datastoreParams
     * @throws IOException
     */
    @Override
    protected void dropInputFeature(DataStore dataStore) throws IOException {
        dropFeature(dataStore, inputTypeName + "_ORIG");
        super.dropInputFeature(dataStore);

    }

}
