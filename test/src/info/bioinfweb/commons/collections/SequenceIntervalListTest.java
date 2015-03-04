/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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


import java.util.Iterator;

import info.bioinfweb.commons.collections.SequenceIntervalList.IntervalInformation;

import org.junit.* ;


import static org.junit.Assert.* ;



public class SequenceIntervalListTest {
	private class Adapter implements SequenceIntervalPositionAdapter<IntervalElement> {
		@Override
		public int getFirstPos(IntervalElement element) {
			return element.firstPos;
		}

		
		@Override
		public int getLastPos(IntervalElement element) {
			return element.lastPos;
		}


		@Override
		public void setFirstPos(IntervalElement element, int newFirstPos) {
			element.firstPos = newFirstPos;
		}


		@Override
		public void setLastPos(IntervalElement element, int newLastPos) {
			element.lastPos = newLastPos;
		}


		@Override
		public int compare(IntervalElement o1, IntervalElement o2) {
			return o1.compareTo(o2);
		}
	}
	
	
	@Test
	public void test_IntervalInformation_add_clear() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
		IntervalInformation i = list.new IntervalInformation(10, new Adapter());
		
		i.add(new IntervalElement(1, 11, "5-15"));
		assertEquals(0, i.getStartList().size());
		assertEquals(1, i.getOverlapList().size());
		
		i.clear();
		assertEquals(0, i.getStartList().size());
		assertEquals(0, i.getOverlapList().size());

		i.add(new IntervalElement(10, 15, "10-15"));
		assertEquals(1, i.getStartList().size());
		assertEquals(0, i.getOverlapList().size());

