package info.webinsel.util.collections;


import info.webinsel.util.Math2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



/**
 * Instances of this class are able to manage a set of sequence intervals (Instances of classes implementing 
 * {@linkplain SequenceInterval}). The key feature if the efficient calculation of a subset of the stored 
 * interval elements that overlap with a defined interval (see {@link #getOverlappingElements(int, int)}. 
 * The actual efficiency depends on the specified interval length (see {@link #SequenceIntervalList(int, int)}).  
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> the type of elements that describe a sequence interval (the elements of the collection)
 */
public class SequenceIntervalList<E> implements Collection<E> {
	public static final int INITIAL_ELEMENT_LIST_CAPACITY = 10;
	public static final int DEFAULT_INITIAL_SEQUENCE_LENGTH = 5000;
	public static final int DEFAULT_INTERVAL_LENGTH = 50;
	

	protected class IntervalInformation {
		private int intervalStart;
		private ArrayList<E> startList = new ArrayList<E>(INITIAL_ELEMENT_LIST_CAPACITY);
		private ArrayList<E> overlapList = new ArrayList<E>(INITIAL_ELEMENT_LIST_CAPACITY);
		
		
		public IntervalInformation(int intervalStart) {
			super();
			this.intervalStart = intervalStart;
		}


		public int getIntervalStart() {
			return intervalStart;
		}


		public ArrayList<E> getStartList() {
			return startList;
		}
		
		
		public ArrayList<E> getOverlapList() {
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
	private Comparator<E> firstPosComparator = new Comparator<E>() {
				@Override
				public int compare(E o1, E o2) {
					return getPositionAdapter().getFirstPos(o1) - getPositionAdapter().getFirstPos(o2);
				}
			};
	private boolean sortedByFirstPos = true;
	

  /**
   * Equivalent to calling <code>SequenceIntervalList({@link #DEFAULT_INITIAL_SEQUENCE_LENGTH}, 
   * {@link DEFAULT_INTERVAL_LENGTH})</code>.
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


	public Comparator<E> getFirstPosComparator() {
		return firstPosComparator;
	}


	public boolean isSortedByFirstPos() {
		return sortedByFirstPos;
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
			intervalList.add(new IntervalInformation(intervalList.size() * getIntervalLength()));
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
		Iterator<IntervalInformation> iterator = getIntervalInformationIterator(getPositionAdapter().getFirstPos(element), 
				getPositionAdapter().getLastPos(element));
		boolean result = true;
		while (iterator.hasNext()) {
			result = result && iterator.next().add(element);
		}
		size++;  //TODO M�sste eigentlich result pr�fen. (Macht aber nur Sinn, wenn einzelne R�ckgabewerte der Schlefe betrachtet werden.)
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

	
	private void addOverlappingElementsFromList(Collection<E> result, List<E> list, int firstPos, int lastPos) {
		Iterator<E> iterator = list.iterator();
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (Math2.overlaps(firstPos, lastPos, 
					getPositionAdapter().getFirstPos(element), getPositionAdapter().getLastPos(element))) {
				
				result.add(element);
			}
		}
	}
	
	
	public Collection<E> getOverlappingElements(int firstPos, int lastPos) {
		Collection<E> result = new ArrayList<E>();
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


	public void sortSublistsByFirstPos() {
		if (!isSortedByFirstPos()) {
			Iterator<IntervalInformation> iterator = intervalList.iterator();
			while (iterator.hasNext()) {
				IntervalInformation i = iterator.next();
				Collections.sort(i.getStartList(), getFirstPosComparator());
				Collections.sort(i.getOverlapList(), getFirstPosComparator());
			}
			sortedByFirstPos = true;
		}
	}
	
	
	/**
	 * Returns an iterator over the list. Each element is only returned once, no matter how many intervals it spans.
	 * The elements are ordered by their first position. (This method sorts the sublists first. If sorting is not 
	 * necessary you can call the faster method {@link #unsortedIterator()})
	 * 
	 * @see #unsortedIterator()
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		sortSublistsByFirstPos();
		return unsortedIterator();
	}
	
	
	/**
	 * Returns an iterator over the list. Each element is only returned once, no matter how many intervals it spans.
	 * Elements contained in the same interval are not necessarily ordered.
	 * 
	 * @see #iterator()
	 */
	public Iterator<E> unsortedIterator() {
		return new Iterator<E>() {
					private Iterator<IntervalInformation> intervalIterator = intervalList.iterator();
					private Iterator<E> listIterator = null;
					
					
					private boolean secureFilledListIterator() {
						if (((listIterator == null) || !listIterator.hasNext()) && intervalIterator.hasNext()) {
							do {  // Search next filled list
								listIterator = intervalIterator.next().getStartList().iterator();
							} while (!listIterator.hasNext() && intervalIterator.hasNext());
						}
						return (listIterator == null) || listIterator.hasNext();  // Returns false of the end of intervalIterator was reached and the last listIterator contains no more elements
					}
					
					
					@Override
					public boolean hasNext() {
						return secureFilledListIterator();
					}
					
		
					@Override
					public E next() {
						secureFilledListIterator();
						if (listIterator == null) {
							
						}
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
			array = (T[])Array.newInstance(array.getClass(), size());
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
