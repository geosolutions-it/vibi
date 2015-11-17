package it.geosolutions.vibi.detectors;

import it.geosolutions.vibi.detectors.DetectorByMatches.ExpectedMatch;
import it.geosolutions.vibi.utils.Sheets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class DetectorByMatchesBuilder {

    private final List<ExpectedMatch> startExpectedMatches = new ArrayList<>();
    private final List<ExpectedMatch> endExpectedMatches = new ArrayList<>();

    public DetectorByMatchesBuilder withStartExpectedMatch(int relativeRowIndex, String columnIndex, String pattern) {
        return withStartExpectedMatch(relativeRowIndex, Sheets.getIndex(columnIndex), pattern);
    }

    public DetectorByMatchesBuilder withStartExpectedMatch(int relativeRowIndex, int columnIndex, String pattern) {
        startExpectedMatches.add(new ExpectedMatch(relativeRowIndex, columnIndex, Pattern.compile(pattern)));
        return this;
    }

    public DetectorByMatchesBuilder withEndExpectedMatch(int relativeRowIndex, String columnIndex, String pattern) {
        return withEndExpectedMatch(relativeRowIndex, Sheets.getIndex(columnIndex), pattern);
    }

    public DetectorByMatchesBuilder withEndExpectedMatch(int relativeRowIndex, int columnIndex, String pattern) {
        endExpectedMatches.add(new ExpectedMatch(relativeRowIndex, columnIndex, Pattern.compile(pattern)));
        return this;
    }

    public DetectorByMatches build() {
        return new DetectorByMatches(startExpectedMatches, endExpectedMatches);
    }
}
