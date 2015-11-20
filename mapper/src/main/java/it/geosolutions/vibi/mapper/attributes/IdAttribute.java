package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class IdAttribute {

    private final String name;
    private final int columnIndex;

    public IdAttribute(String name, int columnIndex) {
        this.name = name;
        this.columnIndex = columnIndex;
    }

    public String getName() {
        return name;
    }

    public String getId(Row row) {
        return Sheets.cellToString(Sheets.evaluateCell(row.getCell(columnIndex)));
    }
}
