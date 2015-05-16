package com.searcheveryaspect.backend.shared;


/**
 * All categories that can be used to classify a motion. Currently incomplete list.
 */
public enum Category {
  SKATT("skatt", new String[]{"skatt", "skatter", "skattebörda", "skattebördan", "skattelättnader", "straffskatt", "pengar", "inkomst", "skattepengar", "skattebetalare", "skattelättnad", "inkomstskatt", "värnskatt", "marginalskatt"}), 
  FORSVAR("försvar", new String[]{"försvar", "försvaret", "militär", "värnplikt", "vapen"}), 
  MILJO("miljö", new String[]{"miljö", "klimat", "klimatet", "träd", "temperatur", "växthuseffekt", "växthuseffekten", "förorening"}),
  VARD("vård", new String[]{"vård", "välfärd", "hälsa", "folkhälsa", "folkhemmet", "liv", "död", "sjukhus","sjukvård", "hälso-", "sjukvården", "vården", "socialstyrelsen"}), 
  SKOLA("skola", new String[]{"skola", "utbildning", "betyg", "betgygsgräns", "skolämnen", "elev", "elever", "student", "studenter"}),
  EKONOMI("ekonomi", new String[]{"företag", "företagare", "kapital", "resurs", "resurser",  "företagaren", "ekonomi", "investeringar", "investering", "tillväxt", "tillväxten","ekonomisk", "export", "import", "industri", "exportindustri", "aktier", "obligationer", "aktie", "börs", "konkurrenskraftig"}),
  INVANDRING("invandring", new String[]{"invandring", "invandrare", "arbetskraftinvandring", "sfi"}),
  RELIGION("religion", new String[]{"reiligion", "religionsutbildningen", "religiösa",  "religionen", "religionsfrihet", "religionsfriheten", "sekulariserat", "sekulariserat", "religioner", "religioners"}),
  UTRIKES("utrikes", new String[]{"utrikes", "utrikespolitik", "kust", "kuster", "terrorism", "stat", "kuststaten", "gränsöverskridande"}),
  TRAFIK("trafik", new String[]{"trafik", "försening", "förseningar", "trafiken", "väg", "vägar", "transport", "transporter", "trafikverket", "resande", "järnväg", "järnvägen", "sj", "bil", "bilen", "resan", "reseavdrag"}),
  UNKNOWN("unknown", new String[]{});
  
  private String prettyName;
  private String[] keywords;

  private Category(String prettyName, String[] keywords) {
    this.prettyName = prettyName;
    this.keywords = keywords;
  }

  /**
   * Returns a lower case string of the enum.
   */
  @Override
  public String toString() {
    return prettyName;
  }
  
  public String[] getKeywords()
  {
	  return keywords;
  }

}
