package it.geosolutions.geobatch.destination.commons;

import java.sql.Connection;

import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockrunner.mock.jdbc.MockConnection;

public abstract class MemoryMockDataStore extends MemoryDataStore{

	protected static final Logger LOGGER = LoggerFactory.getLogger(MemoryMockDataStore.class);

	private Connection connection = new MockConnection();

	public Connection getConnection(Transaction transaction){
		return connection;
	}

}