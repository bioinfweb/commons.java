package info.webinsel.util.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.* ;


import static org.junit.Assert.* ;



public class NonOverlappingIntervalListTest {
	private NonOverlappingIntervalList getList() {
  	NonOverlappingIntervalList list = new NonOverlappingIntervalList();
  	list.add(5, 8);
  	list.add(10, 12);
  	list.add(14, 15);
  	list.add(18, 20);
  	list.add(25, 30);
  	return list;
	}
	
	
	private void checkListCenter(Iterator<SimpleSequenceInterval> iterator) {
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(12, element.getLastPos());
  	element = iterator.next();
  	assertEquals(14, element.getFirstPos());
  	assertEquals(15, element.getLastPos());
  	element = iterator.next();
  	assertEquals(18, element.getFirstPos());
  	assertEquals(20, element.getLastPos());
	}
	
	
  @Test
  public void test_getOverlappingElements1() {
  	NonOverlappingIntervalList list = getList();
  	checkListCenter(list.getOverlappingElements(9, 18).iterator());
  }
	
	
  @Test
  public void test_getOverlappingElements2() {
  	NonOverlappingIntervalList list = getList();
  	checkListCenter(list.getOverlappingElements(12, 18).iterator());
  }
	
	
  @Test
  public void test_getOverlappingElements3() {
  	NonOverlappingIntervalList list = getList();
  	checkListCenter(list.getOverlappingElements(9, 18).iterator());
  }
  
  
  @Test
  public void test_add1() {
  	NonOverlappingIntervalList list = getList();
  	list.add(13, 17);

  	Iterator<SimpleSequenceInterval> iterator = list.iterator();
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(5, element.getFirstPos());
  	assertEquals(8, element.getLastPos());
  	element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(20, element.getLastPos());
  	element = iterator.next();
  	assertEquals(25, element.getFirstPos());
  	assertEquals(30, element.getLastPos());
  }  


  @Test
  public void test_add2() {
  	NonOverlappingIntervalList list = getList();
  	list.add(18, 23);

  	Iterator<SimpleSequenceInterval> iterator = list.iterator();
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(5, element.getFirstPos());
  	assertEquals(8, element.getLastPos());
  	element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(12, element.getLastPos());
  	element = iterator.next();
  	assertEquals(14, element.getFirstPos());
  	assertEquals(15, element.getLastPos());
  	element = iterator.next();
  	assertEquals(18, element.getFirstPos());
  	assertEquals(23, element.getLastPos());
  	element = iterator.next();
  	assertEquals(25, element.getFirstPos());
  	assertEquals(30, element.getLastPos());
  }  


  /**
   * This method makes sure that the extensions to add() are automatically used by addAll(). (This depending on 
   * the precursor implementation.) 
   */
  @Test
  public void test_addAll() {
  	Collection<SimpleSequenceInterval> newElements = new ArrayList<SimpleSequenceInterval>(2);
  	newElements.add(new SimpleSequenceInterval(13, 13));
  	newElements.add(new SimpleSequenceInterval(21, 32));
  	
  	NonOverlappingIntervalList list = getList();
  	list.addAll(newElements);

  	Iterator<SimpleSequenceInterval> iterator = list.iterator();
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(5, element.getFirstPos());
  	assertEquals(8, element.getLastPos());
  	element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(15, element.getLastPos());
  	element = iterator.next();
  	assertEquals(18, element.getFirstPos());
  	assertEquals(32, element.getLastPos());
  }
  
  
  @Test
  public void test_contains() {
  	NonOverlappingIntervalList list = getList();
  	assertFalse(list.contains(0));
  	assertTrue(list.contains(10));
  	assertTrue(list.contains(11));
  	assertTrue(list.contains(12));
  	assertFalse(list.contains(13));
  	assertTrue(list.contains(14));
  	assertFalse(list.contains(40));
  }
  
  
  @Test
  public void test_containsAll() {
  	NonOverlappingIntervalList list = getList();
  	assertFalse(list.containsAll(0, 3));
  	assertTrue(list.containsAll(5, 8));
  	assertFalse(list.containsAll(4, 8));
  	assertFalse(list.containsAll(5, 9));
  	assertFalse(list.containsAll(11, 14));
  	assertTrue(list.containsAll(27, 29));
  }
  
  
  @Test
  public void test_movePositionForward() {
  	NonOverlappingIntervalList list = getList();
  	list.movePositions(14, 10);

  	Iterator<SimpleSequenceInterval> iterator = list.iterator();
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(5, element.getFirstPos());
  	assertEquals(8, element.getLastPos());
  	element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(12, element.getLastPos());
  	element = iterator.next();
  	assertEquals(24, element.getFirstPos());
  	assertEquals(25, element.getLastPos());
  	element = iterator.next();
  	assertEquals(28, element.getFirstPos());
  	assertEquals(30, element.getLastPos());
  	element = iterator.next();
  	assertEquals(35, element.getFirstPos());
  	assertEquals(40, element.getLastPos());
  }
  
  
  @Test
  public void test_movePositionBackwards() {
  	NonOverlappingIntervalList list = getList();
  	list.movePositions(14, -1);

  	Iterator<SimpleSequenceInterval> iterator = list.iterator();
  	SimpleSequenceInterval element = iterator.next();
  	assertEquals(5, element.getFirstPos());
  	assertEquals(8, element.getLastPos());
  	element = iterator.next();
  	assertEquals(10, element.getFirstPos());
  	assertEquals(14, element.getLastPos());
  	element = iterator.next();
  	assertEquals(17, element.getFirstPos());
  	assertEquals(19, element.getLastPos());
  	element = iterator.next();
  	assertEquals(24, element.getFirstPos());
  	assertEquals(29, element.getLastPos());
  }
}
