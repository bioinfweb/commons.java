package info.webinsel.util.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



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
	public static final int DEFAULT_INITIAL_SEQUENCE_LENGTH = 5000;
	public static final int DEFAULT_INTERVAL_LENGTH = 50;
	
	
	private MultiTreeMap<Integer, E> mapByFirstPos = new MultiTreeMap<Integer, E>();
	private MultiTreeMap<Integer, E> mapByLastPos = new MultiTreeMap<Integer, E>();
  private ArrayList<List<E>> intervalList;
  private int intervalLength;
  private SequenceIntervalPositionAdapter<? super E> positionAdapter;
	

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
		intervalList = new ArrayList<List<E>>(sequenceLength / intervalLength + 1);
	}
	
	
	public SequenceIntervalPositionAdapter<? super E> getPositionAdapter() {
		return positionAdapter;
	}


	private int intervalIndex(int seqPos) {
		return seqPos / intervalLength;  // rounds down
	}
	
	
	private List<E> getIntervalList(int intervalIndex) {
		while (intervalIndex >= intervalList.size()) {
			intervalList.add(new LinkedList<E>());
		}
		return intervalList.get(intervalIndex);
	}
	
	
	/**
	 * Returns an iterator over all interval lists, that would contains an element reaching from 
	 * <code>firstSeqPos</code> to <code>lastSeqPos</code>.
	 * 
	 * @param firstSeqPos the first position of the requested interval of the sequence (not the interval index)
	 * @param lastSeqPos the last position of the requested interval of the sequence (not the interval index)
	 * @return the {@link Iterator} (created from a {@link LinkedList})
	 */
	private Iterator<List<E>> getIntervalListIterator(int firstSeqPos, int lastSeqPos) {
		LinkedList<List<E>> list = new LinkedList<List<E>>();
		int firstInterval = intervalIndex(firstSeqPos);
		int lastInterval = intervalIndex(lastSeqPos);
		for (int i = firstInterval; i <= lastInterval; i++) {
			list.add(getIntervalList(i));
		}
		return list.iterator();
	}


	@Override
	public boolean add(E element) {
		mapByFirstPos.put(getPositionAdapter().getFirstPos(element), element);
		mapByLastPos.put(getPositionAdapter().getLastPos(element), element);

		Iterator<List<E>> iterator = getIntervalListIterator(getPositionAdapter().getFirstPos(element), 
				getPositionAdapter().getLastPos(element));
		boolean result = true;
		while (iterator.hasNext()) {
			result = result && iterator.next().add(element);
		}
		return result;
	}
	
	
	public boolean remove(Object o) {
		E element = (E)o;
		mapByFirstPos.remove(getPositionAdapter().getFirstPos(element), element);
		mapByFirstPos.remove(getPositionAdapter().getLastPos(element), element);

		Iterator<List<E>> iterator = getIntervalListIterator(getPositionAdapter().getFirstPos(element), 
				getPositionAdapter().getLastPos(element));
		boolean result = true;
		while (iterator.hasNext()) {
			result = result && iterator.next().remove(element);
		}
		return result;
	}

	
	public Set<E> getOverlappingElements(int firstPos, int lastPos) {
		Set<E> result = new HashSet<E>();
		Iterator<List<E>> iterator = getIntervalListIterator(firstPos, lastPos);
		while (iterator.hasNext()) {
			result.addAll(iterator.next());
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
		mapByFirstPos.clear();
		mapByLastPos.clear();
		intervalList.clear();
	}


	@Override
	public boolean contains(Object o) {
		return mapByFirstPos.containsValue((E)o);
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
		return mapByFirstPos.isEmpty();
	}


	@Override
	public Iterator<E> iterator() {
		return mapByFirstPos.valueIterator();
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
		Iterator<E> iterator = mapByFirstPos.valueIterator();
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
		return mapByFirstPos.totalSize();
	}


	@Override
	public Object[] toArray() {
		return mapByFirstPos.toArray();
	}


	@Override
	public <T> T[] toArray(T[] array) {
		return mapByFirstPos.toArray(array);
	}
}
