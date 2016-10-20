package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Tuple;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.Transaction;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static it.geosolutions.vibi.mapper.service.VibiService.createFeatureType;

public class Fds1Service {

    private final static Logger LOGGER = Logger.getLogger(Fds1Service.class);

    private static final SimpleFeatureType PLOT_MODULE_HERBACEOUS_INFO_TYPE = createFeatureType("plot_module_herbaceous_info",
            "plot_id:String,module_id:String,corner:String,depth:Integer,info:String,cover_class_code:Integer");

    private static final SimpleFeatureType PLOT_MODULE_HERBACEOUS_TYPE = createFeatureType("plot_module_herbaceous",
            "plot_id:String,module_id:String,corner:String,depth:Integer,species:String,cover_class_code:Integer,group_id:String");

    private static final SimpleFeatureType FDS1_SPECIES_MISC_INFO = createFeatureType("fds1_species_misc_info",
            "species:String,plot_id:String,module_id:String,voucher_no:String,comment:String," +
                    "browse_intensity:String,percent_flowering:String,percent_fruiting:String,group_id:String");

    private static final SimpleFeatureType PLOT_TYPE = createFeatureType("plot", "");

    private static final SimpleFeatureType MODULE_TYPE = createFeatureType("module", "");

    private static final SimpleFeatureType CORNER_TYPE = createFeatureType("corner", "");

    private static final SimpleFeatureType DEPTH_TYPE = createFeatureType("depth", "");

    private static final SimpleFeatureType SPECIES_TYPE = createFeatureType("species", "");

    private static final SimpleFeatureType COVER_MIDPOINT_LOOKUP = createFeatureType("cover_midpoint_lookup", "");

    public static void processFds1Sheet(Map<Object, Object> globalContext, Sheet sheet, DataStore store, Transaction transaction) {
        LOGGER.info(String.format("Start parsing spreadsheet '%s'.", sheet.getSheetName()));
        int nextTableIndex = findNextTableIndex(sheet, 0);
        while (nextTableIndex != -1) {
            LOGGER.debug(String.format("Parsing fds1 table '%d'.", nextTableIndex));
            nextTableIndex = findNextTableIndex(sheet, processTable(globalContext, sheet, store, transaction, nextTableIndex));
        }
    }

