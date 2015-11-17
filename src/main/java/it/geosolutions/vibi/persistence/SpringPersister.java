package it.geosolutions.vibi.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

public final class SpringPersister implements Persister {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public SpringPersister(String url, String userName, String password) {
        dataSource = new DriverManagerDataSource(url, userName, password);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void open() {
    }

    @Override
    public void persist(String tableName, Map<String, Object> values) {
        new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName).execute(values);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
