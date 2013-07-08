package info.webinsel.util.collections;


import java.util.Comparator;



/**
 * Comparator that compare two {@link SimpleSequenceInterval} objects simply by their last (end) position.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceIntervalLastPosComparator implements Comparator<SimpleSequenceInterval> {
	@Override
	public int compare(SimpleSequenceInterval o1, SimpleSequenceInterval o2) {
		return o1.getLastPos() - o2.getLastPos();
	}
}
