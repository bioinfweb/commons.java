package info.bioinfweb.commons.collections;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import info.bioinfweb.commons.collections.observable.ListAddEvent;
import info.bioinfweb.commons.collections.observable.ListChangeAdapter;
import info.bioinfweb.commons.collections.observable.ListChangeEvent;
import info.bioinfweb.commons.collections.observable.ListChangeListener;
import info.bioinfweb.commons.collections.observable.ListRemoveEvent;
import info.bioinfweb.commons.collections.observable.ListReplaceEvent;
import info.bioinfweb.commons.collections.observable.ObservableList;

import org.junit.* ;

import static org.junit.Assert.* ;



public class ListDecoratorTest {
	private static class EventLogger extends ListChangeAdapter<String> implements ListChangeListener<String> {  //TODO Also test before* events.
		private Queue<ListChangeEvent<String>> eventQueue = new ArrayDeque<ListChangeEvent<String>>();
		
		public Queue<ListChangeEvent<String>> getEventQueue() {
			return eventQueue;
		}

		@Override
		public void afterElementsAdded(ListAddEvent<String> event) {
			eventQueue.add(event);
		}

		@Override
		public void afterElementReplaced(ListReplaceEvent<String> event) {
			eventQueue.add(event);
		}

		@Override
		public void afterElementsRemoved(ListRemoveEvent<String, String> event) {
			eventQueue.add(event);
		}
		
		public void assertEvent(ListChangeType type, int index, String... elements) {
			ListChangeEvent<String> event = eventQueue.poll();
			
			assertEquals(type, event.getType());
			assertEquals(index, event.getIndex());
			
			int pos = 0;
			Iterator<? extends String> iterator;
			switch (event.getType()) {
				case INSERTION:
					iterator = ((ListAddEvent<String>)event).getAffectedElements().iterator();
					while ((pos < elements.length) && iterator.hasNext()) {
						assertEquals(elements[pos], iterator.next());
						pos++;
					}
					assertEquals(pos, elements.length);  // Test if all elements were found.
					break;
				case DELETION:
					iterator = ((ListRemoveEvent<String, String>)event).getAffectedElements().iterator();
					while ((pos < elements.length) && iterator.hasNext()) {
						assertEquals(elements[pos], iterator.next());
						pos++;
					}
					assertEquals(pos, elements.length);  // Test if all elements were found.
					break;
				case REPLACEMENT:
					if (elements.length > 0) {
						assertEquals(elements[0], ((ListReplaceEvent<String>)event).getOldElement());
					}
					if (elements.length > 1) {
						assertEquals(elements[1], ((ListReplaceEvent<String>)event).getNewElement());
					}
					break;
			}
		}
	}
	
	
	@Test
	public void test_removeAll() {
		ObservableList<String> list = new ObservableList<String>(new ArrayList<String>());
		EventLogger logger = new EventLogger();
		list.addListChangeListener(logger);
		
		list.add("A");
		logger.assertEvent(ListChangeType.INSERTION, 0, "A");
		list.add("B");
		logger.assertEvent(ListChangeType.INSERTION, 1, "B");
		list.add("C");
		logger.assertEvent(ListChangeType.INSERTION, 2, "C");
		list.add("D");
		logger.assertEvent(ListChangeType.INSERTION, 3, "D");
		list.add("E");
		logger.assertEvent(ListChangeType.INSERTION, 4, "E");
		
		List<String> remove = new ArrayList<String>();
		remove.add("B");
		remove.add("C");
		remove.add("E");
		
		list.removeAll(remove);
		logger.assertEvent(ListChangeType.DELETION, 1, "B", "C");
		logger.assertEvent(ListChangeType.DELETION, 2, "E");
	}
	
	
	@Test
	public void test_subList() {
		ObservableList<String> list = new ObservableList<String>(new ArrayList<String>());
		EventLogger logger = new EventLogger();
		list.addListChangeListener(logger);
		
		list.add("A");
		logger.assertEvent(ListChangeType.INSERTION, 0, "A");
		list.add("B");
		logger.assertEvent(ListChangeType.INSERTION, 1, "B");
		list.add("C");
		logger.assertEvent(ListChangeType.INSERTION, 2, "C");
		list.add("D");
		logger.assertEvent(ListChangeType.INSERTION, 3, "D");
		list.add("E");
		logger.assertEvent(ListChangeType.INSERTION, 4, "E");

		List<String> subList = list.subList(1, 3);
		subList.clear();
		logger.assertEvent(ListChangeType.DELETION, 1, "B", "C");
	}
	
	
	@Test
	public void test_iterator() {
		ObservableList<String> list = new ObservableList<String>(new ArrayList<String>());
		EventLogger logger = new EventLogger();
		list.addListChangeListener(logger);
		
		list.add("A");
		logger.assertEvent(ListChangeType.INSERTION, 0, "A");
		list.add("B");
		logger.assertEvent(ListChangeType.INSERTION, 1, "B");
		list.add("C");
		logger.assertEvent(ListChangeType.INSERTION, 2, "C");
		list.add("D");
		logger.assertEvent(ListChangeType.INSERTION, 3, "D");
		list.add("E");
		logger.assertEvent(ListChangeType.INSERTION, 4, "E");

		Iterator<String> iterator = list.iterator();
		iterator.next();
		iterator.remove();
		logger.assertEvent(ListChangeType.DELETION, 0, "A");

		iterator.next();
		iterator.next();
		iterator.remove();
		logger.assertEvent(ListChangeType.DELETION, 1, "C");
	}
}
