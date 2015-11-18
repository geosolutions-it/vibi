package it.geosolutions.vibi.detectors;

import it.geosolutions.vibi.utils.Sheets;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.regex.Pattern;

public class SimpleBoundsDetector implements BoundsDetector {

    public static class ExpectedMatch {

        private final int relativeRowIndex;
        private final int columnIndex;
        private final Pattern pattern;

        public ExpectedMatch(int relativeRowIndex, int columnIndex, Pattern pattern) {
            this.relativeRowIndex = relativeRowIndex;
            this.columnIndex = columnIndex;
            this.pattern = pattern;
        }

        public boolean checkMatch(Row currentRow) {
            int rowToMatchIndex = currentRow.getRowNum() + relativeRowIndex;
            if (rowToMatchIndex < 0) {
                return false;
            }
            Row rowToMatch = currentRow.getSheet().getRow(rowToMatchIndex);
            String cellValue = Sheets.cellToString(rowToMatch.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK));
            return pattern.matcher(cellValue).matches();
        }
    }

    protected final List<ExpectedMatch> startExpectedMatches;
    protected final List<ExpectedMatch> endExpectedMatches;

    public SimpleBoundsDetector(List<ExpectedMatch> startExpectedMatches, List<ExpectedMatch> endExpectedMatches) {
        this.startExpectedMatches = startExpectedMatches;
        this.endExpectedMatches = endExpectedMatches;
    }

    @Override
    public boolean isDataStart(Row row) {
        for (ExpectedMatch expectedMatch : startExpectedMatches) {
            if (!expectedMatch.checkMatch(row)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isDataEnd(Row row) {
        for (ExpectedMatch expectedMatch : endExpectedMatches) {
            if (!expectedMatch.checkMatch(row)) {
                return false;
            }
        }
        return true;
    }
}
