package info.bioinfweb.commons.collections;


import java.util.Comparator;


/**
 * Comparator that compare two {@link SimpleSequenceInterval} objects simply by their first (start) position.
 * (Note that {@link SimpleSequenceInterval#compareTo(SimpleSequenceInterval)}) also takes the second (end)
 * position into account.)
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceIntervalFirstPosComparator implements Comparator<SimpleSequenceInterval> {
	@Override
	public int compare(SimpleSequenceInterval o1, SimpleSequenceInterval o2) {
		return o1.getFirstPos() - o2.getFirstPos();
	}
}
