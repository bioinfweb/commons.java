package info.webinsel.wikihelp.client;


import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;



public interface WikiHelpErrorHandler {
  public void unsupportedOperatingSystem(UnsupportedOperatingSystemException e);
  
  public void initFail(BrowserLaunchingInitializingException e);
}
