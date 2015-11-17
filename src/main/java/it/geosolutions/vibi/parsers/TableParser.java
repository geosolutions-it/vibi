package it.geosolutions.vibi.parsers;

import it.geosolutions.vibi.detectors.DataEndDetector;
import it.geosolutions.vibi.detectors.DataStartDetector;
import it.geosolutions.vibi.meta.TableMeta;
import it.geosolutions.vibi.persistence.Persister;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public final class TableParser {

    private final Sheet containingSheet;
    private final List<TableMeta> tablesMeta;
    private final DataStartDetector dataStartDetector;
    private final DataEndDetector dataEndDetector;

    public TableParser(Sheet containingSheet, List<TableMeta> tablesMeta,
                       DataStartDetector dataStartDetector, DataEndDetector dataEndDetector) {
        this.containingSheet = containingSheet;
        this.tablesMeta = tablesMeta;
        this.dataStartDetector = dataStartDetector;
        this.dataEndDetector = dataEndDetector;
    }

    public void persistTable(Persister persister) {
        Row row = findDataStartRow();
        boolean moreRows = true;
        do {
            if (row == null || dataEndDetector.isEnd(containingSheet, row)) {
                moreRows = false;
            } else {
                processRow(persister, row);
                row = containingSheet.getRow(row.getRowNum() + 1);
            }
        } while (moreRows);
    }

    private Row findDataStartRow() {
        for (Row row : containingSheet) {
            if (dataStartDetector.isStart(containingSheet, row)) {
                return row;
            }
        }
        return null;
    }

    private void processRow(Persister persister, Row row) {
        for (TableMeta tableMeta : tablesMeta) {
            tableMeta.persistRow(persister, row);
        }
    }
}
