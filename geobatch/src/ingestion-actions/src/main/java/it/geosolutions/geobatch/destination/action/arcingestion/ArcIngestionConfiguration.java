/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.destination.action.arcingestion;


import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

/**
 */
public class ArcIngestionConfiguration extends ActionConfiguration {	

    private int aggregationLevel = 1;
    private boolean onGrid = false;
    private boolean dropInput = false;
    private String closePhase = null;
    private boolean segmentation = false;
    private boolean newProcess = false;
    private int lastYear = -1;
    private int years = -1;

	public ArcIngestionConfiguration(String id, String name, String description) {
		super(id, name, description);
	}

    public int getAggregationLevel() {
        return aggregationLevel;
    }

    public void setAggregationLevel(int aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    public boolean isOnGrid() {
        return onGrid;
    }

    public void setOnGrid(boolean onGrid) {
        this.onGrid = onGrid;
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
    
    public boolean isSegmentation() {
		return segmentation;
	}
	public void setSegmentation(boolean segmentation) {
		this.segmentation = segmentation;
	}
	
	/**
	 * @return the newProcess
	 */
	public boolean isNewProcess() {
		return newProcess;
	}

	/**
	 * @param newProcess the newProcess to set
	 */
	public void setNewProcess(boolean newProcess) {
		this.newProcess = newProcess;
	}
	
	

	public int getLastYear() {
		return lastYear;
	}

	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}
	
	

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	@Override
    public ArcIngestionConfiguration clone() {
        final ArcIngestionConfiguration configuration = (ArcIngestionConfiguration) super.clone();
        
        return configuration;
    }
}
