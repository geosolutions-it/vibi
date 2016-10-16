package it.geosolutions.vibi.mapper.utils;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import org.apache.poi.ss.usermodel.Cell;
import org.geotools.util.Converters;

import java.util.Date;

public abstract class Type {

    public static Type STRING = new Type("String", String.class) {
    };

    public static Type INTEGER = new Type("Integer", Integer.class) {
        @Override
        public Object extract(Cell cell) {
            return extractWithNulls(cell);
        }
    };

    public static Type BOOLEAN = new Type("Boolean", Boolean.class) {
    };

    public static Type DATE = new Type("Date", Date.class) {
        @Override
        public Object extract(Cell cell) {
            return extractWithNulls(cell);
        }
    };

    public static Type DOUBLE = new Type("Double", Double.class) {
        @Override
        public Object extract(Cell cell) {
            return extractWithNulls(cell);
        }
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
        return convertValue(cell, rawValue);
    }

    protected Object extractWithNulls(Cell cell) {
        Object rawValue = Sheets.extract(cell);
        if (rawValue == null) {
            return null;
        }
        String stringRawValue = rawValue.toString();
        if (stringRawValue.equalsIgnoreCase("na") || stringRawValue.equals("*")) {
            return null;
        }
        return convertValue(cell, rawValue);
    }

    protected Object convertValue(Cell cell, Object rawValue) {
        if (clazz.isInstance(rawValue)) {
            return rawValue;
        }
        if (clazz.equals(String.class) && rawValue instanceof Number) {
            // hacker for plot number, module numbers and cornern numbers
            Number number = (Number) rawValue;
            double numberAsDouble =  number.doubleValue();
            if ((numberAsDouble == Math.floor(numberAsDouble)) && !Double.isInfinite(numberAsDouble)) {
                // is an integer so avoid any trailing zeros
                return String.valueOf(number.intValue());
            }
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
            case "numeric":
                return DOUBLE;
            default:
                throw new VibiException("Unknown type '%s'.", name);
        }
    }
}
