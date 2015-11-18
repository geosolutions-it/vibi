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
package it.geosolutions.geobatch.destination.action.zeroremoval;


import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

/**
 */
public class ZeroRemovalConfiguration extends ActionConfiguration {

    private int aggregationLevel;
    private boolean newProcess = false;
    private String closePhase;

	public ZeroRemovalConfiguration(String id, String name, String description) {
		super(id, name, description);
	}

    public int getAggregationLevel() {
        return aggregationLevel;
    }

    public void setAggregationLevel(int aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    public String getClosePhase() {
        return closePhase;
    }

    public void setClosePhase(String closePhase) {
        this.closePhase = closePhase;
    }
    
    

    public boolean isNewProcess() {
		return newProcess;
	}

	public void setNewProcess(boolean newProcess) {
		this.newProcess = newProcess;
	}

	@Override
    public ZeroRemovalConfiguration clone() {
        final ZeroRemovalConfiguration configuration = (ZeroRemovalConfiguration) super.clone();
        
        return configuration;
    }
}
