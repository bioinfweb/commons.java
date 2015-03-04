/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.collections;



/**
 * Abstract implementation of {@link SequenceIntervalPositionAdapter} to be used with elements that already implement the
 * {@link Comparable} interface.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <T>
 */
public abstract class SequenceIntervalPositionAdapterForComparable<T extends Comparable<? super T>> implements SequenceIntervalPositionAdapter<T> {
	@Override
	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}
}
