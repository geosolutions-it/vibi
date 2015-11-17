package it.geosolutions.vibi.converters;

import org.apache.poi.ss.usermodel.Cell;

public interface Converter {

    Object convert(Cell cell);
}
