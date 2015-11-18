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
package it.geosolutions.geobatch.destination.ingestion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DamianoG
 *
 */
public class ElementsCounter {

    private Map<String,Long> elementsCounters;
    private Long maxOccurrencies;
    private String maxElement;
    
    public ElementsCounter(){
        elementsCounters = new HashMap<String, Long>();
        maxOccurrencies = Long.MIN_VALUE;
        maxElement = "";
    }
    
    public void addElement(String key){
        Long count = elementsCounters.get(key);
        if(count == null){
            count = 1l;
            elementsCounters.put(key,1l);
        }
        else{
            elementsCounters.put(key,++count);
        }
        if(count > maxOccurrencies){
            maxOccurrencies = count;
            maxElement = key;
        }
    }
    
    public String getMax(String defaultValue){
        if(maxElement == null || maxElement.trim().isEmpty()) {
            return defaultValue;
        }
        return maxElement;
    }
}
