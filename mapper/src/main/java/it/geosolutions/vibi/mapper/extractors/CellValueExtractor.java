package it.geosolutions.vibi.mapper.extractors;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValueExtractor {

    Object extract(Cell cell);
}
