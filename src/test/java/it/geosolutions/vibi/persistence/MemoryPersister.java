package it.geosolutions.vibi.persistence;

import java.util.Map;

public final class MemoryPersister implements Persister {

    @Override
    public void open() {
    }

    @Override
    public void persist(String tableName, Map<String, Object> values) {
        System.out.println("### Table name: " + tableName);
        for (Map.Entry entry : values.entrySet()) {
            System.out.println(String.format("* %s -> %s", entry.getKey(), entry.getValue().toString()));
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
