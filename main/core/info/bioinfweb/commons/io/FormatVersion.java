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
