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
package it.geosolutions.geobatch.destination.ingestion.gate.dao;

import it.geosolutions.geobatch.destination.ingestion.gate.model.Transit;

import java.util.List;

/**
 * Interface for perform data for GateIngestionTest
 * 
 * @author adiaz
 */
public interface TransitDao {

/**
 * Creates a new transit in the transit table.
 * 
 * @return id of the transit
 * @throws Exception if an exception occur when try to insert
 */
public Long createTransit(Transit transit) throws Exception;

/**
 * @return the ignorePks
 */
public Boolean getIgnorePks();

/**
 * @param ignorePks the ignorePks to set
 */
public void setIgnorePks(Boolean ignorePks);

/**
 * Check if exists one transit with all data stored
 * 
 * @param idTransit id of the transit stored
 * @param transit bean with the data
 * @throws Exception if an error occur when check the data
 */
public boolean exists(Long idTransit, Transit transit) throws Exception;

/**
 * Clean all transit stored
 * 
 * @param transitIds
 * @throws Exception if an error occur when try to clean data
 */
public void cleanAll(List<Long> transitIds) throws Exception;

/**
 * Store a gate for test proposal
 * 
 * @param idGate identifier of the gate
 * @param description of the gate
 * 
 * @throws Exception if an error occur when we try to store the gate
 */
public void createGate(Long idGate, String description) throws Exception;

/**
 * Delete a gate for testing proposal
 * 
 * @param idGate identifier of the date to be removed
 * 
 * @throws Exception if an error occur when we try to delete the gate
 */
public void deleteGate(Long idGate) throws Exception;

}
