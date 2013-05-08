package info.webinsel.util;


import java.util.prefs.Preferences;

import info.webinsel.util.appversion.ApplicationVersion;



public class ProgramMainClass {
	public static final boolean IS_MAC = 
		  System.getProperty("os.name").toLowerCase().startsWith("mac");
	
	public static final boolean IS_64_BIT_JRE = 
			System.getProperty("os.arch").contains("64");  


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
