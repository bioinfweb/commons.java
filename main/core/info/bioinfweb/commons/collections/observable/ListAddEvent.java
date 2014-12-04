/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
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
package info.bioinfweb.commons.collections.observable;


import info.bioinfweb.commons.collections.ListChangeType;

import java.util.Collection;
import java.util.List;



/**
 * Event that indicates that one or more new elements have been added to an implementation of {@link List}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * 
 * @see ListChangeListener
 * @see ObservableList
 *
 * @param <E> the type of elements in the list where the change occurred
 */
public class ListAddEvent<E> extends ListMultipleChangesEvent<E> {
	private int index;

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param source - the list instance that has been modified
	 * @param index - the index the first affected element has in the list
	 * @param affectedElements - a list of elements that have been inserted
	 */
	public ListAddEvent(List<E> source, int index, Collection<? extends E> addedElements) {
		super(source, ListChangeType.INSERTION, addedElements);
		this.index = index;
	}


	/**
	 * Creates a new event object. Use this constructor if only one element is affected.
	 * 
	 * @param source - the list instance that has been modified
	 * @param affectedElements - a list of elements that have been inserted
	 */
	public ListAddEvent(List<E> source, int index, E affectedElement) {
		super(source, ListChangeType.INSERTION, affectedElement);
		this.index = index;
	}


	/**
	 * Returns the index the first inserted element has in the list.
	 * 
	 * @return the index of the first new element
	 */
	public int getIndex() {
		return index;
	}
}