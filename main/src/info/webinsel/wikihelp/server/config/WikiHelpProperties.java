package info.webinsel.wikihelp.server.config;


import info.bioinfweb.commons.sql.sqlproperties.ConcreteSQLProperties;



public class WikiHelpProperties extends ConcreteSQLProperties {
	private String wikiBaseURL = "";
	private String invalidHelpCodeURL = "";
	private String errorURL = "";
	
	
	public String getWikiBaseURL() {
		return wikiBaseURL;
	}


	public void setWikiBaseURL(String wikiBaseURL) {
		this.wikiBaseURL = wikiBaseURL;
	}


	public String getErrorURL() {
		return errorURL;
	}
	
	
	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}
	
	
	public String getInvalidHelpCodeURL() {
		return invalidHelpCodeURL;
	}
	
	
	public void setInvalidHelpCodeURL(String invalidHelpCodeURL) {
		this.invalidHelpCodeURL = invalidHelpCodeURL;
	}
}
