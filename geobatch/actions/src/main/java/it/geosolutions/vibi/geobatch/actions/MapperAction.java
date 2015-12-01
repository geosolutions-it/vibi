package it.geosolutions.vibi.geobatch.actions;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.vibi.mapper.service.VibiService;
import org.apache.commons.io.FileUtils;

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
        VibiService.submit(event.getSource(), actionConfiguration.getStore());
        try {

            FileUtils.moveFile(event.getSource(), new File(actionConfiguration.getOutputPath() + "/" + event.getSource().getName()));
        } catch (Exception excetion) {
            throw new RuntimeException(
                    String.format("Error moving file from '%s' to '%s'.", event.getSource(), actionConfiguration.getOutputPath()));
        }
    }
}
