package info.bioinfweb.commons.collections;



/**
 * Basic implementation representing an interval in a {@link SequenceIntervalList}.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceInterval implements Comparable<SimpleSequenceInterval> {
  private int firstPos;
  private int lastPos;
  
  
	public SimpleSequenceInterval(int firstPos, int lastPos) {
		super();
		this.firstPos = firstPos;
		this.lastPos = lastPos;
	}


	public int getFirstPos() {
		return firstPos;
	}


	public int getLastPos() {
		return lastPos;
	}
	
	
	protected void setFirstPos(int firstPos) {
		this.firstPos = firstPos;
	}


	protected void setLastPos(int lastPos) {
		this.lastPos = lastPos;
	}


	@Override
	public String toString() {
		return getClass().getName() + ": (" + firstPos + ", " + lastPos + ")";
	}

	
	@Override
	public boolean equals(Object other) {
		boolean result = (other instanceof SimpleSequenceInterval);
		if (result) {
			SimpleSequenceInterval c = (SimpleSequenceInterval)other;
			result = (firstPos == c.firstPos) && (lastPos == c.lastPos);
		}
		return result;
	}


	@Override
	public int hashCode() {
		return ((7 + firstPos) * 59 + lastPos); 
	}

	
	@Override
	public int compareTo(SimpleSequenceInterval other) {
		int result = firstPos - other.firstPos;
		if (result == 0) {
			result = lastPos - other.lastPos;
		}
		return result;
	}
}
