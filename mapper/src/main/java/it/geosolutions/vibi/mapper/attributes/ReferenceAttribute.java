package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Type;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.List;

public class ReferenceAttribute extends Attribute {

    private final List<Attribute> attributes;

    private final SimpleFeatureType featureType;

    public ReferenceAttribute(String attributeName, Type attributeType, String tableName, List<Attribute> attributes) {
        super(attributeName, attributeType);
        this.attributes = attributes;
        this.featureType = Store.constructFeatureType(tableName, attributes);
    }

    @Override
    public Object getValue(SheetContext context) {
        SimpleFeature feature = Store.constructFeature(featureType, context, attributes);
        Store.persistFeature(context.getStore(), feature);
        return feature.getID();
    }
}
