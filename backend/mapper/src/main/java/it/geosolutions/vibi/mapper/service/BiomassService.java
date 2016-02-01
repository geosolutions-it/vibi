package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetContextUpdater;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.Date;

public class BiomassService {

    private static final SimpleFeatureType PLOT_TYPE = VibiService.createFeatureType("plot", "");

    public static void processBiomassSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new BoundsDetector() {

            @Override
            public boolean ignore(SheetContext context) {
                Cell moduleIdCell = context.getRow().getCell(Sheets.getIndex("F"), Row.RETURN_BLANK_AS_NULL);
                Cell cornerIdCell = context.getRow().getCell(Sheets.getIndex("H"), Row.RETURN_BLANK_AS_NULL);
                return moduleIdCell == null || cornerIdCell == null;
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row previousRow = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                if (previousRow == null) {
                    return false;
                }
                String value = Sheets.extract(previousRow, "A", Type.STRING);
                return value != null && value.equalsIgnoreCase("site");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return false;
            }
        };

        SheetContextUpdater contextUpdater = new SheetContextUpdater();
        contextUpdater.addCallback(new SheetContextUpdater.Callback() {

            @Override
            public void update(SheetContext context) {
                Cell siteCell = context.getRow().getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
                if (siteCell != null) {
                    context.putValue("BIOMASS_PLOT_NO", Type.INTEGER.extract(siteCell));
                }
                Cell dateCell = context.getRow().getCell(Sheets.getIndex("D"), Row.RETURN_BLANK_AS_NULL);
                if (dateCell != null) {
                    context.putValue("BIOMASS_DATE_TIME", Type.DATE.extract(dateCell));
                }
            }
        });

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("biomass_raw")
                .withBoundsDetector(boundsDetector)
                .withContextUpdater(contextUpdater)
                .withAttribute(new Attribute("fid", Type.STRING, true) {
                    @Override
                    public Object getValue(SheetContext context) {
                        return String.format("%d-%d-%d", getPlotNo(context),
                                Sheets.extract(context.getRow(), "F", Type.INTEGER),
                                Sheets.extract(context.getRow(), "H", Type.INTEGER));
                    }
                })
                .withAttribute(new Attribute("plot_no", Type.INTEGER) {
                    @Override
                    public Object getValue(SheetContext context) {
                        int plotNo = getPlotNo(context);
                        VibiService.testForeignKeyExists(context.getStore(), context.getRow(), PLOT_TYPE, plotNo);
                        return plotNo;
                    }
                })
                .withAttribute(new Attribute("date_time", Type.DATE) {
                    @Override
                    public Object getValue(SheetContext context) {
                        return getDateTime(context);
                    }
                })
                .withAttribute("E", "biomass_collected", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("module")
                        .withAttributeName("module_id")
                        .withAttributeType("Integer")
                        .withAttributeId("module_id", "F")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("corner")
                        .withAttributeName("corner")
                        .withAttributeType("Integer")
                        .withAttributeId("corner", "H")
                        .withCreateReference(true)
                        .build())
                .withAttribute("I", "sample_id", "Integer")
                .withAttribute("J", "area_sampled", "Numeric")
                .withAttribute("K", "weight_with_bag", "Numeric")
                .withAttribute("L", "bag_weight", "Numeric")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("biomass_accuracy")
                        .withAttributeName("actual_or_derived")
                        .withAttributeType("Text")
                        .withAttributeId("biomass_accuracy", "M")
                        .withCreateReference(true)
                        .build())
                .build();

        sheetProcessor.process(sheet, store);
    }

    private static Date getDateTime(SheetContext context) {
        Object dateTime = context.getValue("BIOMASS_DATE_TIME");
        if (dateTime == null) {
            throw new VibiException("Date time cannot be found in the context of row '%d' of spreadsheet '%s'.",
                    context.getRow().getRowNum() + 1, context.getSheet().getSheetName());
        }
        return (Date) dateTime;
    }

    private static int getPlotNo(SheetContext context) {
        Object plotNo = context.getValue("BIOMASS_PLOT_NO");
        if (plotNo == null) {
            throw new VibiException("Plot number cannot be found in the context of row '%d' of spreadsheet '%s'.",
                    context.getRow().getRowNum() + 1, context.getSheet().getSheetName());
        }
        return (int) plotNo;
    }
}
