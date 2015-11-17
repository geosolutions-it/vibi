package it.geosolutions.vibi.detectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface DataStartDetector {

    boolean isStart(Sheet sheet, Row row);
}
