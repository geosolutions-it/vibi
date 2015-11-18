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
package it.geosolutions.geobatch.destination.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DamianoG
 * 
 * UtilityBase class form managing PropertiesFile
 * 
 */
public class PropertiesManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesManager.class);

    public PropertiesManager() {
    }

    public static Properties loadProperty(String base, String name) {
        Properties targetProp = new Properties();
        InputStream is = loadInputStream(base + name);
        if(is == null){
            is = loadInputStream(name);
        }
        try {
            targetProp.load(is);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            closeInputStream(is);
        }
        return targetProp;
    }

    public static List<Integer> getKeysStartsWith(Properties prop, String prefix) {
        Iterator iter = prop.keySet().iterator();
        List<Integer> values = new ArrayList<Integer>();
        while (iter.hasNext()) {
            String el = (String) iter.next();
            if (el.startsWith(prefix)) {
                values.add(Integer.parseInt(prop.getProperty(el)));
            }
        }
        return values;
    }

    protected static InputStream loadInputStream(String pathName) {
        InputStream is = PropertiesManager.class.getResourceAsStream(pathName);
        if(is == null){
            try {
                is = new FileInputStream(new File(pathName));
            } catch (FileNotFoundException e) {
                LOGGER.trace(e.getMessage(), e);
            }
        }
        return is;
    }

    protected static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
