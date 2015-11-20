package it.geosolutions.vibi.mapper.sheets;

import it.geosolutions.vibi.mapper.attributes.Aggregation;
import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.ForeignKey;
import it.geosolutions.vibi.mapper.attributes.IdAttribute;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.utils.Store;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.List;

public final class SheetProcessor {

    private final String tableName;
    private final IdAttribute idAttribute;
    private final List<Attribute> attributes;
    private final List<ForeignKey> foreignKeys;
    private final List<Aggregation> aggregations;
    private final BoundsDetector boundsDetector;

    private final SimpleFeatureType featuresType;

    public SheetProcessor(String tableName, IdAttribute idAttribute, List<Attribute> attributes,
                          List<ForeignKey> foreignKeys, List<Aggregation> aggregations, BoundsDetector boundsDetector) {
        this.tableName = tableName;
        this.idAttribute = idAttribute;
        this.attributes = attributes;
        this.foreignKeys = foreignKeys;
        this.aggregations = aggregations;
        this.boundsDetector = boundsDetector;
        this.featuresType = constructFeatureType();
    }

    public void process(Sheet sheet, DataStore store) {
        Row row = findDataStartRow(sheet);
        while (row != null) {
            if (boundsDetector.isDataEnd(row)) {
                break;
            }
            if (boundsDetector.isHeader(row)) {
                continue;
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

    private void processRow(Row row, DataStore store) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featuresType);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = idAttribute.getId(row);
        processAttributes(featureBuilder, row);
        processForeignKeys(featureBuilder, row, store);
        processAggregations(featureBuilder, row, id);
        Store.persistFeature(store, featureBuilder.buildFeature(id));
    }

    private void processAttributes(SimpleFeatureBuilder featureBuilder, Row row) {
        for (Attribute attribute : attributes) {
            String attributeName = attribute.getName();
            Object attributeValue = attribute.getValue(row);
            featureBuilder.set(attributeName, attributeValue);
        }
    }

    private void processForeignKeys(SimpleFeatureBuilder featureBuilder, Row row, DataStore store) {
        for (ForeignKey foreignKey : foreignKeys) {
            featureBuilder.set(foreignKey.getName(), foreignKey.getForeignKey(store, row));
        }
    }

    private void processAggregations(SimpleFeatureBuilder featureBuilder, Row row, String id) {
    }

    private SimpleFeatureType constructFeatureType() {
        StringBuilder featureDescription = new StringBuilder();
        for (Attribute attribute : attributes) {
            featureDescription.append(attribute.getName()).append(":").append(attribute.getType()).append(",");
        }
        for (ForeignKey foreignKey : foreignKeys) {
            featureDescription.append(foreignKey.getName()).append(":").append(foreignKey.getType()).append(",");
        }
        featureDescription.deleteCharAt(featureDescription.length() - 1);
        try {
            return DataUtilities.createType(tableName, featureDescription.toString());
        } catch (Exception exception) {
            throw new RuntimeException(String.format("Error creating feature type for table '%s'.", tableName), exception);
        }
    }
}
