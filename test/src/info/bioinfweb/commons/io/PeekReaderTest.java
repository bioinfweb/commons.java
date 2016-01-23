/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014-2015  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
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


import info.bioinfweb.commons.testing.TestTools;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;

import org.junit.* ;


import static org.junit.Assert.* ;


                                          //           1         2         3         4         5         6
public class PeekReaderTest {             // 01234567890123456789012345678901234567890123456789012345678901
	public static final String TEST_CONTENT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
	public static final String TEST_CONTENT_LINE_BREAK = "Line 1\r\nLine 2\nLine 3\rLine 4"; 
	public static final String TERMINATION_SEQ = "ABC"; 
	public static final String TEST_CONTENT_TERMINATION_SEQ = "A 1ABCBC 2ABCText AB 3ABCLine 4"; 
	public static final String REG_EXP = ".+\\s+"; 
	public static final String TEST_CONTENT_REG_EXP = "Line1\tLine2   Line3 \t Line4"; 
	public static final int PEEK_BUFFER_SIZE = 10; 
	
	
	private static PeekReader createPeekReader(String content) {
		try {
			return new PeekReader(new StringReader(content), PEEK_BUFFER_SIZE);
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			return null;  // unreachable code
		}
	}
	
	
	private char[] getPeekBuffer(PeekReader reader) {
		return (char[])TestTools.getPrivateFieldValue(reader, "peekBuffer");
	}
	
	
	private int getBufferStartPos(PeekReader reader) {
		return (Integer)TestTools.getPrivateFieldValue(reader, "bufferStartPos");
	}
	
	
	private int getBufferContentLength(PeekReader reader) {
		return (Integer)TestTools.getPrivateFieldValue(reader, "bufferContentLength");
	}
	
	
	private char[] getNewChars(int start, int length) {
		char[] newChars = new char[length];
		for (int i = 0; i < length; i++) {
			if (start + i < TEST_CONTENT.length()) {
				newChars[i] = TEST_CONTENT.charAt(start + i);
			}
			else {
				newChars[i] = ' ';
			}
		}
		return newChars;
	}

	
	@Test
	public void test_initPeekBuffer() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		char[] peekBuffer = getPeekBuffer(reader);
		
