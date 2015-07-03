package info.webinsel.wikihelp.client;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class WikiHelp {
	public static final String CODE_REDIRECTOR = "redirect/";
	public static final String WIKI_FOLDER = "wiki/";  //  @jve:decl-index=0:
	public static final String INDEX_TOPIC = "Special:Allpages";
	
	
	private String wikiURL = null;
	private String redirectURL = null;
	

	public WikiHelp(String wikiHelpURL) {
		super();
		wikiURL = wikiHelpURL + WIKI_FOLDER;
		redirectURL = wikiHelpURL + CODE_REDIRECTOR;
	}


	public WikiHelp(String wikiURL, String redirectURL) {
		super();
		this.wikiURL = wikiURL;
		this.redirectURL = redirectURL;
	}
	
	
	public void displayTopic(int code) {
		setPage(redirectURL + code);
	}
	
	
	public void displayTopic(String name) {
  	setPage(wikiURL + name);
  }
  
  
  public void displayContents() {
  	displayTopic("");  // A redirect to "Main_Page" will be done.
	}
  
  
  public void displayIndex() {
  	displayTopic(INDEX_TOPIC);
	}
  
  
  public void setPage(String url) {
  	try {
  		Desktop.getDesktop().browse(new URI(url));
  	}
  	catch (IOException e) {
  		throw new RuntimeException(e);
  	}
  	catch (URISyntaxException e) {
  		throw new RuntimeException(e);
  	}
  }
}
