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
package info.bioinfweb.commons.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



/**
 * Classes that decorate (wrap) a {@link List} can be inherited from this class.
 * <p>
 * In addition to the delegation of all methods to the underlying list this class provides the empty methods 
 * {@link #afterAdd(int, Collection)}, {@link #afterReplace(int, Object, Object)} and {@link #afterRemove(Collection)} 
 * that can be overwritten by inherited classes to react to modifications of the underlying list. They are called from 
 * all according methods that modify the contents of this list, including these of related objects like iterators of sublists. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * 
 * @param <E> the type of elements in the decorated (and this) list
 */
public abstract class ListDecorator<E> implements List<E> {
	private List<E> underlyingList;

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param underlyingList - the list to be decorated
	 */
	public ListDecorator(List<E> underlyingList) {
		super();
		this.underlyingList = underlyingList;
	}


	/**
	 * Returns the list that is decorated by this instance.
	 * 
	 * @return the decorated list instance
	 */
	protected List<E> getUnderlyingList() {
		return underlyingList;
	}
	
	
	/**
	 * This method is called at the beginning of all methods of this list that add elements to it (including
	 * methods called on the results of {@link #subList(int, int)} or iterators) before the actual inserting 
	 * happens. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications of the list.
	 * <p>
	 * If more than one element will be added in one operation (e.g. {@link #addAll(Collection)} this method
	 * is only called once before the first elements is inserted.
	 * 
	 * @param index - the index where the first new element will be inserted
	 * @param addedElements - the elements that shall be added (Always contains at least one element.) 
	 */
	protected void beforeAdd(int index, Collection<? extends E> addedElements) {}
	
	
	private void beforeAdd(int index, E element) {
		beforeAdd(index, Collections.nCopies(1, element));
	}

	
	/**
	 * This method is called at the end of all methods of this list that add elements to it, including
	 * methods called on the results of {@link #subList(int, int) or iterators}. This default implementation 
	 * is empty and can be overwritten by inherited classes which want to track modifications of the list.
	 * <p>
	 * If more than one element is added in one operation (e.g. {@link #addAll(Collection)} this method
	 * is only called once when all elements have been inserted.
	 * <p>
	 * Note that this method is not called if an add operation is not successful. In such a case only
	 * {@link #beforeAdd(int, Collection)} is called.
	 * 
	 * @param index - the index where the first new element has been inserted
	 * @param addedElements - the elements that have been added (Always contains at least one element.) 
	 */
	protected void afterAdd(int index, Collection<? extends E> addedElements) {}
	
	
	private void afterAdd(int index, E element) {
		afterAdd(index, Collections.nCopies(1, element));
	}

	
	/**
	 * This method is called at the beginning of {@link #set(int, Object)} to indicate that an element will be
	 * replaced. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications of the list.
	 * 
	 * @param index - the index where the element will be replaced
	 * @param currentElement - the element that will be replaced
	 * @param newElement - the new element that shall replace the current one
	 */
	protected void beforeReplace(int index, E currentElement, E newElement) {}

	
	/**
	 * This method is called at the end of {@link #set(int, Object)} to indicate that an element has been
	 * replaced. This default implementation is empty and can be overwritten by inherited classes which want 
	 * to track modifications of the list.
	 * 
	 * @param index - the index where the element has been replaced
	 * @param previousElement - the element that was replaced
	 * @param currentElement - the new element that is now contained in the list
	 */
	protected void afterReplace(int index, E previousElement, E currentElement) {}

	
	/**
	 * This method is called at the beginning of all methods of this list that remove elements from it (including
	 * methods called on the results of {@link #subList(int, int)} or iterators) before the actual deletion 
	 * happens. This default implementation is empty and can be overwritten by inherited classes which want to 
	 * track modifications of the list.
	 * <p>
	 * If more than one element will be removed in one operation (e.g. {@link #retainAll(Collection)} this method
	 * is only called once before the first element is removed.
	 * 
	 * @param removedElements - the elements that have been removed.
	 */
	protected void beforeRemove(Collection<Object> removedElements) {}
	
	
	private void beforeRemove(Object element) {
		beforeRemove(Collections.nCopies(1, element));
	}

	
	/**
	 * This method is called at the end of all methods of this list that remove elements from it, including
	 * methods called on the results of {@link #subList(int, int)} or iterators. This default implementation 
	 * is empty and can be overwritten by inherited classes which want to track modifications of the list.
	 * <p>
	 * If more than one element is removed in one operation (e.g. {@link #retainAll(Collection)} this method
	 * is only called once when all elements have been removed.
	 * <p>
	 * Note that this method is not called if an add operation is not successful. In such a case only
	 * {@link #beforeRemove(Collection)} is called.
	 * 
	 * @param removedElements - the elements that have been removed.
	 */
	protected void afterRemove(Collection<? extends E> removedElements) {}
	
	
	private void afterRemove(E element) {
		afterRemove(Collections.nCopies(1, element));
	}

	
	protected ListIterator<E> createDecoratedListIterator(ListIterator<E> iterator) {
		final ListDecorator<E> list = this;
		return new ListIteratorDecorator<E>(iterator) {
					@Override
					protected void beforeAdd(int index, E element) {
						list.beforeAdd(index, element);
					}

					@Override
					protected void afterAdd(int index, E element) {
						list.afterAdd(index, element);
					}
		
					@Override
					protected void beforeReplace(int index, E currentElement, E newElement) {
						list.beforeReplace(index, currentElement, newElement);
					}

					@Override
					protected void afterReplace(int index, E previousElement, E currentElement) {
						list.afterReplace(index, previousElement, currentElement);
					}
		
					@Override
					protected void beforeRemove(Object element) {
						list.beforeRemove(element);
					}

					@Override
					protected void afterRemove(E element) {
						list.afterRemove(element);
					}
				};
	}
	
	
	@Override
	public boolean add(E element) {
		int index = size() - 1;
		beforeAdd(index, element);
		boolean result = underlyingList.add(element);
		if (result) {
			afterAdd(index, element);
		}
		return result;
	}


