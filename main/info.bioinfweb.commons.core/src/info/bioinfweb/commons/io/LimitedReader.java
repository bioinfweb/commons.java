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
 * The focus of this class is to make sure that not more characters than specified can be read from the underlying reader. It can 
 * e.g. be used to ensure that reading beyond the read ahead limit of a call of {@link #mark(int)} is not possible.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @see LimitedInputStream
 */
public class LimitedReader extends Reader {
	//TODO Additional methods could be directly delegated to use possible performance increasing implementations of the decorated reader.
	
	private Reader decoratedReader;
	private long limit;
	private long position = 0;
	private long markPosition = 0;
	private boolean allowClose = true;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param decoratedReader the reader to be decorated
	 * @param limit the maximum number of characters that shall be read from the decorated reader
	 */
	public LimitedReader(Reader decoratedReader, long limit) {
		super();
		if (decoratedReader == null) {
			throw new NullPointerException("The decorated reader must not be null.");
		}
		else if (limit < 0) {
			throw new IllegalArgumentException("The read limit must not be lower than 0, but was " + limit + ".");
		}
		else {
			this.decoratedReader = decoratedReader;
			this.limit = limit;
		}
	}


	/**
	 * Returns the maximum number of characters that will be read from the decorated reader.
	 * 
	 * @return the maximum number of characters to be read from the decorated reader
	 */
	public long getLimit() {
		return limit;
	}
	
	
	/**
	 * The number of characters available, before the specified maximum number will have been read from the decorated reader. Note 
	 * that the end of the input source (e.g. end of file) may possibly be reached before the specified character limit, which is 
	 * not reflected by the return value of this method.
	 * 
	 * @return the number of characters that could be read before the limit of this reader is reached
	 */
	public long availableCharacters() {
		return limit - position;
	}
	
	
	/**
	 * Checks whether the specified maximum number of characters has been read from the underlying reader.
	 * 
	 * @return {@code true} if the maximum number of characters was read
	 */
	public boolean isLimitReached() {
		return availableCharacters() == 0;
	}
	
	
	/**
	 * Determines whether closing this reader shall currently be prevented. Calls of {@link #close()} will throw
	 * an {@link ClosingNotAllowedException} in such cases.
	 * 
	 * @return {@code true}, if closing this reader will currently be prevented or {@code false} otherwise
	 */
	public boolean isAllowClose() {
		return allowClose;
	}


	/**
	 * Allows to specify whether closing this reader shall currently be prevented. Calls of {@link #close()} will then throw
	 * an {@link ClosingNotAllowedException}.
	 * 
	 * @param allowClose Specify {@code true} here, if closing shall be prevented from now on or {@code false} otherwise.
	 */
	public void setAllowClose(boolean allowClose) {
		this.allowClose = allowClose;
	}


	/**
	 * Closes the decorated reader, if that is currently allowed. Closing a previously closed reader has no effect..
	 *
	 * @throws ClosingNotAllowedException if {@link #isAllowClose()} currently returns {@code true}. (Calling this method, if 
	 *         the decorated reader was already closed will also throw this exception, if closing is currently not allowed.)
	 * @throws IOException if an I/O error occurs
	 * @see #setAllowClose(boolean)
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() throws IOException {
		if (allowClose) {
			decoratedReader.close();
		}
		else {
			throw new ClosingNotAllowedException();
		}
	}


	@Override
	public int read(char[] buffer, int offset, int length) throws IOException {
		if (isLimitReached()) {
			return -1;
		}
		else {
			length = (int)Math.min(length, availableCharacters());  // The minimum will always be lower than Integer.MIN_VALUE.
			int result = decoratedReader.read(buffer, offset, length);
			position += result;
			return result;
		}
	}


	@Override
	public long skip(long n) throws IOException {  // This method needs to be overridden because its super implementation would call read() in an endless loop, when the limit is reached.
		n = Math.min(n, availableCharacters());
		long result = decoratedReader.skip(n);
		position += result;
		return result;
	}


	@Override
	public synchronized void mark(int readAheadLimit) throws IOException {
		readAheadLimit = (int)Math.min(readAheadLimit, availableCharacters());  // Buffering more than the read limit is unnecessary.
		markPosition = position;
		decoratedReader.mark(readAheadLimit);
	}


	@Override
	public boolean markSupported() {
		return decoratedReader.markSupported();
	}


	@Override
	public boolean ready() throws IOException {
		return !isLimitReached() && decoratedReader.ready();
	}


	@Override
	public synchronized void reset() throws IOException {
		decoratedReader.reset();
		position = markPosition;  // Will remain unchanged, if the underlying reader would throw an exception.
	}
}
