/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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
package info.bioinfweb.commons;



/**
 * Tool class that allows to analyze command line arguments that have been passed to an application.
 * 
 * @author Ben St&ouml;ver
 */
public class CommandLineReader {
  private String[] args = null;

  
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param args - the passed command line arguments
	 */
	public CommandLineReader(String[] args) {
		super();
		this.args = args;
	}
  
  
	/**
	 * Checks if the given value is contained in the arguments and returns the index.
	 *  
	 * @param value - the value to search for (exact match, case sensitive)
	 * @param start - the start index
	 * @param end - the end index (the last checked index is <code>end - 1</code>)
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value, int start, int end) {
		for (int i = start; i < end; i++) {
			if (args[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Checks if the given value is contained in the arguments and returns the index.
	 * All elements from {@code start} on are searched. 
	 * 
	 * @param value - the value to search for (exact match, case sensitive)
	 * @param start - the start index
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value, int start) {
		return contained(value, start, args.length);
	}
	
	
	/**
	 * Checks if the given value is contained in the arguments and returns the index.
	 * All elements are searched. 
	 * @param value - the value to search for (exact match, case sensitive)
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value) {
		return contained(value, 0, args.length);
	}
	
	
	public String getArg(int index) {
		return args[index];
	}
	
	
	public int argCount() {
		return args.length;
	}
}
