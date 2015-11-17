package it.geosolutions.vibi.meta;

import it.geosolutions.vibi.utils.Sheets;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class AttributeMetaTest {

    @Test
    public void extractAttributesMetaFromEnterPlotInfo() throws Exception {
        try (InputStream input = new FileInputStream("src/test/resources/2010_PCAP_DATA_NC.xls");
             HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(input))) {
            Sheet sheet = workBook.getSheet("ENTER PLOT INFO");
            List<AttributeMeta> attributesMeta = AttributeMeta.extractAttributesMeta(sheet, 2, Sheets.getIndex("A"), Sheets.getIndex("BL"));
            assertThat(attributesMeta.size(), is(equalTo(64)));
        }
    }
}
