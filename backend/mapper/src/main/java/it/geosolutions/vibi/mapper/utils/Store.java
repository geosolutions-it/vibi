package it.geosolutions.vibi.mapper.utils;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.exceptions.Validations;
import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureStore;
import org.geotools.data.Transaction;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.identity.FeatureIdImpl;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.Name;
import org.opengis.filter.FilterFactory2;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Store {

    private final static FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());

    public static Tuple<String, SimpleFeature> constructFeature(SimpleFeatureType featureType, SheetContext context, List<Attribute> attributes) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
        featureBuilder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
        String id = null;
        for (Attribute attribute : attributes) {
            if (!attribute.isIdentifier()) {
                featureBuilder.set(attribute.getName(), attribute.getValue(context));
            } else {
                Object value = attribute.getValue(context);
                if (value != null) {
                    id = value.toString();
                }
            }
        }
        return Tuple.tuple(id, featureBuilder.buildFeature(id));
    }

    public static SimpleFeatureType constructFeatureType(String tableName, List<Attribute> attributes) {
        StringBuilder featureDescription = new StringBuilder();
        for (Attribute attribute : attributes) {
            if (!attribute.isIdentifier()) {
                featureDescription.append(attribute.getName()).append(":").append(attribute.getType().getName()).append(",");
            }
        }
        try {
            return DataUtilities.createType(tableName, featureDescription.toString());
        } catch (Exception exception) {
            throw new VibiException(exception, "Error creating feature type for table '%s'.", tableName);
        }
    }

    public static FeatureStore<SimpleFeatureType, SimpleFeature> getFeatureStore(DataStore store, String featureTypeName) {
        try {
            return (FeatureStore<SimpleFeatureType, SimpleFeature>) store.getFeatureSource(featureTypeName);
        } catch (Exception exception) {
            throw new RuntimeException(String.format("Error obtaining feature store of type '%s'.", featureTypeName), exception);
        }
    }

    public static void persistFeature(DataStore store, Transaction transaction, SimpleFeature simpleFeature) {
        persistFeature(store, transaction, simpleFeature, true);
    }

    public static void persistFeature(DataStore store, Transaction transaction, SimpleFeature simpleFeature, boolean update) {
        SimpleFeature foundFeature = find(store, transaction, simpleFeature);
        if (foundFeature == null) {
            create(store, transaction, simpleFeature);
        } else if (update) {
            update(store, transaction, simpleFeature);
        }
    }

    public static SimpleFeature find(DataStore store, Transaction transaction, final SimpleFeature simpleFeature) {
        return new InTransaction(store, transaction, simpleFeature.getFeatureType().getTypeName()) {

            SimpleFeature feature;

            @Override
            public void doWork(FeatureStore<SimpleFeatureType, SimpleFeature> featureStore) throws Exception {
                FeatureCollection<SimpleFeatureType, SimpleFeature> featuresCollection =
                        featureStore.getFeatures(filterFactory.id(encodeId(simpleFeature, true).getIdentifier()));
                Validations.checkCondition(featuresCollection.size() <= 1, "To much features found.");
                if (!featuresCollection.isEmpty()) {
                    FeatureIterator<SimpleFeature> features = featuresCollection.features();
                    try {
                        feature = features.next();
                    } finally {
                        features.close();
                    }
                }
            }
        }.feature;
    }

    public static void create(DataStore store, Transaction transaction, final SimpleFeature simpleFeature) {
        new InTransaction(store, transaction, simpleFeature.getFeatureType().getTypeName()) {

            @Override
            public void doWork(FeatureStore<SimpleFeatureType, SimpleFeature> featureStore) throws Exception {
                featureStore.addFeatures(DataUtilities.collection(encodeId(simpleFeature, false)));
            }
        };
    }

    public static void delete(DataStore store, Transaction transaction, final String featureTypeName, final String featureId) {
        new InTransaction(store, transaction, featureTypeName) {

            @Override
            public void doWork(FeatureStore<SimpleFeatureType, SimpleFeature> featureStore) throws Exception {
                String encodedId = featureId;
                encodedId = encodedId.replace("'", "''");
                encodedId = URLEncoder.encode(encodedId);
                featureStore.removeFeatures(filterFactory.id(new FeatureIdImpl(encodedId)));
            }
        };
    }

    public static void update(DataStore store, Transaction transaction, final SimpleFeature simpleFeature) {

        final List<Property> properties = filterInvalidProperties(simpleFeature.getProperties());

        if (properties.isEmpty()) {
            return;
        }

        new InTransaction(store, transaction, simpleFeature.getFeatureType().getTypeName()) {

            @Override
            public void doWork(FeatureStore<SimpleFeatureType, SimpleFeature> featureStore) throws Exception {
                Name[] names = new Name[properties.size()];
                Object[] values = new Object[properties.size()];
                int i = 0;
                for (Property property : properties) {
                    names[i] = property.getName();
                    values[i] = property.getValue();
                    i++;
                }
                featureStore.modifyFeatures(names, values, filterFactory.id(encodeId(simpleFeature, false).getIdentifier()));
            }
        };
    }

    private static SimpleFeature encodeId(SimpleFeature simpleFeature, boolean escapeQuotes) {
        SimpleFeatureBuilder copy = new SimpleFeatureBuilder(simpleFeature.getType());
        copy.init(simpleFeature);
        String encodedId = simpleFeature.getID();
        if (escapeQuotes) {
            encodedId = encodedId.replace("'", "''");
        }
        encodedId = URLEncoder.encode(encodedId);
        return copy.buildFeature(encodedId);
    }

    private static List<Property> filterInvalidProperties(Collection<Property> properties) {
        List<Property> filteredProperties = new ArrayList<>();
        for (Property property : properties) {
            if (!(property.getName().toString().equals("") && property.getValue() == null)) {
                filteredProperties.add(property);
            }
        }
        return filteredProperties;
    }

    private static abstract class InTransaction {

        public InTransaction(DataStore store, Transaction transaction, String featureTypeName) {
            FeatureStore<SimpleFeatureType, SimpleFeature> featureStore = getFeatureStore(store, featureTypeName);
            featureStore.setTransaction(transaction);
            try {
                doWork(featureStore);
            } catch (Exception exception) {
                throw new RuntimeException(String.format("Error persisting feature of type '%s'.", featureTypeName), exception);
            }
        }

        public abstract void doWork(FeatureStore<SimpleFeatureType, SimpleFeature> featureStore) throws Exception;
    }
}
