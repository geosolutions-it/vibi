package it.geosolutions.vibi.parsers;

import it.geosolutions.vibi.converters.BasicConverter;
import it.geosolutions.vibi.detectors.DetectorByMatches;
import it.geosolutions.vibi.detectors.DetectorByMatchesBuilder;
import it.geosolutions.vibi.meta.TableMeta;
import it.geosolutions.vibi.meta.TableMetaBuilder;
import it.geosolutions.vibi.persistence.MemoryPersister;
import it.geosolutions.vibi.persistence.Persister;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public final class TableParserTest {

    @Test
    public void parseEnterPlotInfo() throws Exception {
        try (InputStream input = new FileInputStream("src/test/resources/2010_PCAP_DATA_NC.xls");
             HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(input))) {
            Sheet sheet = workBook.getSheet("ENTER PLOT INFO");
            TableMeta tableMeta = new TableMetaBuilder()
                    .withTableName("enter_plot_info")
                    .withAttributeMeta("A", "plot_no", BasicConverter.BASIC_CONVERTER)
                    .withAttributeMeta("B", "project_name", BasicConverter.BASIC_CONVERTER)
                    .withAttributeMeta("C", "plot_name", BasicConverter.BASIC_CONVERTER)
                    .build();
            DetectorByMatches detectorByMatches = new DetectorByMatchesBuilder()
                    .withStartExpectedMatch(-1, "A", "Plot No.")
                    .withEndExpectedMatch(0, "A", "")
                    .build();
            TableParser tableParser = new TableParserBuilder()
                    .withContainingSheet(sheet)
                    .withTableMeta(tableMeta)
                    .withDataStartDetector(detectorByMatches)
                    .withDataEndDetector(detectorByMatches)
                    .build();
            Persister persister = new MemoryPersister();
            tableParser.persistTable(persister);
        }
    }
}
