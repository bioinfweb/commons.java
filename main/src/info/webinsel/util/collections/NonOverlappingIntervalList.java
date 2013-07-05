package info.webinsel.util.collections;


import info.webinsel.util.collections.SequenceIntervalList.IntervalInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;



/**
 * Stores a set of non overlapping intervals of a sequence. The sequence intervals only mark a subset of
 * all sequence characters and carry no additional information.
 * 
 * @see SequenceIntervalList
 * @author Ben St&ouml;ver
 */
public class NonOverlappingIntervalList /*<E> implements Collection<E>*/ {
	private TreeSet<SimpleSequenceInterval> intervals = new TreeSet<SimpleSequenceInterval>();
	
	
  public void addInterval(int firstPos, int lastPos) {
  	SimpleSequenceInterval newElement = new SimpleSequenceInterval(firstPos, lastPos);
  	intervals.floor(newElement);
  }
  
  
  public void movePositions(int start) {
  	
  }
  
  
  public boolean contains(int pos) {
  	
  }
  
  
  public boolean contains(int firstPos, int lastPos) {
  	
  }

  
  public Collection<E> getOverlappingElements(int firstPos, int lastPos) {
		Collection<E> result = new ArrayList<E>();
		Iterator<IntervalInformation> iterator = getIntervalInformationIterator(firstPos, lastPos);
		if (iterator.hasNext()) {
			// Add elements starting in the first interval or before:
			IntervalInformation first = iterator.next();
			addOverlappingElementsFromList(result, first.getOverlapList(), firstPos, lastPos);
			addOverlappingElementsFromList(result, first.getStartList(), firstPos, lastPos);

			// Add elements starting after the first interval:
			while (iterator.hasNext()) {
				addOverlappingElementsFromList(result, iterator.next().getStartList(), firstPos, lastPos);  //TODO This method must be called only for the last iteration. The others could also use result.addAll().
			}
		}
		return result;
	}
	
}
