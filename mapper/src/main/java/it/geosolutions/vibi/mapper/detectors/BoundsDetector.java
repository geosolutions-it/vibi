package it.geosolutions.vibi.mapper.detectors;

import org.apache.poi.ss.usermodel.Row;

public interface BoundsDetector {

    boolean isHeader(Row row);

    boolean isDataStart(Row row);

    boolean isDataEnd(Row row);
}
