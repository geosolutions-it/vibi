package it.geosolutions.vibi.mapper.store;

import java.util.List;
import java.util.Map;

public interface Store {

    void open();

    void persist(String tableName, Map<String, Object> attributes, Map<String, Object> idAttributes);

    List<Map<String, Object>> find(String tableName, Map<String, Object> idAttributes);

    void flush();

    void close();
}
