/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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


import info.bioinfweb.commons.collections.ListDecorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Decorator for an instance of {@link List} that allows to track modifications of the underlying list
 * using an {@link ListChangeListener}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 *
 * @param <E> the type of elements in the decorated (and this) list
 */
public class ObservableList<E> extends ListDecorator<E> {
	private List<ListChangeListener<E>> changeListeners = new ArrayList<ListChangeListener<E>>();
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param underlyingList - the list to be observed
	 */
	public ObservableList(List<E> underlyingList) {
		super(underlyingList);
	}

	
	/**
	 * Adds a change listener to track changes of the underlying list.
	 * 
	 * @param listener - the listener to added
	 */
	public void addListChangeListener(ListChangeListener<E> listener) {
		changeListeners.add(listener);
	}
	

	/**
	 * Removes a listener from this list.
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if this list contained the specified element 
	 */
	public boolean removeListChangeListener(ListChangeListener<E> listener) {
		return changeListeners.remove(listener);
	}
	
	
	@Override
	protected void beforeAdd(int index, Collection<? extends E> addedElements) {
		ListAddEvent<E> event = new ListAddEvent<E>(this, index, addedElements);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.beforeElementsAdded(event);
		}
	}


	@Override
	protected void afterAdd(int index, Collection<? extends E> addedElements) {
		ListAddEvent<E> event = new ListAddEvent<E>(this, index, addedElements);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.afterElementsAdded(event);
		}
	}


	@Override
	protected void beforeReplace(int index, E currentElement, E newElement) {
		ListReplaceEvent<E> event = new ListReplaceEvent<E>(this, index, currentElement, newElement);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.beforeElementReplaced(event);
		}
	}


	@Override
	protected void afterReplace(int index, E previousElement, E currentElement) {
		ListReplaceEvent<E> event = new ListReplaceEvent<E>(this, index, previousElement, currentElement);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.afterElementReplaced(event);
		}
	}


	@Override
	protected void beforeRemove(int index, Collection<Object> removedElements) {
		ListRemoveEvent<E, Object> event = new ListRemoveEvent<E, Object>(this, index, removedElements);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.beforeElementsRemoved(event);
		}
	}


	@Override
	protected void afterRemove(int index, Collection<? extends E> removedElements) {
		ListRemoveEvent<E, E> event = new ListRemoveEvent<E, E>(this, index, removedElements);
		for (ListChangeListener<E> listener : changeListeners) {
			listener.afterElementsRemoved(event);
		}
	}
}
