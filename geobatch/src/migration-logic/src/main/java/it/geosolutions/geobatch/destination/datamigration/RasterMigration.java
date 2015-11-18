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
package it.geosolutions.geobatch.destination.datamigration;

import it.geosolutions.geobatch.destination.common.InputObject;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.destination.ingestion.TargetIngestionProcess;
import it.geosolutions.geobatch.flow.event.ProgressListener;
import it.geosolutions.geobatch.flow.event.ProgressListenerForwarder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.geotools.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RasterMigration extends InputObject {

    private static final String PATHSEPARATOR = File.separator;

    private static final String LOWER_SEP = "_";

    private static final String TIF_EXTENSION = ".tif";

    private final static Logger LOGGER = LoggerFactory.getLogger(RasterMigration.class);

    public static final String ALL_PARTNERS = "all";

    private static Pattern TYPE_NAME_PARTS_TARGETS = Pattern
            .compile("^([A-Z]{2})[_-]([A-Z]{2,3})[_-]([A-Z]+)([_-][C|I])?[_-]([0-9]{8})[_-]([0-9]{2})$");

    private static Pattern TYPE_PARTNER_TARGETS = Pattern.compile("[A-Z]{2}");
	private static Pattern TYPE_NAME_PARTS_ALL = Pattern.compile("^([A-Z]{2})_([0-9]{8})(_.*?)?$");

    private boolean singleFile = false;

    private boolean partnerFiles = false;

    private boolean allFiles = false;

    private String codicePartner;
    
    private Integer partner;

    private String codiceTarget;

    private Integer targetType;

    private String date;
    
    private ProgressListener listener;

    private String outputDirectory;

    private String inputDirectory;

    private boolean listenerPresent = false;

    private static Properties targetNames = new Properties();

    private static Properties targetTypes = new Properties();

    public static Properties partners = new Properties();

    static {
        // load mappings from resources
        try {
            targetTypes.load(TargetIngestionProcess.class
                    .getResourceAsStream("/targets.properties"));
            partners.load(InputObject.class.getResourceAsStream("/partners.properties"));
            targetNames.load(TargetIngestionProcess.class
                    .getResourceAsStream("/targets_name.properties"));
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration: " + e.getMessage(), e);
        }
    }

    public RasterMigration(String typeName, String inputDirectory, String finalDirectory,
    		MetadataIngestionHandler metadataHandler, DataStore dataStore,
    		ProgressListenerForwarder listener) {
    	super(typeName, listener, metadataHandler, dataStore);
        this.inputDirectory = inputDirectory;
        this.outputDirectory = finalDirectory;
        this.listener = listener;

        if (listener != null) {
            listenerPresent = true;
        }
        // regexp used for parsing the input file name
        Matcher m = TYPE_NAME_PARTS_TARGETS.matcher(typeName);
        Matcher p = TYPE_PARTNER_TARGETS.matcher(typeName);
        Matcher a = TYPE_NAME_PARTS_ALL.matcher(typeName);
        if (m.matches()) {
            // partner alphanumerical abbreviation (from siig_t_partner)
            codicePartner = m.group(1);
            partner = Integer.parseInt(partners.get(codicePartner).toString());
            Object partnerObj = partners.get(codicePartner);
            // Check if the Partner is correct
            if (partnerObj != null) {
                // Check if only one file must be elaborated or all the partner files must be copied
                codiceTarget = m.group(3);
                Object target = targetTypes.get(codiceTarget);
                if (target != null) {
                    // // target detailed type id (from siig_t_bersaglio)
                    targetType = Integer.parseInt(target.toString());
                    singleFile = targetType != null;
                } else {
                    // All the partner files can be elaborated
                    partnerFiles = true;
                }
            }
            date = m.group(5);
        } else if (p.matches()) {
            codicePartner = p.group();
            Object partnerObj = partners.get(codicePartner);
            if (partnerObj != null) {
                // All the partner files can be elaborated
                partnerFiles = true;
            }
        } else if (a.matches()) {
        	codicePartner = a.group(1);
			// partner numerical id (from siig_t_partner)
			date = a.group(2);
            Object partnerObj = partners.get(codicePartner);
            if (partnerObj != null) {
                // All the partner files can be elaborated
                partnerFiles = true;
            }
        }
        // If it is not a single file nor the files associated to a single partner, then check if all
        // the directory files must be elaborated
        if (!singleFile && !partnerFiles && typeName.equalsIgnoreCase(ALL_PARTNERS)) {
            allFiles = true;
        }
    }

    /**
     * Method which copies the input files into the final directory
     * 
     * @throws IOException
     */
    public void execute(String closePhase, boolean newProcess) throws IOException {
        if (!(singleFile || partnerFiles || allFiles)) {
            if (listenerPresent) {
                listener.setTask("Unable to process the input string");
            }
            return;
        }
        
        
        int process = -1;
		int trace = -1;

		int errors = 0;
		int startErrors = 0;
		
		// create or retrieve metadata for ingestion
		if(newProcess) {
			removeOldImports();
			// new process
			process = createProcess();
			// write log for the imported file
			trace = logFile(process, NO_TARGET,
					partner, codicePartner, date, false);
		} else {
			// existing process
			MetadataIngestionHandler.Process importData = getProcessData();
			if (importData != null) {
				process = importData.getId();
				trace = importData.getMaxTrace();
				errors = importData.getMaxError();
				startErrors = errors;
			}
		}
        try {
	        // Directory where the input files are stored
	        File inputDir = new File(inputDirectory);
	        // Directory where the input files will be stored
	        File outputDir = new File(outputDirectory);
	        // Check if it is a directory
	        if (inputDir.exists() && inputDir.isDirectory() && outputDir.exists()
	                && outputDir.isDirectory()) {
	            if (singleFile) {
	                // Creation of the file name
	                String fileName = targetNames.get(targetType.toString()).toString();
	                // Copy of the single file
	                copySingleFile(fileName, codicePartner);
	            } else if (partnerFiles) {
	                // Copy of all the files associated to the partner
	                copyPartnerFiles(codicePartner);
	            } else {
	                // Copy of all the files
	                copyAllFiles(inputDir, outputDir);
	            }
	            
	        } else {
	            throw new IllegalArgumentException("File path is not correct");
	        }
        } catch(Exception e){
            	errors++;
            	metadataHandler
					.logError(trace, errors, "Error occurred on raster migration", getError(e), 0);                        
                LOGGER.error("Error occurred on raster migration" + e.getMessage(), e);
        } finally {
        
	        if (process != -1 && closePhase != null) {
				// close current process phase
				metadataHandler.closeProcessPhase(process, closePhase);
			}
	        finalReport("Rasters migration completed", errors - startErrors);
	    }
    }

    /**
     * Copy all the input files
     * 
     * @param inputDir
     * @param outputDir
     * @throws IOException
     */
    private void copyAllFiles(File inputDir, File outputDir) throws IOException {
        // Filter which selects only the directories
        FileFilter onlyDirectories = FileFilterUtils.directoryFileFilter();
        // Array of all the input directories
        File[] partnerDirs = inputDir.listFiles(onlyDirectories);
        // Cycle on all the partners
        for (File partnerDir : partnerDirs) {
            // Copy of all the files inside the Partner directory
            copyPartnerFiles(FilenameUtils.getBaseName(partnerDir.getName()));
        }
    }

    /**
     * Copy all the files associated to the partner name
     * 
     * @param partnerName
     * @throws IOException
     */
    private void copyPartnerFiles(String partnerName) throws IOException {
        if (partnerFiles || partners.containsKey(partnerName)) {
            // Directory associated to the selected partner
            File partnerDir = new File(composePartnerDirPath(partnerName));
            // Check if the directory exists and is a directory
            if (partnerDir.exists() && partnerDir.isDirectory()) {
                // Filter which selects only the files
                FileFilter noDirectories = FileFilterUtils.fileFileFilter();
                // Selection of all the files inside the directory
                File[] partnerFiles = partnerDir.listFiles(noDirectories);
                // Cycle on the input files
                int count = 0;
                for (File file : partnerFiles) {
                	updateImportProgress(count, partnerFiles.length, 1, 0, "Migrating raster " + file.getName());
                    // Copy of the single file
                    copySingleFile(FilenameUtils.getBaseName(file.getName()), partnerName);
                    count++;
                }
            }
        } else {
            // If the input partner does not exists an exception is thrown
            throw new IllegalArgumentException("Input Partner does not exists");
        }
    }

    /**
     * Copy a single file associated to the file name and partner name
     * 
     * @param fileName
     * @param partnerName
     * @throws IOException
     */
    private void copySingleFile(String fileName, String partnerName) throws IOException {
        if (singleFile || targetNames.containsValue(fileName)) {
            // INPUT FILE
            // file associated to the input file path
            File inFile = new File(composeFilePath(partnerName, fileName, true));
            // OUTPUT FILE
            // file associated to the output file path
            File outFile = new File(composeFilePath(partnerName, fileName, false));
            // Check if the file is present
           
            // If the input file is present, the operation must be executed
            if (inFile.exists()) {
                if (listenerPresent) {
                    listener.setTask("copying file " + partnerName + PATHSEPARATOR + fileName + TIF_EXTENSION + " into "
                            + composePartnerDirPath(partnerName));
                }
                // Copy operation
                FileUtils.copyFile(inFile, outFile);
            } else {
                // If the input file does not exist an exception is thrown
                throw new IllegalArgumentException("Input file not found");
            }
        }
    }

    /**
     * Creates the path associated to the file
     * 
     * @param partnerName
     * @param fileName
     * @param inputName
     * @return
     */
    private String composeFilePath(String partnerName, String fileName, boolean inputName) {
        if (inputName) {
            return composePartnerDirPath(partnerName) + PATHSEPARATOR + fileName
                    + TIF_EXTENSION;
        } else {
            return composeTargetDirPath(partnerName,fileName) + PATHSEPARATOR + fileName
                    + LOWER_SEP + partnerName.toLowerCase(Locale.ENGLISH) + TIF_EXTENSION;
        }
    }

    /**
     * Creates the path associated to the partner directory
     * 
     * @param partnerName
     * @return
     */
    private String composePartnerDirPath(String partnerName) {
        return inputDirectory + PATHSEPARATOR + partnerName.toUpperCase(Locale.ENGLISH);
    }
    
    /**
     * Creates the path associated to the target final directory
     * 
     * @param partnerName
     * @param fileName
     * @return
     */
    private String composeTargetDirPath(String partnerName, String fileName) {
        return outputDirectory + PATHSEPARATOR + fileName;
    }

	@Override
	protected boolean parseTypeName(String typeName) {
		return true;
	}
}
