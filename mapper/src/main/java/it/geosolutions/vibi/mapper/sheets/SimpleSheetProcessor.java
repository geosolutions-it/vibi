package it.geosolutions.vibi.mapper.sheets;

import it.geosolutions.vibi.mapper.attributes.AttributeProducer;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.store.Store;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class SimpleSheetProcessor implements SheetProcessor {

    private final LinkedHashSet<String> tablesNamesInOrder;
    private final Map<String, List<AttributeProducer>> attributesProducersIndexedByTable;
    private final BoundsDetector boundsDetector;

    public SimpleSheetProcessor(LinkedHashSet<String> tablesNamesInOrder, Map<String,
            List<AttributeProducer>> attributesProducersIndexedByTable, BoundsDetector boundsDetector) {
        this.tablesNamesInOrder = tablesNamesInOrder;
        this.attributesProducersIndexedByTable = attributesProducersIndexedByTable;
        this.boundsDetector = boundsDetector;
    }

    @Override
    public void process(Sheet sheet, Store store) {
        Row row = findDataStartRow(sheet);
        while (row != null) {
            if (boundsDetector.isDataEnd(row)) {
                break;
            }
            processRow(row, store);
            row = sheet.getRow(row.getRowNum() + 1);
        }
    }

    private Row findDataStartRow(Sheet sheet) {
        for (Row row : sheet) {
            if (boundsDetector.isDataStart(row)) {
                return row;
            }
        }
        return null;
    }

    private void processRow(Row row, Store store) {
        for (String tableName : tablesNamesInOrder) {
            processRowForTable(row, tableName, store);
        }
    }

    private void processRowForTable(Row row, String tableName, Store store) {
        Map<String, Object> attributes = new HashMap<>();
        Map<String, Object> idAttributes = new HashMap<>();
        for (AttributeProducer attributeProducer : attributesProducersIndexedByTable.get(tableName)) {
            String attributeName = attributeProducer.getName();
            Object attributeValue = attributeProducer.getValue(row);
            attributes.put(attributeName, attributeValue);
            if (attributeProducer.isAnIdAttribute()) {
                idAttributes.put(attributeName, attributeValue);
            }
        }
        store.persist(tableName, attributes, idAttributes);
    }
}
