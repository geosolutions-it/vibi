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
package it.geosolutions.geobatch.destination.action.datamigration;

import it.geosolutions.geobatch.actions.ds2ds.dao.FeatureConfiguration;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

public class RasterMigrationConfiguration extends ActionConfiguration {

	private String closePhase = null;
	private boolean newImport = false;
	private FeatureConfiguration sourceFeature;
	
    public RasterMigrationConfiguration(String id, String name, String description) {
        super(id, name, description);
    }

	/**
	 * @return the closePhase
	 */
	public String getClosePhase() {
		return closePhase;
	}

	/**
	 * @param closePhase the closePhase to set
	 */
	public void setClosePhase(String closePhase) {
		this.closePhase = closePhase;
	}

	/**
	 * @return the sourceFeature
	 */
	public FeatureConfiguration getSourceFeature() {
		return sourceFeature;
	}

	/**
	 * @param sourceFeature the sourceFeature to set
	 */
	public void setSourceFeature(FeatureConfiguration sourceFeature) {
		this.sourceFeature = sourceFeature;
	}

	public boolean isNewImport() {
		return newImport;
	}

	public void setNewImport(boolean newImport) {
		this.newImport = newImport;
	}
    
    
}
