/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2014 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.catalog.impl;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Test;

/**
 * JUnit test for {@link TimeFormat} utilities
 * 
 * @author adiaz
 */
public class TimeFormatTest {

/**
 * Parse old known format
 */
private String OLD_TEST_DATE = "2013-04-12T13:38:48+02:00";

/**
 * Parse new format
 */
private String NEW_TEST_DATE = "2014-01-30T08.25.11+01:00";

/**
 * Default time format
 */
private TimeFormat DEFAULT_TIME_FORMAT = new TimeFormat(null, null, null, null);

/**
 * Check if the new parser allow known formats
 * 
 * @throws ParseException
 */
@Test
public void getDateTest() throws ParseException {
    assertNotNull(DEFAULT_TIME_FORMAT.getDate(OLD_TEST_DATE));
    assertNotNull(DEFAULT_TIME_FORMAT.getDate(NEW_TEST_DATE));
}

}
