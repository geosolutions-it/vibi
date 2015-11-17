package it.geosolutions.vibi.detectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface DataEndDetector {

    boolean isEnd(Sheet sheet, Row row);
}
