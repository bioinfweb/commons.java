/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
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
	private void printList(PackedIntegerArrayList list, int elementPerLine) {
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
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(elementLength, intialListSize);
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
    		for (int elementLength = 6; elementLength <= 64; elementLength++) {
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
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(elementLength, intialListSize);
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
    		for (int elementLength = 6; elementLength <= 64; elementLength++) {
        	//System.out.print(elementLength + " ");
        	testRemoveRange(length, removeStart, removeLength, elementLength);
				}
    		//System.out.println();
  		}
    	//System.out.println();
		}
  }
	
  
  public static void main(String[] args) {
  	System.out.println(TestTools.toBinaryRepresentation(2001l));
  	System.out.println(TestTools.toBinaryRepresentation(2001l >>> 63));
  	System.out.println(TestTools.toBinaryRepresentation(2001l >>> 64));  // Does nothing
  	
  	PackedIntegerArrayList list = new PackedIntegerArrayList(6, 100);
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
