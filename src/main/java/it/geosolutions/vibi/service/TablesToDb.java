package it.geosolutions.vibi.service;

import it.geosolutions.vibi.detectors.DetectorByMatches;
import it.geosolutions.vibi.detectors.DetectorByMatchesBuilder;
import it.geosolutions.vibi.meta.AttributeMeta;
import it.geosolutions.vibi.meta.TableMeta;
import it.geosolutions.vibi.meta.TableMetaBuilder;
import it.geosolutions.vibi.parsers.TableParser;
import it.geosolutions.vibi.parsers.TableParserBuilder;
import it.geosolutions.vibi.persistence.Persister;
import it.geosolutions.vibi.persistence.SpringPersister;
import it.geosolutions.vibi.utils.Sheets;
import it.geosolutions.vibi.utils.Validations;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public final class TablesToDb {

    public static void main(String[] args) throws Exception {
        Validations.checkCondition(args.length == 4, "Usage: <database url> <database user> <database password> <work book path>");
        Class.forName("org.postgresql.Driver");
        final Persister persister = new SpringPersister(args[0], args[1], args[2]);
        new Sheets.WorkOnSheet(args[3], "ENTER PLOT INFO") {

            @Override
            public void doWork(Sheet sheet) {
                List<AttributeMeta> attributesMeta = AttributeMeta.extractAttributesMeta(sheet, 2, Sheets.getIndex("A"), Sheets.getIndex("G"));
                TableMeta tableMeta = new TableMetaBuilder().withTableName("plot").withAttributesMeta(attributesMeta).build();
                DetectorByMatches detectorByMatches = new DetectorByMatchesBuilder().withStartExpectedMatch(-1, "A", "Plot No.")
                        .withEndExpectedMatch(0, "A", "").build();
                TableParser tableParser = new TableParserBuilder().withTableMeta(tableMeta).withContainingSheet(sheet)
                        .withDataStartDetector(detectorByMatches).withDataEndDetector(detectorByMatches).build();
                tableParser.persistTable(persister);
            }
        };
    }
}
