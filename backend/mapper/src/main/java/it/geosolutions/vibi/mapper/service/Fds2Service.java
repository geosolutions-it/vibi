package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
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

public class Fds2Service {

    private final static Logger LOGGER = Logger.getLogger(Fds2Service.class);

    private static final SimpleFeatureType PLOT_MODULE_WOODY_RAW = createFeatureType("plot_module_woody_raw",
            "plot_id:Integer,sub:Double,module_id:String,species:String,dbh_class:String,dbh_class_index:Integer,count:String,group_id:String");

    private static final SimpleFeatureType FDS2_SPECIES_MISC_INFO = createFeatureType("fds2_species_misc_info",
            "species:String,plot_id:String,module_id:String,voucher_no:String,comment:String," +
                    "browse_intensity:String,percent_flowering:String,percent_fruiting:String,group_id:String");

    private static final SimpleFeatureType PLOT_TYPE = createFeatureType("plot", "");

    private static final SimpleFeatureType MODULE_TYPE = createFeatureType("module", "");

    private static final SimpleFeatureType SPECIES_TYPE = createFeatureType("species", "");

    private static final SimpleFeatureType DBH_CLASS = createFeatureType("dbh_class", "");

    public static void processFds2Sheet(Map<Object, Object> globalContext, Sheet sheet, DataStore store, Transaction transaction) {
        LOGGER.info(String.format("Start parsing spreadsheet '%s'.", sheet.getSheetName()));
        Row row = findHeaderRow(sheet);
        if (row == null) {
            return;
        }
        List<DbhClass> dbhClassList = getDbhClassList(row);
        String plotNo = null;
        row = sheet.getRow(row.getRowNum() + 1);
        while (row != null) {
            try {
                plotNo = processRow(globalContext, store, transaction, dbhClassList, plotNo, row);
                row = sheet.getRow(row.getRowNum() + 1);
            } catch (VibiException exception) {
                throw exception;
            } catch (Exception exception) {
                throw new VibiException(exception, "Error processing row '%d' of spreadsheet '%s'.",
                        row.getRowNum() + 1, sheet.getSheetName());
            }
        }
    }

    private static Row findHeaderRow(Sheet sheet) {
        for (Row row : sheet) {
            Cell cell = row.getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
            if (cell != null && ((String) Type.STRING.extract(cell)).equalsIgnoreCase("site name")) {
                Row headerRow = sheet.getRow(row.getRowNum());
                Cell plotNoCell = sheet.getRow(headerRow.getRowNum() + 1).getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
                if (plotNoCell == null) {
                    return null;
                }
                return headerRow;
            }
        }
        throw new VibiException("No header could be found on spreadsheet '%s'.", sheet.getSheetName());
    }

    private static List<DbhClass> getDbhClassList(Row row) {
        Row dbhClassIndexRow = row.getSheet().getRow(row.getRowNum() - 1);
        List<DbhClass> dbhClassList = new ArrayList<>();
        int index = Sheets.getIndex("L");
        String dbhClass = (String) Type.STRING.extract(row.getCell(index));
        int dbhClassIndex = (int) Type.INTEGER.extract(dbhClassIndexRow.getCell(index));
        do {
            dbhClassList.add(new DbhClass(dbhClass, dbhClassIndex, index));
            index++;
            dbhClass = (String) Type.STRING.extract(row.getCell(index));
            dbhClassIndex = (int) Type.INTEGER.extract(dbhClassIndexRow.getCell(index));
        } while (!dbhClass.equalsIgnoreCase("clump"));
        return dbhClassList;
    }

    private static String processRow(Map<Object, Object> globalContext, DataStore store, Transaction transaction,
                                     List<DbhClass> dbhClassList, String existingPlotNo, Row row) {
        String plotNo = extractPlotNo(row, existingPlotNo);
        if (plotNo == null) {
            throw new VibiException("Could not map row '%d' of sheet '%s' to site code.",
                    row.getRowNum(), row.getSheet().getSheetName());
        }
        String plotId = VibiService.getPlotId(globalContext, plotNo);
        Cell speciesCell = row.getCell(Sheets.getIndex("H"), Row.RETURN_BLANK_AS_NULL);
        if (speciesCell == null) {
            return plotNo;
        }
        Object sub = Type.DOUBLE.extract(row.getCell(Sheets.getIndex("F"), Row.RETURN_BLANK_AS_NULL));
        Object module = Type.STRING.extract(row.getCell(Sheets.getIndex("G"), Row.RETURN_BLANK_AS_NULL));
        Object species = Type.STRING.extract(speciesCell);
        String groupId = UUID.randomUUID().toString();
        createAndStoreMiscFeature(store, transaction, row, groupId, (String) species, plotId, (String) module,
                (String) Sheets.extract(row, "I", Type.STRING),
                (String) Sheets.extract(row, "J", Type.STRING),
                (String) Sheets.extract(row, "K", Type.STRING));
        for (DbhClass dbhClass : dbhClassList) {
            Cell countCell = row.getCell(dbhClass.columnIndex, Row.RETURN_BLANK_AS_NULL);
            if (countCell != null) {
                Object count = Type.STRING.extract(countCell);
                processDbhClass(store, transaction, row, groupId, dbhClass.dbhClass, dbhClass.dbhClassIndex, plotId, sub, module, species, count);
            }
        }
        return plotNo;
    }

    private static void createAndStoreMiscFeature(DataStore store, Transaction transaction, Row row, String groupId, String species, String plotId,
                                                  String module, String voucherNo, String comment, String browseIntensity) {
        species = VibiService.testSpeciesForeignKey(store, transaction, row, SPECIES_TYPE, species);
        VibiService.testForeignKeyExists(store, transaction, row, PLOT_TYPE, plotId);
        VibiService.testForeignKeyExists(store, transaction, row, MODULE_TYPE, module);
        String id = UUID.randomUUID().toString();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(FDS2_SPECIES_MISC_INFO);
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

    private static void processDbhClass(DataStore store, Transaction transaction, Row row, String groupId, Object dbhClass,
                                        Object dbhClassIndex, Object plotId, Object sub, Object module, Object species, Object count) {
        VibiService.testForeignKeyExists(store, transaction, row, PLOT_TYPE, plotId);
        species = VibiService.testSpeciesForeignKey(store, transaction, row, SPECIES_TYPE, (String) species);
        VibiService.testForeignKeyExists(store, transaction, row, MODULE_TYPE, module);
        VibiService.testForeignKeyExists(store, transaction, row, DBH_CLASS, dbhClass);
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(PLOT_MODULE_WOODY_RAW);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = UUID.randomUUID().toString();
        featureBuilder.set("group_id", plotId);
        featureBuilder.set("plot_id", plotId);
        featureBuilder.set("sub", sub);
        featureBuilder.set("module_id", module);
        featureBuilder.set("species", species);
        featureBuilder.set("dbh_class", dbhClass);
        featureBuilder.set("dbh_class_index", dbhClassIndex);
        featureBuilder.set("count", count);
        Store.persistFeature(store, transaction, featureBuilder.buildFeature(id));
    }

    private static String extractPlotNo(Row row, String plotNo) {
        Cell cell = row.getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            return VibiService.extractPlotNo(cell);
        }
        return plotNo;
    }

    private static class DbhClass {

        String dbhClass;
        int dbhClassIndex;
        int columnIndex;

        public DbhClass(String dbhClass, int dbhClassIndex, int columnIndex) {
            this.dbhClass = dbhClass;
            this.dbhClassIndex = dbhClassIndex;
            this.columnIndex = columnIndex;
        }
    }
}
