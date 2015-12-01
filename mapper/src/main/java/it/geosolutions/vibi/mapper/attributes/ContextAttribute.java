package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;

public class ContextAttribute extends Attribute {

    private static String preKey = "CTXT_ATTRIB_";

    private final String key;

    public ContextAttribute(String name, Type type, int columnIndex) {
        super(name, type);
        key = preKey + columnIndex;
    }

    public Object getValue(SheetContext context) {
        return context.getValue(key);
    }

    public static String getContextKey(String column) {
        return preKey + Sheets.getIndex(column);
    }
}
