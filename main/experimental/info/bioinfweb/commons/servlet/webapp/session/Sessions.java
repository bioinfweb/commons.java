package info.bioinfweb.commons.servlet.webapp.session;


import info.bioinfweb.commons.RandomValues;

import java.util.HashMap;



//TODO Funktionen zum URL Rewriting und erneuten auslesen (request als Parameter) implementieren.
/**
 * Manages the sessions currently active in a tomvat webserver. Because this class is a singleton
 * every servlet and server page can share the session list.
 * @author Ben St&ouml;ver
 */
public class Sessions {
	public static final String ID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final int ID_LENGTH = 20;  // ~2.3E31 possible IDs
	
	
  private static Sessions firstInstace = null;
  
  private HashMap<String, Session> sessions = new HashMap<String, Session>();
  
  
  private Sessions() {}
  
  
  public static Sessions getInstance() {
  	if (firstInstace == null) {
  		firstInstace = new Sessions();
  	}
  	return firstInstace;
  }
  
  
  private String createID() {
  	String result = null;
  	do {
  		result = RandomValues.randChars(ID_CHARS, ID_LENGTH);
  	} while (sessionExists(result));
  	return result;
  }
  
  
  public boolean sessionExists(String id) {
  	return sessions.get(id) != null;
  }
  
  
  /**
   * Creates a new session with a unique ID. It can be obtaines with 
   * {@link info.webinsel.webapp.session.getSession(String)}.
   * @param ip - the IP adress of the requestor
   * @return the new session ID
   */
  public String createSession(String ip) {
  	String result = createID();
  	sessions.put(result, new Session(ip));
  	return result;
  }
  
  
  public Session getSession(String id) {
  	return sessions.get(id);
  }
}
