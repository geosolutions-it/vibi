package it.geosolutions.vibi.meta;

import it.geosolutions.vibi.converters.BasicConverter;
import it.geosolutions.vibi.converters.Converter;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Tuple;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public final class AttributeMeta {

    private final int columnIndex;
    private final String dataBaseColumnName;
    private final Converter converter;

    public AttributeMeta(int columnIndex, String dataBaseColumnName, Converter converter) {
        this.columnIndex = columnIndex;
        this.dataBaseColumnName = dataBaseColumnName;
        this.converter = converter;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public Tuple<String, Object> getDataBaseColumnNameAndValue(Row row) {
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return Tuple.tuple(dataBaseColumnName, null);
        }
        return Tuple.tuple(dataBaseColumnName, converter.convert(cell));
    }

    public static List<AttributeMeta> extractAttributesMeta(Sheet containingSheet, int headerRowIndex,
                                                            int headerStartColumnIndex, int headerEndColumnIndex) {
        List<AttributeMeta> attributesMeta = new ArrayList<>();
        Row row = containingSheet.getRow(headerRowIndex);
        for (int i = headerStartColumnIndex; i <= headerEndColumnIndex; i++) {
            Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                continue;
            }
            String dataBaseColumnName = getNormalizedName(cell);
            attributesMeta.add(new AttributeMeta(i, dataBaseColumnName, BasicConverter.BASIC_CONVERTER));
        }
        return attributesMeta;
    }

    public static String getNormalizedName(Cell cell) {
        if (cell == null) {
            return "NULL";
        }
        return Sheets.cellToString(cell).trim().
                replaceAll("[^a-zA-Z0-9]$", "").replaceAll("[^a-zA-Z0-9]+", "_").toUpperCase();
    }
}
