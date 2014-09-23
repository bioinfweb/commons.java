package info.bioinfweb.commons.io;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;



/**
 * Offers methods that read the whole content of a text file and return it as a string.
 * 
 * @author Ben St&ouml;ver
 */
public class TextReader {
	public static String readText(URL url) throws IOException {
		return readText(url.openStream());
	}
	
	
	public static String readText(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE) {
			throw new IOException("The file \"" + file.getAbsolutePath() + "\" is too large to " +
					"be loaded to a string.");
		}
		else {
			return readText(new FileInputStream(file), (int)file.length());
		}
	}
	
	
	public static String readText(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		ArrayList<String> bufferList = new ArrayList<String>();
		int length = 0;
		try {
			String line = reader.readLine(); 
			while (line != null) {
				bufferList.add(line);
				length += line.length() + 1;  // + 1 für Zeilenumbruch
				line = reader.readLine(); 
			}
		}
		finally {
			reader.close();
		}
		
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < bufferList.size(); i++) {
			builder.append(bufferList.get(i) + "\n");
		}
		bufferList.clear();
		
		return builder.toString();
	}
	
	
	public static String readText(InputStream stream, int bufferSize) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder result = new StringBuilder(bufferSize);
		
		try {
			String line = reader.readLine(); 
			while (line != null) {
				result.append(line + "\n");
				line = reader.readLine(); 
			}
		}
		finally {
			reader.close();
		}		
		
		return result.toString();
	}
}
