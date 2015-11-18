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
package it.geosolutions.geobatch.destination.action.gateingestion;

import it.geosolutions.geobatch.actions.ds2ds.Ds2dsConfiguration;
import it.geosolutions.geobatch.catalog.impl.configuration.TimeFormatConfiguration;

/**
 * Gate ingestion configuration. Not use input ds (input data it's read from
 * xml)
 * 
 * @author adiaz
 */
public class GateIngestionConfiguration extends Ds2dsConfiguration {

/**
 * Ignore primary keys in the xml file and generate it
 */
private Boolean ignorePks;

private Boolean copyFilesAtEnd = false;

private String failPath;

private String successPath;

/**
 * Time format configuration for the ingestion
 */
private TimeFormatConfiguration timeFormatConfiguration;

public GateIngestionConfiguration(String id, String name, String description) {
    super(id, name, description);
}

/**
 * @return the ignorePks
 */
public boolean getIgnorePks() {
    return ignorePks;
}

/**
 * @param ignorePks the ignorePks to set
 */
public void setIgnorePks(boolean ignorePks) {
    this.ignorePks = ignorePks;
}

/**
 * @return the timeFormatConfiguration
 */
public TimeFormatConfiguration getTimeFormatConfiguration() {
    return timeFormatConfiguration;
}

/**
 * @param timeFormatConfiguration the timeFormatConfiguration to set
 */
public void setTimeFormatConfiguration(
        TimeFormatConfiguration timeFormatConfiguration) {
    this.timeFormatConfiguration = timeFormatConfiguration;
}

public Boolean getCopyFilesAtEnd() {
	return copyFilesAtEnd;
}

public void setCopyFilesAtEnd(Boolean copyFilesAtEnd) {
	this.copyFilesAtEnd = copyFilesAtEnd;
}

public String getFailPath() {
	return failPath;
}

public void setFailPath(String failPath) {
	this.failPath = failPath;
}

public String getSuccessPath() {
	return successPath;
}

public void setSuccessPath(String successPath) {
	this.successPath = successPath;
}



}
