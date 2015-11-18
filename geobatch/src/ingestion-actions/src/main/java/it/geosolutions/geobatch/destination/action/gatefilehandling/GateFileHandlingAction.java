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
package it.geosolutions.geobatch.destination.action.gatefilehandling;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.actions.ds2ds.util.FeatureConfigurationUtil;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.destination.common.utils.RemoteBrowserProtocol;
import it.geosolutions.geobatch.destination.common.utils.RemoteBrowserUtils;
import it.geosolutions.geobatch.destination.ingestion.GateIngestionProcess;
import it.geosolutions.geobatch.destination.ingestion.MetadataIngestionHandler;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;

import org.geotools.data.DataStore;
import org.geotools.jdbc.JDBCDataStore;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;

/**
 * GeoBatch gate ingestion remote file handling action
 * 
 * @author adiaz
 */
@Action(configurationClass = GateFileHandlingConfiguration.class)
public class GateFileHandlingAction extends BaseAction<EventObject> {

/**
 * File separator
 */
private static String SEPARATOR = System.getProperty("file.separator");

/**
 * Action configuration
 */
private GateFileHandlingConfiguration configuration;

public GateFileHandlingAction(final GateFileHandlingConfiguration configuration)
        throws IOException {
    super(configuration);
    this.configuration = configuration;
}

/**
 * Check if the configuration it's correctly. Just obtain the data source
 */
public boolean checkConfiguration() {
    DataStore ds = null;
    try {
        // Don't read configuration for the file, just
        // this.outputfeature configuration
        ds = FeatureConfigurationUtil.createDataStore(configuration
                .getOutputFeature());
        if (!(ds instanceof JDBCDataStore)) {
            LOGGER.error("Incorrect datasource for this action");
            return false;
        } else {
            return true;
        }
    } catch (Exception e) {
        LOGGER.error("Incorrect datasource for this action");
        return false;
    } finally {
        ds.dispose();
    }
}

/**
 * Execute process
 */
public Queue<EventObject> execute(Queue<EventObject> events)
        throws ActionException {

    // return object
    final Queue<EventObject> ret = new LinkedList<EventObject>();

    while (events.size() > 0) {
        final EventObject ev;
        try {
            if ((ev = events.remove()) != null) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Working on incoming event: " + ev.getSource());
                }
                if (ev instanceof FileSystemEvent) {
                    FileSystemEvent fileEvent = (FileSystemEvent) ev;
                    @SuppressWarnings("unused")
                    File file = fileEvent.getSource();
                    // Don't read configuration for the file, just
                    // this.outputfeature configuration
                    DataStore ds = FeatureConfigurationUtil
                            .createDataStore(configuration.getOutputFeature());
                    if (ds == null) {
                        throw new ActionException(this, "Can't find datastore ");
                    }
                    try {
                        if (!(ds instanceof JDBCDataStore)) {
                            throw new ActionException(this,
                                    "Bad Datastore type "
                                            + ds.getClass().getName());
                        }
                        JDBCDataStore dataStore = (JDBCDataStore) ds;
                        dataStore.setExposePrimaryKeyColumns(true);
                        MetadataIngestionHandler metadataHandler = new MetadataIngestionHandler(
                                dataStore);
                        doProcess(configuration, dataStore, metadataHandler);

                        // pass the feature config to the next action
                        ret.add(new FileSystemEvent(((FileSystemEvent) ev)
                                .getSource(), FileSystemEventType.FILE_ADDED));
                    } finally {
                        ds.dispose();
                    }
                }

                // add the event to the return
                ret.add(ev);

            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("Encountered a NULL event: SKIPPING...");
                }
                continue;
            }
        } catch (Exception ioe) {
            final String message = "Unable to produce the output: "
                    + ioe.getLocalizedMessage();
            if (LOGGER.isErrorEnabled())
                LOGGER.error(message, ioe);

            throw new ActionException(this, message);
        }
    }
    return ret;
}

/**
 * Call to Gate ingestion process
 * 
 * @param cfg
 * @param dataStore
 * @param metadataHandler
 * @param file
 * @throws ActionException
 */
