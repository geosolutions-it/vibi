package it.geosolutions.vibi.mapper.sheets;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.Transaction;

import java.util.HashMap;
import java.util.Map;

public final class SheetContext {

    private final Sheet sheet;
    private final DataStore store;
    private final Transaction transaction;

    private Row row;

    private Map<String, Object> values = new HashMap<>();

    public SheetContext(Sheet sheet, DataStore store, Transaction transaction) {
        this.store = store;
        this.sheet = sheet;
        this.transaction = transaction;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public DataStore getStore() {
        return store;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Row getRow() {
        return row;
    }

    public SheetContext withRow(Row row) {
        this.row = row;
        return this;
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void putValue(String key, Object value) {
        values.put(key, value);
    }
}
