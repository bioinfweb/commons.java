/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.appversion;



/**
 * Enumerates the possible types of releases of an application versioned with {@link ApplicationVersion}.
 * 
 * @author Ben St&ouml;ver
 */
public enum ApplicationType {
	/** An alpha release of an application. (Not all planned features for the initial release are yet implemented.) */
	ALPHA,
	
	/** A beta release of an application. (The application is not completely tested yet.) */
	BETA,
	
	/** A release candidate. (This version will become a stable release if no errors are found.) */
	RC,
	
	/** A stable release that contains all features planned in this stage and has been sufficiently tested. */
	STABLE;
	
	
	public static final String ALPHA_STRING = "alpha";
	public static final String BETA_STRING = "beta";
	public static final String RC_STRING = "release candidate";
	public static final String STABLE_STRING = "";


	/**
	 * Returns the string representation of this instance. The returned value differs from the 
	 * enum constant names and is equal to {@link #ALPHA_STRING}, {@link #BETA_STRING}, {@link #RC_STRING}
	 * or {@link #STABLE_STRING}. (Note that {@link #STABLE_STRING} is empty since the return value of
	 * this method shall be used to display the application version to the user.)  
	 */
	@Override
	public String toString() {
		switch(this) {
		  case ALPHA:
		  	return ALPHA_STRING;
		  case BETA:
		  	return BETA_STRING;
		  case RC:
		  	return RC_STRING;
		  default:
		  	return STABLE_STRING;
		}
	}
	
	
	/**
	 * Parses a text representation of an application type. This method is not case 
	 * sensitive and the input may contain preceding or trailing white spaces.
	 * 
	 * @param text - the string to parse
	 * @return the parsed value or {@code null} if the string could not be parsed or
	 *         was also {@code null}
	 */
	public static ApplicationType parseType(String text) {
		if (text != null) {
			text = text.trim().toLowerCase();
			if (text.equals(ALPHA_STRING)) {
				return ALPHA;
			}
			else if (text.equals(BETA_STRING)) {
				return BETA;
			}
			else if (text.equals(RC_STRING)) {
				return RC;
			}
			else if (text.equals(STABLE_STRING)) {
				return STABLE;
			}
		}
		return null;
	}
}
