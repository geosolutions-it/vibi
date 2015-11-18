package it.geosolutions.vibi.extractors;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValueExtractor {

    Object extract(Cell cell);
}
