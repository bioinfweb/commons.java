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
