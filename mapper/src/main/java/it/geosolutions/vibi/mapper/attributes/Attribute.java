package it.geosolutions.vibi.mapper.attributes;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class Attribute {

    private final String name;
    private final String type;
    private final int columnIndex;

    public Attribute(String name, String type, int columnIndex) {
        this.name = name;
        this.type = type;
        this.columnIndex = columnIndex;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue(Row row) {
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return null;
        }
        return extract(cell);
    }

    public Object extract(Cell cell) {
        FormulaEvaluator evaluator = cell.getRow().getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        Object result;
        switch (evaluator.evaluateInCell(cell).getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                result = DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = null;
                break;
            case Cell.CELL_TYPE_ERROR:
                result = null;
                break;
            default:
                throw new RuntimeException(String.format("Unknown cell type '%d'.", cell.getCellType()));
        }
        return result;
    }
}
