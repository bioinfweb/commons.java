/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.testing;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;



/**
 * Logger class to be used in debugging and performance testing.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class TestLogger {
	private static TestLogger firstInstance = null;
	
	private TreeMap<String, Long> numericValues = new TreeMap<String, Long>();
	private TreeMap<String, List<CharSequence>> logs = new TreeMap<String, List<CharSequence>>();
	private boolean printDirectly = true;

	
	private TestLogger() {
		super();
	}
	
	
	public static TestLogger getInstance() {
		if (firstInstance == null) {
			firstInstance = new TestLogger();
		}
		return firstInstance;
	}
	
	
	public boolean isPrintDirectly() {
		return printDirectly;
	}


	public void setPrintDirectly(boolean printDirectly) {
		this.printDirectly = printDirectly;
	}


	/**
	 * Returns the log with the specified name. If it does not exist, an empty list will be created.
	 * 
	 * @param logName - the name of the log list
	 * @return the log list
	 */
	public List<CharSequence> getLog(String logName) {
		List<CharSequence> result = logs.get(logName);
		if (result == null) {
			result = new ArrayList<CharSequence>();
			logs.put(logName, result);
		}
		return result;
	}
	
	
	public void addEntry(String logName, CharSequence entry) {
		getLog(logName).add(entry);
		if (isPrintDirectly()) {
			System.out.println("[" + logName + "] " + entry);
		}
	}
	
	
	public void addEntry(String logName, Object... values) {
		if (values.length > 0) {
			StringBuffer entry = new StringBuffer();
			for (int i = 0; i < values.length - 1; i++) {
				entry.append("\"");
				entry.append(values[i]);
				entry.append("\", ");
			}
			entry.append("\"");
			entry.append(values[values.length - 1]);
			entry.append("\"");
			addEntry(logName, entry);
		}
	}
	
	
	public long getNumericValue(String timeName) {
		return numericValues.get(timeName);
	}
	
	
	public void setNumericValue(String valueName, long value) {
		numericValues.put(valueName, value);
	}
	
	
	public void addToNumericValue(String valueName, long addend) {
		if (numericValues.containsKey(valueName)) {
			numericValues.put(valueName, getNumericValue(valueName) + addend);
		}
		else {
			numericValues.put(valueName, addend);
		}
	}
	
	
	public void saveCurrentTime(String timeName) {
		numericValues.put(timeName, System.currentTimeMillis());
	}
	
	
	/**
	 * Adds the time span that has passed between the call of this method and the time stored in
	 * {@code sourceName} to the value in {@code targetName}.
	 * <p>
	 * If there is no value stored for {@code targetName} just the time span is saved under this name.
	 * If there is no value stored for {@code sourceName} this method does nothing. 
	 * 
	 * @param targetName - the name of the time entry to add the time span to
	 * @param sourceName - the name of the time entry marking the start of the time span to be measured
	 */
	public void addTimeDiff(String targetName, String sourceName) {
		if (numericValues.containsKey(sourceName)) {
			long passedTime = System.currentTimeMillis() - getNumericValue(sourceName);
			if (numericValues.containsKey(targetName)) {
				passedTime += getNumericValue(targetName);
			}
			numericValues.put(targetName, passedTime);
		}
	}
	
	
	public void logNumericValue(String logName, String valueName) {
		addEntry(logName, "Value in " + valueName + ": " + getNumericValue(valueName));
	}
	
	
	public void logTimeSince(String logName, String timeName) {
		long now = System.currentTimeMillis();
		addEntry(logName, "Time since " + timeName + ": " + (now - getNumericValue(timeName)) + " ms");
	}
	
	
	public void printLog(PrintStream out, String logName) {
		for (CharSequence entry : getLog(logName)) {
			out.println(entry);
		}
	}
	
	
	public void printLog(String logName) {
		printLog(System.out, logName);
	}
	
	
	public void printLogToErr(String logName) {
		printLog(System.err, logName);
	}
}
