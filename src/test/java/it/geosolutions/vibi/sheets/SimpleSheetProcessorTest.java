package it.geosolutions.vibi.sheets;

import it.geosolutions.vibi.detectors.BoundsDetector;
import it.geosolutions.vibi.detectors.SimpleBoundsDetectorBuilder;
import it.geosolutions.vibi.extractors.CellValueExtractor;
import it.geosolutions.vibi.extractors.SimpleCellValueExtractor;
import it.geosolutions.vibi.store.MemoryStore;
import it.geosolutions.vibi.store.Store;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Tuple;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SimpleSheetProcessorTest {

    @Test
    public void processEnterPlotInfoSheet() {
        new Sheets.WorkBook("src/test/resources/2010_PCAP_DATA_NC.xls") {

            @Override
            public void doWork(HSSFWorkbook workBook) {
                Sheet sheet = workBook.getSheet("ENTER PLOT INFO");
                assertThat(sheet, notNullValue());
                Store store = new MemoryStore();
                CellValueExtractor cellValueExtractor = new SimpleCellValueExtractor();
                BoundsDetector boundsDetector = new SimpleBoundsDetectorBuilder()
                        .withStartExpectedMatch(-1, "A", "Plot No.").withEndExpectedMatch(0, "A", "").build();
                SheetProcessor sheetProcessor = new SimpleSheetProcessorBuilder()
                        .withTable("plot_info").withBoundsDetector(boundsDetector)
                        .withSimpleAttributeProducer("plot_info", true, "plot_no", "A", cellValueExtractor)
                        .withSimpleAttributeProducer("plot_info", true, "project_name", "B", cellValueExtractor)
                        .withSimpleAttributeProducer("plot_info", false, "plot_name", "C", cellValueExtractor)
                        .withSimpleAttributeProducer("plot_info", false, "date", "F", cellValueExtractor)
                        .build();
                sheetProcessor.process(sheet, store);
                List<Map<String, Object>> result = store.find("plot_info", Collections.<String, Object>emptyMap());
                assertThat(result.size(), is(15));
                result = store.find("plot_info", Tuple.tuplesToMap(Tuple.tuple("plot_no", (Object) 1065.0)));
                assertThat(result.size(), is(1));
                assertThat(result.get(0).get("plot_no"), notNullValue());
                assertThat((double)result.get(0).get("plot_no"), is(equalTo(1065.0)));
                assertThat(result.get(0).get("project_name"), notNullValue());
                assertThat((String)result.get(0).get("project_name"), is(equalTo("01NC2010")));
                assertThat(result.get(0).get("plot_name"), notNullValue());
                assertThat((String)result.get(0).get("plot_name"), is(equalTo("Horse Trailer Swamp")));
                assertThat(result.get(0).get("date"), notNullValue());
            }
        };
    }
}
