/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.testing.TestTools;

import org.junit.* ;


import static org.junit.Assert.* ;



public class PackedIntegerArrayListTest {
	public static void printList(PackedIntegerArrayList list, int elementPerLine) {
		for (long i = 0; i < list.size(); i++) {
			if (i % elementPerLine == 0) {
				System.out.print(i + ": ");
			}
			System.out.print(list.get(i) + " ");
			if (i % elementPerLine == elementPerLine - 1) {
				System.out.println();
			}
		}
	}
	
	
  public void testInsertRange(final long intialListSize, final long insertIndex, final long insertionLength, 
  		final int elementLength) {
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(elementLength, 0, intialListSize);
		for (long i = 0; i < intialListSize; i++) {
			list.add(i);
		}
		//printList(list, 10);

		list.insertRange(insertIndex, insertionLength);
		//System.out.println();
		//printList(list, 10);
		
		for (long i = 0; i < insertIndex; i++) {
			assertEquals(i, list.get(i));
		}
		for (long i = insertIndex + insertionLength; i < intialListSize + insertionLength; i++) {
			assertEquals(i - insertionLength, list.get(i));
		}
		assertEquals(intialListSize + insertionLength, list.size());
  }
	
  
  @Test
  public void test_insertRange() {
  	//testInsertRange(64, 0, 33, 64);
  	for (int insertIndex = 0; insertIndex < 64; insertIndex++) {
  		//System.out.println("Start " + insertIndex);
    	for (int insertionLength = 0; insertionLength < 64; insertionLength++) {
    		//System.out.println("  length " + insertionLength);
    		for (int elementLength = 6; elementLength < 64; elementLength++) {
        	//System.out.print(elementLength + " ");
        	testInsertRange(64, insertIndex, insertionLength, elementLength);
				}
    		//System.out.println();
  		}
    	//System.out.println();
		}
  }
  
  
  private void testRemoveRange(final long intialListSize, final long removeIndex, final long removeLength, 
  		final int elementLength) {
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(elementLength, 0, intialListSize);
		for (long i = 0; i < intialListSize; i++) {
			list.add(i);
		}
		//printList(list, 10);

		list.removeRange(removeIndex, removeLength);
		//System.out.println();
		//printList(list, 10);
		
		for (long i = 0; i < removeIndex; i++) {
			assertEquals(i, list.get(i));
		}
		for (long i = removeIndex; i < intialListSize - removeLength; i++) {
			assertEquals(i + removeLength, list.get(i));
		}
		assertEquals(intialListSize - removeLength, list.size());
  }
  
	
  @Test
  public void test_removeRange() {
  	final int length = 63;
  	final int maxRemove = 63;
  	for (int removeStart = 0; removeStart < length - maxRemove + 1; removeStart++) {
  		//System.out.println("Start " + removeStart);
    	for (int removeLength = 0; removeLength <= maxRemove; removeLength++) {
    		//System.out.println("  length " + removeLength);
    		for (int elementLength = 6; elementLength < 64; elementLength++) {
        	//System.out.print(elementLength + " ");
        	testRemoveRange(length, removeStart, removeLength, elementLength);
				}
    		//System.out.println();
  		}
    	//System.out.println();
		}
  }
  
  
	@Test
	public void test_get_set_signed() {
		final int minValue = -10;
		final int size = 128;
		
		for (int bitsPerValue = 7; bitsPerValue < 64; bitsPerValue++) {
			PackedIntegerArrayList list = new PackedIntegerArrayList(bitsPerValue, minValue, size);
			for (int i = minValue; i < size + minValue; i++) {
				list.add(i);
			}
			
			for (int i = 0; i < size; i++) {
				assertEquals(i + minValue, list.get(i));
			}
		}
	}
	
	
	@Test
	public void test_getMaxValue() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(15,-10, 5);
  	assertEquals(-10, list.getMinValue());
  	assertEquals(32757, list.getMaxValue());
	}

	
	@Test(expected=IndexOutOfBoundsException.class)
  public void test_add_lowIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.add(-1, 18);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_add_highIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.add(1, 18);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_get_lowIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.get(-1);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_get_highIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.add(18);
  	list.get(1);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_get_highIndexException2() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.get(0);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_set_lowIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.set(-1, 18);
  }
	
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_set_highIndexException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, 0, 5);
  	list.set(0, 18);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_set_lowValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(0);
  	list.set(0, -11);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_set_highValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(0);
  	list.set(0, 118);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_add_lowValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(-11);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_add_highValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(118);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_add2_lowValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(0);
  	list.add(1, -11);
  }
	
  
  @Test(expected=IllegalArgumentException.class)
  public void test_add2_highValueException() {
  	PackedIntegerArrayList list = new PackedIntegerArrayList(7, -10, 5);
  	list.add(0);
  	list.add(1, 118);
  }
	
  
  public static void main(String[] args) {
  	System.out.println(TestTools.toBinaryRepresentation(2001l));
  	System.out.println(TestTools.toBinaryRepresentation(2001l >>> 63));
  	System.out.println(TestTools.toBinaryRepresentation(2001l >>> 64));  // Does nothing
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(6, 0, 100);
		list.set(56, 8);
		list.set(60, 63);
		list.set(61, 64);
		list.set(62, 7);
		list.set(62, 9);
		list.set(99, 7);
		
		System.out.print(list.get(56) + " ");
		System.out.print(list.get(60) + " ");
		System.out.print(list.get(61) + " ");
		System.out.print(list.get(62) + " ");
		System.out.print(list.get(99) + " ");
	}	
}
