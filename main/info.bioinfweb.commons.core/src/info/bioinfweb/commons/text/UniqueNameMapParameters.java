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
package info.bioinfweb.commons.text;


import java.util.Map;
import java.util.TreeMap;



/**
 * Stores the parameters used by {@link UniqueNameMap}.
 * 
 * @author Ben St&ouml;ver
 */
public class UniqueNameMapParameters {
  public static int UNLIMITED_LENGTH = -1;
	public static final char DEFAULT_FILL_UP_CHAR = ' ';

	
  private int maxNameLength = UNLIMITED_LENGTH;
  private boolean fillUp = false;
  private char fillUpChar = DEFAULT_FILL_UP_CHAR;
  private Map<String, String> replacements = new TreeMap<String, String>();
  
  
	public int getMaxNameLength() {
		return maxNameLength;
	}
	
	
	public void setMaxNameLength(int maxNameLength) {
		this.maxNameLength = maxNameLength;
	}
	
	
	public boolean isUnlimitedLength() {
		return maxNameLength == UNLIMITED_LENGTH;
	}
	
	
	public boolean isFillUp() {
		return fillUp;
	}


	public void setFillUp(boolean fillUp) {
		this.fillUp = fillUp;
	}


	public char getFillUpChar() {
		return fillUpChar;
	}


	public void setFillUpChar(char fillUpChat) {
		this.fillUpChar = fillUpChat;
	}


	public Map<String, String> getReplacements() {
		return replacements;
	}
	
	
	public boolean hasReplacements() {
		return !replacements.isEmpty();
	}
}
