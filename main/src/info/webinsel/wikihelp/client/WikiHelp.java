package info.webinsel.wikihelp.client;


import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;



public class WikiHelp {
	public static final String CODE_REDIRECTOR = "redirect/";
	public static final String WIKI_FOLDER = "wiki/";  //  @jve:decl-index=0:
	public static final String INDEX_TOPIC = "Special:Allpages";
	
	
	private String wikiURL = null;
	private String redirectURL = null;
	private WikiHelpErrorHandler errorHandler = null;
	private BrowserLauncher launcher = null;
	

	public WikiHelp(String wikiHelpURL) {
		super();
		wikiURL = wikiHelpURL + WIKI_FOLDER;
		redirectURL = wikiHelpURL + CODE_REDIRECTOR;
		useDefaultErrorHandler();
	}


	public WikiHelp(String wikiURL, String redirectURL) {
		super();
		this.wikiURL = wikiURL;
		this.redirectURL = redirectURL;
		useDefaultErrorHandler();
	}
	
	
	public WikiHelp(String wikiHelpURL, WikiHelpErrorHandler errorHandler) {
		super();
		wikiURL = wikiHelpURL + WIKI_FOLDER;
		redirectURL = wikiHelpURL + CODE_REDIRECTOR;
		this.errorHandler = errorHandler;
	}


	public WikiHelp(String wikiURL, String redirectURL, WikiHelpErrorHandler errorHandler) {
		super();
		this.wikiURL = wikiURL;
		this.redirectURL = redirectURL;
		this.errorHandler = errorHandler;
	}
	
	
	private void useDefaultErrorHandler() {
		errorHandler = new DefaultErrorHandler();
	}
	
	
	public WikiHelpErrorHandler getErrorHandler() {
		return errorHandler;
	}


	private BrowserLauncher getLauncher() {
		if (launcher == null) {
			try {
				launcher = new BrowserLauncher();
			}
			catch (UnsupportedOperatingSystemException e) {
				getErrorHandler().unsupportedOperatingSystem(e);
			}
			catch (BrowserLaunchingInitializingException e) {
				getErrorHandler().initFail(e);
			}
		}
		return launcher;
	}


	public void displayTopic(int code) {
		setPage(redirectURL + code);
	}
	
	
	public void displayTopic(String name) {
  	setPage(wikiURL + name);
  }
  
  
  public void displayContents() {
  	displayTopic("");  // Es sollte nun ein redirect zu "Main_Page" erfolgen.
	}
  
  
  public void displayIndex() {
  	displayTopic(INDEX_TOPIC);
	}
  
  
  public void setPage(String url) {
  	BrowserLauncher launcher = getLauncher();
  	if (launcher != null) {
  		launcher.openURLinBrowser(url); 
  	}
  }
}
