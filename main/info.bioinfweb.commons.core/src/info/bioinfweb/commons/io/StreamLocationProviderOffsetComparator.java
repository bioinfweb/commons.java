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
package info.bioinfweb.commons.io;


import java.util.Comparator;



/**
 * Comparator for instances of {@link StreamLocationProvider}, which uses 
 * {@link StreamLocationProvider#getCharacterOffset()} as the only comparison criterion.
 * <p>
 * Note that the line and column number properties are not taken into account. As a consequence
 * this comparator is not necessarily consistent with the {@code equals(Object)} methods of the implementations of 
 * {@link StreamLocationProvider}. (If this comparator returns 0 for two objects, these objects are not
 * necessarily equal, they only have the same character offset.) Therefore this comparator should
 * not be used together with sorted sets or sorted maps, as described in the documentation of {@link Comparator}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class StreamLocationProviderOffsetComparator implements Comparator<StreamLocationProvider> {
	@Override
	public int compare(StreamLocationProvider o1, StreamLocationProvider o2) {
		return (int)Math.signum(o1.getCharacterOffset() - o2.getCharacterOffset());  // Signum is used to convert long to int.
	}
}
