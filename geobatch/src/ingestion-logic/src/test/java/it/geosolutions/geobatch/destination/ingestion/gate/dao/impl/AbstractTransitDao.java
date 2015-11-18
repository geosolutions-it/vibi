/*

 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.geobatch.destination.ingestion.gate.dao.impl;

import it.geosolutions.geobatch.destination.ingestion.gate.dao.TransitDao;

/**
 * Abstract implementation for store transit objects
 * 
 * @author adiaz
 */
public abstract class AbstractTransitDao implements TransitDao {

/**
 * Flag to indicate that
 */
protected Boolean ignorePks;

/**
 * @return the ignorePks
 */
public Boolean getIgnorePks() {
    return ignorePks;
}

/**
 * @param ignorePks the ignorePks to set
 */
public void setIgnorePks(Boolean ignorePks) {
    this.ignorePks = ignorePks;
}

}
