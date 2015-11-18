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
import it.geosolutions.geobatch.destination.ingestion.gate.model.Transit;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.geotools.data.memory.MemoryDataStore;

/**
 * Memory data store implementation for store transit objects
 * 
 * @author adiaz
 */
public class TransitDaoMemoryImpl extends AbstractTransitDao implements
        TransitDao {

/**
 * Map to save transits
 */
private static Map<Long, Transit> STORED_TRANSITS;

/**
 * Map to save gates
 */
private static Map<Long, Gate> STORED_GATES;

/**
 * Random to generate magic keys and random strings
 */
private static final Random RANDOM = new Random();

/**
 * Database connection for the insert
 */
protected MemoryDataStore dataStore;

public TransitDaoMemoryImpl(MemoryDataStore dataStore) {
    // init data store
    this.dataStore = (MemoryDataStore) dataStore;
    // TODO: save on data store
    STORED_TRANSITS = new ConcurrentHashMap<Long, Transit>();
    STORED_GATES = new ConcurrentHashMap<Long, TransitDaoMemoryImpl.Gate>();
}

/**
 * Creates a new transit in the transit table.
 * 
 * @return id of the transit
 * @throws Exception if an exception occur when try to insert
 */
public Long createTransit(Transit transit) throws Exception {
    if (this.ignorePks) {
        transit.setIdTransito(RANDOM.nextLong());
    }
    STORED_TRANSITS.put(transit.getIdTransito(), transit);
    return transit.getIdTransito();
}

/**
 * Check if exists one transit with all data stored
 * 
 * @param idTransit id of the transit stored
 * @param transit bean with the data
 * @throws Exception if an error occur when check the data
 */
public boolean exists(Long idTransit, Transit transit) throws Exception {
    boolean found = false;
    if (STORED_TRANSITS.containsKey(idTransit)) {
        Transit storedTransit = STORED_TRANSITS.get(idTransit);
        found = storedTransit.getCorsia().equals(transit.getCorsia())
                && storedTransit.getDataRilevamento().equals(
                        transit.getDataRilevamento())
                && storedTransit.getDirezione().equals(transit.getDirezione())
                && storedTransit.getIdGate().equals(transit.getIdGate())
                && storedTransit.getKemlerCode()
                        .equals(transit.getKemlerCode())
                && storedTransit.getOnuCode().equals(transit.getOnuCode());
    }
    return found;
}

/**
 * Clean all transit stored
 * 
 * @param transitIds
 * @throws Exception if an error occur when try to clean data
 */
public void cleanAll(List<Long> transitIds) throws Exception {
    for (Long id : transitIds) {
        STORED_TRANSITS.remove(id);
    }
}

/**
 * Store a gate for test proposal
 * 
 * @param idGate identifier of the gate
 * @param description of the gate
 * @throws Exception if an error occur when we try to store the gate
 */
public void createGate(Long idGate, String description) throws Exception {
    Gate gate = new Gate(idGate, description);
    STORED_GATES.put(idGate, gate);
}

/**
 * Delete a gate for testing proposal
 * 
 * @param idGate identifier of the date to be removed
 * @throws Exception if an error occur when we try to delete the gate
 */
public void deleteGate(Long idGate) throws Exception {
    STORED_GATES.remove(idGate);
}

/**
 * Inner class to save Gate on memory
 * 
 * @author adiaz
 */
protected class Gate implements Serializable {
/** serialVersionUID */
private static final long serialVersionUID = 874917083461242509L;

protected Long id;

protected String description;

public Gate(Long id, String description) {
    super();
    this.id = id;
    this.description = description;
}
}

}
