package info.webinsel.util.collections;



public abstract class AbstractSequenceInterval implements SequenceInterval {
	public static int compare(SequenceInterval i1, SequenceInterval i2) {
		return i2.getFirstPos() - i1.getFirstPos();
	}
	
	
	@Override
	public int compareTo(SequenceInterval other) {
		return compare(this, other);
	}
}
