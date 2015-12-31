package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;

public class LookupService {

    public static void processLookupSpeciesSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new BoundsDetector() {
            @Override
            public boolean ignore(SheetContext context) {
                return false;
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                String value = Sheets.extract(row, "A", Type.STRING);
                return value != null && value.equalsIgnoreCase("scientific name");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return Sheets.extract(context.getRow(), "A", Type.STRING) == null;
            }
        };

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("species").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "scientific_name")
                .withAttribute("B", "acronym", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("authority")
                        .withAttributeName("authority")
                        .withAttributeType("Text")
                        .withAttributeId("authority", "C")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new Attribute("cofc", Type.INTEGER) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Cell cell = context.getRow().getCell(Sheets.getIndex("D"), Row.RETURN_BLANK_AS_NULL);
                        if (cell == null) {
                            return null;
                        }
                        String rawStringValue = (String) Type.STRING.extract(cell);
                        if (rawStringValue.equals("*") || rawStringValue.equals("F")) {
                            return null;
                        }
                        return Type.INTEGER.extract(cell);
                    }
                })
                .withAttribute("E", "tolerance", "Text")
                .withAttribute("H", "common_name", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("family")
                        .withAttributeName("family")
                        .withAttributeType("Text")
                        .withAttributeId("family", "G")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("ind")
                        .withAttributeName("ind")
                        .withAttributeType("Text")
                        .withAttributeId("ind", "I")
                        .withCreateReference(true)
                        .build())
                .withAttribute("J", "hydro", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("form")
                        .withAttributeName("form")
                        .withAttributeType("Text")
                        .withAttributeId("form", "K")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("habit")
                        .withAttributeName("habit")
                        .withAttributeType("Text")
                        .withAttributeId("habit", "L")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("groupp")
                        .withAttributeName("groupp")
                        .withAttributeType("Text")
                        .withAttributeId("groupp", "M")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("shade")
                        .withAttributeName("shade")
                        .withAttributeType("Text")
                        .withAttributeId("shade", "N")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("nativity")
                        .withAttributeName("nativity")
                        .withAttributeType("Text")
                        .withAttributeId("nativity", "F")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code1")
                        .withAttributeName("code1")
                        .withAttributeType("Text")
                        .withAttributeId("code1", "O")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code2")
                        .withAttributeName("code2")
                        .withAttributeType("Text")
                        .withAttributeId("code2", "P")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code3")
                        .withAttributeName("code3")
                        .withAttributeType("Text")
                        .withAttributeId("code3", "Q")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code4")
                        .withAttributeName("code4")
                        .withAttributeType("Text")
                        .withAttributeId("code4", "R")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code5")
                        .withAttributeName("code5")
                        .withAttributeType("Text")
                        .withAttributeId("code5", "S")
                        .withCreateReference(true)
                        .build())
                .build();
        sheetProcessor.process(sheet, store);
    }

    public static void processLookupCommunitySheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new BoundsDetector() {
            @Override
            public boolean ignore(SheetContext context) {
                return false;
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                String value = Sheets.extract(row, "A", Type.STRING);
                return value != null && value.equalsIgnoreCase("code");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return Sheets.extract(context.getRow(), "A", Type.STRING) == null;
            }
        };

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("class_code_mod_natureserve").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "code")
                .withAttribute("B", "veg_class", "Text")
                .withAttribute("C", "veg_group", "Text")
                .build();
        sheetProcessor.process(sheet, store);
    }

    public static void processLookupMidPointSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new BoundsDetector() {
            @Override
            public boolean ignore(SheetContext context) {
                return false;
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                String value = Sheets.extract(row, "A", Type.STRING);
                return value != null && value.equalsIgnoreCase("cover code");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return Sheets.extract(context.getRow(), "A", Type.STRING) == null;
            }
        };

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("cover_midpoint_lookup").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "cover_code")
                .withAttribute("B", "midpoint", "Double")
                .build();
        sheetProcessor.process(sheet, store);
    }
}
