package it.geosolutions.geobatch.destination.datamigration;

import java.util.Collection;

import it.geosolutions.geobatch.catalog.Identifiable;
import it.geosolutions.geobatch.flow.event.IProgressListener;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

public class MultiProgressListenerForwarder extends ProgressListenerForwarder {
	
	ProgressListenerForwarder wrapped;
	
	int total;
	int current;
	String currentName;
	
	int totalRecords = 0;
	boolean canAdd = false;
	
	public MultiProgressListenerForwarder(Identifiable owner, ProgressListenerForwarder wrapped, int total) {
		super(owner);
		this.wrapped = wrapped;
		this.total = total;
		this.current = 0;
	}

	
	public void addTotalRecords(int records) {
		if(canAdd) {
			totalRecords += records;
			canAdd = false;
		}
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}
	
	public void completed() {
		for (IProgressListener l : wrapped.getListeners()) {
            if (LOGGER.isTraceEnabled())
                LOGGER.trace(getClass().getSimpleName() + " FORWARDING completed message to "
                        + l.getClass().getSimpleName());
            try {
                setProgress(100f); // forcing 100% progress
                l.completed();
            } catch (Exception e) {
                LOGGER.error("Exception in event forwarder: " + e.getLocalizedMessage());
                l.failed(e);
            }
        }
	}

	public void failed(Throwable exception) {
		wrapped.failed(exception);
	}

	public void addListener(IProgressListener listener) {
		wrapped.addListener(listener);
	}

	public boolean equals(Object obj) {
		return wrapped.equals(obj);
	}

	public Identifiable getOwner() {
		return wrapped.getOwner();
	}

	public void progressing(float progress, String task) {
		wrapped.progressing(progress, task);
	}

	public float getProgress() {
		return wrapped.getProgress();
	}

	public String getTask() {
		return wrapped.getTask();
	}

	public void paused() {
		wrapped.paused();
	}

	public void progressing() {
		wrapped.progressing();
	}

	public void resumed() {
		wrapped.resumed();
	}

	public void setProgress(float progress) {
		wrapped.setProgress(calculateProgress(progress));
	}

	private float calculateProgress(float progress) {
		return progress / (float) total + (float)current * 100.0f/(float)total;
	}




	public void setTask(String currentTask) {
		wrapped.setTask("Migrating data for " + currentName);
	}

	public void started() {
		wrapped.started();
	}

	public void terminated() {
		wrapped.terminated();
	}

	public void removeListener(IProgressListener listener) {
		wrapped.removeListener(listener);
	}

	public Collection<IProgressListener> getListeners() {
		return wrapped.getListeners();
	}

	public Collection<IProgressListener> getListeners(Class clazz) {
		return wrapped.getListeners(clazz);
	}

	public int hashCode() {
		return wrapped.hashCode();
	}

	public String toString() {
		return wrapped.toString();
	}


	public void setCurrentName(String currentName) {
		this.currentName = currentName;
		canAdd = true;
	}

	public void incrementCurrent() {
		current++;
	}

	
	

}
