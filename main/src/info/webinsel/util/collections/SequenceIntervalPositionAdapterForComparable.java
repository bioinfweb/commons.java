package info.webinsel.util.collections;



/**
 * Abstract implementation of {@link SequenceIntervalPositionAdapter} to be used with elements that already implement the
 * {@link Comparable} interface.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <T>
 */
public abstract class SequenceIntervalPositionAdapterForComparable<T extends Comparable<? super T>> implements SequenceIntervalPositionAdapter<T> {
	@Override
	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}
}
