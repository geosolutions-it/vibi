package it.geosolutions.vibi.mapper.builders;

import it.geosolutions.vibi.mapper.detectors.SimpleBoundsDetector;
import it.geosolutions.vibi.mapper.detectors.SimpleBoundsDetector.ExpectedMatch;
import it.geosolutions.vibi.mapper.utils.Sheets;

import java.util.regex.Pattern;

public class SimpleBoundsDetectorBuilder {

    private ExpectedMatch ignoreExpectedMatch;
    private ExpectedMatch dataStartExpectedMatch;
    private ExpectedMatch dataEndExpectedMatch;

    public SimpleBoundsDetectorBuilder withIgnoreExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.ignoreExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetectorBuilder withDataStartExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.dataStartExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetectorBuilder withDataEndExpectedMatch(int relativeRowIndex, String column, String pattern) {
        this.dataEndExpectedMatch = new ExpectedMatch(relativeRowIndex, Sheets.getIndex(column), Pattern.compile(pattern));
        return this;
    }

    public SimpleBoundsDetector build() {
        return new SimpleBoundsDetector(ignoreExpectedMatch, dataStartExpectedMatch, dataEndExpectedMatch);
    }
}
