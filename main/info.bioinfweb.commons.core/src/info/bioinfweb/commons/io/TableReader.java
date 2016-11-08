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
package info.bioinfweb.commons.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



/**
 * Implements reading a table from a text file two a two dimensional array with {@link String} objects.
 * 
 * @author Ben St&ouml;ver
 */
public class TableReader {
	public static String[][] readTable(URL url, char separator) throws IOException {
		return readTable(url.openStream(), separator);  // Buffering is done by the underlying TextReader later.
	}
	

	public static String[][] readTable(File file, char separator) throws IOException {
		return readTable(new FileInputStream(file), separator);  // Buffering is done by the underlying TextReader later.
	}
	

	/**
	 * Reads text data into a two dimensional array with {@link String} objects. If an empty file is read 
	 * the returned array has one element containing an empty {@link String}.
	 * 
	 * @param stream - the stream to read the text data from
	 * @param separator - the column separator
	 * @return String[colCount][rowCount]
	 * @throws IOException
	 */
	public static String[][] readTable(InputStream stream, char separator) throws IOException {
		String[] lines = TextReader.readText(stream).split("\r\n|\n|\r");  // Currently unnecessary because TextReader converts all types of line breaks to '\n'
		
		// Calculate column count:
    int maxColCount = 0; 
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			int colCount = 1;
			for (int charIndex = 0; charIndex < lines[lineIndex].length(); charIndex++) {
				if (lines[lineIndex].charAt(charIndex) == separator) {
					colCount++;
				}
			}
			maxColCount = Math.max(maxColCount, colCount);
		}
		
		// Fill array:
		String[][] result = new String[maxColCount][lines.length];
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			String[] cols = lines[lineIndex].split("" + separator);
			for (int colIndex = 0; colIndex < maxColCount; colIndex++) {
				if (colIndex >= cols.length) {
					result[colIndex][lineIndex] = ""; 
				}
				else {
					result[colIndex][lineIndex] = cols[colIndex];
				}
			}
		}
		return result;
	}
}
