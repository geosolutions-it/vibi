package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class Calculations {

    private final static Logger LOGGER = Logger.getLogger(Fds1Service.class);

    private static String MATERIZLIED_VIEWS_REFRESH_SQL = readMaterializedViewsSql();

    private static String readMaterializedViewsSql() {
        try (BufferedInputStream input = new BufferedInputStream(Calculations.class.getClassLoader().
                getResourceAsStream("materialized_views_refresh.sql"));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            int result = input.read();
            while (result != -1) {
                byte data = (byte) result;
                output.write(data);
                result = input.read();
            }
            return output.toString();
        } catch (Exception exception) {
            throw new VibiException("Error reading materialized views refresh SQL.");
        }
    }

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
             Statement statement = connection.createStatement()) {
            statement.execute(MATERIZLIED_VIEWS_REFRESH_SQL);
        } catch (Exception exception) {
            throw new VibiException(exception, "Something bad happen when refreshing the materialized views.");
        }
        LOGGER.info("Calculations updated.");
    }
}
