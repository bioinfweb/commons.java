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
