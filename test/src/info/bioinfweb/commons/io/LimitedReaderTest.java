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
		assertEquals(1, reader.skip(3));
		assertEquals(0, reader.skip(3));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[3]));
		assertEquals(1, reader.read(new char[3]));
		assertEquals(0, reader.read(new char[3]));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read2() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[5], 0, 3));
		assertEquals(1, reader.read(new char[5], 0, 3));
		assertEquals(0, reader.read(new char[5], 0, 3));
		assertEquals('5', underlyingReader.read());
	}

	
	@Test
	public void test_read_buffered() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(new BufferedReader(underlyingReader, 5), 5);
		
		assertEquals(3, reader.read(new char[3]));
		assertEquals(1, reader.read(new char[3]));
		assertEquals(0, reader.read(new char[3]));
		assertEquals('5', underlyingReader.read());  // Would fail, if the buffer size of the BufferedReader would be below or above 5, because it would read beyond the limit, to fill its buffer.
	}

	
	@Test
	public void test_mark_reset() throws IOException {
		StringReader underlyingReader = new StringReader("0123456789");
		LimitedReader reader = new LimitedReader(underlyingReader, 5);
		
		assertEquals(3, reader.read(new char[5], 0, 3));
		reader.mark(10);
		assertEquals(1, reader.read(new char[5], 0, 3));
		reader.reset();
		assertEquals(1, reader.read(new char[5], 0, 3));
		assertEquals(0, reader.read(new char[5], 0, 3));
		assertEquals('5', underlyingReader.read());
	}
}
