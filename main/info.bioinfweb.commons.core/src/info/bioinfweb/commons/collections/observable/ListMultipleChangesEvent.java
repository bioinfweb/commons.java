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
import java.util.Collections;
import java.util.List;



/**
 * An event object indicating that multiple modifications of an instance of {@link List} have happened.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 *
 * @see ListChangeListener
 * @see ObservableList
 *
 * @param <E> the type of elements in the list where the change occurred
 */
public class ListMultipleChangesEvent<E> extends ListChangeEvent<E> {
	private Collection<E> affectedElements;
	
	
	/**
	 * Creates a new event object.
	 * 
	 * @param source the list instance that has been modified
	 * @param type the type of modification that happened
	 * @param index the index the first affected element has in the list
	 * @param affectedElements a list of elements that have been affected (e.g. inserted or removed)
	 */
	public ListMultipleChangesEvent(List<E> source, ListChangeType type, int index, Collection<? extends E> affectedElements) {
		super(source, type, index);
		if (affectedElements.isEmpty()) {
			throw new IllegalArgumentException("At least one affected element has to be specified.");
		}
		this.affectedElements = Collections.unmodifiableCollection(affectedElements);
	}


	/**
	 * Creates a new event object. Use this constructor if only one element is affected.
	 * 
	 * @param source the list instance that has been modified
	 * @param type the type of modification that happened
	 * @param index the index the first affected element has in the list
	 * @param affectedElement a list of elements that have been affected (e.g. inserted, removed)
	 */
	public ListMultipleChangesEvent(List<E> source, ListChangeType type, int index, E affectedElement) {
		super(source, type, index);

		if (affectedElement == null) {
			throw new NullPointerException("Null is not a valid value for the affected element.");
		}
		affectedElements = Collections.nCopies(1, affectedElement);
	}


	/**
	 * Returns a collection of all elements that will be or have been affected by the list modification (e.g.
	 * all elements that have been add).
	 * 
	 * @return a collection of the affected list elements
	 */
	public Collection<? extends E> getAffectedElements() {
		return affectedElements;
	}
	
	
	/**
	 * Returns the first affected element. Convenience method if only one element is affected.
	 * 
	 * @return the fist affected element in {@link #getAffectedElements()}
	 */
	public E getAffectedElement() {
		if (!getAffectedElements().isEmpty()) {
			return getAffectedElements().iterator().next();
		}
		else {
			throw new RuntimeException("The list affected elements was empty.");  // Constructor does not allow empty lists.
		}
	}
}
