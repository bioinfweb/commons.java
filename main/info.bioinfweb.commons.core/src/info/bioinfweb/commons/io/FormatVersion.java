/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
	
	
	public static FormatVersion parseFormatVersion(String text) {
		String[] parts = text.trim().split("\\.");
		if (parts.length == 2) {
			try {
				return new FormatVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}
			catch (NumberFormatException e) {}
		}
		throw new IllegalArgumentException("The specified string \"" + text + "\" cannot be parsed as a format version.");
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


	@Override
	public FormatVersion clone() {
		return new FormatVersion(getMajor(), getMinor());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		return result;
	}


	/**
	 * Returns {@code true} if the passed object is an instance of this class and major and minor version equal
	 * to the values of this instance.
	 * 
	 * @param other - the version to be compared to this one
	 * @return {@code true} of the versions are equal, {@code false} otherwise 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormatVersion other = (FormatVersion) obj;
		if (major != other.major)
			return false;
		if (minor != other.minor)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return getMajor() + "." + getMinor();
	}
}
