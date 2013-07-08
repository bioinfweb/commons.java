package info.webinsel.util.collections;


import info.webinsel.util.Math2;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * Stores a set of non overlapping intervals of a sequence. The sequence intervals only mark a subset of
 * all sequence characters and carry no additional information.
 * 
 * @see SequenceIntervalList
 * @author Ben St&ouml;ver
 */
public class NonOverlappingIntervalList /*implements Collection<SimpleSequenceInterval>*/ {
	private TreeSet<SimpleSequenceInterval> intervals = new TreeSet<SimpleSequenceInterval>();
	
	
  public void addInterval(int firstPos, int lastPos) {
  	SortedSet<SimpleSequenceInterval> overlap = getOverlappingElements(firstPos - 1, lastPos + 1);
  	if (!overlap.isEmpty()) {
    	removeAll(overlap);
  		firstPos = Math.min(firstPos, overlap.first().getFirstPos());
  		lastPos = Math.max(lastPos, overlap.last().getLastPos());
  	}
  	intervals.add(new SimpleSequenceInterval(firstPos, lastPos));
  }
  
  
  public void movePositions(int start, int offset) {
  	
  }
  
  
	public boolean isEmpty() {
		return intervals.isEmpty();
	}


	public Iterator<SimpleSequenceInterval> iterator() {
		return intervals.iterator();
	}


	public int size() {
		return intervals.size();
	}


	public void clear() {
		intervals.clear();
	}


	public SimpleSequenceInterval first() {
		return intervals.first();
	}


	public SimpleSequenceInterval last() {
		return intervals.last();
	}


	public boolean remove(Object o) {
		return intervals.remove(o);
	}


	public boolean removeAll(Collection<?> c) {
		return intervals.removeAll(c);
	}


	public boolean contains(int pos) {
  	return false;
  }
  
  
  public boolean contains(int firstPos, int lastPos) {
  	return false;
  }

  
  public SortedSet<SimpleSequenceInterval> getOverlappingElements(int firstPos, int lastPos) {
		SortedSet<SimpleSequenceInterval> result = new TreeSet<SimpleSequenceInterval>();

		SimpleSequenceInterval newElement = new SimpleSequenceInterval(firstPos, lastPos);
  	SimpleSequenceInterval first = intervals.floor(newElement);
  	SortedSet<SimpleSequenceInterval> overlapSet;
  	if (first != null) {
  		overlapSet = intervals.tailSet(first);
  	}
  	else {
  		overlapSet = intervals;
  	}
  	Iterator<SimpleSequenceInterval> iterator = overlapSet.iterator();
  	if (iterator.hasNext()) {
  		SimpleSequenceInterval current;
  		do {
    		current = iterator.next();
  			if (Math2.overlaps(firstPos, lastPos, current.getFirstPos(), current.getLastPos())) {
  				result.add(current);
  			}
  		} while (iterator.hasNext() && (current.getFirstPos() <= lastPos));
  	}
		return result;
	}
}
