package info.bioinfweb.commons.appversion;



/**
 * Stores the version of an application.
 * 
 * @author Ben St&ouml;ver
 */
public class ApplicationVersion implements Comparable<ApplicationVersion>, Cloneable {
	private int majorRelease = 0;
  private int minorRelease = 0;
  private int patchLevel = 0;
  private long buildNumber = 0;
  private ApplicationType type = ApplicationType.FINAL;
	
  
  public ApplicationVersion() {
		super();
	}


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


	public int compareTo(ApplicationVersion other) {
		// Differenz aus beiden kann wegen Konvertierung von long zu int nicht zurückgegeben werden.
		if (getBuildNumber() == other.getBuildNumber()) {
			return 0;
		}
		else if (getBuildNumber() > other.getBuildNumber()) {
			return 1;
		}
		else {
			return -1;
		}
	}


	@Override
	public ApplicationVersion clone() {
		return new ApplicationVersion(getMajorRelease(), getMinorRelease(), getPatchLevel(), getBuildNumber(), 
				getType());
	}
}