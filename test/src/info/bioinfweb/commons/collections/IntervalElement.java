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
