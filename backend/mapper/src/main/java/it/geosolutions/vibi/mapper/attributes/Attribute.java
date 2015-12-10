package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Type;

public abstract class Attribute {

    private final String name;
    private final Type type;
    private final boolean isIdentifier;

    public Attribute(String name, Type type) {
        this(name, type, false);
    }

    public Attribute(String name, Type type, boolean isIdentifier) {
        this.name = name;
        this.type = type;
        this.isIdentifier = isIdentifier;
    }

    public abstract Object getValue(SheetContext context);

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isIdentifier() {
        return isIdentifier;
    }
}
