package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
                LookupService.processLookupSpeciesSheet(workBook.getSheet("LOOKUP species"), store);
                LookupService.processLookupCommunitySheet(workBook.getSheet("LOOKUP community"), store);
                LookupService.processLookupMidPointSheet(workBook.getSheet("LOOKUP midpoint"), store);
                PlotService.processPlotInfoSheet(workBook.getSheet("ENTER PLOT INFO"), store);
                Fds1Service.processFds1Sheet(workBook.getSheet("ENTER FDS1"), store);
                Fds1Service.processReducedFds1Sheet(workBook.getSheet("REDUCED FDS1"), store);
                Fds2Service.processReducedFds2Sheet(workBook.getSheet("REDUCED FDS2"), store);
            }
        };
    }
}
