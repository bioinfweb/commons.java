/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.* ;

import static org.junit.Assert.* ;



public class LimitedReaderTest {
	@Test
	public void test_skip() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.skip(3));
		assertEquals(2, reader.skip(3));
		assertEquals(0, reader.skip(3));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[3]));
		assertEquals(2, reader.read(new char[3]));
		assertEquals(-1, reader.read(new char[3]));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read2() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[5], 0, 3));
		assertEquals(2, reader.read(new char[5], 0, 3));
		assertEquals(-1, reader.read(new char[5], 0, 3));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read_buffered() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(new BufferedReader(underlyingReader, 5), 5);
		
		assertEquals(3, reader.read(new char[3]));
		assertEquals(2, reader.read(new char[3]));
		assertEquals(-1, reader.read(new char[3]));
		assertEquals('5', underlyingReader.read());  // Would fail, if the buffer size of the BufferedReader would be below or above 5, because it would read beyond the limit, to fill its buffer.
	}

	
	@Test
	public void test_mark_reset() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[5], 0, 3));
		reader.mark(10);
		assertEquals(2, reader.read(new char[5], 0, 3));
		reader.reset();
		assertEquals(2, reader.read(new char[5], 0, 3));
		assertEquals(-1, reader.read(new char[5], 0, 3));
		assertEquals('5', underlyingReader.read());
	}
}
