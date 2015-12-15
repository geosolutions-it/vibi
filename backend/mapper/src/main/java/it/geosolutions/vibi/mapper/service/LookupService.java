package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.ReferenceAttribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.builders.SimpleBoundsDetectorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;

class LookupService {

    static void processLookupSpeciesSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new SimpleBoundsDetectorBuilder()
                .withDataStartExpectedMatch(-1, "A", "SCIENTIFIC NAME")
                .withDataEndExpectedMatch(0, "A", "").build();

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("species").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "scientific_name")
                .withAttribute("B", "acronym", "Text")
                .withAttribute("C", "authority", "Text")
                /*.withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("authority")
                        .withAttributeName("authority")
                        .withAttributeType("Text")
                        .withAttributeId("authority", "C")
                        .build())*/
                .withAttribute(new Attribute("cofc", Type.INTEGER) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Cell cell = context.getRow().getCell(Sheets.getIndex("D"), Row.RETURN_BLANK_AS_NULL);
                        if (cell == null) {
                            return null;
                        }
                        if (Type.STRING.extract(cell).equals("*")) {
                            return null;
                        }
                        return Type.INTEGER.extract(cell);
                    }
                })
                .withAttribute("E", "tolerance", "Text")
                .withAttribute("H", "common_name", "Text")
                .withAttribute("G", "family", "Text")
                .withAttribute("I", "ind", "Text")
                /*.withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("family")
                        .withAttributeName("family")
                        .withAttributeType("Text")
                        .withAttributeId("family", "G")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("ind")
                        .withAttributeName("ind")
                        .withAttributeType("Text")
                        .withAttributeId("ind", "I")
                        .build())*/
                .withAttribute("J", "hydro", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("form")
                        .withAttributeName("form")
                        .withAttributeType("Text")
                        .withAttributeId("form", "K")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("habit")
                        .withAttributeName("habit")
                        .withAttributeType("Text")
                        .withAttributeId("habit", "L")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("groupp")
                        .withAttributeName("groupp")
                        .withAttributeType("Text")
                        .withAttributeId("groupp", "M")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("shade")
                        .withAttributeName("shade")
                        .withAttributeType("Text")
                        .withAttributeId("shade", "N")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("nativity")
                        .withAttributeName("nativity")
                        .withAttributeType("Text")
                        .withAttributeId("nativity", "F")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code1")
                        .withAttributeName("code1")
                        .withAttributeType("Text")
                        .withAttributeId("code1", "O")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code2")
                        .withAttributeName("code2")
                        .withAttributeType("Text")
                        .withAttributeId("code2", "P")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code3")
                        .withAttributeName("code3")
                        .withAttributeType("Text")
                        .withAttributeId("code3", "Q")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code4")
                        .withAttributeName("code4")
                        .withAttributeType("Text")
                        .withAttributeId("code4", "R")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("code5")
                        .withAttributeName("code5")
                        .withAttributeType("Text")
                        .withAttributeId("code5", "S")
                        .build())
                .build();
        sheetProcessor.process(sheet, store);
    }

    static void processLookupCommunitySheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new SimpleBoundsDetectorBuilder()
                .withDataStartExpectedMatch(-1, "A", "code")
                .withDataEndExpectedMatch(0, "A", "").build();

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("class_code_mod_natureserve").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "code")
                .withAttribute("B", "veg_class", "Text")
                .withAttribute("C", "veg_group", "Text")
                .build();
        sheetProcessor.process(sheet, store);
    }

    static void processLookupMidPointSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new SimpleBoundsDetectorBuilder()
                .withDataStartExpectedMatch(-1, "A", "cover code")
                .withDataEndExpectedMatch(0, "A", "").build();

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("cover_midpoint_lookup").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "cover_code")
                .withAttribute("B", "midpoint", "Double")
                .build();
        sheetProcessor.process(sheet, store);
    }
}
