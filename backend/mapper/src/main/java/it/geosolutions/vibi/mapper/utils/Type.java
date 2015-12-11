package it.geosolutions.vibi.mapper.utils;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import org.apache.poi.ss.usermodel.Cell;
import org.geotools.util.Converters;

import java.util.Date;

public abstract class Type {

    public static Type STRING = new Type("String", String.class) {
    };

    public static Type INTEGER = new Type("Integer", Integer.class) {
    };

    public static Type BOOLEAN = new Type("Boolean", Boolean.class) {
    };

    public static Type DATE = new Type("Date", Date.class) {
    };

    public static Type DOUBLE = new Type("Double", Double.class) {
    };

    private final String name;
    private final Class<?> clazz;

    private Type(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Object extract(Cell cell) {
        Object rawValue = Sheets.extract(cell);
        if (rawValue == null) {
            return null;
        }
        if (clazz.isInstance(rawValue)) {
            return rawValue;
        }
        Object value = Converters.convert(rawValue, clazz);
        if (value == null) {
            throw new VibiException("Cannot convert value '%s' of sheet '%s', row '%d' and column '%s' to '%s'.",
                    rawValue, cell.getSheet().getSheetName(), cell.getRow().getRowNum() + 1, Sheets.getColumn(cell.getColumnIndex()), name);
        }
        return value;
    }

    public static Type of(String name) {
        switch (name.toLowerCase()) {
            case "text":
                return STRING;
            case "integer":
                return INTEGER;
            case "boolean":
                return BOOLEAN;
            case "date":
                return DATE;
            case "double":
                return DOUBLE;
            default:
                throw new VibiException("Unknown type '%s'.", name);
        }
    }
}