    private static int findNextTableIndex(Sheet sheet, int startIndex) {
        int index = startIndex;
        try {
            while (true) {
                Row row = sheet.getRow(index);
                if (row == null) {
                    return -1;
                }
                String value = Sheets.extract(row, "O", Type.STRING);
                if (value != null && value.toLowerCase().contains("mod")) {
                    Cell cell = sheet.getRow(row.getRowNum() + 3).getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        return index;
                    }
                }
                index++;
            }
        } catch (VibiException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new VibiException(exception, "Error find nex table index at row '%d' of spreadsheet '%s'.",
                    index + 1, sheet.getSheetName());
        }
    }

    private static int processTable(Map<Object, Object> globalContext, Sheet sheet, DataStore store, Transaction transaction, int startRowIndex) {

        List<ModuleAndCorner> modulesAndCorners = getModulesAndCorners(sheet.getRow(startRowIndex + 1));
        String plotNo = getPlotNo(sheet, startRowIndex);
        String plotId = VibiService.getPlotId(globalContext, plotNo);
        processInfoRow(store, transaction, sheet.getRow(startRowIndex + 3), "%open water", plotId, modulesAndCorners);
        processInfoRow(store, transaction, sheet.getRow(startRowIndex + 4), "%unvegetated open water", plotId, modulesAndCorners);
        processInfoRow(store, transaction, sheet.getRow(startRowIndex + 5), "%bare ground", plotId, modulesAndCorners);
        processInfoRow(store, transaction, sheet.getRow(startRowIndex + 6), "%litter cover", plotId, modulesAndCorners);
        return processSpeciesRows(store, transaction, sheet, startRowIndex, plotId, modulesAndCorners);
    }

    private static String getPlotNo(Sheet sheet, int startRowIndex) {
        int siteHeaderRowIndex = startRowIndex;
        try {
            for (int i = 1; i <= 7; i++) {
                Cell siteCell = sheet.getRow(siteHeaderRowIndex).getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
                if (siteCell != null && Type.STRING.extract(siteCell).toString().trim().equalsIgnoreCase("site")) {
                    return VibiService.extractPlotNo(
                            sheet.getRow(siteHeaderRowIndex + 1).getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL));
                }
                siteHeaderRowIndex++;
            }
        } catch (VibiException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new VibiException(exception, "Error obtaining site for row '%d' of spreadsheet '%s'.",
                    siteHeaderRowIndex + 1, sheet.getSheetName());
        }
        throw new VibiException("Could not found plot number for table that starts at row '%d' of spreadsheet '%s'.",
                startRowIndex + 1, sheet.getSheetName());
    }

    private static void processInfoRow(DataStore store, Transaction transaction, Row row, String expectedInfo,
                                       String plotId, List<ModuleAndCorner> modulesAndCorners) {
        try {
            String info = extractString(row.getCell(Sheets.getIndex("L"), Row.RETURN_BLANK_AS_NULL)).trim();
            if (!info.equals(expectedInfo)) {
                throw new VibiException("Expecting row '%d' of spreadsheet '%s' to contain info '%s' but contains info '%s'.",
                        row.getRowNum() + 1, row.getSheet().getSheetName(), expectedInfo, info);
            }
            for (ModuleAndCorner moduleAndCorner : modulesAndCorners) {
                Tuple<Integer, Integer> depthAndCoverClassCode = extractDepthAndCoverClassCode(row, moduleAndCorner);
                createAndStoreInfoFeature(store, transaction, row, plotId, moduleAndCorner.module, moduleAndCorner.corner,
                        info, depthAndCoverClassCode.first, depthAndCoverClassCode.second);
            }
        } catch (VibiException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new VibiException(exception, "Error processing row '%d' of spreadsheet '%s'.",
                    row.getRowNum() + 1, row.getSheet().getSheetName());
        }
    }

    private static int processSpeciesRows(DataStore store, Transaction transaction, Sheet sheet, int startRowIndex,
                                          String plotId, List<ModuleAndCorner> modulesAndCorners) {
        int index = startRowIndex + 7;
        boolean moreData = true;
        while (moreData) {
            Row row = sheet.getRow(index);
            Cell speciesCell = row.getCell(Sheets.getIndex("L"), Row.RETURN_BLANK_AS_NULL);
            if (speciesCell == null) {
                moreData = false;
                continue;
            }
            String species = extractString(speciesCell);
            String groupId = UUID.randomUUID().toString();
            try {
                processSpeciesRow(store, transaction, row, groupId, plotId, species, modulesAndCorners);
            } catch (VibiException exception) {
                throw exception;
            } catch (Exception exception) {
                throw new VibiException(exception, "Error processing row '%d' of spreadsheet '%s'.",
                        row.getRowNum() + 1, sheet.getSheetName());
            }
            index++;
        }
        return index;
    }


    private static void processSpeciesRow(DataStore store, Transaction transaction, Row row, String groupId, String plotId,
                                          String species, List<ModuleAndCorner> modulesAndCorners) {
        for (ModuleAndCorner moduleAndCorner : modulesAndCorners) {
            Tuple<Integer, Integer> depthAndCoverClassCode = extractDepthAndCoverClassCode(row, moduleAndCorner);
            createAndStoreSpeciesFeature(store, transaction, row, groupId, plotId, moduleAndCorner.module, moduleAndCorner.corner,
                    species, depthAndCoverClassCode.first, depthAndCoverClassCode.second);
            createAndStoreMiscFeature(store, transaction, row, groupId, species, plotId, moduleAndCorner.module,
                    (String) Sheets.extract(row, "M", Type.STRING),
                    (String) Sheets.extract(row, "N", Type.STRING),
                    (String) Sheets.extract(row, "K", Type.STRING));
        }
    }

    private static Tuple<Integer, Integer> extractDepthAndCoverClassCode(Row row, ModuleAndCorner moduleAndCorner) {
        Integer depth = null;
        if (!(moduleAndCorner.corner.equalsIgnoreCase("R") || moduleAndCorner.module.equalsIgnoreCase("R"))) {
            Cell depthCell = row.getCell(moduleAndCorner.depthColumnIndex, Row.RETURN_BLANK_AS_NULL);
            depth = depthCell == null ? null : extractInteger(depthCell);
        }
        Cell coverClassCodeCell = row.getCell(moduleAndCorner.coverClassCodeIndex, Row.RETURN_BLANK_AS_NULL);
        Integer coverClassCode = coverClassCodeCell == null ? null : extractInteger(coverClassCodeCell);
        return Tuple.tuple(depth, coverClassCode);
    }

    private static <T> T safeDefault(T original, T fallBack) {
        if (original == null) {
            return fallBack;
        }
        return original;
    }

    private static SimpleFeatureBuilder createCommonFeatureBuilder(DataStore store, Transaction transaction, Row row, String groupId, String plotId,
                                                                   String module, String corner, Integer depth, Integer coverClassCode, SimpleFeatureType type) {


        VibiService.testForeignKeyExists(store, transaction, row, PLOT_TYPE, plotId);
        VibiService.testForeignKeyExists(store, transaction, row, MODULE_TYPE, module);
        VibiService.testForeignKeyExists(store, transaction, row, CORNER_TYPE, corner);
        if (depth != null) {
            VibiService.testForeignKeyExists(store, transaction, row, DEPTH_TYPE, depth);
        }
        if (coverClassCode != null) {
            VibiService.testForeignKeyExists(store, transaction, row, COVER_MIDPOINT_LOOKUP, coverClassCode);
        }
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        if (groupId != null) {
            featureBuilder.set("group_id", groupId);
        }
        featureBuilder.set("plot_id", plotId);
        featureBuilder.set("module_id", module);
        featureBuilder.set("corner", corner);
        featureBuilder.set("depth", depth);
        featureBuilder.set("cover_class_code", coverClassCode);
        return featureBuilder;
    }

    private static void createAndStoreSpeciesFeature(DataStore store, Transaction transaction, Row row, String groupId, String plotId, String module,
                                                     String corner, String species, Integer depth, Integer coverClassCode) {
        species = VibiService.testSpeciesForeignKey(store, transaction, row, SPECIES_TYPE, species);
        String id = UUID.randomUUID().toString();
        SimpleFeatureBuilder featureBuilder = createCommonFeatureBuilder(
                store, transaction, row, groupId, plotId, module, corner, depth, coverClassCode, PLOT_MODULE_HERBACEOUS_TYPE);
        featureBuilder.set("species", species);
        Store.persistFeature(store, transaction, featureBuilder.buildFeature(id));
    }

    private static void createAndStoreMiscFeature(DataStore store, Transaction transaction, Row row, String groupId, String species, String plotId,
                                                  String module, String voucherNo, String comment, String browseIntensity) {
        species = VibiService.testSpeciesForeignKey(store, transaction, row, SPECIES_TYPE, species);
        VibiService.testForeignKeyExists(store, transaction, row, PLOT_TYPE, plotId);
        VibiService.testForeignKeyExists(store, transaction, row, MODULE_TYPE, module);
        String id = UUID.randomUUID().toString();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(FDS1_SPECIES_MISC_INFO);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        featureBuilder.set("group_id", groupId);
        featureBuilder.set("species", species);
        featureBuilder.set("plot_id", plotId);
        featureBuilder.set("module_id", module);
        featureBuilder.set("voucher_no", voucherNo);
        featureBuilder.set("comment", comment);
        featureBuilder.set("browse_intensity", browseIntensity);
        Store.persistFeature(store, transaction, featureBuilder.buildFeature(id));
    }

    private static void createAndStoreInfoFeature(DataStore store, Transaction transaction, Row row, String plotId, String module, String corner,
                                                  String info, Integer depth, Integer coverClassCode) {
        String id = UUID.randomUUID().toString();
        SimpleFeatureBuilder featureBuilder = createCommonFeatureBuilder(
                store, transaction, row, null, plotId, module, corner, depth, coverClassCode, PLOT_MODULE_HERBACEOUS_INFO_TYPE);
        featureBuilder.set("info", info);
        Store.persistFeature(store, transaction, featureBuilder.buildFeature(id));
    }

    private static List<ModuleAndCorner> getModulesAndCorners(Row row) {
        List<ModuleAndCorner> modulesAndCorners = new ArrayList<>();
        int index = Sheets.getIndex("O");
        boolean moreModulesAndCorners = true;
        while (moreModulesAndCorners && index < 10000) {
            Cell module = row.getCell(index, Row.RETURN_BLANK_AS_NULL);
            Cell corner = row.getCell(index + 1, Row.RETURN_BLANK_AS_NULL);
            if (module == null || corner == null) {
                index += 2;
                continue;
            }
            String moduleNumber = extractString(module);
            if (moduleNumber.equalsIgnoreCase("R")) {
                moreModulesAndCorners = false;
            }
            modulesAndCorners.add(new ModuleAndCorner(extractString(module),
                    extractString(corner), index, index + 1));
            index += 2;
        }
        if (index >= 10000) {
            LOGGER.warn(String.format(
                    "Max number of searching corner/modules reached, if there is no residual corner/modules this is fine."));
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

        String module;
        String corner;
        int depthColumnIndex;
        int coverClassCodeIndex;

        public ModuleAndCorner(String module, String corner, int depthColumnIndex, int coverClassCodeIndex) {
            this.module = module;
            this.corner = corner;
            this.depthColumnIndex = depthColumnIndex;
            this.coverClassCodeIndex = coverClassCodeIndex;
        }
    }
}
