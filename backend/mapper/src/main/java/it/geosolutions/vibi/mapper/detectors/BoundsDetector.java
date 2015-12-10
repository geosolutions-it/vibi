package it.geosolutions.vibi.mapper.detectors;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import org.apache.poi.ss.usermodel.Row;

public interface BoundsDetector {

    boolean ignore(SheetContext context);

    boolean dataStart(SheetContext context);

    boolean dataEnd(SheetContext context);
}
