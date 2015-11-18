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

import it.geosolutions.geobatch.destination.common.OutputObject;
import it.geosolutions.geobatch.destination.ingestion.ArcsIngestionProcess;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.map.MultiKeyMap;
import org.geotools.data.DataStore;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureStore;
import org.geotools.data.Transaction;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DamianoG
 * 
 * This class contains a set of static utility methods for load a list of values from a given feature and a geiven attribute.
 * The purpose of these methods is to deal with tables "that are configuration table" so with a reasonable low amount of records.
 * No controls are made so if you run these methods on a table with one Giga of records you get an out of memory.
 * 
 * Some of these methods cache the results.
 * 
 */
public class FeatureLoaderUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArcsIngestionProcess.class);
    
    public final static String ID_ORIGINE = "id_origine";
    
    public static FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2();
    
    private static MultiKeyMap featureAttributesMap = new MultiKeyMap();

    public static List<SimpleFeature> loadFeatures(DataStore datastore, String featureTypeName) {

        List<SimpleFeature> features = new ArrayList<SimpleFeature>();
        FeatureIterator iter = null;
        Transaction transaction = null;
        try {
            transaction = new DefaultTransaction();
            OutputObject tipobersObject = new OutputObject(datastore, transaction, featureTypeName,
                    "");
            FeatureCollection<SimpleFeatureType, SimpleFeature> bersaglioCollection = tipobersObject
                    .getReader().getFeatures();
            iter = bersaglioCollection.features();

            while (iter.hasNext()) {
                SimpleFeature sf = (SimpleFeature) iter.next();
                features.add(sf);
            }
        } catch (IOException e) {
        } finally {
            if (iter != null) {
                iter.close();
            }
            if (transaction != null) {
                try {
                    transaction.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return features;
    }
    
    /**
     * Load all the values from a given BigDecimal attribute and return a list of String
     * 
     * <b>The result is cached</b>
     * 
     * @param datastore
     * @param featureTypeName
     * @param attribute
     * @param forceLoading
     * @return
     */
    public static List<String> loadFeatureAttributes(DataStore datastore, String featureTypeName,
            String attribute, boolean forceLoading){
        List<BigDecimal> list = loadFeatureAttributesInternal(datastore, featureTypeName,
                attribute, forceLoading);
        List<String> resultList = new ArrayList<String>();
        for(BigDecimal el : list){
            resultList.add(el.toString());
        }
        return resultList;
    }
    
    /**
     * Load all the values from a given BigDecimal attribute and return a list of Double
     * 
     * <b>The result is cached</b>
     * 
     * @param datastore
     * @param featureTypeName
     * @param attribute
     * @param forceLoading
     * @return
     */
    public static List<Double> loadFeatureAttributesInt(DataStore datastore, String featureTypeName,
            String attribute, boolean forceLoading){
        List<BigDecimal> list = loadFeatureAttributesInternal(datastore, featureTypeName,
                attribute, forceLoading);
        List<Double> resultList = new ArrayList<Double>();
        for(BigDecimal el : list){
            resultList.add(el.toBigInteger().doubleValue());
        }
        return resultList;
    }
    
    private static List<BigDecimal> loadFeatureAttributesInternal(DataStore datastore, String featureTypeName,
            String attribute, boolean forceLoading) {

        List<BigDecimal> attributes = (List<BigDecimal>) featureAttributesMap.get(featureTypeName,
                attribute);
        if (!forceLoading && attributes != null) {
            return ListUtils.unmodifiableList(attributes);
        }
        attributes = new ArrayList<BigDecimal>();
        FeatureIterator iter = null;
        Transaction transaction = null;
        try {
            transaction = new DefaultTransaction();
            OutputObject tipobersObject = new OutputObject(datastore, transaction, featureTypeName,
                    "");
            FeatureCollection<SimpleFeatureType, SimpleFeature> bersaglioCollection = tipobersObject
                    .getReader().getFeatures();
            iter = bersaglioCollection.features();

            while (iter.hasNext()) {
                SimpleFeature sf = (SimpleFeature) iter.next();
                // BigDecimal bd = (BigDecimal) sf.getAttribute("id_sostanza");
                BigDecimal bd = (BigDecimal) sf.getAttribute(attribute);
                attributes.add(bd);
            }
            featureAttributesMap.put(featureTypeName, attribute, attributes);
        } catch (IOException e) {
        } finally {
            if (iter != null) {
                iter.close();
            }
            if (transaction != null) {
                try {
                    transaction.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return ListUtils.unmodifiableList(attributes);
    }
    
    /**
     * Load all the values from a given attribute and return a list of that type.
     * 
     * <b>The result is NOT cached</b>
     * 
     * @param datastore
     * @param featureTypeName
     * @param attribute
     * @param forceLoading
     * @return
     */
    public static <T> List<T> loadFeatureAttributesGeneric(JDBCDataStore datastore, String featureTypeName,
            String attribute, boolean forceLoading) {

        List<T> attributes = new ArrayList<T>();
        FeatureIterator iter = null;
        Transaction transaction = null;
        try {
            transaction = new DefaultTransaction();
            OutputObject tipobersObject = new OutputObject(datastore, transaction, featureTypeName,
                    "");
            FeatureCollection<SimpleFeatureType, SimpleFeature> bersaglioCollection = tipobersObject
                    .getReader().getFeatures();
            iter = bersaglioCollection.features();

            while (iter.hasNext()) {
                SimpleFeature sf = (SimpleFeature) iter.next();
                // BigDecimal bd = (BigDecimal) sf.getAttribute("id_sostanza");
                T bd = (T) sf.getAttribute(attribute);
                attributes.add(bd);
            }
        } catch (IOException e) {
        } finally {
            if (iter != null) {
                iter.close();
            }
            if (transaction != null) {
                try {
                    transaction.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return attributes;
    }
    
    /**
	 * Creates a FeatureSource for the given typeName on the given DataStore.
	 * Optionally the source is bound to a transaction, if not null.
	 * 
	 * @param dataStore
	 * @param transaction
	 * @param typeName
	 * @return
	 * @throws IOException
	 */
	public static FeatureStore<SimpleFeatureType, SimpleFeature> createFeatureSource(DataStore dataStore, 
			Transaction transaction, String typeName)
			throws IOException {
		FeatureStore<SimpleFeatureType, SimpleFeature> geoFeatureWriter = (FeatureStore<SimpleFeatureType, SimpleFeature>) dataStore
				.getFeatureSource(typeName);
		geoFeatureWriter.setTransaction(transaction);
		return geoFeatureWriter;
	}
}
