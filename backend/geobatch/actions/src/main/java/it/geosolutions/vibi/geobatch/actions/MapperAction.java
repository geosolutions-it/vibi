package it.geosolutions.vibi.geobatch.actions;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.vibi.mapper.service.*;
import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.geotools.data.DataStore;

import java.io.File;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

@Action(configurationClass = MapperConfiguration.class)
public final class MapperAction extends BaseAction<EventObject> {

    private final MapperConfiguration actionConfiguration;

    public MapperAction(MapperConfiguration actionConfiguration) {
        super(actionConfiguration);
        this.actionConfiguration = actionConfiguration;
    }

    @Override
    @CheckConfiguration
    public boolean checkConfiguration() {
        return true;
    }

    @Override
    public Queue<EventObject> execute(Queue<EventObject> queue) throws ActionException {
        super.listenerForwarder.started();
        for (EventObject event : queue) {
            if (event instanceof FileSystemEvent) {
                handleFileSystemEvent((FileSystemEvent) event);
            }
        }
        super.listenerForwarder.completed();
        return new LinkedList<>();
    }

    private void handleFileSystemEvent(FileSystemEvent event) {
        try {
            final DataStore store = actionConfiguration.getStore();
            new Sheets.WorkBook(event.getSource().getPath()) {

                @Override
                public void doWork(HSSFWorkbook workBook) {
                    LookupService.processLookupSpeciesSheet(workBook.getSheet("LOOKUP species"), store);
                    MapperAction.super.listenerForwarder.progressing(20f, "LOOKUP SPECIES");
                    LookupService.processLookupNatureSOPEACommunitySheet(workBook.getSheet("LOOKUP NatureS+OEPA community"), store);
                    MapperAction.super.listenerForwarder.progressing(27.5f, "LOOKUP NATURES+OEPA COMMUNITY");
                    LookupService.processLookupMidPointSheet(workBook.getSheet("LOOKUP midpoint"), store);
                    MapperAction.super.listenerForwarder.progressing(30f, "LOOKUP MIDPOINT");
                    PlotService.processPlotInfoSheet(workBook.getSheet("ENTER PLOT INFO"), store);
                    MapperAction.super.listenerForwarder.progressing(45f, "ENTER PLOT INFO");
                    Fds1Service.processFds1Sheet(workBook.getSheet("ENTER FDS1"), store);
                    MapperAction.super.listenerForwarder.progressing(65f, "ENTER FDS1");
                    Fds2Service.processFds2Sheet(workBook.getSheet("ENTER FDS2"), store);
                    MapperAction.super.listenerForwarder.progressing(85f, "ENTER FDS2");
                    BiomassService.processBiomassSheet(workBook.getSheet("ENTER BIOMASS"), store);
                    MapperAction.super.listenerForwarder.progressing(92.5f, "ENTER BIOMASS");
                    Calculations.refresh(actionConfiguration.getDbUrl(), actionConfiguration.getUser(), actionConfiguration.getPasswd());
                    MapperAction.super.listenerForwarder.progressing(100f, "CALCULATIONS");
                }
            };
        } finally {
            saveFile(event);
        }
    }

    private void saveFile(FileSystemEvent event) {
        try {
            FileUtils.moveFile(event.getSource(), new File(actionConfiguration.getOutputPath() + "/" + event.getSource().getName()));
        } catch (Exception excetion) {
            throw new RuntimeException(
                    String.format("Error moving file from '%s' to '%s'.", event.getSource(), actionConfiguration.getOutputPath()));
        }
    }
}
