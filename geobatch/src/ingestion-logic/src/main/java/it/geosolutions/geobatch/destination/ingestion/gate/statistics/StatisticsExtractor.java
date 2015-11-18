/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2013, Open Source Geospatial Foundation (OSGeo)
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
package it.geosolutions.geobatch.destination.ingestion.gate.statistics;

import java.io.IOException;
import java.util.List;

/**
 * Statistics extractor definition
 * 
 * @author adiaz
 */
public interface StatisticsExtractor {

/**
 * Extract statistics for a interval
 * 
 * @param interval
 * @return all statistics for the interval
 */
public List<StatisticsBean> getStatistics(StatisticInterval interval)  throws IOException ;

/**
 * Extract all statistics
 * 
 * @return all statistics
 */
public List<StatisticsBean> getStatistics()  throws IOException;

}
