package it.geosolutions.vibi.detectors;

import it.geosolutions.vibi.utils.Sheets;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.regex.Pattern;

public class DetectorByMatches implements DataStartDetector, DataEndDetector {

    public static class ExpectedMatch {

        private final int relativeRowIndex;
        private final int columnIndex;
        private final Pattern pattern;

        public ExpectedMatch(int relativeRowIndex, int columnIndex, Pattern pattern) {
            this.relativeRowIndex = relativeRowIndex;
            this.columnIndex = columnIndex;
            this.pattern = pattern;
        }

        public boolean checkMatch(Sheet sheet, Row currentRow) {
            int rowToMatchIndex = currentRow.getRowNum() + relativeRowIndex;
            if (rowToMatchIndex < 0) {
                return false;
            }
            Row rowToMatch = sheet.getRow(rowToMatchIndex);
            String cellValue = Sheets.cellToString(rowToMatch.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK));
            return pattern.matcher(cellValue).matches();
        }
    }

    private final List<ExpectedMatch> startExpectedMatches;
    private final List<ExpectedMatch> endExpectedMatches;

    public DetectorByMatches(List<ExpectedMatch> startExpectedMatches, List<ExpectedMatch> endExpectedMatches) {
        this.startExpectedMatches = startExpectedMatches;
        this.endExpectedMatches = endExpectedMatches;
    }

    @Override
    public boolean isStart(Sheet sheet, Row row) {
        for (ExpectedMatch expectedMatch : startExpectedMatches) {
            if (!expectedMatch.checkMatch(sheet, row)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEnd(Sheet sheet, Row row) {
        for (ExpectedMatch expectedMatch : endExpectedMatches) {
            if (!expectedMatch.checkMatch(sheet, row)) {
                return false;
            }
        }
        return true;
    }
}
