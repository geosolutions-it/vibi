package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.ArrayList;
import java.util.List;

class Fds1Service {

    private static final SimpleFeatureType PLOT_MODULE_HERBACEOUS_TYPE = createFeatureType("plot_module_herbaceous",
            "plot_no:Integer,module_id:Integer,corner:Integer,depth:Integer,species:String,cover_class_code:Integer");

    private static final SimpleFeatureType PLOT_TYPE = createFeatureType("plot", "");

    private static final SimpleFeatureType MODULE_TYPE = createFeatureType("module", "");

    private static final SimpleFeatureType CORNER_TYPE = createFeatureType("corner", "");

    private static final SimpleFeatureType DEPTH_TYPE = createFeatureType("depth", "");

    private static final SimpleFeatureType SPECIES_TYPE = createFeatureType("species", "");

    private static final SimpleFeatureType COVER_MIDPOINT_LOOKUP = createFeatureType("cover_midpoint_lookup", "");

    private static SimpleFeatureType createFeatureType(String tableName, String description) {
        try {
            return DataUtilities.createType(tableName, description);
        } catch (Exception exception) {
            throw new VibiException(exception, "Error creating feature type for table '%s' with description'%s'.",
                    tableName, description);
        }
    }

    static void processFds1Sheet(Sheet sheet, DataStore store) {
        int nextTableIndex = findNextTableIndex(sheet, 0);
        while (nextTableIndex != -1) {
            nextTableIndex = findNextTableIndex(sheet, processTable(sheet, store, nextTableIndex));
        }
    }
    private static int findNextTableIndex(Sheet sheet, int startIndex) {
        int index = startIndex;
        while (true) {
            Row row = sheet.getRow(index);
            if (row == null) {
                return -1;
            }
            Cell cell = row.getCell(Sheets.getIndex("O"));
            if (cell == null) {
                return -1;
            }
            if (Sheets.cellToString(cell).toLowerCase().contains("mod")) {
                return index;
            }
            index++;
        }
    }

    private static int processTable(Sheet sheet, DataStore store, int startRowIndex) {
        List<ModuleAndCorner> modulesAndCorners = getModulesAndCorners(sheet.getRow(startRowIndex + 1));
        int index = startRowIndex + 7;
        boolean moreData = true;
        int plotNo = extractInteger(sheet.getRow(startRowIndex + 3).getCell(Sheets.getIndex("A")));
        while (moreData) {
            Row row = sheet.getRow(index);
            Cell speciesCell = row.getCell(Sheets.getIndex("L"), Row.RETURN_BLANK_AS_NULL);
            if (speciesCell == null) {
                moreData = false;
                continue;
            }
            String species = extractString(speciesCell);
            processRow(store, row, plotNo, species, modulesAndCorners);
            index++;
        }
        return index;
    }

    private static void processRow(DataStore store, Row row, int plotNo,
                                   String species, List<ModuleAndCorner> modulesAndCorners) {
        for (ModuleAndCorner moduleAndCorner : modulesAndCorners) {
            Cell depthCell = row.getCell(moduleAndCorner.depthColumnIndex, Row.RETURN_BLANK_AS_NULL);
            Integer depth = depthCell == null ? null : extractInteger(depthCell);
            Cell coverClassCodeCell = row.getCell(moduleAndCorner.coverClassCodeIndex, Row.RETURN_BLANK_AS_NULL);
            Integer coverClassCode = depthCell == null ? null : extractInteger(coverClassCodeCell);
            createAndStoreFeature(store, plotNo, moduleAndCorner.module,
                    moduleAndCorner.corner, species, depth, coverClassCode);
        }
    }

    private static void createAndStoreFeature(DataStore store, int plotNo, int module, int corner,
                                              String species, Integer depth, Integer coverClassCode) {
        createForeignKeyIfNeed(store, PLOT_TYPE, plotNo);
        createForeignKeyIfNeed(store, MODULE_TYPE, module);
        createForeignKeyIfNeed(store, CORNER_TYPE, corner);
        createForeignKeyIfNeed(store, SPECIES_TYPE, species);
        if (depth != null) {
            createForeignKeyIfNeed(store, DEPTH_TYPE, depth);
        }
        if (coverClassCode != null) {
            createForeignKeyIfNeed(store, COVER_MIDPOINT_LOOKUP, coverClassCode);
        }
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(PLOT_MODULE_HERBACEOUS_TYPE);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = String.format("%d-%d-%d-%s", plotNo, module, corner, species.toLowerCase());
        featureBuilder.set("plot_no", plotNo);
        featureBuilder.set("module_id", module);
        featureBuilder.set("corner", corner);
        featureBuilder.set("depth", depth);
        featureBuilder.set("species", species);
        featureBuilder.set("cover_class_code", coverClassCode);
        Store.persistFeature(store, featureBuilder.buildFeature(id));
    }

    private static void createForeignKeyIfNeed(DataStore store, SimpleFeatureType type, Object id) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        Store.persistFeature(store, featureBuilder.buildFeature(id.toString()), false);
    }

    private static List<ModuleAndCorner> getModulesAndCorners(Row row) {
        List<ModuleAndCorner> modulesAndCorners = new ArrayList<>();
        int index = Sheets.getIndex("O");
        boolean moreModulesAndCorners = true;
        while (moreModulesAndCorners) {
            Cell module = row.getCell(index, Row.RETURN_BLANK_AS_NULL);
            Cell corner = row.getCell(index + 1, Row.RETURN_BLANK_AS_NULL);
            if (module == null || corner == null) {
                moreModulesAndCorners = false;
                continue;
            }
            modulesAndCorners.add(new ModuleAndCorner(extractInteger(module),
                    extractInteger(corner), index, index + 1));
            index += 2;
        }
        return modulesAndCorners;
    }

    private static Integer extractInteger(Cell cell) {
        return (Integer) Type.INTEGER.extract(cell);
    }

    private static String extractString(Cell cell) {
        return (String) Type.STRING.extract(cell);
    }

    private static final class ModuleAndCorner {

        int module;
        int corner;
        int depthColumnIndex;
        int coverClassCodeIndex;

        public ModuleAndCorner(int module, int corner, int depthColumnIndex, int coverClassCodeIndex) {
            this.module = module;
            this.corner = corner;
            this.depthColumnIndex = depthColumnIndex;
            this.coverClassCodeIndex = coverClassCodeIndex;
        }
    }
}
