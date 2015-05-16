package com.searcheveryaspect.backend.database.update;


import com.google.common.collect.ImmutableList;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a fetch request to Oppna Data API.
 * 
 */
public class GovFetchRequest {

  private final String RIKSDAG_URL = "http://data.riksdagen.se/dokumentlista/";
  // Sökord
  private final String searchString;
  // Riksmöte
  private final String rm;
  // Interval of time for the request.
  private final Interval interval;
  // Systemdatum
  private final String ts;
  // Beteckning
  private final String bet;
  // Tempbet
  private final String tempbet;
  // Nummer
  private final String nr;
  // Utskott/Organ
  private final String organ;
  // Ledamot
  private final int commissionerId; // -1 if not used
  // List of different parties
  private final ImmutableList<String> parties;

  /**
   * Creates a GovFetchRequest with the specified parameters.
   */
  public GovFetchRequest(String searchString, String rm, Interval interval, String ts, String bet,
      String tempbet, String nr, String organ, int commissionerId, List<String> parties) {
    this.searchString = searchString.replace(" ", "%20");
    this.rm = rm;
    this.interval = interval;
    this.ts = ts;
    this.bet = bet;
    this.tempbet = tempbet;
    this.nr = nr;
    this.organ = organ;
    this.commissionerId = commissionerId;
    this.parties = ImmutableList.copyOf(parties);
  }

  /**
   * Creates a GovFetchRequest with the specified parameters. CommissionerId is set to -1
   * and not used.
   */
  public GovFetchRequest(String searchString, String rm, Interval interval, String ts, String bet,
      String tempbet, String nr, String organ, List<String> parties) {
    this(searchString, rm, interval, ts, bet, tempbet, nr, organ, -1, parties);
  }

  private GovFetchRequest(Builder b) {
    this.searchString = b.searchString;
    this.rm = b.rm;
    this.interval = b.interval;
    this.ts = b.ts;
    this.bet = b.bet;
    this.tempbet = b.tempbet;
    this.nr = b.nr;
    this.organ = b.organ;
    this.commissionerId = b.commissionerId;
    this.parties = ImmutableList.copyOf(b.parties);
  }

  public String getRIKSDAG_URL() {
    return RIKSDAG_URL;
  }

  public String getSearchString() {
    return searchString;
  }

  public String getRm() {
    return rm;
  }

  public Interval getInterval() {
    return interval;
  }

  public String getTs() {
    return ts;
  }

  public String getBet() {
    return bet;
  }

  public String getTempbet() {
    return tempbet;
  }

  public String getNr() {
    return nr;
  }

  public String getOrgan() {
    return organ;
  }

  public int getCommissionerId() {
    return commissionerId;
  }

  public ImmutableList<String> getParties() {
    return parties;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(RIKSDAG_URL);
    sb.append("?sok=");
    sb.append(searchString);
    sb.append("&doktyp=mot&rm=");
    sb.append(rm);
    sb.append("&from=");
    sb.append(interval.getStart().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
    sb.append("&tom=");
    sb.append(interval.getEnd().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
    sb.append("&ts=");
    sb.append(ts);
    sb.append("&bet=");
    sb.append(bet);
    sb.append("&tempbet=");
    sb.append(tempbet);
    sb.append("&nr=");
    sb.append("");
    sb.append("&org=");
    sb.append(organ);
    sb.append("&iid=");
    if (commissionerId != -1) sb.append(commissionerId);
    for (int i = 0; i < parties.size(); i++) {
      sb.append("&parti=");
      sb.append(parties.get(i));
    }
    sb.append("&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=json&a=s#soktraff");
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof GovFetchRequest) {
      GovFetchRequest that = (GovFetchRequest) o;
      return searchString.equals(that.searchString) && rm.equals(that.rm)
          && interval.equals(that.interval) && ts.equals(that.ts) && bet.equals(that.bet)
          && tempbet.equals(that.tempbet) && nr.equals(that.nr) && organ.equals(that.organ)
          && commissionerId == that.commissionerId && parties.equals(that.parties);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchString, rm, interval, ts, bet, tempbet, nr, organ, commissionerId,
        parties);
  }

  public static class Builder {
    // Because of using string builder to build the request string field not used are
    // Initialised to empty string or set to -1.
    private String searchString = "";
    private String rm = "";
    private Interval interval;
    private String ts = "";
    private String bet = "";
    private String tempbet = "";
    private String nr = "";
    private String organ = "";
    private int commissionerId = -1;
    private List<String> parties = new ArrayList<String>();

    public Builder searchString(String s) {
      searchString = s;
      return this;
    }

    public Builder riksmote(String s) {
      rm = s;
      return this;
    }

    public Builder interval(Interval i) {
      interval = i;
      return this;
    }

    public Builder systemDate(String s) {
      ts = s;
      return this;
    }

    public Builder beteckning(String s) {
      bet = s;
      return this;
    }

    public Builder tempBeteckning(String s) {
      tempbet = s;
      return this;
    }

    public Builder number(String s) {
      nr = s;
      return this;
    }

    public Builder organ(String s) {
      organ = s;
      return this;
    }

    public Builder commissionerId(int i) {
      commissionerId = i;
      return this;
    }

    public Builder parties(List<String> l) {
      parties = l;
      return this;
    }

    public GovFetchRequest build() {
      return new GovFetchRequest(this);
    }
  }

  public static Builder newGovFetchRequest() {
    return new Builder();
  }

  /**
   * Gets this GovFetchRequest as a mutable builder with all the fields preset.
   * 
   * @return a builder with the fields preset.
   */
  public Builder toBuilder() {
    Builder builder = new Builder();
    builder.bet = bet;
    builder.commissionerId = commissionerId;
    builder.interval = interval;
    builder.nr = nr;
    builder.organ = organ;
    builder.parties = parties;
    builder.rm = rm;
    builder.searchString = searchString;
    builder.tempbet = tempbet;
    builder.ts = ts;
    return builder;
  }
}