		for (int i = 0; i < peekBuffer.length; i++) {
			assertEquals(TEST_CONTENT.charAt(i), peekBuffer[i]);
		}
	}
	
	
	private static void assertLocation(long expectedCharacterOffset, long expectedLineNumner, long expectedColumnNumber, 
			PeekReader reader) {
		
		assertEquals(expectedCharacterOffset, reader.getCharacterOffset());
		assertEquals(expectedLineNumner, reader.getLineNumber());
		assertEquals(expectedColumnNumber, reader.getColumnNumber());
	}

	
	@Test
	public void test_peek_array() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		try {
			// Test reading from the beginning:
			char[] cbuf = new char[10];
			assertEquals(10, reader.peek(cbuf, 0, 10));
			
			assertEquals('0', cbuf[0]);
			assertEquals('1', cbuf[1]);
			assertEquals('2', cbuf[2]);
			assertEquals('3', cbuf[3]);
			assertEquals('4', cbuf[4]);
			assertEquals('5', cbuf[5]);
			assertEquals('6', cbuf[6]);
			assertEquals('7', cbuf[7]);
			assertEquals('8', cbuf[8]);
			assertEquals('9', cbuf[9]);
			
			
			// Test reading of first part:
			char[] peekBuffer = getPeekBuffer(reader);
			peekBuffer[0] = 'A';
			peekBuffer[1] = 'B';
			peekBuffer[2] = 'C';
			peekBuffer[3] = 'D';
			TestTools.getPrivateField(reader.getClass(), "bufferStartPos").set(reader, 4);

			for (int i = 0; i < cbuf.length; i++) {
				cbuf[i] = '_';
			}
			assertEquals(3, reader.peek(cbuf, 2, 3));
			
			assertEquals('_', cbuf[0]);
			assertEquals('_', cbuf[1]);
			assertEquals('4', cbuf[2]);
			assertEquals('5', cbuf[3]);
			assertEquals('6', cbuf[4]);
			assertEquals('_', cbuf[5]);
			assertEquals('_', cbuf[6]);
			assertEquals('_', cbuf[7]);
			assertEquals('_', cbuf[8]);
			assertEquals('_', cbuf[9]);
			
			// Test reading of both parts:
			for (int i = 0; i < cbuf.length; i++) {
				cbuf[i] = '_';
			}
			assertEquals(7, reader.peek(cbuf, 2, 7));
			
			assertEquals('_', cbuf[0]);
			assertEquals('_', cbuf[1]);
			assertEquals('4', cbuf[2]);
			assertEquals('5', cbuf[3]);
			assertEquals('6', cbuf[4]);
			assertEquals('7', cbuf[5]);
			assertEquals('8', cbuf[6]);
			assertEquals('9', cbuf[7]);
			assertEquals('A', cbuf[8]);
			assertEquals('_', cbuf[9]);
			
			// Test reading of more than buffer length:
			cbuf = new char[20];
			for (int i = 0; i < cbuf.length; i++) {
				cbuf[i] = '_';
			}
			assertEquals(10, reader.peek(cbuf, 0, 20));
			
			assertEquals('4', cbuf[0]);
			assertEquals('5', cbuf[1]);
			assertEquals('6', cbuf[2]);
			assertEquals('7', cbuf[3]);
			assertEquals('8', cbuf[4]);
			assertEquals('9', cbuf[5]);
			assertEquals('A', cbuf[6]);
			assertEquals('B', cbuf[7]);
			assertEquals('C', cbuf[8]);
			assertEquals('D', cbuf[9]);
			for (int i = 10; i < cbuf.length; i++) {
				assertEquals('_', cbuf[i]);
			}
			
//			peekBuffer = getPeekBuffer(reader);
//			for (int i = 0; i < peekBuffer.length; i++) {
//				System.out.print(peekBuffer[i]);
//			}
//			System.out.println();
//			System.out.println(getBufferStartPos(reader));
//			System.out.println(getBufferContentLength(reader));
//			
//			System.out.println(cbuf);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
	}
	
	
	@Test
	public void test_writeToPeekBuffer() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		Method method = TestTools.getPrivateMethod(reader.getClass(), "writeToPeekBuffer", char[].class, int.class);
		
		try {
			// Overwrite first part of the buffer:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE, 5), 5);
			char[] peekBuffer = getPeekBuffer(reader);
			String expectedContent = TEST_CONTENT.substring(10, 15) + TEST_CONTENT.substring(5, 10);
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(5, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));
			
			// Overwrite second part of the buffer overlapping to the beginning again:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 5, 7), 7);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "KLCDEFGHIJ";
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(2, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));

			// Overwrite middle part of the buffer:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 5 + 7, 2), 2);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "KLMNEFGHIJ";
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(4, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));

			// Read more characters than elements in the buffer:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 14, 15), 15);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "TUVWXYZabc";  // If more characters than elements in the buffer are read, bufferStartPos will always be set to 0 again. That's why "UVWXYZabcT" is not the expected sequence.
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(0, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));

			// Request more characters than are available in the underlying reader:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 14 + 15, 25), 23);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "stuvwxyz";  // The last two characters are not tested, because their value is undefined.
			for (int i = 0; i < expectedContent.length(); i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(0, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE - 2, getBufferContentLength(reader));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}	

	
	/**
	 * This methods tests the decrease of bufferContentLength, when the last read operation consists of less characters 
	 * than present in buffer, which is not tested in {@link #test_writeToPeekBuffer()}.
	 */
	@Test
	public void test_writeToPeekBuffer2() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		Method method = TestTools.getPrivateMethod(reader.getClass(), "writeToPeekBuffer", char[].class, int.class);
		
		try {
			// First move further to the end of the stream:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE, 40), 40);
			char[] peekBuffer = getPeekBuffer(reader);
			String expectedContent = "efghijklmn";
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(0, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));
			
			// Overwrite second part of the buffer overlapping to the beginning again:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 40, 8), 8);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "opqrstuvmn";
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(8, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE, getBufferContentLength(reader));

			// Overwrite second part of the buffer overlapping to the beginning again with an incomplete array:
			method.invoke(reader, getNewChars(PEEK_BUFFER_SIZE + 48, 5), 4);
			peekBuffer = getPeekBuffer(reader);
			expectedContent = "yz rstuvwx";
			for (int i = 0; i < peekBuffer.length; i++) {
				assertEquals(expectedContent.charAt(i), peekBuffer[i]);
			}
			assertEquals(3, getBufferStartPos(reader));
			assertEquals(PEEK_BUFFER_SIZE - 1, getBufferContentLength(reader));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_read() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		char[] cbuf = new char[20];
		for (int i = 0; i < cbuf.length; i++) {
			cbuf[i] = '_';
		}
		try {
			reader.read(cbuf, 0, 5);
			for (int i = 0; i < 5; i++) {
				assertEquals(("" + i).charAt(0), cbuf[i]);
			}
			for (int i = 5; i < cbuf.length; i++) {
				assertEquals('_', cbuf[i]);
			}
			
			reader.read(cbuf, 5, 10);
			for (int i = 5; i < 10; i++) {
				assertEquals(("" + i).charAt(0), cbuf[i]);
			}
			assertEquals('A', cbuf[10]);
			assertEquals('B', cbuf[11]);
			assertEquals('C', cbuf[12]);
			assertEquals('D', cbuf[13]);
			assertEquals('E', cbuf[14]);
			for (int i = 15; i < cbuf.length; i++) {
				assertEquals('_', cbuf[i]);
			}
			
			reader.read(cbuf);
			assertEquals('F', cbuf[0]);
			assertEquals('G', cbuf[1]);
			assertEquals('H', cbuf[2]);
			assertEquals('I', cbuf[3]);
			assertEquals('J', cbuf[4]);
			assertEquals('K', cbuf[5]);
			assertEquals('L', cbuf[6]);
			assertEquals('M', cbuf[7]);
			assertEquals('N', cbuf[8]);
			assertEquals('O', cbuf[9]);
			assertEquals('P', cbuf[10]);
			assertEquals('Q', cbuf[11]);
			assertEquals('R', cbuf[12]);
			assertEquals('S', cbuf[13]);
			assertEquals('T', cbuf[14]);
			assertEquals('U', cbuf[15]);
			assertEquals('V', cbuf[16]);
			assertEquals('W', cbuf[17]);
			assertEquals('X', cbuf[18]);
			assertEquals('Y', cbuf[19]);
			
		}
		catch (Exception e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test_peek_single() {
		PeekReader reader = createPeekReader(TEST_CONTENT);
		
		try {
			assertEquals('0', reader.peekChar());
			assertEquals('1', reader.peekChar(1));
			assertEquals('2', reader.peekChar(2));
			assertEquals('3', reader.peekChar(3));
			assertEquals('4', reader.peekChar(4));
			assertEquals('5', reader.peekChar(5));
			assertEquals('6', reader.peekChar(6));
			assertEquals('7', reader.peekChar(7));
			assertEquals('8', reader.peekChar(8));
			assertEquals('9', reader.peekChar(9));
			
			reader.read();
			reader.read();

			assertEquals('2', reader.peekChar());
			assertEquals('3', reader.peekChar(1));
			assertEquals('4', reader.peekChar(2));
			assertEquals('5', reader.peekChar(3));
			assertEquals('6', reader.peekChar(4));
			assertEquals('7', reader.peekChar(5));
			assertEquals('8', reader.peekChar(6));
			assertEquals('9', reader.peekChar(7));
			assertEquals('A', reader.peekChar(8));
			assertEquals('B', reader.peekChar(9));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void test_peek_single_exceptionStart() {
		try {
			createPeekReader(TEST_CONTENT).peekChar(10);
		}
		catch (EOFException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	private void assertReadResult(String expectedLine, boolean expectedConsumedCompletely, PeekReader.ReadResult result) {
		assertEquals(expectedLine, result.getSequence().toString());
		assertEquals(expectedConsumedCompletely, result.isCompletelyRead());
	}
	
	
	@Test
	public void test_readLine() {
		PeekReader reader = createPeekReader(TEST_CONTENT_LINE_BREAK);
		try {
			assertReadResult("Line 1", true, reader.readLine());
			assertReadResult("Line 2", true, reader.readLine());
			assertReadResult("Line 3", true, reader.readLine());
			assertReadResult("Line 4", true, reader.readLine());
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readLine_maxLength() {
		PeekReader reader = createPeekReader(TEST_CONTENT_LINE_BREAK);
		try {
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 1", true, reader.readLine(4));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 2", true, reader.readLine(4));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 3", true, reader.readLine(4));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 4", true, reader.readLine(4));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readLine_maxLengthExactEnd() {
		PeekReader reader = createPeekReader(TEST_CONTENT_LINE_BREAK);
		try {
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 1", true, reader.readLine(2));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 2", true, reader.readLine(2));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 3", true, reader.readLine(2));
			assertReadResult("Line", false, reader.readLine(4));
			assertReadResult(" 4", true, reader.readLine(2));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_readUntil_illegalArgument() {
		PeekReader reader = createPeekReader(TEST_CONTENT_TERMINATION_SEQ);
		try {
			reader.readUntil(2, "ABC");
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readUntil() {
		PeekReader reader = createPeekReader(TEST_CONTENT_TERMINATION_SEQ);
		try {
			assertReadResult("A 1", true, reader.readUntil(20, TERMINATION_SEQ));
			assertReadResult("BC 2", true, reader.readUntil(20, TERMINATION_SEQ));
			assertReadResult("Text AB 3", true, reader.readUntil(20, TERMINATION_SEQ));
			assertReadResult("Line 4", true, reader.readUntil(20, TERMINATION_SEQ));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readUntil_exactEnd() {
		// "A 1ABCBC 2ABCText AB 3ABCLine 4"
		PeekReader reader = createPeekReader(TEST_CONTENT_TERMINATION_SEQ);
		try {
			assertReadResult("A 1", true, reader.readUntil(3, TERMINATION_SEQ));
			assertReadResult("BC 2", true, reader.readUntil(4, TERMINATION_SEQ));
			assertReadResult("Text AB 3", true, reader.readUntil(9, TERMINATION_SEQ));
			assertReadResult("Line 4", true, reader.readUntil(6, TERMINATION_SEQ));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_ReadUntilWhitespace() {
		PeekReader reader = createPeekReader("ABC DEF; GHI[");
		final String[] terminationSeqs = new String[]{";", "["};
		try {
			assertReadResult("ABC", true, reader.readUntilWhitespace(terminationSeqs));
			assertReadResult("DEF", true, reader.readUntilWhitespace(terminationSeqs));
			assertReadResult("", true, reader.readUntilWhitespace(terminationSeqs));
			assertReadResult("GHI", true, reader.readUntilWhitespace(terminationSeqs));

			reader = createPeekReader("ABC DEF; GHI[");
			assertReadResult("ABC DEF", true, reader.readUntil(terminationSeqs));
			assertReadResult(" GHI", true, reader.readUntil(terminationSeqs));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readRegExp_maxLength() {
		// "Line1\tLine2   Line3 \t Line4"
		PeekReader reader = createPeekReader(TEST_CONTENT_REG_EXP);
		try {
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("1\t", true, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("2   ", true, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("3 \t ", true, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("4", true, reader.readRegExp(4, REG_EXP, true));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readRegExp_maxLengthExactEnd() {
		PeekReader reader = createPeekReader(TEST_CONTENT_REG_EXP);
		try {
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("1\t", true, reader.readRegExp(2, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("2   ", true, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("3 \t ", true, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, true));
			assertReadResult("4", true, reader.readRegExp(1, REG_EXP, true));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readRegExp_maxLengthNotGreedy() {
		// "Line1\tLine2   Line3 \t Line4"
		PeekReader reader = createPeekReader(TEST_CONTENT_REG_EXP);
		try {
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("1\t", true, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("2 ", true, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("  ", true, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("3 ", true, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("\t ", true, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("4", true, reader.readRegExp(4, REG_EXP, false));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readRegExp_maxLengthExactEndNotGreedy() {
		PeekReader reader = createPeekReader(TEST_CONTENT_REG_EXP);
		try {
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("1\t", true, reader.readRegExp(2, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("2 ", true, reader.readRegExp(2, REG_EXP, false));
			assertReadResult("  ", true, reader.readRegExp(2, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("3 ", true, reader.readRegExp(2, REG_EXP, false));
			assertReadResult("\t ", true, reader.readRegExp(2, REG_EXP, false));
			assertReadResult("Line", false, reader.readRegExp(4, REG_EXP, false));
			assertReadResult("4", true, reader.readRegExp(1, REG_EXP, false));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_readPeekString() {
		PeekReader reader = createPeekReader(TEST_CONTENT_LINE_BREAK);
		try {
			assertEquals("Line 1", reader.peekString(6));
			assertEquals("Line 1", reader.readString(6));
			reader.consumeNewLine();
			assertEquals("Line 2", reader.peekString(6));
			assertEquals("Line 2", reader.readString(6));
			reader.consumeNewLine();
			assertEquals("Line 3", reader.peekString(6));
			assertEquals("Line 3", reader.readString(6));
			reader.consumeNewLine();
			assertEquals("Line 4", reader.peekString(6));
			assertEquals("Line 4", reader.readString(6));
			assertEquals(-1, reader.read());
			assertEquals("", reader.readString(6));
			assertEquals("", reader.peekString(6));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void test_peekLine() {
		PeekReader reader = createPeekReader("Line 1\r\nLine 0123456789\nLine 3\rLine 4");
		try {
			assertReadResult("Line 1", true, reader.peekLine());
			reader.readLine();
			assertReadResult("Line 01234", false, reader.peekLine());
			reader.readLine();
			assertReadResult("Line 3", true, reader.peekLine());
			reader.readLine();
			assertReadResult("Line 4", true, reader.peekLine());
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void testLocation() {
		PeekReader reader = createPeekReader("Line 0\nLine 0123456789\r\nLine 2\n\rLine 4\r\nLine 5\r");
		try {
			assertLocation(0, 0, 0, reader);
			reader.readLine();
			assertLocation(7, 1, 0, reader);
			reader.skip(14);
			assertLocation(21, 1, 14, reader);
			reader.read();  // 9
			assertLocation(22, 1, 15, reader);
			reader.read();  // \r
			assertLocation(23, 1, 16, reader);
			reader.read();  // \n
			assertLocation(24, 2, 0, reader);
			reader.readLine();
			assertLocation(31, 3, 0, reader);
			reader.readLine();
			assertLocation(32, 4, 0, reader);
			reader.skip(10);
			assertLocation(42, 5, 2, reader);
			reader.readLine();
			assertLocation(47, 6, 0, reader);
			assertEquals(-1, reader.read());
			assertLocation(47, 6, 0, reader);
		}
		catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
}
