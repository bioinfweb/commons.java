package info.bioinfweb.commons.io;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import org.junit.* ;

import static org.junit.Assert.* ;



public class LimitedInputStreamTest {
	@Test
	public void test_skip() throws IOException {
		ByteArrayInputStream underlyingStream = new ByteArrayInputStream("0123456789".getBytes());
		LimitedInputStream stream = new LimitedInputStream(underlyingStream, 5);
		
		assertEquals(3, stream.skip(3));
		assertEquals(2, stream.skip(3));
		assertEquals(0, stream.skip(3));
		assertEquals('5', underlyingStream.read());
	}

	
	@Test
	public void test_read() throws IOException {
		ByteArrayInputStream underlyingStream = new ByteArrayInputStream("0123456789".getBytes());
		LimitedInputStream stream = new LimitedInputStream(underlyingStream, 5);
		
		assertEquals(3, stream.read(new byte[3]));
		assertEquals(2, stream.read(new byte[3]));
		assertEquals(-1, stream.read(new byte[3]));
		assertEquals('5', underlyingStream.read());
	}

	
	@Test
	public void test_read2() throws IOException {
		ByteArrayInputStream underlyingStream = new ByteArrayInputStream("0123456789".getBytes());
		LimitedInputStream stream = new LimitedInputStream(underlyingStream, 5);
		
		assertEquals(3, stream.read(new byte[5], 0, 3));
		assertEquals(2, stream.read(new byte[5], 0, 3));
		assertEquals(-1, stream.read(new byte[5], 0, 3));
		assertEquals('5', underlyingStream.read());
	}

	
	@Test
	public void test_read_buffered() throws IOException {
		ByteArrayInputStream underlyingStream = new ByteArrayInputStream("0123456789".getBytes());
		LimitedInputStream stream = new LimitedInputStream(new BufferedInputStream(underlyingStream, 5), 5);
		
		assertEquals(3, stream.read(new byte[3]));
		assertEquals(2, stream.read(new byte[3]));
		assertEquals(-1, stream.read(new byte[3]));
		assertEquals('5', underlyingStream.read());  // Would fail, if the buffer size of the BufferedReader would be below or above 5, because it would read beyond the limit, to fill its buffer.
	}

	
	@Test
	public void test_mark_reset() throws IOException {
		ByteArrayInputStream underlyingStream = new ByteArrayInputStream("0123456789".getBytes());
		LimitedInputStream stream = new LimitedInputStream(underlyingStream, 5);
		
		assertEquals(3, stream.read(new byte[5], 0, 3));
		stream.mark(10);
		assertEquals(2, stream.read(new byte[5], 0, 3));
		stream.reset();
		assertEquals(2, stream.read(new byte[5], 0, 3));
		assertEquals(-1, stream.read(new byte[5], 0, 3));
		assertEquals('5', underlyingStream.read());
	}
}
