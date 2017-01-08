/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.servlet.acceptlanguage;


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
