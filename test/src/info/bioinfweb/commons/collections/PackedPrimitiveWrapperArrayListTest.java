/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.testing.TestTools;

import org.junit.* ;


import static org.junit.Assert.* ;



public class PackedPrimitiveWrapperArrayListTest {
  @Test
  public void testInteger() {
  	PackedPrimitiveWrapperArrayList<Integer> list = PackedPrimitiveWrapperArrayList.newIntegerInstance(15, -10, 32);
  	//System.out.println(TestTools.toBinaryRepresentation(list.packedList.array[0]));
  	list.add(0);
  	//System.out.println(TestTools.toBinaryRepresentation(list.packedList.array[0]));
  	list.add(-10);
  	//System.out.println(TestTools.toBinaryRepresentation(list.packedList.array[0]));
  	list.add(32757);
  	//System.out.println(TestTools.toBinaryRepresentation(list.packedList.array[0]));
  	
  	assertEquals(0, list.get(0).intValue());
  	assertEquals(-10, list.get(1).intValue());
  	assertEquals(32757, list.get(2).intValue());
  	assertEquals(3, list.size());
  	
  	PackedPrimitiveWrapperArrayList<Integer> list2 = PackedPrimitiveWrapperArrayList.newIntegerInstance(15, -10, 32);
  	list2.add(0);
  	list2.add(247);
  	list2.add(32757);
  	
  	list.retainAll(list2);
  	assertEquals(0, list.get(0).intValue());
  	assertEquals(32757, list.get(1).intValue());
  	assertEquals(2, list.size());
  	
  	list.clear();  // Test removeRange()
  	assertEquals(0, list.size());
  }
  
  
  @Test(expected=IllegalArgumentException.class)
  public void test_getBytenstance_exception() {
  	PackedPrimitiveWrapperArrayList.newByteInstance(7, 1, 32);
  }
  
  
  @Test
  public void test_getByteInstance_exception2() {
  	PackedPrimitiveWrapperArrayList.newByteInstance(7, 0, 32); 	// Just tests that no IllegalArgumentException is thrown.
  }
  
  
  @Test(expected=IllegalArgumentException.class)
  public void test_getShortInstance_exception() {
  	PackedPrimitiveWrapperArrayList.newShortInstance(15, 1, 32);
  }
  
  
  @Test
  public void test_getShortInstance_exception2() {
  	PackedPrimitiveWrapperArrayList.newShortInstance(15, 0, 32); 	// Just tests that no IllegalArgumentException is thrown.
  }
  
  
  @Test(expected=IllegalArgumentException.class)
  public void test_getIntegerInstance_exception() {
  	PackedPrimitiveWrapperArrayList.newIntegerInstance(31, 1, 32);
  }
  
  
  @Test
  public void test_getIntegerInstance_exception2() {
  	PackedPrimitiveWrapperArrayList.newIntegerInstance(31, 0, 32); 	// Just tests that no IllegalArgumentException is thrown.
  }
  
  
  @Test(expected=IllegalArgumentException.class)
  public void test_getLongInstance_exception() {
  	PackedPrimitiveWrapperArrayList.newLongInstance(63, 1, 32);
  }
  
  
  @Test
  public void test_getLongInstance_exception2() {
  	PackedPrimitiveWrapperArrayList.newLongInstance(63, 0, 32); 	// Just tests that no IllegalArgumentException is thrown.
  }
  
  
  public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}
}
