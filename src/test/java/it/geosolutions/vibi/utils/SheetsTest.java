package it.geosolutions.vibi.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public final class SheetsTest {

    @Test
    public void getIndexTest() {
        assertThat(Sheets.getIndex("a"), is(equalTo(0)));
        assertThat(Sheets.getIndex("m"), is(equalTo(12)));
        assertThat(Sheets.getIndex("z"), is(equalTo(25)));
        assertThat(Sheets.getIndex("aa"), is(equalTo(26)));
        assertThat(Sheets.getIndex("ab"), is(equalTo(27)));
        assertThat(Sheets.getIndex("az"), is(equalTo(51)));
        assertThat(Sheets.getIndex("ba"), is(equalTo(52)));
        assertThat(Sheets.getIndex("bb"), is(equalTo(53)));
        assertThat(Sheets.getIndex("zz"), is(equalTo(701)));
    }
}
