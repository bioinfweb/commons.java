package info.bioinfweb.commons;


import info.bioinfweb.commons.appversion.ApplicationVersion;

import java.util.prefs.Preferences;



/**
 * Main classes of applications providing a global {@link Preferences} and {@link ApplicationVersion}
 * object can be inherited from this class.
 * 
 * @author Ben St&ouml;ver
 */
public class ProgramMainClass {
	private ApplicationVersion version;
	private Preferences preferences;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param version - the version number of the application this class is used for
	 */
	public ProgramMainClass(ApplicationVersion version) {
		super();
		this.version = version;
		preferences = Preferences.userNodeForPackage(this.getClass());
	}


	/**
	 * Returns the associated preferences object.
	 * 
	 * @return
	 */
	public Preferences getPreferences() {
		return preferences;
	}


	public ApplicationVersion getVersion() {
		return version;
	}
}
