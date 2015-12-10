package it.geosolutions.vibi.mapper.sheets;

import java.util.ArrayList;
import java.util.List;

public final class SheetContextUpdater {

    private final List<Callback> callbacks = new ArrayList<>();

    public void addCallback(Callback callback) {
        callbacks.add(callback);
    }

    public void update(SheetContext context) {
        for (Callback callback : callbacks) {
            callback.update(context);
        }
    }

    public interface Callback {
        void update(SheetContext context);
    }
}
