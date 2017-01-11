package it.geosolutions.vibi.mapper.utils;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class Sheets {

    private Sheets() {
    }

    public static int getIndex(String column) {
        return CellReference.convertColStringToIndex(column);
    }

    public static String getColumn(int index) {
        return CellReference.convertNumToColString(index);
    }

    public static <T> T extract(Row row, String column, Type type) {
        return extract(row, Sheets.getIndex(column), type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T extract(Row row, int columnIndex, Type type) {
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        return (T) type.extract(cell);
    }

    public static Object extract(Cell cell) {
        if (cell == null) {
            return null;
        }
        FormulaEvaluator evaluator = cell.getRow().getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        Object result;
        try {
            evaluator.evaluateInCell(cell);
        } catch (Exception exception) {
            throw new VibiException(exception, "Error evaluating cell from sheet '%s', row '%d' and column '%s'.",
                    cell.getSheet().getSheetName(), cell.getRow().getRowNum() + 1, Sheets.getColumn(cell.getColumnIndex()));
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                try {
                    result = cell.getBooleanCellValue();
                } catch (Exception exception) {
                    throw extractException(cell, "BOOLEAN", exception);
                }
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    try {
                        result = cell.getDateCellValue();
                    } catch (Exception exception) {
                        throw extractException(cell, "DATE", exception);
                    }
                } else {
                    try {
                        result = cell.getNumericCellValue();
                    } catch (Exception exception) {
                        throw extractException(cell, "NUMERIC", exception);
                    }
                }
                break;
            case Cell.CELL_TYPE_STRING:
                try {
                    result = cell.getRichStringCellValue().getString();
                } catch (Exception exception) {
                    throw extractException(cell, "STRING", exception);
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                result = null;
                break;
            case Cell.CELL_TYPE_ERROR:
                result = null;
                break;
            default:
                throw new VibiException("Unknown cell type '%d' from sheet '%s', row '%d' and column '%s'.", cell.getCellType(),
                        cell.getSheet().getSheetName(), cell.getRow().getRowNum() + 1, Sheets.getColumn(cell.getColumnIndex()));
        }
        return result;
    }

    private static VibiException extractException(Cell cell, String type, Exception exception) {
        return new VibiException(exception,
                "Error extracting %s value from sheet '%s', row '%d' and column '%s'.", type,
                cell.getSheet().getSheetName(), cell.getRow().getRowNum() + 1, Sheets.getColumn(cell.getColumnIndex()));
    }

    public static Object getValue(SheetContext context, int columnIndex, Type type) {
        Cell cell = context.getRow().getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return null;
        }
        return type.extract(cell);
    }

    public static List<Cell> getCells(Sheet sheet, int rowIndex, String startColumn, String endColumn) {
        int startColumnIndex = getIndex(startColumn);
        int endColumnIndex = getIndex(endColumn);
        List<Cell> cells = new ArrayList<>(endColumnIndex - startColumnIndex + 1);
        Row row = sheet.getRow(rowIndex - 1);
        for (int i = startColumnIndex; i <= endColumnIndex; i++) {
            cells.add(row.getCell(i, Row.RETURN_BLANK_AS_NULL));
        }
        return cells;
    }

    public static abstract class WorkBook {

        public WorkBook(String workBookPath) {
            try (
                InputStream input = new FileInputStream(workBookPath);
                HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(input));
            ){
                doWork(workBook);
            } catch (VibiException exception) {
                throw exception;
            } catch (Exception exception) {
                throw new RuntimeException(String.format("Error processing work book '%s'.", workBookPath), exception);
            }
        }

        public abstract void doWork(HSSFWorkbook workBook);
    }
}
