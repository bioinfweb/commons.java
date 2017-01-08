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
package info.bioinfweb.commons.collections;


import java.util.ListIterator;



/**
 * Classes that decorate (wrap) a {@link ListIterator} can be inherited from this class.
 * <p>
 * In addition to the delegation of all methods to the underlying iterator this class provides the empty methods 
 * {@link #afterAdd(int, Object)}, {@link #afterReplace(int, Object, Object)} and {@link #afterRemove(int, Object)} 
 * that can be overwritten by inherited classes to react to modifications of the associated list made by this iterator. 
 * They are called from the according methods that make such modifications. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * 
 * @param <E> the type of elements over which this instance iterates
 */
public class ListIteratorDecorator<E> implements ListIterator<E> {
	private ListIterator<E> underlyingIterator;
  private E current = null;

  
  /**
   * Creates a new instance of this class.
   * 
   * @param underlyingIterator - the list iterator to be decorated by this instance
   */
  public ListIteratorDecorator(ListIterator<E> underlyingIterator) {
		super();
		this.underlyingIterator = underlyingIterator;
	}


	/**
   * Returns the list iterator that has been decorated by this instance.
   * 
   * @return the underlying list iterator
   */
  protected ListIterator<E> getUnderlyingIterator() {
		return underlyingIterator;
	}


	/**
	 * This method is called at the beginning of {@link #add(Object)} to indicate that an element will be
	 * inserted. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications made by this iterator.
	 * 
	 * @param index the index where the first new element will be inserted
	 * @param element the new element that will be inserted 
	 */
	protected void beforeAdd(int index, E element) {}
  

	/**
	 * This method is called at the end of {@link #add(Object)} to indicate that an element has been
	 * inserted. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications made by this iterator.
	 * 
	 * @param index the index where the first new element has been inserted
	 * @param element the new element that has been inserted 
	 */
	protected void afterAdd(int index, E element) {}
  

	/**
	 * This method is called at the beginning of {@link #set(Object)} to indicate that an element will be
	 * replaced. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications made by this iterator.
	 * 
	 * @param index the index where the element will be replaced
	 * @param currentElement the element that will be replaced
	 * @param newElement the new element that will replace the current element
	 */
	protected void beforeReplace(int index, E currentElement, E newElement) {}
  

	/**
	 * This method is called at the end of {@link #set(Object)} to indicate that an element has been
	 * replaced. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications made by this iterator.
	 * 
	 * @param index the index where the element has been replaced
	 * @param previousElement the element that has been replaced
	 * @param currentElement the new element that is now contained in the list
	 */
	protected void afterReplace(int index, E previousElement, E currentElement) {}
  

	/**
	 * This method is called at the beginning of {@link #remove()} to indicate that an element will be removed from
	 * the associated list. This default implementation is empty and can be overwritten by inherited classes which 
	 * want to track modifications made by this iterator.
	 * 
	 * @param index the index of the element to be removed
	 * @param element the element that will be removed 
	 */
  protected void beforeRemove(int index, Object element) {}
  

	/**
	 * This method is called at the end of {@link #remove()} to indicate that an element has been removed from
	 * the associated list. This default implementation is empty and can be overwritten by inherited classes which 
	 * want to track modifications made by this iterator.
	 * 
	 * @param index the index of the element that has been removed
	 * @param element the element that has been removed 
	 */
  protected void afterRemove(int index, E element) {}
  

	@Override
	public void add(E e) {
		beforeAdd(underlyingIterator.nextIndex(), e);
		underlyingIterator.add(e);
		afterAdd(underlyingIterator.nextIndex(), e);
	}

	
	@Override
	public boolean hasNext() {
		return underlyingIterator.hasNext();
	}

	
	@Override
	public boolean hasPrevious() {
		return underlyingIterator.hasPrevious();
	}

	
	@Override
	public E next() {
		current = underlyingIterator.next();
		return current;
	}

	
	@Override
	public int nextIndex() {
		return underlyingIterator.nextIndex();
	}

	
	@Override
	public E previous() {
		current = underlyingIterator.previous();
		return current;
	}

	@Override
	public int previousIndex() {
		return underlyingIterator.previousIndex();
	}

	
	@Override
	public void remove() {
		int index = underlyingIterator.nextIndex() - 1;
		beforeRemove(index, current);
		underlyingIterator.remove();
		afterRemove(index, current);
	}

	
	@Override
	public void set(E e) {
		int index = underlyingIterator.nextIndex() - 1;
		beforeReplace(index, current, e);
		underlyingIterator.set(e);
		afterReplace(index, current, e);
	}
}
