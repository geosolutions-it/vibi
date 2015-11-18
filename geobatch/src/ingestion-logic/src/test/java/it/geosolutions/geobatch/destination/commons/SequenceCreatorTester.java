/*
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.destination.commons;

import it.geosolutions.geobatch.destination.common.utils.SequenceManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.geotools.jdbc.JDBCDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DamianoG
 *
 * Test the creation and the usage of a postgis sequence used for the arcs id generation
 * 
 * TODO: implement as onlinetestCase
 *
 */
public class SequenceCreatorTester {

    private final static Logger LOGGER = LoggerFactory.getLogger(SequenceCreatorTester.class);
    
    public SequenceCreatorTester(){}
    
    public static void main(String[] args) {

        Map<String, Serializable> datastoreParams = new HashMap<String, Serializable>();
        datastoreParams.put("port", 5432);
        datastoreParams.put("schema", "siig_p");
        datastoreParams.put("passwd", "siig_p");
        datastoreParams.put("dbtype", "postgis");
        datastoreParams.put("host", "192.168.88.132");
        datastoreParams.put("Expose primary keys", "true");
        datastoreParams.put("user", "siig_p");
        datastoreParams.put("database", "destination_staging");

        JDBCDataStore dataStore;
        try {
            dataStore = (JDBCDataStore) DataStoreFinder.getDataStore(datastoreParams);
            SequenceManager sm = new SequenceManager(dataStore, "RP_BU-ASAN_20130424_02_seq");
            LOGGER.info(sm.retrieveValue()+"");
            LOGGER.info(sm.retrieveValue()+"");
            sm.disposeManager();
        } catch (IOException e) {
        }
    }
    
}
