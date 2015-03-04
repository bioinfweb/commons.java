/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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


import info.bioinfweb.commons.Math2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.TreeMultiset;



/**
 * Instances of this class are able to manage a set of sequence intervals (instances of classes implementing 
 * {@linkplain SequenceInterval}). The key feature is the efficient calculation of a subset of the stored 
 * interval elements that overlap with a defined interval (see {@link #getOverlappingElements(int, int)}. 
 * The actual efficiency depends on the specified interval length (see {@link #SequenceIntervalList(int, int)}).  
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> the type of elements that describe a sequence interval (the elements of the collection)
 */
public class SequenceIntervalList<E> implements Collection<E> {
	public static final int DEFAULT_INITIAL_SEQUENCE_LENGTH = 5000;
	public static final int DEFAULT_INTERVAL_LENGTH = 50;
	

	protected class IntervalInformation {
		private int intervalStart;
		private TreeMultiset<E> startList;
		private TreeMultiset<E> overlapList;
		
		
		public IntervalInformation(int intervalStart, Comparator<E> comparator) {
			super();
			this.intervalStart = intervalStart;
			startList = TreeMultiset.create(comparator);
			overlapList = TreeMultiset.create(comparator);
		}


		public int getIntervalStart() {
			return intervalStart;
		}


		public TreeMultiset<E> getStartList() {
			return startList;
		}
		
		
		public TreeMultiset<E> getOverlapList() {
			return overlapList;
		}
		
		
		public boolean add(E element) {
			int firstPos = getPositionAdapter().getFirstPos(element);
			int intervalEnd = getIntervalStart() + getIntervalLength() - 1; 
			if (firstPos > intervalEnd) {
				return false;
			}
			else {
				if (firstPos < getIntervalStart()) {
					return getOverlapList().add(element);
				}
				else {
					return getStartList().add(element);
				}
			}
		}


		public boolean remove(E element) {
			if (Math2.isBetween(getPositionAdapter().getFirstPos(element), 
					getIntervalStart(), getIntervalStart() + getIntervalLength() - 1)) {
				
				return getStartList().remove(element);
			}
			else {
				return getOverlapList().remove(element);
			}
	  }


		public void clear() {
			getStartList().clear();
			getOverlapList().clear();
		}
	}
	
	
  private ArrayList<IntervalInformation> intervalList;
  private int size = 0;
  private int intervalLength;
  private SequenceIntervalPositionAdapter<? super E> positionAdapter;
	

  /**
   * Equivalent to calling <code>SequenceIntervalList(positionAdapter, 
   * {@link #DEFAULT_INITIAL_SEQUENCE_LENGTH}, {@link DEFAULT_INTERVAL_LENGTH})</code>.
   */
  public SequenceIntervalList(SequenceIntervalPositionAdapter<? super E> positionAdapter) {
		this(positionAdapter, DEFAULT_INITIAL_SEQUENCE_LENGTH, DEFAULT_INTERVAL_LENGTH);
	}


	/**
	 * Creates a new instance of this class.
	 * 
	 * @param sequenceLength - the expected length of the whole sequence, which is populated by the 
	 *        interval elements that are going to be stored in the new instance (it will be extended if necessary) 
	 * @param intervalLength - the interval (number of sequence positions) in which a list of elements overlapping
	 *        with this interval is stored. (The value specified here has a major influence on the efficiency of
	 *        this instance. It should depend on how many entries are expected per interval. If you expect e.g. 
	 *        1 element every 100 sequence positions in average, you should specify a value like 500 or 1000.
	 *        Alternatively you could make the value dependent on the calls of {@link #getOverlappingElements(int, int)}.
	 *        If you already know that you are going to call this method with intervals of a certain length, you specify 
	 *        a value here that is in the dimension of that length.) 
	 */
	public SequenceIntervalList(SequenceIntervalPositionAdapter<? super E> positionAdapter, int sequenceLength, int intervalLength) {
		super();
		this.positionAdapter = positionAdapter;
		this.intervalLength = intervalLength;
		intervalList = new ArrayList<IntervalInformation>(sequenceLength / intervalLength + 1);
	}
	
	
	public int getIntervalLength() {
		return intervalLength;
	}


