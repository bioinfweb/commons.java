package info.webinsel.wikihelp.client;


import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;



public class DefaultErrorHandler implements WikiHelpErrorHandler {
	public void initFail(BrowserLaunchingInitializingException e) {
		e.printStackTrace();
	}

	
	public void unsupportedOperatingSystem(UnsupportedOperatingSystemException e) {
		e.printStackTrace();
	}
}
