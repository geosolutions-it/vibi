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

public class PlotService {

    public static void processPlotInfoSheet(Sheet sheet, DataStore store) {

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

        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("plot").withBoundsDetector(boundsDetector)
                .withAttribute(new Attribute("plot_no", Type.STRING, true) {
                    @Override
                    public Object getValue(SheetContext context) {
                        return Sheets.getValue(context, Sheets.getIndex("A"), Type.INTEGER).toString();
                    }
                })
                .withAttribute("B", "project_name", "Text")
                .withAttribute("C", "plot_name", "Text")
                .withAttribute("D", "plot_label", "Text")
                .withAttribute("E", "monitoring_event", "Text")
                .withAttribute("F", "datetimer", "Date")
                .withAttribute("G", "party", "Text")
                .withAttribute("H", "plot_not_sampled", "Text")
                .withAttribute("I", "commentplot_not_sampled", "Text")
                .withAttribute("J", "sampling_quality", "Text")
                .withAttribute("K", "tax_accuracy_vascular", "Text")
                .withAttribute("L", "tax_accuracy_bryophytes", "Text")
                .withAttribute("M", "tax_accuracy_lichens", "Text")
                .withAttribute("N", "authority", "Text")
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
                .withAttribute("AC", "estimate_of_perunvegetated_ow_entire_site", "Double")
                .withAttribute("AD", "estimate_per_invasives_entire_site", "Double")
                .withAttribute("AE", "centerline", "Double")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("plant_comm_code")
                        .withAttributeName("oneo_plant")
                        .withAttributeType("Text")
                        .withAttributeId("code", "AF")
                        .withCreateReference(true)
                        .build())
                .withAttribute("AG", "oneo_text", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("veg_class")
                        .withAttributeName("vegclass")
                        .withAttributeType("Text")
                        .withAttributeId("veg_class", "AH")
                        .withCreateReference(true)
                        .build())
                .withAttribute("AI", "vegsubclass", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("plant_comm_code")
                        .withAttributeName("twoo_plant")
                        .withAttributeType("Text")
                        .withAttributeId("code", "AJ")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_class")
                        .withAttributeName("hgmclass")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_class", "AK")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_subclass")
                        .withAttributeName("hgmsubclass")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_subclass", "AL")
                        .withCreateReference(true)
                        .build())
                .withAttribute("AM", "twoo_hgm", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hgm_group")
                        .withAttributeName("hgmgroup")
                        .withAttributeType("Text")
                        .withAttributeId("hgm_group", "AN")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("class_code_mod_natureserve")
                        .withAttributeName("oneo_class_code_mod_natureserve")
                        .withAttributeType("Text")
                        .withAttributeId("code", "AO")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("veg_class")
                        .withAttributeName("veg_class_wetlands_only")
                        .withAttributeType("Text")
                        .withAttributeId("veg_class", "AQ")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("landform_type")
                        .withAttributeName("landform_type")
                        .withAttributeType("Text")
                        .withAttributeId("landform_type", "AR")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("homogeneity")
                        .withAttributeName("homogeneity")
                        .withAttributeType("Text")
                        .withAttributeId("homogeneity", "AS")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("stand_size")
                        .withAttributeName("stand_size")
                        .withAttributeType("Text")
                        .withAttributeId("stand_size", "AT")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("drainage")
                        .withAttributeName("drainage")
                        .withAttributeType("Text")
                        .withAttributeId("drainage", "AU")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("salinity")
                        .withAttributeName("salinity")
                        .withAttributeType("Text")
                        .withAttributeId("salinity", "AV")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("hydrologic_regime")
                        .withAttributeName("hydrologic_regime")
                        .withAttributeType("Text")
                        .withAttributeId("hydrologic_regime", "AW")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("oneo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "AX")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("oneo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "AY")
                        .withCreateReference(true)
                        .build())
                .withAttribute("AZ", "oneo_disturbance_years_ago", "Integer")
                .withAttribute("BA", "oneo_distubance_per_of_plot", "Integer")
                .withAttribute("BB", "oneo_disturbance_description", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("twoo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "BC")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("twoo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "BD")
                        .withCreateReference(true)
                        .build())
                .withAttribute("BE", "twoo_disturbance_years_ago", "Integer")
                .withAttribute("BF", "twoo_distubance_per_of_plot", "Integer")
                .withAttribute("BG", "twoo_disturbance_description", "Text")
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("threeo_disturbance_type")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_type", "BH")
                        .withCreateReference(true)
                        .build())
                .withAttribute(new ReferenceAttributeBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("threeo_disturbance_severity")
                        .withAttributeType("Text")
                        .withAttributeId("disturbance_severity", "BI")
                        .withCreateReference(true)
                        .build())
                .withAttribute("BJ", "threeo_disturbance_years_ago", "Integer")
                .withAttribute("BK", "threeo_distubance_per_of_plot", "Integer")
                .withAttribute("BL", "threeo_disturbance_description", "Text")
                .build();
        sheetProcessor.process(sheet, store);
    }
}
