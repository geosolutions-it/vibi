package it.geosolutions.vibi.mapper.detectors;

import it.geosolutions.vibi.mapper.detectors.SimpleBoundsDetector.ExpectedMatch;
import it.geosolutions.vibi.mapper.utils.Sheets;

import java.util.regex.Pattern;

public class SimpleBoundsDetectorBuilder {

    private ExpectedMatch headerExpectedMatch;
    private ExpectedMatch startExpectedMatch;
    private ExpectedMatch endExpectedMatch;

    public SimpleBoundsDetectorBuilder withHeaderExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.headerExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetectorBuilder withStartExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.startExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetectorBuilder withEndExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.endExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetector build() {
        return new SimpleBoundsDetector(headerExpectedMatch, startExpectedMatch, endExpectedMatch);
    }
}
