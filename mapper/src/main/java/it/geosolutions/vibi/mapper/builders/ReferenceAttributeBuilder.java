package it.geosolutions.vibi.mapper.builders;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.ReferenceAttribute;
import it.geosolutions.vibi.mapper.attributes.SimpleAttribute;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;

import java.util.ArrayList;
import java.util.List;

public final class ReferenceAttributeBuilder {

    private String attributeName;
    private Type attributeType;
    private String tableName;
    private List<Attribute> attributes = new ArrayList<>();
    private boolean updateReference = true;

    public ReferenceAttributeBuilder withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public ReferenceAttributeBuilder withAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public ReferenceAttributeBuilder withAttributeType(String typeName) {
        attributeType = Type.of(typeName);
        return this;
    }

    public ReferenceAttributeBuilder withAttributeId(String attributeName, String column) {
        attributes.add(new SimpleAttribute(attributeName, Type.STRING, Sheets.getIndex(column), true));
        return this;
    }

    public ReferenceAttributeBuilder withAttribute(String attributeName, String typeName, String column) {
        attributes.add(new SimpleAttribute(attributeName, Type.of(typeName), Sheets.getIndex(column)));
        return this;
    }

    public ReferenceAttributeBuilder withUpdateReference(boolean updateReference) {
        this.updateReference = updateReference;
        return this;
    }

    public ReferenceAttribute build() {
        return new ReferenceAttribute(attributeName, attributeType, tableName, attributes, updateReference);
    }
}
