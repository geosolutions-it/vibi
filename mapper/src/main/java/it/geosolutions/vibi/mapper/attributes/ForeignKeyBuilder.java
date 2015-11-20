package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.utils.Sheets;

import java.util.ArrayList;
import java.util.List;

public final class ForeignKeyBuilder {

    private String tableName;
    private IdAttribute idAttribute;
    private List<Attribute> attributes = new ArrayList<>();
    private String attributeName;
    private String attributeType;

    public ForeignKeyBuilder withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public ForeignKeyBuilder withAttributeId(String name, String column) {
        idAttribute = new IdAttribute(name, Sheets.getIndex(column));
        return this;
    }

    public ForeignKeyBuilder withAttribute(String name, String type, String column) {
        attributes.add(new Attribute(name, type, Sheets.getIndex(column)));
        return this;
    }

    public ForeignKeyBuilder withAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public ForeignKeyBuilder withAttributeType(String attributeType) {
        this.attributeType = attributeType;
        return this;
    }

    public ForeignKey build() {
        return new ForeignKey(tableName, idAttribute, attributes, attributeName, attributeType);
    }
}
