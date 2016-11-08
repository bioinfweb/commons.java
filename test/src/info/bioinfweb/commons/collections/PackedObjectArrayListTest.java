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


import java.util.List;

import org.junit.* ;

import static org.junit.Assert.* ;



public class PackedObjectArrayListTest {
  @Test(expected=IndexOutOfBoundsException.class)
  public void test_tooManyObjects() {
  	PackedObjectArrayList<String> list = new PackedObjectArrayList<String>(5, 20);
  	for (int i = 0; i <= list.getMaxObjectTypeCount(); i++) {
			list.add("String " + i);  // The last call of add should lead to an exception.
		}
  }
  
  
  @Test
  public void test_add_get_retainAll() {
  	PackedObjectArrayList<String> list = new PackedObjectArrayList<String>(5, 20);
  	for (int i = 0; i < 100; i++) {
			list.add("ABC");
		}
  	
  	assertEquals(100, list.size());
  	assertEquals("ABC", list.get(27));
  	
  	list.set(27, "DEF");
  	assertEquals(100, list.size());
  	assertEquals("DEF", list.get(27));
  	assertEquals("ABC", list.get(28));
  	
  	PackedObjectArrayList<String> list2 = new PackedObjectArrayList<String>(1, 1);
    list2.add("DEF");
    list.retainAll(list2);
  	assertEquals(1, list.size());
  	assertEquals("DEF", list.get(0));
  }


  private void printList(List<String> list) {
  	for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i));
		}
  	System.out.println();
  }
  
  
  @Test
  public void test_shiftAddDelete() {
  	PackedObjectArrayList<String> list = new PackedObjectArrayList<String>(11, 20);
  	for (int i = 0; i < 10; i++) {
    	list.add("A");
    	list.add("B");
		}
  	printList(list);
  	list.add(17, "C");  // Test case created from a bug observed in LibrAlign, which occurs only at this index.
  	printList(list);
  	list.remove(16);
  	printList(list);

  	for (int i = 0; i <= 14; i += 2) {
    	assertEquals("A", list.get(i));
    	assertEquals("B", list.get(i + 1));
		}
  	assertEquals("C", list.get(16));
  	assertEquals("B", list.get(17));
  	assertEquals("A", list.get(18));
  	assertEquals("B", list.get(19));
  }
}
