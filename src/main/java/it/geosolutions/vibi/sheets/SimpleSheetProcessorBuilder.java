package it.geosolutions.vibi.sheets;

import it.geosolutions.vibi.attributes.AttributeProducer;
import it.geosolutions.vibi.attributes.SimpleAttributeProducer;
import it.geosolutions.vibi.detectors.BoundsDetector;
import it.geosolutions.vibi.extractors.CellValueExtractor;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Validations;

import java.util.*;

public class SimpleSheetProcessorBuilder {

    private LinkedHashSet<String> tablesNamesInOrder = new LinkedHashSet<>();
    private Map<String, List<AttributeProducer>> attributesProducersIndexedByTable = new HashMap<>();
    private BoundsDetector boundsDetector;

    public SimpleSheetProcessorBuilder withTable(String tableName) {
        tablesNamesInOrder.add(tableName);
        return this;
    }

    public SimpleSheetProcessorBuilder withSimpleAttributeProducer(String tableName, boolean isAnIdAttribute, String name,
                                                                   String column, CellValueExtractor cellValueExtractor) {
        return withSimpleAttributeProducer(tableName, isAnIdAttribute, name, Sheets.getIndex(column), cellValueExtractor);
    }

    public SimpleSheetProcessorBuilder withSimpleAttributeProducer(String tableName, boolean isAnIdAttribute, String name,
                                                                   int columnIndex, CellValueExtractor cellValueExtractor) {
        return withAttributeProducer(tableName, new SimpleAttributeProducer(isAnIdAttribute, name, columnIndex, cellValueExtractor));
    }

    public SimpleSheetProcessorBuilder withAttributeProducer(String tableName, AttributeProducer attributeProducer) {
        List<AttributeProducer> attributesProducers = attributesProducersIndexedByTable.get(tableName);
        if (attributesProducers == null) {
            attributesProducers = new ArrayList<>();
            attributesProducersIndexedByTable.put(tableName, attributesProducers);
        }
        attributesProducers.add(attributeProducer);
        return this;
    }

    public SimpleSheetProcessorBuilder withBoundsDetector(BoundsDetector boundsDetector) {
        this.boundsDetector = boundsDetector;
        return this;
    }

    public SimpleSheetProcessor build() {
        Validations.checkNotNull(boundsDetector, "Bounds detector is NULL.");
        return new SimpleSheetProcessor(tablesNamesInOrder, attributesProducersIndexedByTable, boundsDetector);
    }
}