public void doProcess(GateFileHandlingConfiguration cfg,
        JDBCDataStore dataStore, MetadataIngestionHandler metadataHandler)
        throws ActionException {

    try {

        // Input directory is the temporal directory (for file download)
        String inputDir = configuration.getInputPath();

        // Remote server configuration
        String remotePath = cfg.getInputRemotePath();
        RemoteBrowserProtocol serverProtocol = configuration
                .getRemoteBrowserConfiguration().getServerProtocol();
        String serverHost = configuration.getRemoteBrowserConfiguration()
                .getFtpserverHost();
        String serverUser = configuration.getRemoteBrowserConfiguration()
                .getFtpserverUSR();
        String serverPWD = configuration.getRemoteBrowserConfiguration()
                .getFtpserverPWD();
        int serverPort = configuration.getRemoteBrowserConfiguration()
                .getFtpserverPort();
        int timeout = configuration.getRemoteBrowserConfiguration()
                .getTimeout();
        final FTPConnectMode connectMode = configuration
                .getRemoteBrowserConfiguration().toString()
                .equalsIgnoreCase(FTPConnectMode.ACTIVE.toString()) ? FTPConnectMode.ACTIVE
                : FTPConnectMode.PASV;

        // Remote server result configuration
        RemoteBrowserProtocol serverResultProtocol = null;
        String serverResultHost = null;
        String serverResultUser = null;
        String serverResultPWD = null;
        int serverResultPort = 0;
        int resultTimeout = 0;
        FTPConnectMode resultConnectMode = null;
        if (configuration.getRemoteResultBrowserConfiguration() != null
                && !configuration.isStoreLocal()) {
            serverResultHost = configuration
                    .getRemoteResultBrowserConfiguration().getFtpserverHost();
            serverResultUser = configuration
                    .getRemoteResultBrowserConfiguration().getFtpserverUSR();
            serverResultPWD = configuration
                    .getRemoteResultBrowserConfiguration().getFtpserverPWD();
            serverResultPort = configuration
                    .getRemoteResultBrowserConfiguration().getFtpserverPort();
            resultTimeout = configuration.getRemoteResultBrowserConfiguration()
                    .getTimeout();
            resultConnectMode = configuration
                    .getRemoteResultBrowserConfiguration().toString()
                    .equalsIgnoreCase(FTPConnectMode.ACTIVE.toString()) ? FTPConnectMode.ACTIVE
                    : FTPConnectMode.PASV;
        }

        // File pattern
        Pattern pattern = Pattern.compile(configuration.getFilePattern());

        // obtain filenames
        List<String> fileNames = RemoteBrowserUtils.ls(serverProtocol,
                serverUser, serverPWD, serverHost, serverPort, remotePath,
                connectMode, timeout, pattern);

        // For each file on the remote directory
        for (String fileName : fileNames) {

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Processing file " + fileName);
            }

            // check if exists
            boolean exists = false;
            if (configuration.isCheckIfExists()) {
                if (configuration.isStoreLocal()) {
                    exists = checkIfExists(inputDir, fileName)
                            | checkIfExists(configuration.getSuccesPath(),
                                    fileName)
                            | checkIfExists(configuration.getFailPath(),
                                    fileName);
                } else {
                    exists = checkIfExists(serverResultProtocol,
                            serverResultUser, serverResultHost,
                            serverResultPWD, serverResultPort, inputDir,
                            fileName, resultConnectMode, resultTimeout)
                            | checkIfExists(serverResultProtocol,
                                    serverResultUser, serverResultHost,
                                    serverResultPWD, serverResultPort,
                                    configuration.getSuccesPath(), fileName,
                                    resultConnectMode, resultTimeout)
                            | checkIfExists(serverResultProtocol,
                                    serverResultUser, serverResultHost,
                                    serverResultPWD, serverResultPort,
                                    configuration.getFailPath(), fileName,
                                    resultConnectMode, resultTimeout);
                }
            }

            // only download and handle if was not be already handled
            if (!exists) {
                // Download the file
                File inputFile = null;
                try {
	                inputFile = RemoteBrowserUtils.downloadFile(
	                        serverProtocol, serverUser, serverPWD, serverHost,
	                        serverPort, remotePath + SEPARATOR + fileName, inputDir
	                                + SEPARATOR + fileName, timeout);
	                
	             // process the file
	                if (inputFile.exists()) {
	                    if (configuration.isDeleteDownloadedFiles()) {
	                        // delete downloaded file
	                        RemoteBrowserUtils.deleteFile(serverProtocol,
	                                serverUser, serverPWD, serverHost, serverPort,
	                                timeout, remotePath, fileName, connectMode);
	                    }

	                    boolean error = true;

	                    try {
	                        // Import gate data
	                        GateIngestionProcess computation = new GateIngestionProcess(
	                                // type name read on file name
	                                getInputTypeName(inputFile), listenerForwarder,
	                                metadataHandler, dataStore, inputFile,
	                                configuration.getTimeFormatConfiguration());
	                        Map<String, Object> procResult = computation
	                                .doProcess(cfg.getIgnorePks(), false, "", "");

	                        // is correct?
	                        if (procResult != null
	                                && !procResult.isEmpty()
	                                && procResult
	                                        .get(GateIngestionProcess.ERROR_COUNT) != null
	                                && procResult.get(
	                                        GateIngestionProcess.ERROR_COUNT)
	                                        .equals(0)) {
	                            error = false;
	                        }
	                    } catch (Exception e) {
	                        if (LOGGER.isErrorEnabled()) {
	                            LOGGER.error("Error processing " + fileName, e);
	                        }
	                    }

	                    // Post process.
	                    String targetPath = null;
	                    if (!error) {
	                        // success: put on success remote dir
	                        targetPath = configuration.getSuccesPath();
	                    } else {
	                        // fail: put on fail remote dir
	                        targetPath = configuration.getFailPath();
	                    }

	                    // result could be local or remote
	                    if (configuration.isStoreLocal()) {
	                        // move the the target path
	                        inputFile.renameTo(new File(targetPath + SEPARATOR
	                                + inputFile.getName()));
	                    } else {
	                        // upload file
	                        RemoteBrowserUtils.putFile(serverResultProtocol,
	                                serverResultUser, serverResultHost,
	                                serverResultPWD, serverResultPort, targetPath
	                                        + SEPARATOR + inputFile.getName(),
	                                inputFile.getAbsolutePath(), resultConnectMode,
	                                resultTimeout);

	                        // clean downloaded file in the input directory
	                        inputFile.delete();
	                    }

	                } else if (LOGGER.isErrorEnabled()) {
	                    LOGGER.error("Error downloading " + fileName);
	                }
                } catch(Exception e) {
                	LOGGER.error("Error downloading " + fileName, e);
                }

                
            } else if (LOGGER.isInfoEnabled()) {
                LOGGER.info("File " + fileName
                        + " ignored because was processed after this execution");
            }
        }

    } catch (Exception ex) {
        // TODO: what shall we do here??
        // log and rethrow for the moment, but a rollback should be
        // implementened somewhere
        LOGGER.error("Error in importing gates", ex);
        throw new ActionException(this, "Error in importing gates", ex);
    }

}

/**
 * @param file
 * @return
 */
private String getInputTypeName(File file) {
    String fileName = file != null ? file.getName() : null;
    if (fileName != null && fileName.contains(".")) {
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
    }
    return fileName;
}

/**
 * Check if exists a file on a remote
 * 
 * @param serverProtocol
 * @param serverUser
 * @param serverHost
 * @param serverPWD
 * @param serverPort
 * @param path
 * @param fileName
 * @param connectMode
 * @param timeout
 * @return
 * @throws IOException
 * @throws FTPException
 * @throws ParseException
 */
private boolean checkIfExists(RemoteBrowserProtocol serverProtocol,
        String serverUser, String serverHost, String serverPWD, int serverPort,
        String path, String fileName, FTPConnectMode connectMode, int timeout)
        throws IOException, FTPException, ParseException {
    return RemoteBrowserUtils.checkIfExists(serverProtocol, serverUser,
            serverHost, serverPWD, serverPort, path, fileName, connectMode,
            timeout);
}

/**
 * Check if a file exists
 * 
 * @param inputDir
 * @param fileName
 * @return
 */
private boolean checkIfExists(String inputDir, String fileName) {
    File file = new File(inputDir + SEPARATOR + fileName);
    return file.exists();
}

}
