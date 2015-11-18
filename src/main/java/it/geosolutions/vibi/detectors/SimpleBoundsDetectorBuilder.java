package it.geosolutions.vibi.detectors;

import it.geosolutions.vibi.detectors.SimpleBoundsDetector.ExpectedMatch;
import it.geosolutions.vibi.utils.Sheets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleBoundsDetectorBuilder {

    private final List<ExpectedMatch> startExpectedMatches = new ArrayList<>();
    private final List<ExpectedMatch> endExpectedMatches = new ArrayList<>();

    public SimpleBoundsDetectorBuilder withStartExpectedMatch(int relativeRowIndex, String column, String pattern) {
        return withStartExpectedMatch(relativeRowIndex, Sheets.getIndex(column), pattern);
    }

    public SimpleBoundsDetectorBuilder withStartExpectedMatch(int relativeRowIndex, int columnIndex, String pattern) {
        startExpectedMatches.add(new ExpectedMatch(relativeRowIndex, columnIndex, Pattern.compile(pattern)));
        return this;
    }

    public SimpleBoundsDetectorBuilder withEndExpectedMatch(int relativeRowIndex, String column, String pattern) {
        return withEndExpectedMatch(relativeRowIndex, Sheets.getIndex(column), pattern);
    }

    public SimpleBoundsDetectorBuilder withEndExpectedMatch(int relativeRowIndex, int columnIndex, String pattern) {
        endExpectedMatches.add(new ExpectedMatch(relativeRowIndex, columnIndex, Pattern.compile(pattern)));
        return this;
    }

    public SimpleBoundsDetector build() {
        return new SimpleBoundsDetector(startExpectedMatches, endExpectedMatches);
    }
}
