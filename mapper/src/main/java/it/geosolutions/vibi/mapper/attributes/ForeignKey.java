package it.geosolutions.vibi.mapper.attributes;

import it.geosolutions.vibi.mapper.utils.Store;
import org.apache.poi.ss.usermodel.Row;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.List;

public class ForeignKey {

    private final String tableName;
    private final IdAttribute idAttribute;
    private final List<Attribute> attributes;
    private final String attributeName;
    private final String attributeType;

    private final SimpleFeatureType featuresType;

    public ForeignKey(String tableName, IdAttribute idAttribute,
                      List<Attribute> attributes, String attributeName, String attributeType) {
        this.tableName = tableName;
        this.idAttribute = idAttribute;
        this.attributes = attributes;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.featuresType = getSimpleFeatureType();
    }

    public String getType() {
        return attributeType;
    }

    public String getName() {
        return attributeName;
    }

    public String getForeignKey(DataStore store, Row row) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featuresType);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = idAttribute.getId(row);
        processAttributes(featureBuilder, row);
        Store.persistFeature(store, featureBuilder.buildFeature(id));
        return id;
    }

    private void processAttributes(SimpleFeatureBuilder featureBuilder, Row row) {
        for (Attribute attribute : attributes) {
            String attributeName = attribute.getName();
            Object attributeValue = attribute.getValue(row);
            featureBuilder.set(attributeName, attributeValue);
        }
    }

    private SimpleFeatureType getSimpleFeatureType() {
        if(attributes.isEmpty()) {
            return getSimpleFeatureType("");
        }
        StringBuilder featureDescription = new StringBuilder();
        for (Attribute attribute : attributes) {
            featureDescription.append(attribute.getName()).append(":").append(attribute.getType()).append(",");
        }
        featureDescription.deleteCharAt(featureDescription.length() - 1);
        return getSimpleFeatureType(featureDescription.toString());
    }

    private SimpleFeatureType getSimpleFeatureType(String featureDescription) {
        try {
            return DataUtilities.createType(tableName, featureDescription.toString());
        } catch (Exception exception) {
            throw new RuntimeException(String.format("Error creating feature type for table '%s'.", tableName), exception);
        }
    }
}
