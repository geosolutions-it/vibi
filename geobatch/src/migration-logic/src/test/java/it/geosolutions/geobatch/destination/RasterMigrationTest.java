/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.destination;

import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.destination.commons.DestinationMemoryTest;
import it.geosolutions.geobatch.destination.datamigration.RasterMigration;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.geotools.test.TestData;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;

public class RasterMigrationTest extends DestinationMemoryTest {

    private static final Class<RasterMigrationTest> CALLER = RasterMigrationTest.class;

    private static final Logger LOGGER = Logger.getLogger(CALLER.getName());

    private static final String PATHSEPARATOR = File.separator;

    private static final String LOWER_SEP = "_";

    private static final String TIF_EXTENSION = ".tif";

    private final static String SINGLE_FILE = "AO_BNU-ABOSC_C_20130730_02";

    private final static String[] PARTNER_FILES = { "AO", "RP" };

    private final static String ALL_FILES = "ALL";

    private final static String INPUT_DIR;

    private final static String OUTPUT_DIR;

    // Selection of the input and output directories
    static {
        File inputdir = null;
        File outputdir = null;
        try {
            inputdir = TestData.file(CALLER, "inputdir");
            outputdir = TestData.file(CALLER, "outputdir");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        // Selection of the associated names
        INPUT_DIR = inputdir.getPath();
        OUTPUT_DIR = outputdir.getPath();
    }

    @Before
	public void before() throws Exception { 	
		initTestWithData(new String[] {"targets_test_data"});
	}
    
    @Test
    public void testCopySingleFile() throws IOException {
        // Initialization of the migration object
        RasterMigration rasterMig = new RasterMigration(SINGLE_FILE, INPUT_DIR, OUTPUT_DIR, metadataHandler, dataStore, new ProgressListenerForwarder(null));
        // Copying file
        rasterMig.execute(null, false);
        // Path of the new file
        String filePath = OUTPUT_DIR + PATHSEPARATOR + "aree_boscate" + PATHSEPARATOR
                + "aree_boscate_ao" + TIF_EXTENSION;
        // File associated to the path
        File file = new File(filePath);
        // Check if the file is copied
        assertTrue(file.exists());
        // Elimination of the new file created
        file.delete();
        // Elimination of file associated new directory
        file.getParentFile().delete();
    }

    @Test
    public void testCopyPartnerFiles() throws IOException {
        // First partner string initialization
        String partnerName = PARTNER_FILES[0];
        // Initialization of the migration object
        RasterMigration rasterMig = new RasterMigration(partnerName, INPUT_DIR, OUTPUT_DIR, metadataHandler, dataStore, new ProgressListenerForwarder(null));
        // Copying file
        rasterMig.execute(null, false);
        // Path of the new files
        String filePathBase = INPUT_DIR + PATHSEPARATOR + partnerName;
        File inputDirPartner = new File(filePathBase);
        File[] files = inputDirPartner.listFiles();
        String fileName = null;
        String outputFilePath = OUTPUT_DIR + PATHSEPARATOR;
        for (File file : files) {
            // Selection of the name associated to the file without the extension
            fileName = FilenameUtils.getBaseName(file.getName());
            // New file copied with the new name
            File newFile = new File(outputFilePath + fileName + PATHSEPARATOR + fileName
                    + LOWER_SEP + partnerName.toLowerCase(Locale.ENGLISH) + TIF_EXTENSION);
            // Check if the file is present
            assertTrue(newFile.exists());
            // Elimination of the new file created
            newFile.delete();
            // Elimination of file associated new directory
            newFile.getParentFile().delete();
        }
    }

    @Test
    public void testCopyAllFiles() throws IOException {
        // Initialization of the migration object
        RasterMigration rasterMig = new RasterMigration(ALL_FILES, INPUT_DIR, OUTPUT_DIR, metadataHandler, dataStore,  new ProgressListenerForwarder(null));
        // Copying file
        rasterMig.execute(null, false);
        // Path of the new files
        String filePathBase = INPUT_DIR + PATHSEPARATOR + PARTNER_FILES[0];
        File inputDirPartner = new File(filePathBase);
        File[] files = inputDirPartner.listFiles();
        String fileName = null;
        String outputFilePath = OUTPUT_DIR + PATHSEPARATOR;
        int end = PARTNER_FILES.length;
        int last = end - 1;
        for (File file : files) {
            // Selection of the name associated to the file without the extension
            fileName = FilenameUtils.getBaseName(file.getName());
            // New file copied with the new name
            for (int i = 0; i < end; i++) {
                String partnerName = PARTNER_FILES[i];
                File newFile = new File(outputFilePath + fileName + PATHSEPARATOR + fileName
                        + LOWER_SEP + partnerName.toLowerCase(Locale.ENGLISH) + TIF_EXTENSION);
                // Check if the file is present
                assertTrue(newFile.exists());
                // Elimination of the new file created
                newFile.delete();
                if (i == last) {
                    // Elimination of file associated new directory
                    newFile.getParentFile().delete();
                }
            }
        }
    }

	@Override
	protected void checkData(SimpleFeature feature) {
		
	}
}
