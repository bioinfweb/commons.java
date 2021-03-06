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
package info.bioinfweb.commons;


import java.awt.Rectangle;

import org.junit.* ;


import static org.junit.Assert.* ;



/**
 * Tests {@link Math2}.
 * 
 * @author Ben St&ouml;ver
 */
public class Math2Test {
	@Test
	public void test_containsRect() {
		Rectangle r1 = new Rectangle(5, 5, 10, 10);
		Rectangle r2 = new Rectangle(6, 7, 6, 6);
		assertTrue(Math2.containsRect(r2, r1));
		assertFalse(Math2.containsRect(r1, r2));
		assertTrue(Math2.containsRect(r1, r1));

		r2 = new Rectangle(6, 7, 9, 8);
		assertTrue(Math2.containsRect(r2, r1));

		r2 = new Rectangle(6, 7, 10, 8);
		assertFalse(Math2.containsRect(r2, r1));

		r2 = new Rectangle(6, 7, 9, 9);
		assertFalse(Math2.containsRect(r2, r1));

		r2 = new Rectangle(4, 5, 2, 2);
		assertFalse(Math2.containsRect(r2, r1));

		r2 = new Rectangle(5, 4, 2, 2);
		assertFalse(Math2.containsRect(r2, r1));
	}
	
	
	@Test
  public void test_sum1ToN() {
		assertEquals(0, Math2.sum1ToN(0));
		assertEquals(10, Math2.sum1ToN(4));
		assertEquals(15, Math2.sum1ToN(5));
		assertEquals(55, Math2.sum1ToN(10));
	}

	
	@Test(expected=ArithmeticException.class)
  public void test_sum1ToN_exception() {
		Math2.sum1ToN(-1);
	}
	
	
	@Test
  public void test_minInt() {
		assertEquals(0, Math2.minInt(100, 24, 0, 23, 36, 78));
		assertEquals(0, Math2.minInt(0, 23, 36, 78));
	}
	
	
	@Test
  public void test_minLong() {
		assertEquals(0, Math2.minLong(100, 24, 0, 23, 36, 78));
		assertEquals(0, Math2.minLong(0, 23, 36, 78));
	}
	
	
	@Test
  public void test_minFloat() {
		assertEquals(0f, Math2.minFloat(100f, 24f, 0f, 23f, 36f, 78f), 0f);
		assertEquals(0f, Math2.minFloat(0f, 23f, 36f, 78f), 0f);
	}
	
	
	@Test
  public void test_minDouble() {
		assertEquals(0.0, Math2.minDouble(100.0, 24.0, 0.0, 23.0, 36.0, 78.0), 0.0);
		assertEquals(0.0, Math2.minDouble(0.0, 23.0, 36.0, 78.0), 0.0);
	}
	
	
	@Test
  public void test_maxInt() {
		assertEquals(100, Math2.maxInt(0, 24, 100, 23, 36, 78));
		assertEquals(100, Math2.maxInt(100, 23, 36, 78));
	}	
	
	
	@Test
  public void test_maxLong() {
		assertEquals(100, Math2.maxLong(0, 24, 100, 23, 36, 78));
		assertEquals(100, Math2.maxLong(100, 23, 36, 78));
	}	
	
	
	@Test
  public void test_maxFloat() {
		assertEquals(100, Math2.maxFloat(0, 24, 100, 23, 36, 78), 0f);
		assertEquals(100, Math2.maxFloat(100, 23, 36, 78), 0f);
	}	
	
	
	@Test
  public void test_maxDouble() {
		assertEquals(100, Math2.maxDouble(0, 24, 100, 23, 36, 78), 0.0);
		assertEquals(100, Math2.maxDouble(100, 23, 36, 78), 0.0);
	}	

	
	@Test
  public void test_roundUpFloat() {
		assertEquals(3f, Math2.roundUp(2.1f), 0.0);
		assertEquals(2f, Math2.roundUp(2f), 0.0);
	}	

	
	@Test
  public void test_roundUpDouble() {
		assertEquals(3.0, Math2.roundUp(2.1), 0.0);
		assertEquals(2.0, Math2.roundUp(2.0), 0.0);
	}
	
	
	@Test
	public void test_parseDouble() {
		assertEquals(1000.2, Math2.parseDouble("1000.2"), 0.0000001);
		assertEquals(1000.2, Math2.parseDouble("1000,2"), 0.0000001);
	}
}
