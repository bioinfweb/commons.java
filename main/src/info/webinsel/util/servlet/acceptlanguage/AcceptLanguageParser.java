package info.webinsel.util.servlet.acceptlanguage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;



/**
 * Class that parses the accept-language HTTP header including its quality scores.
 * 
 * @author Ben St&ouml;ver
 */
public class AcceptLanguageParser {
	public static final String HEADER_NAME = "Accept-Language";
	public static final Pattern ACCEPT_LANG_PATTERN_ALL = Pattern.compile("([^\\-]+)\\-([^;]+);q=(.+)");
	public static final Pattern ACCEPT_LANG_PATTERN_NO_QUALITY = Pattern.compile("([^\\-]+)\\-([^;]+)");
	public static final Pattern ACCEPT_LANG_PATTERN_NO_COUNTRY = Pattern.compile("([^\\-]+);q=(.+)");
	
	
  public static List<AcceptLanguageEntry> parseHeader(HttpServletRequest request) {
 		return parseHeader(request.getHeader(HEADER_NAME));
  }

  
  private static AcceptLanguageEntry parseSingleHeader(String singleHeader) {
  	singleHeader = singleHeader.trim();
		AcceptLanguageEntry result = new AcceptLanguageEntry();
  	Matcher matcher = ACCEPT_LANG_PATTERN_ALL.matcher(singleHeader);
  	if (matcher.matches()) {
  		result.setLanguage(matcher.group(1));
  		result.setCountry(matcher.group(2));
  		result.setQuality(Double.parseDouble(matcher.group(3)));
  	}
  	else {
    	matcher = ACCEPT_LANG_PATTERN_NO_COUNTRY.matcher(singleHeader);
    	if (matcher.matches()) {
    		result.setLanguage(matcher.group(1));
    		result.setQuality(Double.parseDouble(matcher.group(2)));
    	}
    	else {
      	matcher = ACCEPT_LANG_PATTERN_NO_QUALITY.matcher(singleHeader);
      	if (matcher.matches()) {
      		result.setLanguage(matcher.group(1));
      		result.setCountry(matcher.group(2));
      	}
      	else {
      		result.setLanguage(singleHeader);
      	}
    	}
  	}
  	return result;
  }
  
  
  public static List<AcceptLanguageEntry> parseHeader(String header) {
  	List<AcceptLanguageEntry> result = new ArrayList<AcceptLanguageEntry>();
  	if ((header != null) && !"".equals(header)) {
	  	String[] entries = header.split(",");
	  	for (int i = 0; i < entries.length; i++) {
				result.add(parseSingleHeader(entries[i]));
			}
	  	Collections.sort(result, QualityComparator.getInstance());
  	}
  	return result;
  }
}
