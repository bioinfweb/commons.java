/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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
 * Stores the version of a format (e.g. a XML format) consisting of a major and minor version. 
 * 
 * @author Ben St&ouml;ver
 */
public class FormatVersion implements Comparable<FormatVersion>, Cloneable {
	private int major = 1;
  private int minor = 0;
  
  
	public FormatVersion(int major, int minor) {
		super();
		this.major = major;
		this.minor = minor;
	}


	public int getMajor() {
		return major;
	}
	
	
	public void setMajor(int major) {
		this.major = major;
	}
	
	
	public int getMinor() {
		return minor;
	}
	
	
	public void setMinor(int minor) {
		this.minor = minor;
	}
	
	
	/**
	 * Returns whether this version is newer than the specified argument.
	 * @param other - the version to be compared with this one
	 * @return
	 */
	public boolean geraterThan(FormatVersion other) {
		return getMajor() > other.getMajor() || ((getMajor() == other.getMajor()) && (getMinor() > other.getMinor())); 
	}
	
	
	/**
	 * Returns 0 if major and minor version are equal, 1 if this version is later than the specified one and -1 if the
	 * specified version is later.
	 * @param other - the version to be compared to this one
	 * @return 0, 1 or -1 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(FormatVersion other) {
		if ((getMajor() == other.getMajor()) && (getMinor() == other.getMinor())) {
			return 0;
		}
		else if (getMajor() > other.getMajor() || ((getMajor() == other.getMajor()) && (getMinor() > other.getMinor()))) {
			return 1;
		}
		else {
			return -1;
		}
	}


	/**
	 * Returns <code>true</code> if the passed object is an instance of this class and major and minor version equal
	 * to the values of this instance.
	 * @param other - the version to be compared to this one
	 * @return <code>true</code> of the versions are equal 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof FormatVersion) && (compareTo((FormatVersion)other) == 0);
	}


	@Override
	public FormatVersion clone() {
		return new FormatVersion(getMajor(), getMinor());
	}


	@Override
	public String toString() {
		return getMajor() + "." + getMinor();
	}
}
