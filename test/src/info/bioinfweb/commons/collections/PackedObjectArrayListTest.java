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
}
