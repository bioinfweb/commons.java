package info.webinsel.util.servlet.urltree.singlelang;


import info.webinsel.util.servlet.urltree.AbstractContentDispatcher;
import info.webinsel.util.servlet.urltree.ErrorPageData;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;



public class SingleLangContentDispatcher extends AbstractContentDispatcher {
	public static final String ATTR_PROPERTIES = "SingleLangContentDispatcherProperties";
	
	
	public SingleLangContentDispatcher() throws XMLStreamException {
		super();
	}


	public SingleLangProperties getProperties() throws XMLStreamException {
		return SingleLangPropertiesManager.getProperties(getServletContext());
	}


	@Override
	protected Connection createConnection() throws Exception {
		return SingleLangPropertiesManager.createConnection(getServletContext());
	}

	
	@Override
	protected String getRedirectHost(String lang, String path) throws Exception {
		return getProperties().getHostName();
	}


	@Override
	public String getJSPURL() throws Exception {
		return getProperties().getMainJSP();
	}


	@Override
	public String getTablePrefix() throws Exception {
		return getProperties().getTablePrefix();
	}


	@Override
	protected ErrorPageData getErrorPage() throws Exception {
		return getProperties().getError404();
	}


	@Override
	protected void addAttributes(HttpServletRequest request) throws Exception {
    request.setAttribute(ATTR_PROPERTIES, getProperties());
	}
}
