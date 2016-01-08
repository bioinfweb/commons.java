/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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

import java.util.List;



/**
 * Event that indicates that one or more new elements will be or have been added to an implementation of {@link List}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * 
 * @see ListChangeListener
 * @see ObservableList
 *
 * @param <E> the type of elements in the list where the change occurred
 */
public class ListReplaceEvent<E> extends ListChangeEvent<E> {
	private E oldElement;
	private E newElement;

	
	/**
	 * Creates a new event object.
	 * 
	 * @param source - the list instance that has been modified
	 * @param oldElement - the element that will be or has been replaced in the list
	 * @param newElement - the element that is the replacement for the old element
	 */
	public ListReplaceEvent(List<E> source, int index, E oldElement, E newElement) {
		super(source, ListChangeType.REPLACEMENT, index);
		this.oldElement = oldElement;
		this.newElement = newElement;
	}


	/**
	 * Returns the element that has been replaced or will be replaced.
	 * 
	 * @return the removed element
	 */
	public E getOldElement() {
		return oldElement;
	}


	/**
	 * Returns the element that is now contained in the list.
	 * 
	 * @return the element that will or did replaced another element in the list
	 */
	public E getNewElement() {
		return newElement;
	}
}
