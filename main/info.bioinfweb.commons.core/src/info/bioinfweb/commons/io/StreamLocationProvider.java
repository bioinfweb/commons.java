/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2016  Ben Stöver
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


import javax.xml.stream.Location;



/**
 * Interface to be implemented by classes (e.g. readers) that offer properties to determine their
 * current location in a stream.
 * <p>
 * For compatibility, the properties in this interface have the same names as in {@link Location}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public interface StreamLocationProvider {
	/**
	 * Returns the number of characters that have been read from this instance since the beginning of the 
	 * underlying stream.
	 * <p>
	 * Note that due to the buffering of this class, this value may differ from the number of characters
	 * that have been read from the underlying stream.
	 * 
	 * @return the number of characters that have currently been read from this reader instance
	 */
	public long getCharacterOffset();

	/**
	 * Returns the number of the line in the stream, where the cursor of this instance is currently located.
	 * <p>
	 * This reader keeps track of the line number by monitoring each line separator that is read. Supported
	 * line separators are {@code '\n'}, {@code '\r'} or {@code '\r\n'}. 
	 * 
	 * @return the current line number
	 */
	public long getLineNumber();

	/**
	 * Returns the column of the current line, where the cursor of this instance is currently located.
	 * 
	 * @return the current column number in the current line
	 * @see #getLineNumber() 
	 */
	public long getColumnNumber();	
}
