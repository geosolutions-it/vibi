package it.geosolutions.vibi.mapper.service;

import it.geosolutions.vibi.mapper.attributes.Attribute;
import it.geosolutions.vibi.mapper.attributes.Location;
import it.geosolutions.vibi.mapper.builders.ReferenceAttributeBuilder;
import it.geosolutions.vibi.mapper.builders.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.sheets.SheetContext;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.utils.Sheets;
import it.geosolutions.vibi.mapper.utils.Store;
import it.geosolutions.vibi.mapper.utils.Type;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;
import org.geotools.data.Transaction;

import java.util.Map;

public class PlotService {

    public static void processPlotInfoSheet(final Map<Object, Object> globalContext, Sheet sheet, DataStore store, Transaction transaction) {

        BoundsDetector boundsDetector = new BoundsDetector() {
            @Override
            public boolean ignore(SheetContext context) {
                return false;
            }

            @Override
            public boolean dataStart(SheetContext context) {
                Row row = context.getSheet().getRow(context.getRow().getRowNum() - 1);
                String value = Sheets.extract(row, "A", Type.STRING);
                return value != null && value.equalsIgnoreCase("plot no.");
            }

            @Override
            public boolean dataEnd(SheetContext context) {
                return Sheets.extract(context.getRow(), "A", Type.STRING) == null;
            }
        };
        SheetProcessorBuilder spBuilder = new SheetProcessorBuilder()
                .withTable("plot").withBoundsDetector(boundsDetector)
                .withAttribute(new Attribute("plot_id", Type.STRING, true) {
                    @Override
                    public Object getValue(SheetContext context) {
                        // using the current row
                        Row row = context.getRow();
                        // select the cells we need to create the primary key
                        Cell plotNoCell = row.getCell(Sheets.getIndex("A"), Row.RETURN_BLANK_AS_NULL);
                        Cell monitoringEventCell = row.getCell(Sheets.getIndex("E"), Row.RETURN_BLANK_AS_NULL);
                        // extract the values from the selected cells
                        String plotNo = plotNoCell == null ? "null" : (String) Type.STRING.extract(plotNoCell);
                        String monitoringEvent = monitoringEventCell == null ? "null" : (String) Type.STRING.extract(monitoringEventCell);
                        // build the plot sampling the key
                        String plotKey = plotNo + "_" + monitoringEvent;
                        // removing any registered events for this plot sampling
                        Store.delete(context.getStore(), context.getTransaction(), "plot", plotKey);
                        // update plots indexes stored in the global context
                        Map<String, String> plotsIndexes = VibiService.getPlotsIndex(globalContext);
                        plotsIndexes.put(plotNo, plotKey);
                        return plotKey;
                    }
                })
                .withAttribute("A", "plot_no", "Text")
                .withAttribute("B", "project_name", "Text")
                .withAttribute("C", "plot_name", "Text")
                .withAttribute("D", "project_label", "Text")
                .withAttribute("E", "monitoring_event", "Text")
                .withAttribute("F", "datetimer", "Date")
                .withAttribute("G", "party", "Text")
                .withAttribute("H", "plot_not_sampled", "Text")
                .withAttribute("I", "commentplot_not_sampled", "Text")
                .withAttribute("J", "sampling_quality", "Text")
                .withAttribute("O", "state", "Text")
                .withAttribute("P", "county", "Text")
                .withAttribute("Q", "quadrangle", "Text")
                .withAttribute("R", "local_place_name", "Text")
                .withAttribute("S", "landowner", "Text")
                .withAttribute("T", "xaxis_bearing_of_plot", "Integer")
                .withAttribute("U", "enter_gps_location_in_plot", "Text")
                .withAttribute("V", "latitude", "Double")
                .withAttribute("W", "longitude", "Double")
                .withAttribute("X", "total_modules", "Integer")
                .withAttribute("Y", "intensive_modules", "Integer")
                .withAttribute("Z", "plot_configuration", "Text")
                .withAttribute("AA", "plot_size_for_cover_data_area_ha", "Double")
                .withAttribute("AB", "estimate_of_per_open_water_entire_site", "Double")
                .withAttribute("AC", "estimate_of_per_unvegetated_ow_entire_site", "Double")
                .withAttribute("AD", "estimate_per_invasives_entire_site", "Double")
                .withAttribute("AE", "centerline", "Double")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("veg_class")
                        .withAttributeName("vegclass")
                        .withAttributeType("Text")
                        .withAttributeId("veg_class", "AH")
                        .build())
                .withAttribute("AI", "vegsubclass", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_class")
                        .withAttributeName("hgmclass")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_class", "AK")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_subclass")
                        .withAttributeName("hgmsubclass")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_subclass", "AL")
                        .build())
                .withAttribute("AM", "twoo_hgm", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_group")
                        .withAttributeName("hgmgroup")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_group", "AN")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("class_code_mod_natureserve")
                        .withAttributeName("code")
                        .withAttributeType("Text")
                        .withAttributeId("code", "AO")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("landform_type")
                        .withAttributeName("landform_type")
                        .withAttributeType("Text")
                        .withAttributeId("Landform_type", "AR")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("homogeneity")
                        .withAttributeName("homogeneity")
                        .withAttributeType("Text")
                        .withAttributeId("homogeneity", "AS")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("stand_size")
                        .withAttributeName("stand_size")
                        .withAttributeType("Text")
                        .withAttributeId("stand_size", "AT")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("drainage")
                        .withAttributeName("drainage")
                        .withAttributeType("Text")
                        .withAttributeId("drainage", "AU")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("salinity")
                        .withAttributeName("salinity")
                        .withAttributeType("Text")
                        .withAttributeId("salinity", "AV")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hydrologic_regime")
                        .withAttributeName("hydrologic_regime")
                        .withAttributeType("Text")
                        .withAttributeId("hydrologic_regime", "AW")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("oneo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "AX")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("oneo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "AY")
                        .build())
                .withAttribute("AZ", "oneo_disturbance_years_ago", "Integer")
                .withAttribute("BA", "oneo_distubance_per_of_plot", "Integer")
                .withAttribute("BB", "oneo_disturbance_description", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("twoo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "BC")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("twoo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "BD")
                        .build())
                .withAttribute("BE", "twoo_disturbance_years_ago", "Integer")
                .withAttribute("BF", "twoo_distubance_per_of_plot", "Integer")
                .withAttribute("BG", "twoo_disturbance_description", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("threeo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "BH")
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("threeo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "BI")
                        .build())
                .withAttribute("BJ", "threeo_disturbance_years_ago", "Integer")
                .withAttribute("BK", "threeo_distubance_per_of_plot", "Integer")
                .withAttribute("BL", "threeo_disturbance_description", "Text");

        if(globalContext.get(VibiService.PLOT_LOCATION) != null){
            spBuilder.withAttribute(new Attribute("location", Type.STRING) {
                    @Override
                    public Object getValue(SheetContext context) {
                        return ((Location)globalContext.get(VibiService.PLOT_LOCATION)).toString();
                    }
                });
        }
        
        SheetProcessor sheetProcessor = spBuilder.build();
        sheetProcessor.process(sheet, store, transaction);
    }
}