	public SequenceIntervalPositionAdapter<? super E> getPositionAdapter() {
		return positionAdapter;
	}


	/**
	 * Reinserts all elements of the list. This method should be called, if it is not sure anymore
	 * if the elements are contained in the correct lists, because their position might have changed.
	 */
	private void resortElements() {
		ArrayList<E> elements = new ArrayList<E>(size());
		elements.addAll(this);
		clear();
		addAll(elements);
	}
	
	
	/**
	 * Calling this methods leads to resorting of all elements in the list.
	 * 
	 * @param positionAdapter - the new position adapter
	 */
	public void setPositionAdapter(SequenceIntervalPositionAdapter<? super E> positionAdapter) {  //TODO Write a test for this method
		this.positionAdapter = positionAdapter;
		resortElements();
	}


	private int intervalIndex(int seqPos) {
		return seqPos / getIntervalLength();  // rounds down
	}
	
	
	/**
	 * Returns the interval lists at the specified position. If no lists exists for this position yet, they are created.
	 * Additionally a lists between the old maximum interval and the specified position are created.
	 * 
	 * @param intervalIndex - the index of the interval the returned list covers
	 * @return the lists covering the specified interval
	 */
	private IntervalInformation getIntervalInformation(int intervalIndex) {
		while (intervalIndex >= intervalList.size()) {
			intervalList.add(new IntervalInformation(intervalList.size() * getIntervalLength(), 
					(Comparator<E>)getPositionAdapter()));
		}
		return intervalList.get(intervalIndex);
	}
	
	
	/**
	 * Returns an iterator over all interval lists, that would contains an element reaching from 
	 * <code>firstSeqPos</code> to <code>lastSeqPos</code>.
	 * 
	 * @param firstSeqPos - the first position of the requested interval of the sequence (not the interval index)
	 * @param lastSeqPos - the last position of the requested interval of the sequence (not the interval index)
	 * @return the {@link Iterator} (created from a {@link LinkedList})
	 */
	private Iterator<IntervalInformation> getIntervalInformationIterator(int firstSeqPos, int lastSeqPos) {
		LinkedList<IntervalInformation> list = new LinkedList<IntervalInformation>();
		int firstInterval = intervalIndex(firstSeqPos);
		int lastInterval = intervalIndex(lastSeqPos);
		for (int i = firstInterval; i <= lastInterval; i++) {
			list.add(getIntervalInformation(i));
		}
		return list.iterator();
	}


