package it.geosolutions.vibi.mapper.detectors;

import org.apache.poi.ss.usermodel.Row;

public interface BoundsDetector {

    boolean isDataStart(Row row);

    boolean isDataEnd(Row row);
}
