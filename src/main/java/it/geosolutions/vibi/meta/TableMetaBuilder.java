package it.geosolutions.vibi.meta;

import it.geosolutions.vibi.converters.Converter;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TableMetaBuilder {

    private String tableName;
    private Map<Integer, AttributeMeta> attributesMetaIndexedByColumnIndex = new HashMap<>();

    public TableMetaBuilder withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TableMetaBuilder withAttributeMeta(String columnIndex, String dataBaseColumnName, Converter converter) {
        return withAttributeMeta(Sheets.getIndex(columnIndex), dataBaseColumnName, converter);
    }

    public TableMetaBuilder withAttributeMeta(int columnIndex, String dataBaseColumnName, Converter converter) {
        attributesMetaIndexedByColumnIndex.put(columnIndex, new AttributeMeta(columnIndex, dataBaseColumnName, converter));
        return this;
    }

    public TableMetaBuilder withAttributesMeta(List<AttributeMeta> attributesMeta) {
        for (AttributeMeta attributeMeta : attributesMeta) {
            withAttributeMeta(attributeMeta);
        }
        return this;
    }

    public TableMetaBuilder withAttributeMeta(AttributeMeta attributeMeta) {
        attributesMetaIndexedByColumnIndex.put(attributeMeta.getColumnIndex(), attributeMeta);
        return this;
    }

    public TableMeta build() {
        Validations.checkNotNull(tableName, "Table name is NULL.");
        return new TableMeta(tableName, new ArrayList<>(attributesMetaIndexedByColumnIndex.values()));
    }
}
