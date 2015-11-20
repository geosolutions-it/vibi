package it.geosolutions.vibi.mapper.detectors;

import it.geosolutions.vibi.mapper.utils.Sheets;
import org.apache.poi.ss.usermodel.Row;

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

    private final ExpectedMatch headerExpectedMatch;
    private final ExpectedMatch startExpectedMatch;
    private final ExpectedMatch endExpectedMatch;

    public SimpleBoundsDetector(ExpectedMatch headerExpectedMatch, ExpectedMatch startExpectedMatch, ExpectedMatch endExpectedMatch) {
        this.headerExpectedMatch = headerExpectedMatch;
        this.startExpectedMatch = startExpectedMatch;
        this.endExpectedMatch = endExpectedMatch;
    }

    @Override
    public boolean isHeader(Row row) {
        return headerExpectedMatch != null && headerExpectedMatch.checkMatch(row);
    }

    @Override
    public boolean isDataStart(Row row) {
        return startExpectedMatch.checkMatch(row);
    }

    @Override
    public boolean isDataEnd(Row row) {
        return endExpectedMatch.checkMatch(row);
    }
}