		i.add(new IntervalElement(15, 25, "15-25"));
		assertEquals(2, i.getStartList().size());
		assertEquals(0, i.getOverlapList().size());
	}
	
	
	@Test
	public void test_IntervalInformation_remove() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
		IntervalInformation i = list.new IntervalInformation(10, new Adapter());
		
		IntervalElement e1 = new IntervalElement(5, 15, "overlap"); 
		IntervalElement e2 = new IntervalElement(10, 15, "start"); 
		IntervalElement e3 = new IntervalElement(0, 30, "dummy");
		
		i.add(e1);
		assertEquals(0, i.getStartList().size());
		assertEquals(1, i.getOverlapList().size());

		i.add(e2);
		assertEquals(1, i.getOverlapList().size());
		assertEquals(1, i.getOverlapList().size());
		
		i.remove(e3);
		assertEquals(1, i.getStartList().size());
		assertEquals(1, i.getOverlapList().size());
		
    i.remove(e1);
		assertEquals(1, i.getStartList().size());
		assertEquals(0, i.getOverlapList().size());
		
    i.remove(e2);
		assertEquals(0, i.getStartList().size());
		assertEquals(0, i.getOverlapList().size());
	}
	
	
  @Test
  public void test_add_getOverlappingElements() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(new IntervalElement(1, 5, "a"));
  	list.add(new IntervalElement(1, 11, "b"));
  	list.add(new IntervalElement(12, 16, "c"));
  	
  	assertEquals(3, list.getOverlappingElements(4, 12).size());
  	assertEquals(2, list.getOverlappingElements(10, 12).size());
  	assertEquals("c", list.getOverlappingElements(12, 20).iterator().next().text);
  	assertEquals("b", list.getOverlappingElements(6, 9).iterator().next().text);
  	assertEquals("b", list.getOverlappingElements(6, 10).iterator().next().text);
  	assertEquals("c", list.getOverlappingElements(12, 12).iterator().next().text);
  }
	
	
  @Test
  public void test_contains() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
		IntervalElement e1 = new IntervalElement(5, 15, "overlap"); 
		IntervalElement e2 = new IntervalElement(10, 15, "start"); 
		IntervalElement e3 = new IntervalElement(0, 30, "dummy");
		
  	list.add(e1);
  	list.add(e2);
  	
  	assertTrue(list.contains(e1));
  	assertTrue(list.contains(e2));
  	assertTrue(!list.contains(e3));
  	assertTrue(!list.contains("abc"));
  }
	
	
  @Test
  public void test_iterator1() {
  	IntervalElement e1 = new IntervalElement(1, 5, "a"); 
  	IntervalElement e2 = new IntervalElement(2, 11, "b"); 
  	IntervalElement e3 = new IntervalElement(12, 16, "c"); 
  	IntervalElement e4 = new IntervalElement(12, 16, "d"); 
  	
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(e1);
  	list.add(e2);
  	list.add(e3);
  	list.add(e4);
  	
  	Iterator<IntervalElement> iterator = list.iterator();
  	assertEquals(e1, iterator.next());
  	assertEquals(e2, iterator.next());
  	IntervalElement return1 = iterator.next();
  	IntervalElement return2 = iterator.next();
  	assertTrue((return1.equals(e3) && return2.equals(e4)) || (return1.equals(e4) && return2.equals(e3)));
  	assertFalse(iterator.hasNext());
  }

  
  @Test
  public void test_iterator2() {
  	IntervalElement e1 = new IntervalElement(1, 5, "a"); 
  	IntervalElement e2 = new IntervalElement(2, 11, "b"); 
  	IntervalElement e3 = new IntervalElement(12, 16, "c"); 
  	IntervalElement e4 = new IntervalElement(12, 16, "d"); 
  	
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(e4);
  	list.add(e3);
  	list.add(e2);
  	list.add(e1);
  	
  	Iterator<IntervalElement> iterator = list.iterator();
  	assertEquals(e1, iterator.next());
  	assertEquals(e2, iterator.next());
  	IntervalElement return1 = iterator.next();
  	IntervalElement return2 = iterator.next();
  	assertTrue((return1.equals(e3) && return2.equals(e4)) || (return1.equals(e4) && return2.equals(e3)));
  	assertFalse(iterator.hasNext());
  }
  
  
  @Test
  public void test_iterator_empty() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	assertFalse(list.iterator().hasNext());
  }
  
  
  @Test
  public void test_toArray() {
  	IntervalElement e1 = new IntervalElement(1, 5, "a"); 
  	IntervalElement e2 = new IntervalElement(2, 11, "b"); 
  	IntervalElement e3 = new IntervalElement(12, 16, "c"); 
  	IntervalElement e4 = new IntervalElement(12, 16, "d"); 
  	
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(e4);
  	list.add(e3);
  	list.add(e2);
  	list.add(e1);
  	
    IntervalElement[] array = list.toArray(new IntervalElement[list.size()]);
  	assertEquals(e1, array[0]);
  	assertEquals(e2, array[1]);
  	assertTrue((array[2].equals(e3) && array[3].equals(e4)) || (array[2].equals(e4) && array[3].equals(e3)));
  	
    array = list.toArray(new IntervalElement[0]);
  	assertEquals(e1, array[0]);
  	assertEquals(e2, array[1]);
  	assertTrue((array[2].equals(e3) && array[3].equals(e4)) || (array[2].equals(e4) && array[3].equals(e3)));
  }
  
  
  @Test
  public void test_add_remove_size_contains() {
  	IntervalElement e1 = new IntervalElement(1, 5, "a"); 
  	IntervalElement e2 = new IntervalElement(2, 11, "b"); 
  	IntervalElement e3 = new IntervalElement(12, 16, "c"); 
  	IntervalElement e4 = new IntervalElement(12, 16, "d"); 
  	
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(e4);
  	assertEquals(1, list.size());
  	
  	list.add(e3);
  	assertEquals(2, list.size());
  	
  	list.remove(e4);
  	assertEquals(1, list.size());
  	
  	list.add(e2);
  	assertEquals(2, list.size());
  	
  	list.add(e1);
  	assertEquals(3, list.size());
  	
  	list.remove(e2);
  	assertEquals(2, list.size());
  	
  	assertTrue(list.contains(e1));
  	assertTrue(list.contains(e3));
  	
  	list.clear();
  	assertEquals(0, list.size());
  }
  
  
  @Test
  public void test_contains_equalElements() {
  	IntervalElement e1 = new IntervalElement(12, 16, "a"); 
  	IntervalElement e2 = new IntervalElement(12, 16, "a"); 

  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(e1);
  	list.add(e2);
  	
  	assertEquals(2, list.size());
  	assertTrue(list.contains(e1));
  	assertTrue(list.contains(e2));
  	
  	list.clear();
  	assertEquals(0, list.size());
  	list.add(e1);
  	assertTrue(list.contains(e2));  // e1.equals(e2) == true
  }
  
  
  @Test
  public void test_containsPosition() {
  	SequenceIntervalList<IntervalElement> list = new SequenceIntervalList<IntervalElement>(new Adapter(), 100, 10);
  	list.add(new IntervalElement(1, 5, "a"));
  	list.add(new IntervalElement(1, 11, "b"));
  	list.add(new IntervalElement(13, 16, "c"));
  	
  	assertTrue(list.containsPosition(1));
  	assertTrue(list.containsPosition(3));
  	assertTrue(list.containsPosition(5));
  	assertTrue(list.containsPosition(8));
  	assertTrue(list.containsPosition(11));
  	assertFalse(list.containsPosition(12));
  	assertTrue(list.containsPosition(13));
  	assertTrue(list.containsPosition(15));
  	assertTrue(list.containsPosition(16));
  	assertFalse(list.containsPosition(17));
  	assertFalse(list.containsPosition(256));
  }
}
