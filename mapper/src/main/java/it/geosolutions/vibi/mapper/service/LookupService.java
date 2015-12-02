package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.builders.SimpleBoundsDetectorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
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
                //.withAttribute("D", "cofc", "Integer")
                .withAttribute("G", "family", "Text")
                .withAttribute("H", "common_name", "Text")
                .withAttribute("K", "form", "Text")
                .withAttribute("L", "habit", "Text")
                .withAttribute("N", "shade", "Text")
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
