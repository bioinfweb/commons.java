package info.webinsel.util.collections;


import info.webinsel.util.Math2;

import java.util.ArrayList;
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
public class NonOverlappingIntervalList extends TreeSet<SimpleSequenceInterval> {
  public NonOverlappingIntervalList() {
		super(new SimpleSequenceIntervalFirstPosComparator());
	}


  @Override
	public boolean add(SimpleSequenceInterval e) {
  	return add(e.getFirstPos(), e.getLastPos());
	}


	public boolean add(int firstPos, int lastPos) {
  	SortedSet<SimpleSequenceInterval> overlap = getOverlappingElements(firstPos - 1, lastPos + 1);
  	if (!overlap.isEmpty()) {
    	removeAll(overlap);
  		firstPos = Math.min(firstPos, overlap.first().getFirstPos());
  		lastPos = Math.max(lastPos, overlap.last().getLastPos());
  	}
  	return super.add(new SimpleSequenceInterval(firstPos, lastPos));
  }
  
  
	public void movePositions(int start, int offset) {
		SortedSet<SimpleSequenceInterval> elements = tailSet(new SimpleSequenceInterval(start, start));
		Collection<SimpleSequenceInterval> copy = new ArrayList<SimpleSequenceInterval>(elements.size());  // Copy necessary because the set is just a view.
		copy.addAll(elements);
		removeAll(copy);
		Iterator<SimpleSequenceInterval> iterator = copy.iterator();
  	while (iterator.hasNext()) {
  		SimpleSequenceInterval current = iterator.next();
  		add(current.getFirstPos() + offset, current.getLastPos() + offset);  // If offset is negative, add() will automatically join preceding elements.
  	}
  }
  
  
  /**
   * Returns <code>true</code> if the specified position is marked by this list.
   */
	public boolean contains(int pos) {
  	SimpleSequenceInterval interval = floor(new SimpleSequenceInterval(pos, pos));
  	return (interval != null) && Math2.isBetween(pos, interval.getFirstPos(), interval.getLastPos());
  }
  
  
  /**
   * Returns <code>true</code> if all positions in the specified interval are marked by this list.
   */
  public boolean containsAll(int firstPos, int lastPos) {
  	SimpleSequenceInterval interval = floor(new SimpleSequenceInterval(firstPos, lastPos));  // Only a single element can contain the whole interval.
  	return (interval != null) && (interval.getFirstPos() <= firstPos) && (interval.getLastPos() >= lastPos);
  }

  
  public SortedSet<SimpleSequenceInterval> getOverlappingElements(int firstPos, int lastPos) {
		SortedSet<SimpleSequenceInterval> result = 
				new TreeSet<SimpleSequenceInterval>(new SimpleSequenceIntervalFirstPosComparator());

		SimpleSequenceInterval newElement = new SimpleSequenceInterval(firstPos, lastPos);
  	SimpleSequenceInterval first = floor(newElement);
  	SortedSet<SimpleSequenceInterval> overlapSet;
  	if (first != null) {
  		overlapSet = tailSet(first);
  	}
  	else {
  		overlapSet = this;
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
