package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public final class Calculations {

    private final static Logger LOGGER = Logger.getLogger(Fds1Service.class);

    public static void initJdbcDriver(String jdbcDriver) {
        try {
            Class.forName(jdbcDriver);
        } catch (Exception exception) {
            throw new VibiException(exception, "Something bad happen when initiating JDBC driver '%s'.", jdbcDriver);
        }
    }

    public static void refresh(String dbUrl, String user, String pass) {
        LOGGER.info("Start updating calculations");
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass);
             CallableStatement statement = connection.prepareCall("{call refresh_calculations()}")) {
            statement.execute();
        } catch (Exception exception) {
            throw new VibiException(exception, "Something bad happen when refreshing the materialized views.");
        }
        LOGGER.info("Calculations updated.");
    }
}
