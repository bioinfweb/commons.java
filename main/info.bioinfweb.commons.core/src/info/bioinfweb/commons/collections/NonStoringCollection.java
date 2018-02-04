/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.collections;


import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;



/**
 * Instances of this class do not store objects add to them. This can be useful if objects are add to a collection 
 * in a standardized way but in some cases are not intended to be used later. To avoid using memory this non-storing 
 * collection can be used. 
 * 
 * @author Sarah Wiechers
 *
 * @param <E> any Object type
 */
public class NonStoringCollection<E> implements Collection<E> {

	
	/**
	 * Always returns {@code false}. The object will not be stored.
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean add(Object e) {
		return false;
	}

	
	/**
	 * Always returns {@code false}. The object in the collection will not be stored.
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean addAll(Collection c) {
		return false;
	}
	

	@Override
	public void clear() {}
	

	/**
	 * Since no objects are stored in this collection this method will always return {@code false}.
	 * 
	 * @return always {@code false}
	 */
	@Override
	public boolean contains(Object o) {		
		return false;
	}
	

	/**
	 * Since no objects are stored in this collection this method will always return {@code false}.
	 * 
	 * @return always {@code false}
	 */
	@Override
	public boolean containsAll(Collection c) {
		return false;
	}
	

	/**
	 * Since no objects are stored in this collection this method will always return {@code true}.
	 * 
	 * @return always {@code true}
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}

	
	/**
	 * Since no objects are stored in this collection this method will always return an empty iterator.
	 * 
	 * @return an empty iterator
	 */
	@Override
	public Iterator iterator() {
		return Collections.emptyIterator();
	}
	

	/**
	 * Since no objects are stored in this collection, no objects can be removed. 
	 * Therefore this method will always return {@code false}.
	 * 
	 * @return always {@code false}
	 */
	@Override
	public boolean remove(Object o) {
		return false;
	}

	
	/**
	 * Since no objects are stored in this collection, no objects can be removed. 
	 * Therefore this method will always return {@code false}.
	 * 
	 * @return always {@code false}
	 */
	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	
	/**
	 * Since no objects are stored in this collection, no objects can be removed. 
	 * Therefore this method will always return {@code false}.
	 * 
	 * @return always {@code false}
	 */
	@Override
	public boolean retainAll(Collection c) {
		return false;
	}


	/**
	 * Since no objects are stored in this collection its size will always be 0.
	 * 
	 * @return 0
	 */
	@Override
	public int size() {
		return 0;
	}
	

	@Override
	public Object[] toArray() {
		return new Object[0];
	}
	

	@Override
	public Object[] toArray(Object[] a) {
		return new Object[0];
	}	
}
