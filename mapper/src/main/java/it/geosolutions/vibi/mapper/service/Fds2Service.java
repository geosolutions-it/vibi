package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.exceptions.VibiException;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.ArrayList;
import java.util.List;

class Fds2Service {


    static void processReducedFds2Sheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new BoundsDetector() {
            @Override
            public boolean ignore(SheetContext context) {
                String speciesValue = ((String) Type.STRING.extract(context.getRow().getCell(Sheets.getIndex("J")))).toLowerCase();
                return speciesValue == null
                        || speciesValue.contains("0.0");
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                if (row == null) {
                    return false;
                }
                String speciesValue = Sheets.cellToString(row.getCell(Sheets.getIndex("J"))).toLowerCase();
                return speciesValue.contains("species");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return false;
            }
        };

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("woody_importance_value").withBoundsDetector(boundsDetector)
                .withAttribute(new Attribute("fid", Type.STRING, true) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Object plotNo = Type.INTEGER.extract(context.getRow().getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL));
                        Object species = Type.STRING.extract(context.getRow().getCell(Sheets.getIndex("J"), Row.RETURN_BLANK_AS_NULL));
                        if (plotNo == null || species == null) {
                            throw new VibiException("Error extract data from row '%d' fo sheet '%s', site and species cannot be NULL.",
                                    context.getRow().getRowNum(), context.getSheet().getSheetName());
                        }
                        return plotNo + "-" + species;
                    }
                })
                .withAttribute(new ReferenceAttributeBuilder().withTableName("plot")
                        .withAttributeName("plot_no")
                        .withAttributeType("Integer")
                        .withAttributeId("plot_no", "A")
                        .withUpdateReference(false)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder().withTableName("species")
                        .withAttributeName("species")
                        .withAttributeType("Text")
                        .withAttributeId("scientific_name", "J")
                        .withUpdateReference(false)
                        .build())
                .withAttribute(new Attribute("subcanopy_iv_partial", Type.DOUBLE) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Cell cell = context.getRow().getCell(Sheets.getIndex("EU"), Row.RETURN_BLANK_AS_NULL);
                        if (((String) Type.STRING.extract(cell)).toLowerCase().contains("none")) {
                            return null;
                        }
                        return Type.DOUBLE.extract(cell);
                    }
                })
                .withAttribute(new Attribute("subcanopy_iv_shade", Type.DOUBLE) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Cell cell = context.getRow().getCell(Sheets.getIndex("EV"), Row.RETURN_BLANK_AS_NULL);
                        if (((String) Type.STRING.extract(cell)).toLowerCase().contains("none")) {
                            return null;
                        }
                        return Type.DOUBLE.extract(cell);
                    }
                })
                .withAttribute(new Attribute("canopy_iv", Type.DOUBLE) {
                    @Override
                    public Object getValue(SheetContext context) {
                        Cell cell = context.getRow().getCell(Sheets.getIndex("EW"), Row.RETURN_BLANK_AS_NULL);
                        if(((String)Type.STRING.extract(cell)).toLowerCase().contains("none")) {
                            return null;
                        }
                        return Type.DOUBLE.extract(cell);
                    }
                })
                .build();

        sheetProcessor.process(sheet, store);
    }
}
