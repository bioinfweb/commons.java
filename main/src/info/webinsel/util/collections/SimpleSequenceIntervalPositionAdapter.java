package info.webinsel.util.collections;



/**
 * Implementation of {@link SequenceIntervalPositionAdapter} for {@link SimpleSequenceInterval}.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceIntervalPositionAdapter implements SequenceIntervalPositionAdapter<SimpleSequenceInterval> {
	@Override
	public int compare(SimpleSequenceInterval o1, SimpleSequenceInterval o2) {
		return o1.compareTo(o2);
	}

	
	@Override
	public int getFirstPos(SimpleSequenceInterval o) {
		return o.getFirstPos();
	}
	

	@Override
	public int getLastPos(SimpleSequenceInterval o) {
		return o.getLastPos();
	}

	
	@Override
	public void setFirstPos(SimpleSequenceInterval o, int newFirstPos) {
		o.setFirstPos(newFirstPos);
	}

	
	@Override
	public void setLastPos(SimpleSequenceInterval o, int newLastPos) {
		o.setLastPos(newLastPos);
	}
}
