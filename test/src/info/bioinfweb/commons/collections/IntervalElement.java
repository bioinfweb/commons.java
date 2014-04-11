package info.bioinfweb.commons.collections;



public class IntervalElement implements Comparable<IntervalElement> {
	public int firstPos;
	public int lastPos;
	public String text;

	
	public IntervalElement(int firstPos, int lastPos, String text) {
		super();
		this.firstPos = firstPos;
		this.lastPos = lastPos;
		this.text = text;
	}


	@Override
	public String toString() {
		return text + " (" + firstPos + ", " + lastPos + ")";
	}

	
	@Override
	public boolean equals(Object other) {
		boolean result = (other instanceof IntervalElement);
		if (result) {
			IntervalElement c = (IntervalElement)other;
			result = (firstPos == c.firstPos) && (lastPos == c.lastPos) && text.equals(c.text);
		}
		return result;
	}


	@Override
	public int hashCode() {
		return ((7 + firstPos) * 59 + lastPos) * 13 + text.hashCode(); 
	}

	
	@Override
	public int compareTo(IntervalElement other) {
		int result = firstPos - other.firstPos;
		if (result == 0) {
			result = lastPos - other.lastPos;
		}
		if (result == 0) {
			result = text.compareTo(other.text);
		}
		return result;
	}
}
