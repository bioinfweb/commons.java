/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
