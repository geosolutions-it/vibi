package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;

public final class VibiService {

    private VibiService() {
    }

    public static void submit(File woorkBookFile, final DataStore store) {
        submit(woorkBookFile.getPath(), store);
    }

    public static void submit(String woorkBookPath, final DataStore store) {
        new Sheets.WorkBook(woorkBookPath) {

            @Override
            public void doWork(HSSFWorkbook workBook) {
                LookupService.processLookupSpeciesSheet(workBook.getSheet("LOOKUP species"), store);
                LookupService.processLookupCommunitySheet(workBook.getSheet("LOOKUP community"), store);
                LookupService.processLookupMidPointSheet(workBook.getSheet("LOOKUP midpoint"), store);
                PlotService.processPlotInfoSheet(workBook.getSheet("ENTER PLOT INFO"), store);
                Fds1Service.processFds1Sheet(workBook.getSheet("ENTER FDS1"), store);
                Fds2Service.processFds2Sheet(workBook.getSheet("ENTER FDS2"), store);
                BiomassService.processBiomassSheet(workBook.getSheet("ENTER BIOMASS"), store);
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


    static String testSpeciesForeignKey(DataStore store, Row row, SimpleFeatureType type, String species) {
        if (!testForeignKeyExists(store, type, species)) {
            species = species.toUpperCase();
            if (!testForeignKeyExists(store, type, species)) {
                species = lowerSpecies(species);
                testForeignKeyExists(store, row, type, species);
            }
        }
        return species;
    }

    static String lowerSpecies(String species) {
        return Character.toUpperCase(species.charAt(0)) + species.substring(1, species.length()).toLowerCase();
    }

    static void testForeignKeyExists(DataStore store, Row row, SimpleFeatureType type, Object id) {
        if (!testForeignKeyExists(store, type, id)) {
            throw new VibiException("Foreign key '%s' of type '%s' in the context of row " +
                    "'%d' of spreadsheet '%s' could not be found.",
                    id, type.getTypeName(), row.getRowNum() + 1, row.getSheet().getSheetName());
        }
    }

    static boolean testForeignKeyExists(DataStore store, SimpleFeatureType type, Object id) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        SimpleFeature foundFeature = Store.find(store, featureBuilder.buildFeature(id.toString()));
        return foundFeature != null;
    }

    static void createForeignKeyIfNeed(DataStore store, SimpleFeatureType type, Object id) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        Store.persistFeature(store, featureBuilder.buildFeature(id.toString()), false);
    }
}
