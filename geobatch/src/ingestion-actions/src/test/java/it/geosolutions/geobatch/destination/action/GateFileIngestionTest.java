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
package it.geosolutions.geobatch.destination.action;

import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.destination.action.gatefilehandling.GateFileHandlingAction;
import it.geosolutions.geobatch.destination.action.gatefilehandling.GateFileHandlingConfiguration;
import it.geosolutions.geobatch.destination.common.utils.RemoteBrowserProtocol;
import it.geosolutions.geobatch.destination.common.utils.RemoteBrowserUtils;
import it.geosolutions.geobatch.ftp.client.configuration.FTPActionConfiguration.FTPConnectMode;
import it.geosolutions.geobatch.remoteBrowser.configuration.RemoteBrowserConfiguration;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

/**
 * JUnit test for {@link GateFileHandlingAction} action
 * 
 * @author adiaz
 */
public class GateFileIngestionTest {

	/**
	 * Mocked FTP server for testing
	 */
	private FakeFtpServer fakeFtpServer;

	/**
	 * Action configuration
	 */
	private GateFileHandlingConfiguration config;

	/**
	 * Port to start fake FTP server
	 */
	private int fakeFtpPort = 10580;

	/**
	 * Temporal directory
	 */
	private static String TMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Prepare test for remote FTP file browsing
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {

		// fake FTP server
		fakeFtpServer = new FakeFtpServer();
		fakeFtpServer.setServerControlPort(fakeFtpPort);
		fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/"));
		FileSystem fileSystem = new UnixFakeFileSystem();
		fileSystem.add(new DirectoryEntry("/tmp"));
		fileSystem.add(new DirectoryEntry("/tmp/SIIG"));
		fileSystem.add(new DirectoryEntry("/tmp/SIIG/Acquisiti"));
		fileSystem.add(new DirectoryEntry("/tmp/SIIG/Elaborati"));
		fileSystem.add(new DirectoryEntry("/tmp/SIIG/Scarti"));
		fileSystem.add(new FileEntry(
				"/tmp/SIIG/Acquisiti/00_20131218_141116.xml", "fake"));
		fileSystem.add(new FileEntry(
				"/tmp/SIIG/Acquisiti/00_20140107_142500.xml", "fake"));
		fakeFtpServer.setFileSystem(fileSystem);
		fakeFtpServer.start();

		// file handling config
		config = new GateFileHandlingConfiguration(null, null, null);
		config.setInputRemotePath("/tmp/SIIG/Acquisiti");
		config.setSuccesPath("/tmp/SIIG/Elaborati");
		config.setFailPath("/tmp/SIIG/Scarti");
		config.setInputPath(TMP_DIR);
		config.setDeleteDownloadedFiles(true);
		config.setStoreLocal(false);
		RemoteBrowserConfiguration remoteBrowserConfiguration = new RemoteBrowserConfiguration(
				null, null, null);
		remoteBrowserConfiguration.setFtpserverHost("localhost");
		remoteBrowserConfiguration.setFtpserverPort(fakeFtpPort);
		remoteBrowserConfiguration.setServerProtocol(RemoteBrowserProtocol.ftp);
		remoteBrowserConfiguration.setFtpserverPWD("password");
		remoteBrowserConfiguration.setFtpserverUSR("user");
		remoteBrowserConfiguration.setTimeout(5000);
		remoteBrowserConfiguration.setConnectMode(FTPConnectMode.ACTIVE);
		config.setRemoteBrowserConfiguration(remoteBrowserConfiguration);
		config.setRemoteResultBrowserConfiguration(remoteBrowserConfiguration);
		config.setIgnorePks(Boolean.TRUE);
	}

	/**
	 * Run {@link GateFileHandlingAction} with two files that will fail only to
	 * check if the remote file browsing it's ok
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testTwoErrors() throws Exception {
		GateFileHandlingAction gateFileHandlingAction = new GateFileHandlingAction(
				config);
		gateFileHandlingAction.doProcess(config, null, null);

		// Remote server configuration configuration
		String inputPath = config.getInputRemotePath();
		String successPath = config.getSuccesPath();
		String failPath = config.getFailPath();
		RemoteBrowserProtocol serverProtocol = config
				.getRemoteBrowserConfiguration().getServerProtocol();
		String serverHost = config.getRemoteBrowserConfiguration()
				.getFtpserverHost();
		String serverUser = config.getRemoteBrowserConfiguration()
				.getFtpserverUSR();
		String serverPWD = config.getRemoteBrowserConfiguration()
				.getFtpserverPWD();
		int serverPort = config.getRemoteBrowserConfiguration()
				.getFtpserverPort();
		int timeout = config.getRemoteBrowserConfiguration().getTimeout();
		final com.enterprisedt.net.ftp.FTPConnectMode connectMode = config
				.getRemoteBrowserConfiguration().toString()
				.equalsIgnoreCase(FTPConnectMode.ACTIVE.toString()) ? com.enterprisedt.net.ftp.FTPConnectMode.ACTIVE
				: com.enterprisedt.net.ftp.FTPConnectMode.PASV;

		// files must be on fail directory
		List<String> remoteLs = RemoteBrowserUtils.ls(serverProtocol,
				serverUser, serverPWD, serverHost, serverPort, failPath,
				connectMode, timeout);
		assertTrue(remoteLs.size() == 2);

		// success dir must be empty
		remoteLs = RemoteBrowserUtils.ls(serverProtocol, serverUser, serverPWD,
				serverHost, serverPort, successPath, connectMode, timeout);
		assertTrue(remoteLs.size() == 0);

		// input dir must be empty
		remoteLs = RemoteBrowserUtils.ls(serverProtocol, serverUser, serverPWD,
				serverHost, serverPort, inputPath, connectMode, timeout);
		assertTrue(remoteLs.size() == 0);
	}

	/**
	 * Stop FTP fake server
	 */
	@After
	public void tearDown() {
		fakeFtpServer.stop();
	}

}
