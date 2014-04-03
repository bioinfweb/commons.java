package info.bioinfweb.commons;


import java.util.prefs.Preferences;

import info.bioinfweb.commons.appversion.ApplicationVersion;



public class ProgramMainClass {
	private ApplicationVersion version;
	private Preferences preferences;
	
	
	public ProgramMainClass(ApplicationVersion version) {
		super();
		this.version = version;
		preferences = Preferences.userNodeForPackage(this.getClass());
	}


	public Preferences getPreferences() {
		return preferences;
	}


	public ApplicationVersion getVersion() {
		return version;
	}
}
