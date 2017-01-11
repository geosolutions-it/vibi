package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.Transaction;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class VibiService {

    private VibiService() {
    }

    public static void submit(File workBookFile, final DataStore store, final Transaction transaction) {
        submit(workBookFile.getPath(), store, transaction);
    }

    public static final String PLOTS_INDEX = "plots_index";
    public static final String PLOT_LOCATION = "plot_location";

    public static Map<String, String> getPlotsIndex(Map<Object, Object> globalContext) {
        Map<String, String> plotsIndex = (Map<String, String>) globalContext.get(PLOTS_INDEX);
        if (plotsIndex == null) {
            plotsIndex = new HashMap<>();
            globalContext.put(PLOTS_INDEX, plotsIndex);
        }
        return plotsIndex;
    }

    public static String getPlotId(Map<Object, Object> globalContext, String plotNo) {
        Map<String, String> plotsIndex = VibiService.getPlotsIndex(globalContext);
        String plotId = plotsIndex.get(plotNo);
        if (plotId == null) {
            throw new RuntimeException(String.format("Could not find plot id for plot number '%s'.", plotNo));
        }
        return plotId;
    }

    public static void submit(String workBookPath, final DataStore store, final Transaction transaction) {
        new Sheets.WorkBook(workBookPath) {

            @Override
            public void doWork(HSSFWorkbook workBook) {
                Map<Object, Object> globalContext = new HashMap<>();
                LookupService.processLookupNatureSOPEACommunitySheet(workBook.getSheet("LOOKUP NatureS+OEPA community"), store, transaction);
                LookupService.processLookupMidPointSheet(workBook.getSheet("LOOKUP midpoint"), store, transaction);
                PlotService.processPlotInfoSheet(globalContext, workBook.getSheet("ENTER PLOT INFO"), store, transaction);
                Fds1Service.processFds1Sheet(globalContext, workBook.getSheet("ENTER FDS1"), store, transaction);
                Fds2Service.processFds2Sheet(globalContext, workBook.getSheet("ENTER FDS2"), store, transaction);
                BiomassService.processBiomassSheet(globalContext, workBook.getSheet("ENTER BIOMASS"), store, transaction);
            }
        };
    }

    static SimpleFeatureType createFeatureType(String tableName, String description) {
        try {
            return DataUtilities.createType(tableName, description);
        } catch (Exception exception) {
            throw new VibiException(exception, "Error creating feature type for table '%s' with description'%s'.",
                    tableName, description);
        }
    }

    static String testSpeciesForeignKey(DataStore store, Transaction transaction, Row row, SimpleFeatureType type, String species) {
        if (!testForeignKeyExists(store, transaction, type, species)) {
            species = species.toUpperCase();
            if (!testForeignKeyExists(store, transaction, type, species)) {
                species = lowerSpecies(species);
                testForeignKeyExists(store, transaction, row, type, species);
            }
        }
        return species;
    }

    static String lowerSpecies(String species) {
        return Character.toUpperCase(species.charAt(0)) + species.substring(1, species.length()).toLowerCase();
    }

    static void testForeignKeyExists(DataStore store, Transaction transaction, Row row, SimpleFeatureType type, Object id) {
        if (!testForeignKeyExists(store, transaction, type, id)) {
            throw new VibiException("Foreign key '%s' of type '%s' in the context of row " +
                    "'%d' of spreadsheet '%s' could not be found.",
                    id, type.getTypeName(), row.getRowNum() + 1, row.getSheet().getSheetName());
        }
    }

    static boolean testForeignKeyExists(DataStore store, Transaction transaction, SimpleFeatureType type, Object id) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        SimpleFeature foundFeature = Store.find(store, transaction, featureBuilder.buildFeature(id.toString()));
        return foundFeature != null;
    }

    static void createForeignKeyIfNeed(DataStore store, Transaction transaction, SimpleFeatureType type, Object id) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        Store.persistFeature(store, transaction, featureBuilder.buildFeature(id.toString()), false);
    }

    // helper method to extract a plot number and properly converting it to string
    static String extractPlotNo(Cell cell) {
        if (cell == null) {
            return null;
        }
       return (String) Type.STRING.extract(cell);
    }
}
