package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;

public class SimpleAttribute extends Attribute {

    private final int columnIndex;

    public SimpleAttribute(String name, Type type, int columnIndex) {
        this(name, type, columnIndex, false);
    }

    public SimpleAttribute(String name, Type type, int columnIndex, boolean isIdentifier) {
        super(name, type, isIdentifier);
        this.columnIndex = columnIndex;
    }

    public Object getValue(SheetContext context) {
        return Sheets.getValue(context, columnIndex, super.getType());
    }
}
