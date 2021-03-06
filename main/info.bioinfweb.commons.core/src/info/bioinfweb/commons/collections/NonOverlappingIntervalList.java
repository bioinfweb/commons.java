/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.Math2;

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


	public boolean add(int pos) {
		return add(pos, pos);
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
	
	
	/**
	 * Removes the specified object from this interval list. If {@code o} in an instance of 
	 * {@link SimpleSequenceInterval} but this exact interval is not contained in this list,
	 * this method will delegate to {@link #remove(int, int)}, which may then modify 
	 * overlapping interval entries.
	 * 
	 * @return {@code true} if an object was removed or intervals have been modified.
	 * @see java.util.TreeSet#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		boolean result = super.remove(o);  // This must be tried first to avoid recursions via the call of removeAll() in remove(int, int).
		if ((!result) && (o instanceof SimpleSequenceInterval)) {
			SimpleSequenceInterval interval = (SimpleSequenceInterval)o;
			result = remove(interval.getFirstPos(), interval.getLastPos());
		}
		return result;
	}


	/**
	 * Removes the specified interval from this list. (Overlapping interval objects will be modified.) 
	 * 
	 * @param firstPos the first index of the interval to be removed (must be {@code >= 0}) 
	 * @param lastPos the index after the last position to be removed (must be &gt; {@code firstIndex}).
	 * @return {@code true} if any changes were made to the list 
	 */
	public boolean remove(int firstPos, int lastPos) {
		SortedSet<SimpleSequenceInterval> overlap = getOverlappingElements(firstPos -1, lastPos +1);
		if (!overlap.isEmpty()) {
			removeAll(overlap);
			if (overlap.first().getFirstPos() < firstPos) {
				super.add(new SimpleSequenceInterval(overlap.first().getFirstPos(), firstPos));
			}
			if (lastPos < overlap.last().getLastPos()) {
				super.add(new SimpleSequenceInterval(lastPos, overlap.last().getLastPos()));
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Adds all or a part of the intervals contained in another list to this list. 
	 * 
	 * @param other the other list instance
	 * @param firstIndex the first position from where to start the import (must be {@code >= 0})
	 * @param lastIndex the index after the last position to be imported (must be &gt; {@code firstIndex}).
	 * @param moveToFront Specify {@code true} here, if you want to move the imported intervals to the
	 *        front of the target list. (An interval starting at {@code firstIndex} would than start at 0
	 *        in the target list.)
	 */
	public void addAll(NonOverlappingIntervalList other, int firstIndex, int lastIndex, boolean moveToFront) {
		SortedSet<SimpleSequenceInterval> set = other.getOverlappingElements(firstIndex, lastIndex);  //TODO Change meaning of lastIndex in getOverlappingElements()
		int offset = moveToFront ? -firstIndex : 0;
		Iterator<SimpleSequenceInterval> iterator = set.iterator();
		while (iterator.hasNext()) {
			SimpleSequenceInterval interval = iterator.next();
			add(Math.max(firstIndex, interval.getFirstPos()) + offset, 
					Math.min(lastIndex - 1, interval.getLastPos()) + offset);  // If getOverlappingElements() works correctly, it is not necessary to check the upper bound for the firstIndex or the lower bound for the last pos.
		}
	}
	
	
	/**
	 * Adds all of the intervals contained in another list to this list. Calling this method is equivalent to
	 * calling {@code addAll(other, 0, other.last().getLastPos() + 1, false)}. 
	 * 
	 * @param other - the other list instance
	 */
	public void addAll(NonOverlappingIntervalList other) {
		addAll(other, 0, other.last().getLastPos() + 1, false);
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
   * Checks whether the specified position is marked by this list.
   * 
   * @param pos the position to be checked
   * @return {@code true} if the specified position is marked by this list, {@code false} otherwise.
   */
	public boolean contains(int pos) {
  	SimpleSequenceInterval interval = floor(new SimpleSequenceInterval(pos, pos));
  	return (interval != null) && Math2.isBetween(pos, interval.getFirstPos(), interval.getLastPos());
  }
  
  
  /**
   * Checks whether all positions in the specified interval are marked by this list.
   * 
   * @param firstPos the first position of the interval to be checked
   * @param lastPos the last position of the interval to be checked (inclusive)
   * @return {@code true} if all specified positions are marked by this list, {@code false} otherwise.
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
