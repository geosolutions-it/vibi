package it.geosolutions.vibi.geobatch.actions;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.vibi.mapper.attributes.Location;
import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.service.*;
import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.geotools.data.DataStore;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

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
            final Location plotLocation = getLocationFromFilename(event);
            new Sheets.WorkBook(event.getSource().getPath()) {

                @Override
                public void doWork(HSSFWorkbook workBook) {
                    Transaction transaction = new DefaultTransaction(UUID.randomUUID().toString());
                    try {
                        Map<Object, Object> globalContext = new HashMap<>();
                        if(plotLocation != null){
                            globalContext.put(VibiService.PLOT_LOCATION, plotLocation);
                        }
                        LookupService.processLookupNatureSOPEACommunitySheet(workBook.getSheet("LOOKUP NatureS+OEPA community"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(5.0f, "LOOKUP NATURES+OEPA COMMUNITY");
                        LookupService.processLookupMidPointSheet(workBook.getSheet("LOOKUP midpoint"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(10.0f, "LOOKUP MIDPOINT");
                        PlotService.processPlotInfoSheet(globalContext, workBook.getSheet("ENTER PLOT INFO"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(30.0f, "ENTER PLOT INFO");
                        Fds1Service.processFds1Sheet(globalContext, workBook.getSheet("ENTER FDS1"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(60.0f, "ENTER FDS1");
                        Fds2Service.processFds2Sheet(globalContext, workBook.getSheet("ENTER FDS2"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(90.0f, "ENTER FDS2");
                        BiomassService.processBiomassSheet(globalContext, workBook.getSheet("ENTER BIOMASS"), store, transaction);
                        MapperAction.super.listenerForwarder.progressing(92.5f, "ENTER BIOMASS");
                        transaction.commit();
                        Calculations.refresh(actionConfiguration.getDbUrl(), actionConfiguration.getUser(), actionConfiguration.getPasswd());
                        MapperAction.super.listenerForwarder.progressing(100f, "CALCULATIONS");
                    } catch (Exception exception) {
                        try {
                            transaction.rollback();
                        } catch (IOException ignored) {
                        }
                        if (exception instanceof VibiException) {
                            throw (VibiException) exception;
                        }
                        throw new VibiException(exception, "Something bad happen when invoking VIBI services:");
                    } finally {
                        try {
                            transaction.close();
                        } catch (IOException ignored) {
                        }
                    }
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
    
    /**
     * Reads the file name and extracts a Location from the form "_locationname_" or returns null
     * Example:
     *   deadbeef0102_ncne_filename1234.xls must return Location.NCNE
     *   deadbeef0102_uuid_filename1234.xls must return NULL
     * @param event
     * @return
     */
    private Location getLocationFromFilename(FileSystemEvent event){
        if(event == null
        || event.getSource() == null
        || event.getSource().getName() == null
        || event.getSource().getName().isEmpty()){
            return null;
        }
        
        for (Location loc : Location.values()) {
            if(event.getSource().getName().toUpperCase().contains("_"+loc.toString().toUpperCase()+"_")){
                return loc;
            }
        }
        
        return null;
    }
}
