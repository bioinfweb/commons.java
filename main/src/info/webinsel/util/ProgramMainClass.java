package info.webinsel.util;


import java.util.prefs.Preferences;

import info.webinsel.util.appversion.ApplicationVersion;



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
