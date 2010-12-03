package info.webinsel.wikihelp.server.config;


import info.webinsel.util.servlet.SQLConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;



public class PropertiesManager implements WikiHelpXMLConstants {
	public static final String PROPERTIES_PATH = "/WEB-INF/WikiHelp.xml";


	private static HashMap<ServletContext, WikiHelpProperties> propertiesMap =
		  new HashMap<ServletContext, WikiHelpProperties>();
	
	
	public static WikiHelpProperties getProperties(ServletContext context) 
	    throws XMLStreamException {
		
		if (propertiesMap.get(context) == null) {
			propertiesMap.put(context, new PropertiesReader().read(
				  context.getResourceAsStream(PROPERTIES_PATH)));
		}
		return propertiesMap.get(context);
	}
	
	
	public static Connection createConnection(ServletContext context) 
	    throws XMLStreamException, ClassNotFoundException, SQLException {
		
		WikiHelpProperties properties = getProperties(context);
		return SQLConnector.getConnection(properties.getDBHost(), 
				properties.getDBName(), properties.getDBUser(), 
				properties.getDBPassword(), properties.getDBDriver());
	}
}
