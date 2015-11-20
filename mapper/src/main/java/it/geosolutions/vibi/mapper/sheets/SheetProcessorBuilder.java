package it.geosolutions.vibi.mapper.sheets;

import it.geosolutions.vibi.mapper.attributes.Aggregation;
import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.ForeignKey;
import it.geosolutions.vibi.mapper.attributes.IdAttribute;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.utils.Sheets;

import java.util.ArrayList;
import java.util.List;

public class SheetProcessorBuilder {

    private String tableName;
    private IdAttribute idAttribute;
    private List<Attribute> attributes = new ArrayList<>();
    private List<ForeignKey> foreignKeys = new ArrayList<>();
    private List<Aggregation> aggregations = new ArrayList<>();
    private BoundsDetector boundsDetector;

    public SheetProcessorBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SheetProcessorBuilder withAttributeId(String column, String name) {
        idAttribute = new IdAttribute(name, Sheets.getIndex(column));
        return this;
    }

    public SheetProcessorBuilder withAttribute(String column, String name, String type) {
        attributes.add(new Attribute(name, type, Sheets.getIndex(column)));
        return this;
    }

    public SheetProcessorBuilder withForeignKey(ForeignKey foreignKey) {
        foreignKeys.add(foreignKey);
        return this;
    }

    public SheetProcessorBuilder withBoundsDetector(BoundsDetector boundsDetector) {
        this.boundsDetector = boundsDetector;
        return this;
    }

    public SheetProcessor build() {
        return new SheetProcessor(tableName, idAttribute, attributes, foreignKeys, aggregations, boundsDetector);
    }
}
