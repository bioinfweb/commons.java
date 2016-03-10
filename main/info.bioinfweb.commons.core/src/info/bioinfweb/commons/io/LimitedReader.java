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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;



/**
 * Guarantees that no more characters as specified are read from the decorated reader. It is though possible, that the decorated
 * reader reads beyond the read limit from its underlying reader (e.g. if a {@link BufferedReader} is decorated).
 * <p>
 * In contrast to many similar classes, the focus of this class is not on limiting the number of characters to be returned by this
 * reader, but to make sure that not more characters than specified can be read from the underlying reader. It can e.g. be used
 * to ensure that reading beyond the read ahead limit of a call of {@link #mark(int)} is not possible.
 * <p>
 * Note that for testing the end of the underlying stream one character more needs to be read from the decorated reader than can
 * be returned by this instance. Therefore one character less than the limit specified in the constructor will at most be returned. 
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class LimitedReader extends Reader {
	//TODO Additional methods could be directly delegated to use possible performance increasing implementations of the decorated reader.
	
	private Reader underlyingReader;
	private long limit;
	private long position = 0;
	private long markPosition = 0; 
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param underlyingReader the reader to be decorated
	 * @param limit the maximum number of characters that shall be read from the decorated reader (Note that this reader will return
	 *        at most {@code limit - 1} characters.)
	 */
	public LimitedReader(Reader underlyingReader, long limit) {
		super();
		this.underlyingReader = underlyingReader;
		this.limit = limit;
	}


	/**
	 * Returns the maximum number of characters that will be read from the decorated reader. Note that this reader will return
	 *        at most {@code getLimit() - 1} characters.
	 * 
	 * @return the maximum number of characters to be read from the decorated reader
	 */
	public long getLimit() {
		return limit;
	}
	
	
	/**
	 * The number of characters available, before the specified maximum number of characters will have been read from the decorated
	 * reader. Note that the number of characters that can actually be read may be lower, if the decorated reader provides less 
	 * characters than the specified read limit.
	 * <p>
	 * In a new instance of this class the return value of this method will be {@link #getLimit()}{@code - 1}.
	 * 
	 * @return the number of characters that 
	 */
	public long availableCharacters() {
		return limit - position - 1;
	}
	
	
	public boolean isLimitReached() {
		return availableCharacters() == 0;
	}
	
	
	@Override
	public void close() throws IOException {
		underlyingReader.close();
	}


	@Override
	public int read(char[] buffer, int offset, int length) throws IOException {
		length = (int)Math.min(length, availableCharacters());  // The minimum will always be lower than Integer.MIN_VALUE.
		int result = underlyingReader.read(buffer, offset, length);
		position += result;
		return result;
	}


	@Override
	public long skip(long n) throws IOException {  // This method needs to be overridden because its super implementation would call read() in an endless loop, when the limit is reached.
		n = Math.min(n, availableCharacters());
		long result = underlyingReader.skip(n);
		position += result;
		return result;
	}


	public synchronized void mark(int readAheadLimit) throws IOException {
		readAheadLimit = (int)Math.min(readAheadLimit, availableCharacters());  // Buffering more than the read limit is unnecessary.
		markPosition = position;
		underlyingReader.mark(readAheadLimit);
	}


	public boolean markSupported() {
		return underlyingReader.markSupported();
	}


	public boolean ready() throws IOException {
		return !isLimitReached() && underlyingReader.ready();
	}


	public synchronized void reset() throws IOException {
		underlyingReader.reset();
		position = markPosition;
	}
}
