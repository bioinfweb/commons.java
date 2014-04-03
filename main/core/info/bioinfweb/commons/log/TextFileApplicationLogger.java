package info.bioinfweb.commons.log;


import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * This implementation of {@link ApplicationLogger} appends all log messages to a text file, where every
 * message is written to one line. Note that the {@link #close()} method of this logger has to be called 
 * after the end of the logging process.
 *  
 * @author Ben St&ouml;ver
 */
public class TextFileApplicationLogger extends AbstractApplicationLogger implements Closeable, Flushable {
	private PrintWriter writer;
	
	
	/**
	 * Creates a new instance of this class and writes all messaged to the specified file.
	 * 
	 * @param file - the output file
	 * @param append - Specify <code>true</code> here if you want messages to be appended to an existing file
	 *        or <code>false</code> if an existing file shall be overwritten.
	 * @throws IOException
	 */
	public TextFileApplicationLogger(File file, boolean append) throws IOException {
		super();
		writer = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
	}

	
	/**
	 * Creates a new instance of this class and appends all messaged to the specified file.
	 * 
	 * @param file - the output file
	 * @throws IOException
	 */
	public TextFileApplicationLogger(File file) throws IOException {
		this(file, true);
	}

	
	@Override
	public synchronized void addMessage(ApplicationLoggerMessage message) {
		writer.println(message.toString());
	}


	@Override
	public void close() {
		writer.close();
	}


	@Override
	public void flush() {
		writer.flush();
	}
}
