/*
 *    GeoTools - The Open Source Java GIS Toolkit
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
package it.geosolutions.geobatch.destination.common;


import it.geosolutions.geobatch.destination.common.utils.FeatureLoaderUtils;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;

import java.io.IOException;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureStore;
import org.geotools.data.Transaction;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 * Class representing an Ingestion output object.
 *  
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class OutputObject {
	private String name = null;
	String id = null;
	private SimpleFeatureType schema = null;
	private SimpleFeatureBuilder builder = null;
	private FeatureStore<SimpleFeatureType, SimpleFeature> source = null;
	protected MetadataIngestionHandler metadataHandler;
	
	public OutputObject(DataStore dataStore, Transaction transaction, String name, String id) throws IOException {
		this.name = name;
		this.id = id;
		
		metadataHandler = new MetadataIngestionHandler(dataStore);
		schema = dataStore.getSchema(name);
		builder = new SimpleFeatureBuilder(schema);
		source = FeatureLoaderUtils.createFeatureSource(dataStore, transaction, name);			
	}

	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the schema
	 */
	public SimpleFeatureType getSchema() {
		return schema;
	}

	/**
	 * @return the builder
	 */
	public SimpleFeatureBuilder getBuilder() {
		return builder;
	}

	/**
	 * @return the source
	 */
	public FeatureStore<SimpleFeatureType, SimpleFeature> getSource() {
		return source;
	}
	
	/**
	 * @return the source
	 */
	public FeatureStore<SimpleFeatureType, SimpleFeature> getReader() {
		return source;
	}
	
	/**
	 * @return the source
	 */
	public FeatureStore<SimpleFeatureType, SimpleFeature> getWriter() {
		return source;
	}
}
