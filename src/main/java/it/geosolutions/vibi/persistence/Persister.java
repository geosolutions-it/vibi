package it.geosolutions.vibi.persistence;

import java.util.Map;

public interface Persister {

    void open();

    void persist(String tableName, Map<String, Object> values);

    void flush();

    void close();
}
