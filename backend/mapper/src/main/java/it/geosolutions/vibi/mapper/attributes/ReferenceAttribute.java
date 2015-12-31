package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Tuple;
import it.geosolutions.vibi.mapper.utils.Type;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.List;

public class ReferenceAttribute extends Attribute {

    private final List<Attribute> attributes;

    private final SimpleFeatureType featureType;

    private final boolean createReference;
    private final boolean updateReference;

    public ReferenceAttribute(String attributeName, Type attributeType, String tableName,
                              List<Attribute> attributes, boolean createReference, boolean updateReference) {
        super(attributeName, attributeType);
        this.attributes = attributes;
        this.createReference = createReference;
        this.updateReference = updateReference;
        this.featureType = Store.constructFeatureType(tableName, attributes);
    }

    @Override
    public Object getValue(SheetContext context) {
        Tuple<String, SimpleFeature> feature = Store.constructFeature(featureType, context, attributes);
        if (feature.first == null || feature.first.isEmpty()) {
            return null;
        }
        SimpleFeature foundFeature = Store.find(context.getStore(), feature.second);
        if (foundFeature == null) {
            if (createReference) {
                Store.create(context.getStore(), feature.second);
            } else {
                throw new VibiException("Foreign key for table '%s' with value '%s' don't exists, this is required " +
                        "in the context of row '%d' in spreadsheet '%s'.", feature.second.getType().getTypeName(),
                        feature.second.getID(), context.getRow().getRowNum() + 1, context.getSheet().getSheetName());
            }
        } else if (updateReference) {
            Store.update(context.getStore(), feature.second);
        }
        return feature.second.getID();
    }
}
