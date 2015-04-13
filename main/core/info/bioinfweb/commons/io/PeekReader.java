/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
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


import info.bioinfweb.commons.text.StringUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;



/**
 * Reader implementation that allows to peek upcoming characters from the underlying reader. A specified
 * number of characters a precached into a buffer from the underlying reader to keep then available for
 * peek operations.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class PeekReader extends Reader {
	public static final int DEFAULT_PEEK_BUFFER_SIZE = 8192;
	
	
	private Reader underlyingReader;
	private int peekLength;
	private char[] peekBuffer;
	private int bufferStartPos = -1;
	private int bufferContentLength = -1;
	
	
	/**
	 * Creates a new instance of this class and fills the initial peek buffer.
	 * 
	 * @param underlyingReader the reader to read the data from
	 * @param peekLength the number of characters to keep available for peek operations in in front of
	 *        the actual reader position 
	 * @throws IOException if an I/O exception occurs while filling the initial peek buffer 
	 */
	public PeekReader(Reader underlyingReader, int peekLength) throws IOException {
		super(underlyingReader);
		
		this.underlyingReader = underlyingReader;
		this.peekLength = peekLength;
		
		initPeekBuffer();
	}

	
	/**
	 * Creates a new instance of this class with the default peek buffer size ({@link #DEFAULT_PEEK_BUFFER_SIZE})
	 * and fills the initial peek buffer.
	 * 
	 * @param underlyingReader the reader to read the data from
	 * @throws IOException if an I/O exception occurs while filling the initial peek buffer 
	 */
	public PeekReader(Reader underlyingReader) throws IOException {
		this(underlyingReader, DEFAULT_PEEK_BUFFER_SIZE);
	}
	
	
	/**
	 * Returns the last index (with the character which is closest to the end of the underlying stream)
	 * in the peek buffer containing a defined value.
	 * 
	 * @return the index of the last character or -1 if the buffer does not contain any characters anymore
	 *         because the end of the underlying stream was reached and all characters from the butter
	 *         have been read from this stream 
	 */
	private int getBufferEndPos() {
		if (bufferContentLength == 0) {
			return -1;
		}
		else {
			int result = bufferStartPos - (peekLength - bufferContentLength);  // bufferStartPos - availableSpace
			if (result < 0) {
				result = peekLength + result;
			}
			return result;
		}
	}
	

	/**
	 * Returns the number of characters that can be previewed in front of the current position in the stream.
	 * <p>
	 * The return value will be equal to the buffer size specified in the constructor unless the end of the 
	 * underlying stream is enclosed.
	 * 
	 * @return the number of currently buffered characters
	 */
	public int getAvailablePeek() {
		return bufferContentLength;
	}


	@Override
	public void close() throws IOException {
		underlyingReader.close();
	}


	@Override
	public boolean markSupported() {
		return underlyingReader.markSupported();
	}


	@Override
	public void reset() throws IOException {
		underlyingReader.reset();
	}

	
	private void initPeekBuffer() throws IOException {
		bufferStartPos = 0;
		peekBuffer = new char[peekLength];
		bufferContentLength = underlyingReader.read(peekBuffer);
	}
	
	
	/**
	 * Copies the specified number of positions from the peek buffer to the specified array. If more characters are requested
	 * than are contained in the buffer, all characters in the buffer are copied. 
	 * 
	 * @param cbuf the array to copy the buffer contents to
	 * @param off the target offset in {@code cbuf} 
	 * @param len the maximum number of characters that shall be copied
	 * @return the number of characters that have been copied (This will never be greater than {@code len} or the current 
	 *         buffer content length.)
	 */
	public int peek(char[] cbuf, int off, int len) {
		int lengthToCopy = Math.min(len, bufferContentLength);
		int firstLengthToCopy = Math.min(lengthToCopy, peekLength - bufferStartPos);  // The number of positions to be copied between bufferStartPos and the end of the buffer
		System.arraycopy(peekBuffer, bufferStartPos, cbuf, off, firstLengthToCopy);
		
		int secondLengthToCopy = lengthToCopy - firstLengthToCopy;  // The number of positions to copy between the beginning of the buffer and bufferStartPos
		if (secondLengthToCopy > 0) {
			System.arraycopy(peekBuffer, 0, cbuf, off + firstLengthToCopy, secondLengthToCopy);
		}
		
		return lengthToCopy;
	}
	
	
	/**
	 * Writes the specified characters into the peek buffer. 
	 * <p>
	 * If {@code charsRequested} is lower than the size of {@code newChars} only the first elements 
	 * of {@code newChars} used as source data. If the length of {@code newChars} is greater than 
	 * {@link #getPeekLength()}, only the last elements of {@code newChars} are copied. If 
	 * {@code newCharsRead} is lower than {@link #getPeekLength()}, {@link #bufferContentLength} 
	 * will be set accordingly. If {@code newCharsRead}  is -1, not characters are written to the 
	 * peek buffer.
	 * 
	 * @param newChars the characters that have just been read from the underlying reader
	 * @param newCharsRead the number of characters that have just been read from the underlying reader
	 */
	private void writeToPeekBuffer(char[] newChars, int newCharsRead) {
		if (newCharsRead == -1) {  // end of stream was reached
			newCharsRead = 0;
		}
		if (newCharsRead > 0) {
			if (newCharsRead >= peekLength) {  // In this case the whole buffer is overwritten with the end of newChars.
				System.arraycopy(newChars, newChars.length - peekLength, peekBuffer, 0, peekLength);
				bufferContentLength = peekLength;
				bufferStartPos = 0;
			}
			else {  // Only a part of the buffer will be replaced.
				int firstPartLength = Math.min(peekLength - bufferStartPos, newCharsRead);
				System.arraycopy(newChars, 0, peekBuffer, bufferStartPos, firstPartLength);
				if (firstPartLength < newChars.length) {
					int secondPartLength = newChars.length - firstPartLength;
					System.arraycopy(newChars, firstPartLength, peekBuffer, 0, secondPartLength);
					bufferStartPos = secondPartLength;
				}
				else {
					bufferStartPos += firstPartLength;
					if (bufferStartPos >= peekLength) {
						bufferStartPos -= peekLength;  // Otherwise bufferStartPos could be equal to peekLength. 
					}
				}
			}
			bufferContentLength -= newChars.length - newCharsRead;  // If the same number of new characters could be read from the underlying reader as were requested from this reader, bufferContentLength will not be changed here.
		}
		else {
			bufferStartPos += newChars.length;
			if (bufferStartPos >= peekLength) {
				bufferStartPos -= peekLength;
			}
			bufferContentLength = Math.max(0, bufferContentLength - newChars.length);
		}
	}
	

	/**
	 * Copies the specified number of characters into the specified array. The characters are taken from the peek buffer
	 * (and additional characters are copied from the underlying reader if necessary) and the peek buffer is refilled by
	 * data read from the underlying reader, if possible.
	 * 
	 * @param cbuf the destination buffer
	 * @param off the offset at which to start storing characters
	 * @param len the maximum number of characters to read
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		//TODO Check correct minimal size of cbuf?
		char[] newChars = new char[len];
		int newCharsRead = underlyingReader.read(newChars);  //TODO Some streams may read only a part of the requested bytes, although the end of the stream is not reached. That would currently result in a smaller peek size.
		
		if ((newCharsRead == -1) && (getAvailablePeek() == 0)) {  // End of stream and buffer empty.
			return -1;
		}
		else {
			int positionsCopied = peek(cbuf, off, len);
			
			int additionalPositionsToCopy = Math.min(newCharsRead, len - positionsCopied);
			if (additionalPositionsToCopy > 0) {
				System.arraycopy(newChars, 0, cbuf, off + positionsCopied, additionalPositionsToCopy);
				positionsCopied += additionalPositionsToCopy;
			}
			
			writeToPeekBuffer(newChars, newCharsRead);
	
			return positionsCopied;
		}
	}
	
	
	public char readChar() throws IOException {
		return (char)read();
	}
	
	
	/**
	 * Returns the character at the current position of this reader without moving forward in the stream.
	 * 
	 * @return the next character
	 * @throws EOFException if the end of the stream was already reached
	 */
	public char peekChar() throws EOFException {
		if (getAvailablePeek() > 0) {
			return peekBuffer[bufferStartPos];
		}
		else {
			throw new EOFException("There are no more characters available in this stream.");
		}
	}
	
	
	/**
	 * Returns integer representation of the character at the current position of this reader without 
	 * moving forward in the stream.
	 * 
	 * @return the integer representation of next the character
	 * @throws EOFException if the end of the stream was already reached
	 */
	public int peek() throws EOFException {
		return (int)peekChar();  //TODO Is this the correct transformation?
	}
	
	
	/**
	 * Returns the character that would be returned after {@code offset} calls of {@link #read()}. 
	 * 
	 * @param offset the index of the character relative to the current reader position (The character at the current
	 *        position (that would be returned by the next call of {@link #read()}) would have the index 0.)
	 * @return
	 * @throws EOFException if the specified index lies behind the end of the stream
	 * @throws IndexOutOfBoundsException if the specified index lies further away from the current reader position
	 *         than the number of precached characters allows
	 * @see #getAvailablePeek()
	 */
	public char peekChar(int offset) throws IndexOutOfBoundsException {
		if (getAvailablePeek() > offset) {
			int index = bufferStartPos + offset;
			if (index >= peekBuffer.length) {
				index -= peekBuffer.length;
			}
			return peekBuffer[index];
		}
		else {
			throw new IndexOutOfBoundsException("The specified offset (" + offset + ") is outside the current buffer range.");  //TODO Should EOFException be thrown instead in some cases?
		}
	}
	
	
	public int peek(int offset) throws EOFException, IndexOutOfBoundsException {
		return (int)peekChar(offset);
	}
	
	
	/**
	 * Tests of the character that will be returned by the next call of e.g. {@link #read()} will be a
	 * new line character ({@code \n} or ({@code \r}).  
	 * 
	 * @return {@code true} if the next character will be a new line character or {@code false} if it will be some
	 *         other character or the end of the underlying stream has been reached.
	 */
	public boolean isNewLineNext() {
		try {
			return StringUtils.isNewLineChar(peekChar());
		}
		catch (EOFException e) {
			return false;
		}
	}
	
	
	public void consumeNewLine() throws IOException {
		if (isNewLineNext()) {
			int c = read();
			if (c != -1) {  // Not end of stream
				try {
					if (peekChar() == '\n') {  // Treat \r\n as one new line event.
						read();
					}
				}
				catch (EOFException e) {}  // Nothing to do. (Stream ends of the first new line character.)
			}
		}
	}
	
	
	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed {@code '\n'}, 
	 * a carriage return {@code '\r'}, or a carriage return followed immediately by a line feed.
	 * <p>
	 * The terminating new line characters are consumed from the underlying reader by this method although
	 * they are not contained in the returned value.
	 * 
	 * @return the line of text not including the terminal new line character(s)
	 * @throws IOException
	 */
	public CharSequence readLine() throws IOException {
		return readLine(Integer.MAX_VALUE);
	}
	
	
	/**
	 * Reads a line of text with the specified maximum length. A line is considered to be terminated by any 
	 * one of a line feed {@code '\n'}, a carriage return {@code '\r'}, or a carriage return followed 
	 * immediately by a line feed.
	 * <p>
	 * If terminating new line characters are reached, they are consumed from the underlying reader by this 
	 * method although they are not contained in the returned value.
	 * 
	 * @param maxLength the maximum length the returned line may have
	 * @return a line of text with the specified length or shorter if a new character or the end of the stream 
	 *         were found earlier (Does not include the terminal new line character(s).)
	 * @throws IOException if an I/O exception occurs   
	 */
	public CharSequence readLine(int maxLength) throws IOException {
		StringBuffer result = new StringBuffer();
		boolean endOfStream = false;
		while ((result.length() < maxLength) && !endOfStream && !isNewLineNext()) { 	//TODO check eof
			int c = read();
			if (c == -1) {
				endOfStream = true;
			}
			else {
				result.append((char)c);
			}
		}
		if (!endOfStream) {
			consumeNewLine();
		}
		return result;
	}
}
