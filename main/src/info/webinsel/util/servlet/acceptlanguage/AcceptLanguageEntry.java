package info.webinsel.util.servlet.acceptlanguage;


import java.util.Locale;



/**
 * Represents one entry in the list of accepted languages in the <code>Accept-Language</code> HTTP header.
 * 
 * @author Ben St&ouml;ver
 */
public class AcceptLanguageEntry {
	public static final String DEFAULT_COUNTRY = "*";
	public static final double DEFAULT_QUALITY = 1.0;
	
	
  private String language = "";
  private String country = DEFAULT_COUNTRY;
  private double quality = DEFAULT_QUALITY;
  
  
	public AcceptLanguageEntry() {
		super();
	}


	public AcceptLanguageEntry(String language, String country, double quality) {
		super();
		this.language = language;
		this.country = country;
		this.quality = quality;
	}


	public AcceptLanguageEntry(String language, String country) {
		super();
		this.language = language;
		this.country = country;
  }


	public String getLanguage() {
		return language;
	}
	
	
	public String getCountry() {
		return country;
	}
	
	
	public double getQuality() {
		return quality;
	}
  
  
  public void setLanguage(String language) {
		this.language = language;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public void setQuality(double quality) {
		this.quality = quality;
	}


	public Locale toLocale() {
  	return new Locale(getLanguage(), getCountry());
  }
}
