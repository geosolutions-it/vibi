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
package it.geosolutions.geobatch.destination;

import java.io.IOException;

import org.geotools.data.DataStore;

import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;

/**
 * @author "Mauro Bartolomeoli - mauro.bartolomeoli@geo-solutions.it"
 *
 */
public class MockMetadataIngestionHandler extends MetadataIngestionHandler {

		/**
	 * @param dataStore
	 */
	public MockMetadataIngestionHandler(DataStore dataStore) {
		super(dataStore);
	}

	@Override
	public int createProcess() throws IOException {
		return 1;
	}

	@Override
	public Process getProcessData(String inputTypeName) throws IOException {
		return new Process(1, 1, 1);
	}

	@Override
	public void closeProcessPhase(int processo, String phase)
			throws IOException {
		
	}

	@Override
	public int logFile(int processo, int bersaglio, int partner,
			String codicePartner, String typeName, String date, boolean update)
			throws IOException {
		return 1;
	}

	@Override
	public void logError(int trace, int progr, String codiceLog, String error,
			int idTematico) throws IOException {
		
	}

	@Override
	public void updateLogFile(int trace, int total, int errors,
			boolean updateTotals) throws IOException {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void removeImports(String inputTypeName) throws IOException {
		
	}
	
	

}
