package it.geosolutions.geobatch.destination.action.streetuser;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

public class StreetUserConfiguration extends ActionConfiguration {

    private int aggregationLevel;
    private boolean dropInput = false;
    private String closePhase = null;
    private boolean newImport = false;
	
	public StreetUserConfiguration(String id, String name, String description) {
		super(id, name, description);
	}

	public int getAggregationLevel() {
		return aggregationLevel;
	}

	public void setAggregationLevel(int aggregationLevel) {
		this.aggregationLevel = aggregationLevel;
	}
	
	public boolean isDropInput() {
        return dropInput;
    }

    public void setDropInput(boolean dropInput) {
        this.dropInput = dropInput;
    }

    public String getClosePhase() {
        return closePhase;
    }

    public void setClosePhase(String closePhase) {
        this.closePhase = closePhase;
    }
    
    public boolean isNewImport() {
		return newImport;
	}

	public void setNewImport(boolean newImport) {
		this.newImport = newImport;
	}

	@Override
    public StreetUserConfiguration clone() {
        final StreetUserConfiguration configuration = (StreetUserConfiguration) super.clone();
        
        return configuration;
    }
	
	

}
