package it.geosolutions.vibi.attributes;

import it.geosolutions.vibi.extractors.CellValueExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public final class SimpleAttributeProducer implements AttributeProducer {

    private final boolean isAnIdAttribute;
    private final String name;
    private final int columnIndex;
    private final CellValueExtractor cellValueExtractor;

    public SimpleAttributeProducer(boolean isAnIdAttribute, String name, int columnIndex, CellValueExtractor cellValueExtractor) {
        this.isAnIdAttribute = isAnIdAttribute;
        this.name = name;
        this.columnIndex = columnIndex;
        this.cellValueExtractor = cellValueExtractor;
    }

    @Override
    public boolean isAnIdAttribute() {
        return isAnIdAttribute;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue(Row row) {
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return null;
        }
        return cellValueExtractor.extract(cell);
    }
}
