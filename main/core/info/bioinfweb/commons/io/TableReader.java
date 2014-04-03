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
