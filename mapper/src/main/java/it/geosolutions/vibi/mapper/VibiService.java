package it.geosolutions.vibi.mapper;

import it.geosolutions.vibi.mapper.attributes.ForeignKeyBuilder;
import it.geosolutions.vibi.mapper.detectors.BoundsDetector;
import it.geosolutions.vibi.mapper.detectors.SimpleBoundsDetectorBuilder;
import it.geosolutions.vibi.mapper.sheets.SheetProcessor;
import it.geosolutions.vibi.mapper.sheets.SheetProcessorBuilder;
import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.geotools.data.DataStore;

import java.io.File;

public final class VibiService {

    private VibiService() {
    }

    public static void submit(File woorkBookFile, final DataStore store) {
        submit(woorkBookFile.getPath(), store);
    }

    public static void submit(String woorkBookPath, final DataStore store) {
        new Sheets.WorkBook(woorkBookPath) {

            @Override
            public void doWork(HSSFWorkbook workBook) {
                Sheet sheet = workBook.getSheet("ENTER PLOT INFO");
                processEnterPlotInfoSheet(sheet, store);
            }
        };
    }

    private static void processEnterPlotInfoSheet(Sheet sheet, DataStore store) {

        BoundsDetector boundsDetector = new SimpleBoundsDetectorBuilder()
                .withStartExpectedMatch(-1, "A", "Plot No.")
                .withEndExpectedMatch(0, "A", "").build();
        SheetProcessor sheetProcessor = new SheetProcessorBuilder()
                .withTable("plot").withBoundsDetector(boundsDetector)
                .withAttributeId("A", "plot_no")
                .withAttribute("B", "project_name", "String")
                .withAttribute("C", "plot_name", "String")
                .withAttribute("D", "plot_label", "String")
                .withAttribute("E", "monitoring_event", "String")
                .withAttribute("F", "datetimer", "Date")
                .withAttribute("G", "party", "String")
                .withAttribute("H", "plot_not_sampled", "String")
                .withAttribute("I", "commentplot_not_sampled", "String")
                .withAttribute("J", "sampling_quality", "String")
                .withAttribute("K", "tax_accuracy_vascular", "String")
                .withAttribute("L", "tax_accuracy_bryophytes", "String")
                .withAttribute("M", "tax_accuracy_lichens", "String")
                .withAttribute("N", "authority", "String")
                .withAttribute("O", "state", "String")
                .withAttribute("P", "county", "String")
                .withAttribute("Q", "quadrangle", "String")
                .withAttribute("R", "local_place_name", "String")
                .withAttribute("S", "landowner", "String")
                .withAttribute("T", "xaxis_bearing_of_plot", "Integer")
                .withAttribute("U", "enter_gps_location_in_plot", "String")
                .withAttribute("V", "latitude", "Double")
                .withAttribute("W", "longitude", "Double")
                .withAttribute("X", "total_modules", "Integer")
                .withAttribute("Y", "intensive_modules", "Integer")
                .withAttribute("Z", "plot_configuration", "String")
                .withAttribute("AA", "plot_size_for_cover_data_area_ha", "Double")
                .withAttribute("AB", "estimate_of_per_open_water_entire_site", "Double")
                .withAttribute("AC", "estimate_of_perunvegetated_ow_entire_site", "Double")
                .withAttribute("AD", "estimate_per_invasives_entire_site", "Double")
                .withAttribute("AE", "centerline", "Double")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("plant_comm_code")
                        .withAttributeName("oneo_plant")
                        .withAttributeType("String")
                        .withAttributeId("code", "AF")
                        .build())
                .withAttribute("AG", "oneo_text", "String")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("veg_class")
                        .withAttributeName("vegclass")
                        .withAttributeType("String")
                        .withAttributeId("veg_class", "AH")
                        .build())
                .withAttribute("AI", "vegsubclass", "String")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("plant_comm_code")
                        .withAttributeName("twoo_plant")
                        .withAttributeType("String")
                        .withAttributeId("code", "AJ")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("hgm_class")
                        .withAttributeName("hgmclass")
                        .withAttributeType("String")
                        .withAttributeId("hgm_class", "AK")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("hgm_subclass")
                        .withAttributeName("hgmsubclass")
                        .withAttributeType("String")
                        .withAttributeId("hgm_subclass", "AL")
                        .build())
                .withAttribute("AM", "twoo_hgm", "String")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("hgm_group")
                        .withAttributeName("hgmgroup")
                        .withAttributeType("String")
                        .withAttributeId("hgm_group", "AN")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("class_code_mod_natureserve")
                        .withAttributeName("oneo_class_code_mod_natureserve")
                        .withAttributeType("String")
                        .withAttributeId("code", "AO")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("veg_class")
                        .withAttributeName("veg_class_wetlands_only")
                        .withAttributeType("String")
                        .withAttributeId("veg_class", "AQ")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("landform_type")
                        .withAttributeName("landform_type")
                        .withAttributeType("String")
                        .withAttributeId("landform_type", "AR")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("homogeneity")
                        .withAttributeName("homogeneity")
                        .withAttributeType("String")
                        .withAttributeId("homogeneity", "AS")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("stand_size")
                        .withAttributeName("stand_size")
                        .withAttributeType("String")
                        .withAttributeId("stand_size", "AT")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("drainage")
                        .withAttributeName("drainage")
                        .withAttributeType("String")
                        .withAttributeId("drainage", "AU")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("salinity")
                        .withAttributeName("salinity")
                        .withAttributeType("String")
                        .withAttributeId("salinity", "AV")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("hydrologic_regime")
                        .withAttributeName("hydrologic_regime")
                        .withAttributeType("String")
                        .withAttributeId("hydrologic_regime", "AW")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("oneo_disturbance_type")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_type", "AX")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("oneo_disturbance_severity")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_severity", "AY")
                        .build())
                .withAttribute("AZ", "oneo_disturbance_years_ago", "Integer")
                .withAttribute("BA", "oneo_distubance_per_of_plot", "Integer")
                .withAttribute("BB", "oneo_disturbance_description", "String")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("twoo_disturbance_type")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_type", "BC")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("twoo_disturbance_severity")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_severity", "BD")
                        .build())
                .withAttribute("BE", "twoo_disturbance_years_ago", "Integer")
                .withAttribute("BF", "twoo_distubance_per_of_plot", "Integer")
                .withAttribute("BG", "twoo_disturbance_description", "String")
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_type")
                        .withAttributeName("threeo_disturbance_type")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_type", "BH")
                        .build())
                .withForeignKey(new ForeignKeyBuilder()
                        .withTableName("disturbance_severity")
                        .withAttributeName("threeo_disturbance_severity")
                        .withAttributeType("String")
                        .withAttributeId("disturbance_severity", "BI")
                        .build())
                .withAttribute("BJ", "threeo_disturbance_years_ago", "Integer")
                .withAttribute("BK", "threeo_distubance_per_of_plot", "Integer")
                .withAttribute("BL", "threeo_disturbance_description", "String")
                .build();
        sheetProcessor.process(sheet, store);
    }
}
