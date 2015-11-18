package it.geosolutions.vibi.mapper.store;

import it.geosolutions.vibi.mapper.utils.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MemoryStore implements Store {


    private Map<String, List<Map<String, Object>>> store = new HashMap<>();

    @Override
    public void open() {
    }

    @Override
    public void persist(String tableName, Map<String, Object> attributes, Map<String, Object> idAttributes) {
        List<Map<String, Object>> existingRow = find(tableName, idAttributes);
        Validations.checkCondition(existingRow.size() <= 1, "Multiple rows found.");
        if (existingRow.isEmpty()) {
            List<Map<String, Object>> table = store.get(tableName);
            if (table == null) {
                table = new ArrayList<>();
                store.put(tableName, table);
            }
            table.add(attributes);
        } else {
            existingRow.get(0).putAll(attributes);
        }
    }

    @Override
    public List<Map<String, Object>> find(String tableName, Map<String, Object> idAttributes) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> table = store.get(tableName);
        if (table == null) {
            return result;
        }
        for (Map<String, Object> row : table) {
            if (checkRowMatchesIdAttributes(row, idAttributes)) {
                result.add(row);
            }
        }
        return result;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    private boolean checkRowMatchesIdAttributes(Map<String, Object> row, Map<String, Object> idAttributes) {
        for (Map.Entry<String, Object> idAttribute : idAttributes.entrySet()) {
            Object rowAttributeValue = row.get(idAttribute.getKey());
            if (rowAttributeValue == null && idAttribute.getValue() != null) {
                return false;
            }
            if (rowAttributeValue != null && !rowAttributeValue.equals(idAttribute.getValue())) {
                return false;
            }
        }
        return true;
    }
}
