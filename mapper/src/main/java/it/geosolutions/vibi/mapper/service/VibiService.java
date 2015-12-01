package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.ContextAttribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.builders.SimpleBoundsDetectorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetContextUpdater;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;

import java.io.File;

public final class VibiService {

    private VibiService() {
    }

    public static void submit(File woorkBookFile, final DataStore store) {
        submit(woorkBookFile.getPath(), store);
    }

    public static void submit(String woorkBookPath, final DataStore store) {
        new Sheets.WorkBook(woorkBookPath) {

            @Override
            public void doWork(HSSFWorkbook workBook) {
                PlotService.processPlotInfoSheet(workBook.getSheet("ENTER PLOT INFO"), store);
                Fds1Service.processFds1Sheet(workBook.getSheet("ENTER FDS1"), store);
            }
        };
    }
}
