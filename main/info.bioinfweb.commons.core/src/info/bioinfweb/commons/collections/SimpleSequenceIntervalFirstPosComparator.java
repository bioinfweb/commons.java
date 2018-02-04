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


import java.util.Comparator;


/**
 * Comparator that compare two {@link SimpleSequenceInterval} objects simply by their first (start) position.
 * (Note that {@link SimpleSequenceInterval#compareTo(SimpleSequenceInterval)}) also takes the second (end)
 * position into account.)
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceIntervalFirstPosComparator implements Comparator<SimpleSequenceInterval> {
	@Override
	public int compare(SimpleSequenceInterval o1, SimpleSequenceInterval o2) {
		return o1.getFirstPos() - o2.getFirstPos();
	}
}
