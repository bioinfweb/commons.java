/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2016  Ben Stöver
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
package info.bioinfweb.commons.io;



/**
 * A read only implementation of {@link StreamLocationProvider}, which can be used to store
 * a static location.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class StreamLocation implements StreamLocationProvider, Comparable<StreamLocationProvider> {
	private long characterOffset = 0;
	private long lineNumber = 0;
	private long columnNumber = 0;
	
	
	public StreamLocation(long characterOffset, long lineNumber, long columnNumber) {
		super();
		this.characterOffset = characterOffset;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}


	public StreamLocation(StreamLocationProvider locationProvider) {
		super();
		this.characterOffset = locationProvider.getCharacterOffset();
		this.lineNumber = locationProvider.getLineNumber();
		this.columnNumber = locationProvider.getColumnNumber();
	}


	@Override
	public long getCharacterOffset() {
		return characterOffset;
	}
	
	
	@Override
	public long getLineNumber() {
		return lineNumber;
	}
	
	
	@Override
	public long getColumnNumber() {
		return columnNumber;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (characterOffset ^ (characterOffset >>> 32));
		result = prime * result + (int) (columnNumber ^ (columnNumber >>> 32));
		result = prime * result + (int) (lineNumber ^ (lineNumber >>> 32));
		return result;
	}


	/**
	 * Note that this method will only return {@code true}, if the other object is also an instance
	 * of this class or an inherited class. It will not return {@code true} if the passed object is 
	 * a different implementation of {@link StreamLocationProvider}, since it is not guaranteed that
	 * such implementations would have a symmetric equals method.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StreamLocation other = (StreamLocation) obj;
		if (characterOffset != other.characterOffset)
			return false;
		if (columnNumber != other.columnNumber)
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		return true;
	}


	@Override
	public int compareTo(StreamLocationProvider o) {
		return (int)Math.signum(characterOffset - o.getCharacterOffset());  // Signum is used to convert long to int.
	}
}
