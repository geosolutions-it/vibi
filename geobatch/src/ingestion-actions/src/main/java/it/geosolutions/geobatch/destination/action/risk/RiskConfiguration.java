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
package it.geosolutions.geobatch.destination.action.risk;


import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import java.io.IOException;

/**
 */
public class RiskConfiguration extends ActionConfiguration {

	private Integer precision;
    private int aggregationLevel;
    private int processing;
    private int formula;
    private int target;
    private String materials;
    private String scenarios;
    private String entities;
    private String severeness;
    private String fpfield;
    private String writeMode;
    private String closePhase;
    private boolean dropInput;
    private boolean newImport = false;
    
	public RiskConfiguration(String id, String name, String description) {
		super(id, name, description);
	}

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public int getAggregationLevel() {
        return aggregationLevel;
    }

    public void setAggregationLevel(int aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    public int getProcessing() {
        return processing;
    }

    public void setProcessing(int processing) {
        this.processing = processing;
    }

    public int getFormula() {
        return formula;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getScenarios() {
        return scenarios;
    }

    public void setScenarios(String scenarios) {
        this.scenarios = scenarios;
    }

    public String getEntities() {
        return entities;
    }

    public void setEntities(String entities) {
        this.entities = entities;
    }

    public String getSevereness() {
        return severeness;
    }

    public void setSevereness(String severeness) {
        this.severeness = severeness;
    }

    public String getFpfield() {
        return fpfield;
    }

    public void setFpfield(String fpfield) {
        this.fpfield = fpfield;
    }

    public String getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(String writeMode) {
        this.writeMode = writeMode;
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

	/**
	 * @return the dropInput
	 */
	public boolean isDropInput() {
		return dropInput;
	}

	/**
	 * @param dropInput the dropInput to set
	 */
	public void setDropInput(boolean dropInput) {
		this.dropInput = dropInput;
	}

	@Override
    public RiskConfiguration clone() {
        final RiskConfiguration configuration = (RiskConfiguration) super.clone();
        
        return configuration;
    }
}
