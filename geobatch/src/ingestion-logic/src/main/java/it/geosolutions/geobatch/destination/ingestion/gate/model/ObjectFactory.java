/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.destination.ingestion.gate.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the de.arnezelasko.spieltag package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

/**
 * Create a new ObjectFactory that can be used to create new instances of schema
 * derived classes for package:
 * it.geosolutions.geobatch.destination.ingestion.gate.model
 */
public ObjectFactory() {
}

/**
 * Create an instance of {@link ExportData }
 */
public ExportData createExportData() {
    return new ExportData();
}

/**
 * Create an instance of {@link Transits }
 */
public Transits createTransits() {
    return new Transits();
}

/**
 * Create an instance of {@link Transit }
 */
public Transit createTransit() {
    return new Transit();
}

}
