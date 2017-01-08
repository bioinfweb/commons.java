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
