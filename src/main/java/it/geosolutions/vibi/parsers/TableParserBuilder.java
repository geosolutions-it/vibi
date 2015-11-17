package it.geosolutions.vibi.parsers;

import it.geosolutions.vibi.detectors.DataEndDetector;
import it.geosolutions.vibi.detectors.DataStartDetector;
import it.geosolutions.vibi.meta.TableMeta;
import it.geosolutions.vibi.utils.Validations;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public final class TableParserBuilder {

    private Sheet containingSheet;
    private List<TableMeta> tablesMeta = new ArrayList<>();
    private DataStartDetector dataStartDetector;
    private DataEndDetector dataEndDetector;

    public TableParserBuilder withContainingSheet(Sheet containingSheet) {
        this.containingSheet = containingSheet;
        return this;
    }

    public TableParserBuilder withTableMeta(TableMeta tableMeta) {
        tablesMeta.add(tableMeta);
        return this;
    }

    public TableParserBuilder withDataStartDetector(DataStartDetector dataStartDetector) {
        this.dataStartDetector = dataStartDetector;
        return this;
    }

    public TableParserBuilder withDataEndDetector(DataEndDetector dataEndDetector) {
        this.dataEndDetector = dataEndDetector;
        return this;
    }

    public TableParser build() {
        Validations.checkNotNull(containingSheet, "Containing sheet is NULL.");
        Validations.checkNotNull(dataStartDetector, "Data start decoder is NULL.");
        Validations.checkNotNull(dataEndDetector, "Data end decoder is NULL.");
        return new TableParser(containingSheet, tablesMeta, dataStartDetector, dataEndDetector);
    }
}
