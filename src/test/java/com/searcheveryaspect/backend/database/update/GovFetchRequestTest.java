package com.searcheveryaspect.backend.database.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.joda.time.Interval;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests GovFetchRequest.
 */
public class GovFetchRequestTest {
  final String BETECKNING = "BETECKNING";
  final Interval INTERVAL = Interval.parse("2013-01-01/2014-01-01");
  final String NUMBER = "NUMBER";
  final String ORGAN = "ORGAN";
  final String RIKSMOTE = "RIKSMOTE";
  final String SEARCHSTRING = "SEARCHSTRING";
  final String SYSTEMDATE = "SYSTEMDATE";
  final String TEMPBETECKNING = "TEMPBETECKNING";
  final int COMMUSSIONERID = 1;
  final List<String> PARTIES = Arrays.asList("MP", "C");

  @Test
  public void toBuilderEqualsSuccessTest() {
    GovFetchRequest original =
        GovFetchRequest.newGovFetchRequest().beteckning(BETECKNING).interval(INTERVAL)
            .number(NUMBER).organ(ORGAN).parties(PARTIES).riksmote(RIKSMOTE)
            .searchString(SEARCHSTRING).systemDate(SYSTEMDATE).commissionerId(COMMUSSIONERID)
            .tempBeteckning(TEMPBETECKNING).build();
    assertEquals(original, original.toBuilder().build());
  }

  @Test
  public void toBuilderEqualsFailTest() {
    GovFetchRequest original =
        GovFetchRequest.newGovFetchRequest().beteckning(BETECKNING).interval(INTERVAL)
            .number(NUMBER).organ(ORGAN).parties(PARTIES).riksmote(RIKSMOTE)
            .searchString(SEARCHSTRING).systemDate(SYSTEMDATE).commissionerId(COMMUSSIONERID)
            .tempBeteckning(TEMPBETECKNING).build();
    assertNotEquals(original, original.toBuilder().beteckning("foo").build());
    assertNotEquals(original, original.toBuilder().commissionerId(6).build());
    assertNotEquals(original, original.toBuilder()
        .interval(Interval.parse("1999-01-01/1999-02-01")).build());
    assertNotEquals(original, original.toBuilder().number("foo").build());
    assertNotEquals(original, original.toBuilder().organ("foo").build());
    assertNotEquals(original, original.toBuilder().parties(Arrays.asList("S", "M")).build());
    assertNotEquals(original, original.toBuilder().riksmote("foo").build());
    assertNotEquals(original, original.toBuilder().searchString("foo").build());
    assertNotEquals(original, original.toBuilder().systemDate("foo").build());
    assertNotEquals(original, original.toBuilder().tempBeteckning("foo").build());
  }
}
