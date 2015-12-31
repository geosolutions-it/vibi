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
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.ArrayList;
import java.util.List;

import static it.geosolutions.vibi.mapper.service.VibiService.createFeatureType;

public class Fds2Service {

    private final static Logger LOGGER = Logger.getLogger(Fds2Service.class);

    private static final SimpleFeatureType PLOT_MODULE_WOODY_RAW = createFeatureType("plot_module_woody_raw",
            "plot_no:Integer,sub:Double,module_id:Integer,species:String,dbh_class:String,dbh_class_index:Integer,count:String");

    private static final SimpleFeatureType PLOT_TYPE = createFeatureType("plot", "");

    private static final SimpleFeatureType MODULE_TYPE = createFeatureType("module", "");

    private static final SimpleFeatureType SPECIES_TYPE = createFeatureType("species", "");

    private static final SimpleFeatureType DBH_CLASS = createFeatureType("dbh_class", "");

    public static void processFds2Sheet(Sheet sheet, DataStore store) {
        LOGGER.info(String.format("Start parsing spreadsheet '%s'.", sheet.getSheetName()));
        Row row = findHeaderRow(sheet);
        if (row == null) {
            throw new VibiException("No header could be found on spreadsheet '%s'.", sheet.getSheetName());
        }
        List<DbhClass> dbhClassList = getDbhClassList(row);
        Integer plotNo = null;
        row = sheet.getRow(row.getRowNum() + 1);
        while (row != null) {
            try {
                plotNo = processRow(store, dbhClassList, plotNo, row);
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
                return sheet.getRow(row.getRowNum());
            }
        }
        return null;
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

    private static Integer processRow(DataStore store, List<DbhClass> dbhClassList, Integer existingPlotNo, Row row) {
        Integer plotNo = extractPlotNo(row, existingPlotNo);
        if (plotNo == null) {
            throw new VibiException("Could not map row '%d' of sheet '%s' to site code.",
                    row.getRowNum(), row.getSheet().getSheetName());
        }
        Cell speciesCell = row.getCell(Sheets.getIndex("H"), Row.RETURN_BLANK_AS_NULL);
        if (speciesCell == null) {
            return plotNo;
        }
        Object sub = Type.DOUBLE.extract(row.getCell(Sheets.getIndex("F"), Row.RETURN_BLANK_AS_NULL));
        Object module = Type.INTEGER.extract(row.getCell(Sheets.getIndex("G"), Row.RETURN_BLANK_AS_NULL));
        Object species = Type.STRING.extract(speciesCell);
        for (DbhClass dbhClass : dbhClassList) {
            Cell countCell = row.getCell(dbhClass.columnIndex, Row.RETURN_BLANK_AS_NULL);
            if (countCell != null) {
                Object count = Type.STRING.extract(countCell);
                processDbhClass(store, row, dbhClass.dbhClass, dbhClass.dbhClassIndex, plotNo, sub, module, species, count);
            }
        }
        return plotNo;
    }

    private static void processDbhClass(DataStore store, Row row, Object dbhClass, Object dbhClassIndex, Object plotNo,
                                        Object sub, Object module, Object species, Object count) {
        VibiService.testForeignKeyExists(store, row, PLOT_TYPE, plotNo);
        species = VibiService.testSpeciesForeignKey(store, row, SPECIES_TYPE, (String) species);
        VibiService.createForeignKeyIfNeed(store, MODULE_TYPE, module);
        VibiService.createForeignKeyIfNeed(store, DBH_CLASS, dbhClass);
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(PLOT_MODULE_WOODY_RAW);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = String.format("%s-%s-%s-%s", plotNo, module, species, dbhClass);
        featureBuilder.set("plot_no", plotNo);
        featureBuilder.set("sub", sub);
        featureBuilder.set("module_id", module);
        featureBuilder.set("species", species);
        featureBuilder.set("dbh_class", dbhClass);
        featureBuilder.set("dbh_class_index", dbhClassIndex);
        featureBuilder.set("count", count);
        Store.persistFeature(store, featureBuilder.buildFeature(id));
    }

    private static Integer extractPlotNo(Row row, Integer plotNo) {
        Cell cell = row.getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            return (Integer) Type.INTEGER.extract(cell);
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