	@Override
	public void add(int index, E element) {
		beforeAdd(index, element);
		underlyingList.add(index, element);
		afterAdd(index, element);
	}


	@Override
	public boolean addAll(Collection<? extends E> c) {
		beforeAdd(size(), c);
		boolean result = getUnderlyingList().addAll(c);
		if (result) {
			afterAdd(size() - c.size(), c);
		}
		return result;
	}


	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		beforeAdd(index, c);
		boolean result = getUnderlyingList().addAll(index, c);
		if (result) {
			afterAdd(index, c);
		}
		return result;
	}


	@Override
	public void clear() {
		Collection<E> copy = new ArrayList<E>(size());  // Clone cannot be used here, because changes there also affect the original list.
		copy.addAll(this);
		copy = Collections.unmodifiableCollection(copy);
		
		beforeRemove(copy);
		getUnderlyingList().clear();
		afterRemove(copy);
	}


	@Override
	public boolean contains(Object element) {
		return underlyingList.contains(element);
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		return underlyingList.containsAll(c);
	}


	@Override
	public boolean equals(Object o) {
		return underlyingList.equals(o);
	}


	@Override
	public E get(int index) {
		return underlyingList.get(index);
	}


	@Override
	public int hashCode() {
		return underlyingList.hashCode();
	}


	@Override
	public int indexOf(Object o) {
		return underlyingList.indexOf(o);
	}


	@Override
	public boolean isEmpty() {
		return underlyingList.isEmpty();
	}


	@Override
	public Iterator<E> iterator() {
		final Iterator<E> iterator = getUnderlyingList().iterator();
		return new Iterator<E>() {
					private E currentElement = null;
					
					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}
		
					@Override
					public E next() {
						currentElement = iterator.next();
						return currentElement;
					}
		
					@Override
					public void remove() {
						beforeRemove(currentElement);
						iterator.remove();  // Would throw an exception if currentArea would still be null.
						afterRemove(currentElement);
					}
				};
	}


	@Override
	public int lastIndexOf(Object o) {
		return underlyingList.lastIndexOf(o);
	}


	@Override
	public ListIterator<E> listIterator() {
		return createDecoratedListIterator(underlyingList.listIterator());
	}


	@Override
	public ListIterator<E> listIterator(int index) {
		return createDecoratedListIterator(underlyingList.listIterator(index));
	}


	@Override
	public E remove(int index) {
		beforeRemove(get(index));
		E result = getUnderlyingList().remove(index);
		afterRemove(result);
		return result;
	}


	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		beforeRemove(o);
		boolean result = getUnderlyingList().remove(o);
		if (result) {
			afterRemove((E)o);  // If this would not be of type E, result should have been false.
		}
		return result;
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		Collection<E> removedElements = new ArrayList<E>(size());  // c cannot be used here, because it may contain elements which are not contained in this instance.
		removedElements.addAll(this);
		removedElements.retainAll(c);
		removedElements = Collections.unmodifiableCollection(removedElements);
		
		beforeRemove(removedElements);
		boolean result = getUnderlyingList().removeAll(c);
		if (result) {
			afterRemove(removedElements);
		}
		return result;
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		Collection<E> removedElements = new ArrayList<E>(size());  // Clone cannot be used here, because changes there also affect the original list.
		removedElements.addAll(this);
		removedElements.removeAll(c);
		removedElements = Collections.unmodifiableCollection(removedElements);
		
		beforeRemove(removedElements);
		boolean result = getUnderlyingList().retainAll(c);
		if (result) {
			afterRemove(removedElements);
		}
		return result;
	}


	@Override
	public E set(int index, E element) {
		beforeReplace(index, get(index), element);
		E result = getUnderlyingList().set(index, element);
		afterReplace(index, result, element);
		return result;
	}


	@Override
	public int size() {
		return underlyingList.size();
	}


	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		final ListDecorator<E> thisList = this;
		return new ListDecorator<E>(getUnderlyingList().subList(fromIndex, toIndex)) {
					@Override
					protected void beforeAdd(int index, Collection<? extends E> addedElements) {
						thisList.beforeAdd(index, addedElements);
					}
		
					@Override
					protected void afterAdd(int index, Collection<? extends E> addedElements) {
						thisList.afterAdd(index, addedElements);
					}
		
					@Override
					protected void beforeReplace(int index, E currentElement, E newElement) {
						thisList.beforeReplace(index, currentElement, newElement);
					}
		
					@Override
					protected void afterReplace(int index, E previousElement, E currentElement) {
						thisList.afterReplace(index, previousElement, currentElement);
					}
		
					@Override
					protected void beforeRemove(Collection<Object> removedElements) {
						thisList.beforeRemove(removedElements);
					}

					@Override
					protected void afterRemove(Collection<? extends E> removedElements) {
						thisList.afterRemove(removedElements);
					}
				};
	}


	@Override
	public Object[] toArray() {
		return underlyingList.toArray();
	}


	@Override
	public <T> T[] toArray(T[] a) {
		return underlyingList.toArray(a);
	}
}