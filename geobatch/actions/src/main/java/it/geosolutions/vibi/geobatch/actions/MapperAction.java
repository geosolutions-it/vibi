package it.geosolutions.vibi.geobatch.actions;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.vibi.mapper.VibiService;
import org.geotools.data.DataStore;

import java.util.EventObject;
import java.util.Queue;

@Action(configurationClass = MapperConfiguration.class)
public final class MapperAction extends BaseAction<EventObject> {

    private final DataStore store;

    public MapperAction(MapperConfiguration actionConfiguration) {
        super(actionConfiguration);
        store = actionConfiguration.getStore();
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
        return null;
    }

    private void handleFileSystemEvent(FileSystemEvent event) {
        VibiService.submit(event.getSource(), store);
    }
}
