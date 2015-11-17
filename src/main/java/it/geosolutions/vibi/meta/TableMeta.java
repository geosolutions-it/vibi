package it.geosolutions.vibi.meta;

import it.geosolutions.vibi.persistence.Persister;
import it.geosolutions.vibi.utils.Tuple;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TableMeta {

    private final String tableName;
    private final List<AttributeMeta> attributesMeta;

    public TableMeta(String tableName, List<AttributeMeta> attributesMeta) {
        this.tableName = tableName;
        this.attributesMeta = attributesMeta;
    }

    public void persistRow(Persister persister, Row row) {
        Map<String, Object> valuesToInsert = new HashMap<>();
        for (AttributeMeta attributeMeta : attributesMeta) {
            Tuple<String, Object> result = attributeMeta.getDataBaseColumnNameAndValue(row);
            valuesToInsert.put(result.first, result.second);
        }
        persister.persist(tableName, valuesToInsert);
    }
}
