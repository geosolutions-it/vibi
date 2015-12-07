package it.geosolutions.vibi.mapper.builders;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.SimpleAttribute;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContextUpdater;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;

import java.util.ArrayList;
import java.util.List;

public class SheetProcessorBuilder {

    private String tableName;
    private List<Attribute> attributes = new ArrayList<>();
    private BoundsDetector boundsDetector;
    private SheetContextUpdater contextUpdater = new SheetContextUpdater();

    public SheetProcessorBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SheetProcessorBuilder withAttributeId(String column, String name) {
        attributes.add(new SimpleAttribute(name, Type.STRING, Sheets.getIndex(column), true));
        return this;
    }

    public SheetProcessorBuilder withAttribute(String column, String name, String type) {
        attributes.add(new SimpleAttribute(name, Type.of(type), Sheets.getIndex(column)));
        return this;
    }

    public SheetProcessorBuilder withAttribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

    public SheetProcessorBuilder withBoundsDetector(BoundsDetector boundsDetector) {
        this.boundsDetector = boundsDetector;
        return this;
    }

    public SheetProcessorBuilder withContextUpdater(SheetContextUpdater contextUpdater) {
        this.contextUpdater = contextUpdater;
        return this;
    }

    public SheetProcessor build() {
        return new SheetProcessor(tableName, attributes, boundsDetector, contextUpdater);
    }
}