	@Override
	public boolean add(E element) {
		Iterator<IntervalInformation> iterator = getIntervalInformationIterator(
				getPositionAdapter().getFirstPos(element), getPositionAdapter().getLastPos(element));
		boolean result = true;
		while (iterator.hasNext()) {
			result = result && iterator.next().add(element);
		}
		size++;  //TODO Müsste eigentlich result prüfen. (Macht aber nur Sinn, wenn einzelne Rückgabewerte der Schleife betrachtet werden.)
		return result;
	}
	
	
	public boolean remove(Object o) {
		try {
			E element = (E)o;
			Iterator<IntervalInformation> iterator = getIntervalInformationIterator(getPositionAdapter().getFirstPos(element), 
					getPositionAdapter().getLastPos(element));
			boolean result = true;
			while (iterator.hasNext()) {
				result = result && iterator.next().remove(element);
			}
			if (result) {  // Make sure the specified element was present in the list before 
				size--;
			}
			return result;
		}
		catch (ClassCastException e) {
			return false;
		}
	}
	
	
	/**
	 * Moves an element of the list to a different position. To change positions of elements only this method
	 * should be used. If the according properties of the element are set directly, the ordering of this list
	 * will not be correct anymore.
	 * 
	 * @param element - the element to be moved (with unchanged position values)
	 * @param newFirstPos - the new start position
	 * @param newLastPos - the new end position
	 * @return <code>true</code> if the element was moved, <code>false</code> if the element is not contained 
	 *         in the list
	 */
	public boolean move(E element, int newFirstPos, int newLastPos) {
		if (remove(element)) { 
			getPositionAdapter().setFirstPos(element, newFirstPos);
			getPositionAdapter().setLastPos(element, newLastPos);
			add(element);
			return true;
		}
		else {
			return false;
		}
	}

	
	private void addOverlappingElementsFromList(Collection<E> result, Collection<E> list, int firstPos, int lastPos) {
		Iterator<E> iterator = list.iterator();
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (Math2.overlaps(firstPos, lastPos, 
					getPositionAdapter().getFirstPos(element), getPositionAdapter().getLastPos(element))) {
				
				result.add(element);
			}
		}
	}
	
	
	public SortedSet<E> getOverlappingElements(int firstPos, int lastPos) {
		SortedSet<E> result = new TreeSet<E>((Comparator<E>)getPositionAdapter());
		Iterator<IntervalInformation> iterator = getIntervalInformationIterator(firstPos, lastPos);
		if (iterator.hasNext()) {
			// Add elements starting in the first interval or before:
			IntervalInformation first = iterator.next();
			addOverlappingElementsFromList(result, first.getOverlapList(), firstPos, lastPos);
			addOverlappingElementsFromList(result, first.getStartList(), firstPos, lastPos);

			// Add elements starting after the first interval:
			while (iterator.hasNext()) {
				addOverlappingElementsFromList(result, iterator.next().getStartList(), firstPos, lastPos);  //TODO This method must be called only for the last iteration. The others could also use result.addAll().
			}
		}
		return result;
	}
	
	
	private boolean collectionContainsPosition(Collection<E> collection, int pos) {
		Iterator<E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (Math2.isBetween(pos, getPositionAdapter().getFirstPos(element), getPositionAdapter().getLastPos(element))) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean containsPosition(int pos) {
		IntervalInformation info = getIntervalInformation(intervalIndex(pos));
		if (collectionContainsPosition(info.getOverlapList(), pos)) {
			return true;
		}
		else {
			return collectionContainsPosition(info.getStartList(), pos);
		}
	}


	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean result = true;
		for (E element: collection) {
			result = result && add(element);
		}
		return result;
	}


	@Override
	public void clear() {
		intervalList.clear();
		size = 0;
	}


	@Override
	public boolean contains(Object o) {
		try {
			return getIntervalInformation(intervalIndex(getPositionAdapter().getFirstPos((E)o))).getStartList().contains(o);
		}
		catch (ClassCastException e) {
			return false;
		}
	}


	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			boolean result = contains(element);
			if (!result) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean isEmpty() {
		return (size == 0);
	}


	/**
	 * Returns an iterator over the list. Each element is only returned once, no matter how many intervals it spans.
	 * The elements are ordered by their first position.
	 * 
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
					private Iterator<IntervalInformation> intervalIterator = intervalList.iterator();
					private Iterator<E> listIterator = null;
					
					
					private boolean secureFilledListIterator() {
						if (((listIterator == null) || !listIterator.hasNext()) && intervalIterator.hasNext()) {
							do {  // Search next filled list
								listIterator = intervalIterator.next().getStartList().iterator();
							} while (!listIterator.hasNext() && intervalIterator.hasNext());
						}
						return (listIterator != null) && listIterator.hasNext();  // Returns false of the end of intervalIterator was reached and the last listIterator contains no more elements
					}
					
					
					@Override
					public boolean hasNext() {
						return secureFilledListIterator();
					}
					
		
					@Override
					public E next() {
						secureFilledListIterator();
						return listIterator.next();
					}
					
		
					@Override
					public void remove() {
						throw new UnsupportedOperationException();  //TODO Could be implemented some time
					}
				};
	}


	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = true;
		for (Object element: collection) {
			result = result && remove(element);
		}
		return result;
	}


	@Override
	public boolean retainAll(Collection<?> collection) {
		Iterator<E> iterator = iterator();
		boolean result = true;
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (!collection.contains(element)) {
				result = result && remove(element);
			}
		}
		return result;
	}


	@Override
	public int size() {
		return size;
	}


	@Override
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}


	@Override
	public <T> T[] toArray(T[] array) {
		if (array.length < size()) {
			array = (T[])Array.newInstance(array.getClass().getComponentType(), size());
		}
		Iterator<E> iterator = iterator();
		int index = 0;
		while (iterator.hasNext()) {
			array[index] = (T)iterator.next();
			index++;
		}
		return array;
	}
}
