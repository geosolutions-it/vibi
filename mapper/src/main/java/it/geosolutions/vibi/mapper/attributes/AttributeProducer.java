package it.geosolutions.vibi.mapper.attributes;

import org.apache.poi.ss.usermodel.Row;

public interface AttributeProducer {

    boolean isAnIdAttribute();

    String getName();

    Object getValue(Row row);
}
