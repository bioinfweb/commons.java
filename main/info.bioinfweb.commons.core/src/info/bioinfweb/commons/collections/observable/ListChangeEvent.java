/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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

import java.util.EventObject;
import java.util.List;



/**
 * An event object indicating that an instance of {@link List} has been modified.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 *
 * @see ListChangeListener
 * @see ObservableList
 *
 * @param <E> the type of elements in the list where the change occurred
 */
public class ListChangeEvent<E> extends EventObject {
	private ListChangeType type;
	private int index;
	
	
	/**
	 * Creates a new event object.
	 * 
	 * @param source - the list instance that has been modified
	 * @param type - the type of modification that happened
	 */
	public ListChangeEvent(List<E> source, ListChangeType type, int index) {
		super(source);
		this.type = type;
		this.index = index;
	}


	/**
	 * Returns the list instance where the change occurred.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> getSource() {
		return (List<E>)super.getSource();
	}


	/**
	 * Returns the type of change that happened.
	 * 
	 * @return a value enumerated by {@link ListChangeType}
	 */
	public ListChangeType getType() {
		return type;
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
