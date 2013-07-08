package info.webinsel.util.collections;


import java.util.Iterator;

import org.junit.* ;


import static org.junit.Assert.* ;



public class NonOverlappingIntervalListTest {
	private NonOverlappingIntervalList getList() {
  	NonOverlappingIntervalList list = new NonOverlappingIntervalList();
  	list.addInterval(5, 8);
  	list.addInterval(10, 12);
  	list.addInterval(14, 15);
  	list.addInterval(18, 20);
  	list.addInterval(25, 30);
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
  public void test_add() {
  	NonOverlappingIntervalList list = getList();
  	list.addInterval(13, 17);

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
}
