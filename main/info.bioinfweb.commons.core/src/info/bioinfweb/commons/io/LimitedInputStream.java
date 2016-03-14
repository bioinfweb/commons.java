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
import java.io.InputStream;



/**
 * Guarantees that no more bytes as specified are read from the decorated reader. It is though possible, that the decorated
 * reader reads beyond the read limit from its underlying reader (e.g. if a {@link BufferedReader} is decorated).
 * <p>
 * The focus of this class is to make sure that not more characters than specified can be read from the underlying reader. It can 
 * e.g. be used to ensure that reading beyond the read ahead limit of a call of {@link #mark(int)} is not possible.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @see LimitedReader
 */
public class LimitedInputStream extends InputStream {
	private InputStream decoratedStream;
	private long limit;
	private long position = 0;
	private long markPosition = 0;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param decoratedStream the input stream to be decorated
	 * @param limit the maximum number of bytes that shall be read from the decorated input stream
	 */
	public LimitedInputStream(InputStream decoratedStream, long limit) {
		super();
		if (decoratedStream == null) {
			throw new NullPointerException("The decorated input stream must not be null.");
		}
		else if (limit < 0) {
			throw new IllegalArgumentException("The read limit must not be lower than 0, but was " + limit + ".");
		}
		else {
			this.decoratedStream = decoratedStream;
			this.limit = limit;
		}
	}

	
	/**
	 * Returns the maximum number of bytes that will be read from the decorated stream.
	 * 
	 * @return the maximum number of bytes to be read from the decorated stream
	 */
	public long getLimit() {
		return limit;
	}
	
	
	/**
	 * The number of bytes available, before the specified maximum number will have been read from the decorated input stream. Note 
	 * that the end of the stream (e.g. end of file) may possibly be reached before the specified limit, which is not reflected by 
	 * the return value of this method.
	 * 
	 * @return the number of bytes that could be read before the limit of this input stream is reached
	 */
	public long availableCharacters() {
		return limit - position;
	}
	
	
	/**
	 * Checks whether the specified maximum number of bytes has been read from the decorated input stream.
	 * Note that this method will return {@code true}, if the maximum number of bytes has not yet been read, even if the end of
	 * the stream was already reached. In such cases it can be used to determine whether the limit or the real end of the stream
	 * have been reached. 
	 * 
	 * @return {@code true} if the maximum number of bytes was read, {@code false} if the limit is not yet reached
	 */
	public boolean isLimitReached() {
		return availableCharacters() == 0;
	}
	
	
	/**
	 * Returns the next byte from the decorated stream or -1 if either the end of the decorated stream or the limit specified to
	 * this stream have been reached.
	 * 
	 * @return the next byte from the decorated stream or -1
	 */
	@Override
	public int read() throws IOException {
		if (isLimitReached()) {
			return -1;
		}
		else {
			int result = decoratedStream.read();
			if (result > -1) {
				position++;
			}
			return result;
		}
	}
	

	@Override
	public int available() throws IOException {
		return decoratedStream.available();
	}

	
	@Override
	public void close() throws IOException {
		decoratedStream.close();
	}

	
	@Override
	public synchronized void mark(int readAheadLimit) {
		readAheadLimit = (int)Math.min(readAheadLimit, availableCharacters());  // Buffering more than the read limit is unnecessary.
		markPosition = position;
		decoratedStream.mark(readAheadLimit);
	}

	
	@Override
	public synchronized void reset() throws IOException {
		decoratedStream.reset();
		position = markPosition;  // Will remain unchanged, if the underlying stream would throw an exception.
	}
	

	@Override
	public long skip(long n) throws IOException {
		n = Math.min(n, availableCharacters());
		long result = decoratedStream.skip(n);
		position += result;
		return result;
	}
}
