package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Type;

public class ConstantAttribute extends Attribute {

    private final Object value;

    public ConstantAttribute(String name, Type type, Object value) {
        super(name, type);
        this.value = value;
    }

    @Override
    public Object getValue(SheetContext context) {
        return value;
    }
}
