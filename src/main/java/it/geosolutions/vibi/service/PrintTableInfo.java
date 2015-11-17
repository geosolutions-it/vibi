package it.geosolutions.vibi.service;

import it.geosolutions.vibi.meta.AttributeMeta;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Validations;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public final class PrintTableInfo {

    public static void main(final String[] args) {

        Validations.checkCondition(args.length == 6, "Usage: <work book> <sheet name> <header row index> " +
                "<data sample row index> <start column> <end column>");

        new Sheets.WorkOnSheet(args[0], args[1]) {

            @Override
            public void doWork(Sheet sheet) {
                List<Cell> headerCells = Sheets.getCells(sheet, Integer.parseInt(args[2]), args[4], args[5]);
                List<Cell> dataSampleCells = Sheets.getCells(sheet, Integer.parseInt(args[3]), args[4], args[5]);
                System.out.println("Attributes:\n");
                for (int i = 0; i < headerCells.size(); i++) {
                    System.out.println(String.format("\t%s %s",
                            AttributeMeta.getNormalizedName(headerCells.get(i)).toLowerCase(),
                            getCellType(dataSampleCells.get(i))));
                }
            }
        };
    }

    private static String getCellType(Cell cell) {
        if (cell == null) {
            return "null";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return "text";
            case Cell.CELL_TYPE_NUMERIC:
                return DateUtil.isCellDateFormatted(cell) ? "timestamp" : "numeric";
            case Cell.CELL_TYPE_BOOLEAN:
                return "boolean";
            case Cell.CELL_TYPE_FORMULA:
                return "formula";
            default:
                throw new RuntimeException(String.format("Unknown cell type '%d'.", cell.getCellType()));
        }
    }
}
