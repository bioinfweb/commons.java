package info.webinsel.util.servlet.urltree.singlelang;


import info.webinsel.util.sql.SQLConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;



public class SingleLangPropertiesManager {
	public static final String PROPERTIES_PATH = 
	    "/WEB-INF/SingleLangContentDispatcher.xml";


  private static HashMap<ServletContext, SingleLangProperties> propertiesMap =
  	  new HashMap<ServletContext, SingleLangProperties>();

  
  public static SingleLangProperties getProperties(ServletContext context) 
      throws XMLStreamException {
  	
  	if (propertiesMap.get(context) == null) {
  		propertiesMap.put(context, new PropertiesReader().read(
				  context.getResourceAsStream(PROPERTIES_PATH)));
  	}
  	return propertiesMap.get(context);
  }
  
  
  public static Connection createConnection(ServletContext context) 
      throws XMLStreamException, ClassNotFoundException, SQLException {
  	
    SingleLangProperties properties = getProperties(context);
  	return SQLConnector.getConnection(properties.getDBHost(), 
  			properties.getDBName(), properties.getDBUser(), 
  			properties.getDBPassword(), properties.getDBDriver());
  }
}
