/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.appversion;


import info.bioinfweb.commons.io.FormatVersion;



/**
 * Stores the version of an application.
 * 
 * @author Ben St&ouml;ver
 * @see FormatVersion
 */
public class ApplicationVersion implements Comparable<ApplicationVersion>, Cloneable {
	private int majorRelease = 0;
  private int minorRelease = 0;
  private int patchLevel = 0;
  private long buildNumber = 0;
  private ApplicationType type = ApplicationType.STABLE;
	
  
  /**
   * Creates a new instance of this class with the default values {@code 0.0.0.0} (stable).
   */
  public ApplicationVersion() {
		super();
	}


	/**
	 * Creates a new instance of this class using the specified values.
	 * 
	 * @param majorRelease - the major release number
	 * @param minorRelease - the minor release number
	 * @param patchLevel - the patch level number
	 * @param buildNumber - the build number
	 * @param type - the type of application
	 */
	public ApplicationVersion(int majorRelease, int minorRelease, int patchLevel, 
			long buildNumber, ApplicationType type) {
		
		super();
		this.majorRelease = majorRelease;
		this.minorRelease = minorRelease;
		this.patchLevel = patchLevel;
		this.buildNumber = buildNumber;
		this.type = type;
	}


	public long getBuildNumber() {
		return buildNumber;
	}


	public int getMajorRelease() {
		return majorRelease;
	}


	public int getMinorRelease() {
		return minorRelease;
	}


	public int getPatchLevel() {
		return patchLevel;
	}


	public ApplicationType getType() {
		return type;
	}


	public void setBuildNumber(long buildNumber) {
		this.buildNumber = buildNumber;
	}


	public void setMajorRelease(int majorRelease) {
		this.majorRelease = majorRelease;
	}


	public void setMinorRelease(int minorRelease) {
		this.minorRelease = minorRelease;
	}


	public void setPatchLevel(int patchLevel) {
		this.patchLevel = patchLevel;
	}


	public void setType(ApplicationType type) {
		this.type = type;
	}


	/**
	 * This method can be overriden if a special name is needed.
	 */
	@Override
	public String toString() {
		return getMajorRelease() + "." + getMinorRelease() + "." + getPatchLevel() + "-" + getBuildNumber() + " " + getType();
	}
	
	
  /**
   * Checks if two versions are equal. Major, minor, patch level and build number have to be equal. The
   * application type is not checked by this method, since there should never be two versions of the same
   * application having the same set of version numbers.
   */
  @Override
	public boolean equals(Object other) {
  	if (other instanceof ApplicationVersion) {
  		ApplicationVersion version = (ApplicationVersion)other;
  		return (version.getMajorRelease() == getMajorRelease()) && 
  		    (version.getMinorRelease() == getMinorRelease()) && 
  		    (version.getPatchLevel() == getPatchLevel()) && 
  		    (version.getBuildNumber() == getBuildNumber());
  	}
  	else {
  		return false;
  	}
	}


	/**
	 * Compares two application versions. If the major version is equal, the minor version is compared, than
	 * the patch level and than the build number. The application type is not considered by this method.
	 */
  @Override
	public int compareTo(ApplicationVersion other) {
		if (getMajorRelease() == other.getMajorRelease()) {
			if (getMinorRelease() == other.getMinorRelease()) {
				if (getPatchLevel() == other.getPatchLevel()) {
					if (getBuildNumber() == other.getBuildNumber()) {
						return 0;
					}
					else if (getBuildNumber() > other.getBuildNumber()) { // The difference cannot be returned directly because it might be outside the integer range.
						return 1;
					}
					else {
						return -1;
					}
				}
				else {
					return getPatchLevel() - other.getPatchLevel();
				}
			}
			else {
				return getMinorRelease() - other.getMinorRelease();
			}
		}
		else {
			return getMajorRelease() - other.getMajorRelease();
		}
	}


	/**
	 * Returns a new instance if this class having the same version numbers and application type as this instance.
	 */
	@Override
	public ApplicationVersion clone() {
		return new ApplicationVersion(getMajorRelease(), getMinorRelease(), getPatchLevel(), getBuildNumber(), 
				getType());
	}
}