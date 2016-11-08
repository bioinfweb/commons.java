/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.io;


import info.bioinfweb.commons.text.StringUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;



/**
 * Reader implementation that allows to peek upcoming characters from the underlying reader. A specified
 * number of characters a precached into a buffer from the underlying reader to keep then available for
 * peek operations.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class PeekReader extends Reader implements StreamLocationProvider {
	public static final int DEFAULT_PEEK_BUFFER_SIZE = 8192;
	
	
	/**
	 * Stores information about the result of a read operation, as it is returned by some methods of 
	 * {@link PeekReader}.
	 * 
	 * @author Ben St&ouml;ver
	 * @since 1.2.0	 
	 */
	public static class ReadResult {
		private CharSequence sequence;
		private boolean completelyRead;
		
		
		/**
		 * Creates a new instance of this class
		 * 
		 * @param sequence the character sequence that was read
		 * @param completelyRead Specify {@code true} here if the returned sequence contains all characters that
		 *        should have been read or {@code false} if reading was aborted because the maximum number of 
		 *        characters was read. 
		 */
		public ReadResult(CharSequence sequence, boolean completelyRead) {
			super();
			this.sequence = sequence;
			this.completelyRead = completelyRead;
		}


		/**
		 * The character sequence that was read from the underlying data source.
		 * 
		 * @return the read characters
		 */
		public CharSequence getSequence() {
			return sequence;
		}


		/**
		 * Allows to determine whether the specified maximum number of characters were read or all characters
		 * were read as intended. 
		 * 
		 * @return {@code true} if the returned sequence contains all characters that should have been read or 
		 *         {@code false} if reading was aborted because the maximum number of characters was reached 
		 */
		public boolean isCompletelyRead() {
			return completelyRead;
		}
	}
	
	
	private Reader underlyingReader;
	private int peekLength;
	private char[] peekBuffer;
	private int bufferStartPos = -1;
	private int bufferContentLength = -1;
	
	private long characterOffset = 0;
	private long lineNumber = 0;
	private long columnNumber = 0;
	
	
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
	 * Returns the number of characters that have been read from this reader since the beginning of the 
	 * underlying stream.
	 * <p>
	 * Note that due to the buffering of this class, this value may differ from the number of characters
	 * that have been read from the underlying stream.
	 * 
	 * @return the number of characters that have currently been read from this reader instance
	 */
	@Override
	public long getCharacterOffset() {
		return characterOffset;
	}


	/**
	 * Returns the number of the line in the stream, where the cursor of this reader is currently located.
	 * <p>
	 * This reader keeps track of the line number by monitoring each line separator that is read. Supported
	 * line separators are {@code '\n'}, {@code '\r'} or {@code '\r\n'}. 
	 * 
	 * @return the current line number
	 */
	@Override
	public long getLineNumber() {
		return lineNumber;
	}


	/**
	 * Returns the column of the current line, where the cursor of this reader is currently located.
	 * 
	 * @return the current column number in the current line
	 * @see #getLineNumber() 
	 */
	@Override
	public long getColumnNumber() {
		return columnNumber;
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
	
	
	public int peek(char[] cbuf) {
		return peek(cbuf, 0, cbuf.length);
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
		int lengthToCopy = Math.min(len, bufferContentLength);  //TODO Problem: bufferContentLength ist -1 => In Debugmodus schauen, wo es so gesetzt wird.
		int firstLengthToCopy = Math.min(lengthToCopy, peekLength - bufferStartPos);  // The number of positions to be copied between bufferStartPos and the end of the buffer
		System.arraycopy(peekBuffer, bufferStartPos, cbuf, off, firstLengthToCopy);
		
		int secondLengthToCopy = lengthToCopy - firstLengthToCopy;  // The number of positions to copy between the beginning of the buffer and bufferStartPos
		if (secondLengthToCopy > 0) {
			System.arraycopy(peekBuffer, 0, cbuf, off + firstLengthToCopy, secondLengthToCopy);
		}
		
		return lengthToCopy;
	}
	
	
	private String charArrayToString(char[] chars, int length) {
		StringBuilder result = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			result.append(chars[i]);
		}
		return result.toString();
	}
	
	
	/**
	 * Previews the specified number of characters and returns them as a string. If the end of the stream is
	 * reached before the specified number of characters have been read, the returned string will be shorter
	 * than {@code length}.
	 * 
	 * @param length the number of characters to preview
	 * @return the characters that would be read next as a string
	 */
	public String peekString(int length) {
		char[] chars = new char[length]; 
		int lengthRead = peek(chars);
		return charArrayToString(chars, lengthRead);
	}
	
	
	/**
	 * Reads the specified number of characters and returns them as a string. If the end of the stream is
	 * reached before the specified number of characters have been read, the returned string will be shorter
	 * than {@code length}.
	 * 
	 * @param length the number of characters to read
	 * @return the read characters as a string
	 * @throws IOException if an I/O error occurs during the read operation
	 */
	public String readString(int length) throws IOException {
		char[] chars = new char[length]; 
		int lengthRead = read(chars);
		if (lengthRead == -1) {  // end of file
			return "";
		}
		else {
			return charArrayToString(chars, lengthRead);
		}
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
				int firstPartLength = Math.min(peekLength - bufferStartPos, newChars.length/*newCharsRead*/);
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
			bufferContentLength = Math.max(0, bufferContentLength - (newChars.length - newCharsRead));  // If the same number of new characters could be read from the underlying reader as were requested from this reader, bufferContentLength will not be changed here.
		}
		else {
			bufferStartPos += newChars.length;
			if (bufferStartPos >= peekLength) {
				bufferStartPos -= peekLength;
			}
			bufferContentLength = Math.max(0, bufferContentLength - newChars.length);
		}
	}
	
	
	private boolean isCharNextInBuffer(char c, char[] cbuf, int pos, int end) {
		if (pos + 1 < end) {
			return cbuf[pos + 1] == c;
		}
		else {
			int code = peek();
			return (code != -1) && ((char)code == c);
		}
	}
	
	
	private void countPositionChange(char[] cbuf, int offset, int copiedLength) {
		characterOffset += copiedLength;
		
		int end = offset + copiedLength;
		for (int pos = offset; pos < end; pos++) {
			if (((cbuf[pos] == '\r') && !isCharNextInBuffer('\n', cbuf, pos, end))  // Line number will be increased in the next loop cycle or method call.
					|| (cbuf[pos] == '\n')) {
				
				lineNumber++;
				columnNumber = 0;
			}
			else {
				columnNumber++;
			}
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
	public int read(char[] cbuf, int off, int len) throws IOException {  // All inherited read methods seem to call this method.
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
			
			countPositionChange(cbuf, off, positionsCopied);
			return positionsCopied;
		}
	}
	
	
	/**
	 * Reads the next character.
	 * 
	 * @return the next character
	 * @throws IOException if there are no more characters to read (the end of the stream has been reached)
	 */
	public char readChar() throws IOException {
		int code = read();
		if (code == -1) {
			throw new EOFException("The end of the underlying stream was already reached.");
		}
		return (char)code;
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
	 * @return the integer representation of next the character or -1 if the end of the stream has been reached
	 */
	public int peek() {
		if (getAvailablePeek() > 0) {
			return (int)peekBuffer[bufferStartPos];
		}
		else {
			return -1;
		}
	}
	
	
	/**
	 * Returns the character that would be returned after {@code offset} calls of {@link #read()}. 
	 * 
	 * @param offset the index of the character relative to the current reader position (The character at the current
	 *        position (that would be returned by the next call of {@link #read()}) would have the index 0.)
	 * @return the peeked character
	 * @throws IndexOutOfBoundsException if the specified index lies further away from the current reader position
	 *         than the number of precached characters allows
	 * @throws EOFException if this reader already knows that the specified offset lies behind the end of the 
	 *         underlying stream. (This is only possible for offsets below the buffer size, specified in the
	 *         constructor.)
	 * @see #getAvailablePeek()
	 */
	public char peekChar(int offset) throws IndexOutOfBoundsException, EOFException {
		if (getAvailablePeek() > offset) {
			int index = bufferStartPos + offset;
			if (index >= peekBuffer.length) {
				index -= peekBuffer.length;
			}
			return peekBuffer[index];
		}
		else if ((getAvailablePeek() < peekLength) && (peekLength > offset)) {
			throw new EOFException("The specified offset lies behind the end of the stream.");
		}
		else {
			throw new IndexOutOfBoundsException("The specified offset (" + offset + ") is outside the current buffer range.");
		}
	}
	
	
	/**
	 * Returns the integer representation of the character that would be returned after {@code offset} calls of {@link #read()}.
	 * 
	 * @param offset the index of the character relative to the current reader position (The character at the current
	 *        position (that would be returned by the next call of {@link #read()}) would have the index 0.)
	 * @return the integer representation of the character at the specified offset or -1 if this reader already knows
	 *         that the specified offset lies behind the end of the underlying stream. (This is only possible for 
	 *         offsets below the buffer size, specified in the constructor.)
	 * @throws IndexOutOfBoundsException if the specified index lies further away from the current reader position
	 *         than the number of precached characters allows
	 * @see #getAvailablePeek()
	 */
	public int peek(int offset) throws IndexOutOfBoundsException {
		try {
			return (int)peekChar(offset);
		}
		catch (EOFException e) {
			return -1;
		}
	}
	
	
	public ReadResult peekLine() {
		StringBuilder result = new StringBuilder();
		int c = peek();
		int pos = 0;
		while ((pos < getAvailablePeek()) && (c != -1) && !StringUtils.isNewLineChar((char)c)) {
			result.append((char)c);
			pos++;
			if (pos < peekBuffer.length) {
				c = peek(pos);
			}		
		}
		
		return new ReadResult(result, (c == -1) || (pos < getAvailablePeek()));
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
	
	
	/**
	 * Tests of the specified string is contained in the underlying character stream at the current position.
	 * 
	 * @param sequence the string to search for
	 * @return {@code true} if the specified string is found, {@code false} otherwise
	 * @throws IllegalArgumentException if the specified string is longer than the peek length
	 */
	public boolean isNext(String sequence) throws IllegalArgumentException {
		try {
			for (int i = 0; i < sequence.length(); i++) {
				int c = peek(i);
				if ((c == -1) || (sequence.charAt(i) != (char)c)) {
					return false;
				}
			}
			return true;
		}
		catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("The specified sequence is longer than the available peek length (" + 
					sequence.length() + " > " + getAvailablePeek() + ")");
		}
	}
	
	
	/**
	 * Tests if one of the specified strings is contained in the underlying character stream at the current position.
	 * 
	 * @param sequences the strings to search for
	 * @return {@code true} if one of the specified strings is found, {@code false} otherwise
	 * @throws IllegalArgumentException if one of the specified strings is longer than the peek length
	 */
	public boolean isNext(String[] sequences) throws IllegalArgumentException {
		return whichIsNext(sequences) != -1;
	}
	
	
	/**
	 * Determines which of the specified strings is located at the current position of this reader.
	 * 
	 * @param sequences the strings to check for
	 * @return the index of the string in {@code sequences} that was found or -1 if none of the strings was found
	 * @throws IllegalArgumentException if one of the specified strings is longer than the peek length
	 */
	public int whichIsNext(String[] sequences) throws IllegalArgumentException {
		for (int i = 0; i < sequences.length; i++) {
			if (isNext(sequences[i])) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Consumes any one of a line feed {@code '\n'},  a carriage return {@code '\r'}, or a carriage return 
	 * followed immediately by a line feed, if found at the current position of the reader.
	 * 
	 * @return the number of characters that have been consumed by this method (0 - 2)
	 * @throws IOException if an I/O error occurs during the read operation
	 */
	public int consumeNewLine() throws IOException {
		int result = 0;
		if (isNewLineNext()) {
			int c = read();
			result++;
			if (c != -1) {  // Not end of stream
				try {
					if ((c == '\r') && (peekChar() == '\n')) {  // Treat \r\n as one new line event.
						read();
						result++;
					}
				}
				catch (EOFException e) {}  // Nothing to do. (Stream ends of the first new line character.)
			}
		}
		return result;
	}
	
	
	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed {@code '\n'}, 
	 * a carriage return {@code '\r'}, or a carriage return followed immediately by a line feed.
	 * <p>
	 * The terminating new line characters are consumed from the underlying reader by this method although
	 * they are not contained in the returned value.
	 * 
	 * @return the line of text not including the terminal new line character(s)
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readLine() throws IOException {
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
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readLine(int maxLength) throws IOException {
		StringBuffer line = new StringBuffer();
		boolean endOfStream = false;
		while ((line.length() < maxLength) && !endOfStream && !isNewLineNext()) {
			int c = read();
			if (c == -1) {
				endOfStream = true;
			}
			else {
				line.append((char)c);
			}
		}
		
		int newLinesConsumed = 1;  // Indicates end of stream that was already reached.
		if (!endOfStream) {
			newLinesConsumed = consumeNewLine();
			if ((newLinesConsumed == 0) && (peek() == -1)) {
				newLinesConsumed = 1;  // Indicate end of stream that will be reached.
			}
		}
		return new ReadResult(line, newLinesConsumed > 0);
	}
	
	
	/**
	 * Reads characters into a string buffer until the specified termination sequence is found. 
	 * <p>
	 * The returned result does not contain the termination sequence, although these characters have been consumed. 
	 * 
	 * @param terminationSequence a string specifying the termination sequence
	 * @return the character sequence read from the underlying stream not containing the specified termination sequence
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readUntil(String terminationSequence) throws IOException {
		return readUntil(Integer.MAX_VALUE, terminationSequence);
	}
	
	
	/**
	 * Reads characters into a string buffer until the specified termination sequence is found or the end of the 
	 * stream is reached.
	 * <p>
	 * The returned result does not contain the termination sequence, if it was found, although these characters 
	 * have been consumed. 
	 * 
	 * @param maxLength the maximum length the read sequence (not including the termination sequence) may have
	 * @param terminationSequence a string specifying the termination sequence
	 * @return the character sequence read from the underlying stream
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 * @throws IllegalArgumentException if the {@code terminationSequence} is longer than {@code maxLength} or 
	 *         longer than the available peek length 
	 */
	public ReadResult readUntil(int maxLength, String terminationSequence) throws IOException {
		return readUntil(maxLength, new String[]{terminationSequence}, false);
	}
	
	
	public ReadResult readUntilWhitespace(int maxLength, String terminationSequence) throws IOException {
		return readUntil(maxLength, new String[]{terminationSequence}, true);
	}
	
	
	public ReadResult readUntil(String[] terminationSequences) throws IOException {
		return readUntil(Integer.MAX_VALUE, terminationSequences, false);
	}
	
	
	public ReadResult readUntilWhitespace(String[] terminationSequences) throws IOException {
		return readUntil(Integer.MAX_VALUE, terminationSequences, true);
	}
	
	
	public ReadResult readUntil(int maxLength, String[] terminationSequences) throws IOException {
		return readUntil(maxLength, terminationSequences, false);
	}
	
	
	public ReadResult readUntilWhitespace(int maxLength, String[] terminationSequences) throws IOException {
		return readUntil(maxLength, terminationSequences, true);
	}
	
	
	protected ReadResult readUntil(int maxLength, String[] terminationSequences, boolean untilWhitespace) throws IOException {
		for (int i = 0; i < terminationSequences.length; i++) {
			if (terminationSequences[i].length() > maxLength) {
				throw new IllegalArgumentException(
						"The allowed maximal length must be greater of equal to the length of the termination sequence.");
			}
		}
		
		StringBuffer result = new StringBuffer();
		boolean endOfStream = false;
		
		int c = peek();
		while ((result.length() < maxLength) && !endOfStream && !isNext(terminationSequences) && 
				(!untilWhitespace || !Character.isWhitespace(c))) {
			
			if (c == -1) {
				endOfStream = true;
			}
			else {
				result.append((char)c);
			}
			skip(1);
			c= peek();
		}
		
		boolean completelyRead = endOfStream;
		if (Character.isWhitespace(c)) {
			skip(1);
			completelyRead = true;
		}
		else {
			int index = whichIsNext(terminationSequences);
			if (!endOfStream && (index != -1)) {
				skip(terminationSequences[index].length());
				completelyRead = true;
			}
			else {
				completelyRead = completelyRead || (peek() == -1);  // Check if the end of the stream is reached at the same time as the maximum length
			}
		}
		return new ReadResult(result, completelyRead);
	}
	
	
	/**
	 * Reads characters into a string buffer until the contents of this buffer match the specified pattern or the end
	 * of the stream is reached.
	 * <p>
	 * This method is useful to read strings that can be separated by different character patterns. To e.g. allow all white
	 * spaces as separators the following call could be made: {@code readRagExp(100, ".+\\s+")}.
	 * <p>
	 * Note that in contrast to {@link #readLine(int)} or {@link #readUntil(int, String)} no defined termination sequence
	 * will be removed from the returned result, because the whole result is matched against the specified pattern.
	 * 
	 * @param regExp the regular expression defining how the returned sequence should look like
	 * @param greedy Specify {@code true} here if the longest possible sequence matching the pattern shall be read or
	 *        {@code false} if the algorithms should stop already when the shortest possible matching sequence was found.  
	 * @return a character sequence matching the specified pattern (or the end of the stream is reached if the pattern could 
	 *         not be matched before)
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readRegExp(String regExp, boolean greedy) throws IOException {
		return readRegExp(Integer.MAX_VALUE, regExp, greedy);
	}
	
	
	/**
	 * Reads characters into a string buffer until the contents of this buffer match the specified pattern or the end
	 * of the stream is reached or the maximum number of characters was read.
	 * <p>
	 * This method is useful to read strings that can be separated by different character patterns. To e.g. allow all white
	 * spaces as separators the following call could be made: {@code readRagExp(100, ".+\\s+")}.
	 * <p>
	 * Note that in contrast to {@link #readLine(int)} or {@link #readUntil(int, String)} no defined termination sequence
	 * will be removed from the returned result, because the whole result is matched against the specified pattern.  
	 * 
	 * @param maxLength the maximum length the returned sequence may have
	 * @param regExp the regular expression defining how the returned sequence should look like
	 * @param greedy Specify {@code true} here if the longest possible sequence (considering {@code maxLength}) matching the 
	 *        pattern shall be read or {@code false} if the algorithms should stop already when the shortest possible matching 
	 *        sequence was found.  
	 * @return a character sequence matching the specified pattern (or the characters from the current position until
	 *         {@code maxLength} or the end of the stream is reached if the pattern could not be matched before)
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readRegExp(int maxLength, String regExp, boolean greedy) throws IOException {
		return readRegExp(maxLength, Pattern.compile(regExp), greedy);
	}
	
	
	/**
	 * Reads characters into a string buffer until the contents of this buffer match the specified pattern or the end
	 * of the stream is reached.
	 * <p>
	 * This method is useful to read strings that can be separated by different character patterns. To e.g. allow all white
	 * spaces as separators the following call could be made: {@code readRagExp(100, ".+\\s+")}.
	 * <p>
	 * Note that in contrast to {@link #readLine(int)} or {@link #readUntil(int, String)} no defined termination sequence
	 * will be removed from the returned result, because the whole result is matched against the specified pattern.  
	 * 
	 * @param pattern the regular expression pattern defining how the returned sequence should look like
	 * @param greedy Specify {@code true} here if the longest possible sequence matching the pattern shall be read or
	 *        {@code false} if the algorithms should stop already when the shortest possible matching sequence was found.  
	 * @return a character sequence matching the specified pattern (or the end of the stream is reached if the pattern could 
	 *         not be matched before)
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readRegExp(Pattern pattern, boolean greedy) throws IOException {
		return readRegExp(Integer.MAX_VALUE, pattern, greedy);
	}
	
	
	/**
	 * Reads characters into a string buffer until the contents of this buffer match the specified pattern or the end
	 * of the stream is reached or the maximum number of characters was read.
	 * <p>
	 * This method is useful to read strings that can be separated by different character patterns. To e.g. allow all white
	 * spaces as separators the following call could be made: {@code readRagExp(100, ".+\\s+")}.
	 * <p>
	 * Note that in contrast to {@link #readLine(int)} or {@link #readUntil(int, String)} no defined termination sequence
	 * will be removed from the returned result, because the whole result is matched against the specified pattern.  
	 * 
	 * @param maxLength the maximum length the read sequence may have
	 * @param pattern the regular expression pattern defining how the returned sequence should look like
	 * @param greedy Specify {@code true} here if the longest possible sequence (considering {@code maxLength}) matching the 
	 *        pattern shall be read or {@code false} if the algorithms should stop already when the shortest possible matching 
	 *        sequence was found.  
	 * @return a character sequence matching the specified pattern (or the characters from the current position until
	 *         {@code maxLength} or the end of the stream is reached if the pattern could not be matched before)
	 * @throws IOException if an I/O exception occurs while reading from the underlying stream
	 */
	public ReadResult readRegExp(int maxLength, Pattern pattern, boolean greedy) throws IOException {
		StringBuffer result = new StringBuffer();
		boolean endOfStream = false;
		
		while ((result.length() < maxLength) && !endOfStream && !pattern.matcher(result).matches()) {
			int c = read();
			if (c == -1) {
				endOfStream = true;
			}
			else {
				result.append((char)c);
			}
		}
		
		// Try to consume additional characters while still matching the pattern:
		if (greedy) {
			while ((result.length() < maxLength) && !endOfStream) {
				int c = peek();
				if (c == -1) {
					endOfStream = true;
				}
				else {
					result.append((char)c);
					if (pattern.matcher(result).matches()) {
						read();  // Consume character
					}
					else {
						result.delete(result.length() - 1, result.length());
						break;
					}
				}
			}
		}
		
		return new ReadResult(result, endOfStream || pattern.matcher(result).matches() || (peek() == -1));
	}
}
