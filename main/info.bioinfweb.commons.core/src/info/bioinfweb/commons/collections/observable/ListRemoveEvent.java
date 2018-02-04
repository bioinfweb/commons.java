/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.collections.observable;


import info.bioinfweb.commons.collections.ListChangeType;

import java.util.Collection;
import java.util.List;



/**
 * Event that indicates that one or more elements have been removed from an implementation of {@link List}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * 
 * @see ListChangeListener
 * @see ObservableList
 *
 * @param <L> the type of elements in the list to be modified
 * @param <E> the type of elements will be or have been removed (Note that these types can only be different if
 *            this event is fired before the removal, because than elements which are not in the associated list
 *            might anyway be requested to be removed.)
 */
public class ListRemoveEvent<L, E> extends ListChangeEvent<L> {
	private ListMultipleChangesEvent<E> decoratedEvent;
	
	
	/**
	 * Creates a new event object.
	 * 
	 * @param source - the list instance that has been modified
	 * @param affectedElements - a list of elements that have been removed
	 */
	public ListRemoveEvent(List<L> source, int index, Collection<? extends E> affectedElements) {
		super(source, ListChangeType.DELETION, index);
		decoratedEvent = new ListMultipleChangesEvent<E>((List)source, ListChangeType.DELETION, index, affectedElements);  // null cannot be passed as the list, because EventObject would than throw an exception. //TODO Think about better solution.
	}

	
	/**
	 * Creates a new event object. Use this constructor if only one element is affected.
	 * 
	 * @param source the list instance that has been modified
	 * @param index the index the first affected element has in the list
	 * @param affectedElement a list of elements that have been removed
	 */
	public ListRemoveEvent(List<L> source, int index, E affectedElement) {
		super(source, ListChangeType.DELETION, index);
		decoratedEvent = new ListMultipleChangesEvent<E>((List)source, ListChangeType.DELETION, index, affectedElement);  // null cannot be passed as the list, because EventObject would than throw an exception. //TODO Think about better solution.
	}


	/**
	 * Returns a collection of all elements that will be or have been removed from the list.
	 * <p>
	 * Note that this collection may contain elements have never been contained in the associated
	 * list, if this event is fired before the removal operation. 
	 * 
	 * @return a collection of the affected list elements
	 */
	public Collection<? extends E> getAffectedElements() {
		return decoratedEvent.getAffectedElements();
	}


	/**
	 * Returns the first affected element. Convenience method if only one element is affected.
	 * 
	 * @return the fist affected element in {@link #getAffectedElements()}
	 */
	public E getAffectedElement() {
		return decoratedEvent.getAffectedElement();
	}
}
