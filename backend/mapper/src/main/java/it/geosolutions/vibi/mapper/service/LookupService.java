package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;

public class LookupService {

    private static BoundsDetector commonBoundsDetector = new BoundsDetector() {
        @Override
        public boolean ignore(SheetContext context) {
            return context.getRow().getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL) == null;
        }

        @Override
        public boolean dataStart(SheetContext context) {
            Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
            String value = Sheets.extract(row, "A", Type.STRING);
            return value != null && value.equalsIgnoreCase("code");
        }

        @Override
        public boolean dataEnd(SheetContext context) {
            return false;
        }
    };

    public static void processLookupNatureSOPEACommunitySheet(Sheet sheet, DataStore store) {

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("class_code_mod_natureserve").withBoundsDetector(commonBoundsDetector)
                .withAttributeId("A", "code")
                .withAttribute("c", "community_class", "Text")
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
