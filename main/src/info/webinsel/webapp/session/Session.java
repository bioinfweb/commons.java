package info.webinsel.webapp.session;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;



public class Session {
  private String ip = null;
  private Calendar started = null;
  private Calendar lastRequest = null;
  private HashMap<String, Object> objectMap = new HashMap<String, Object>();
  
  
	public Session(String ip) {
		super();
		this.ip = ip;
		started = new GregorianCalendar();  // current Time is used
		lastRequest = new GregorianCalendar();  // current Time is used
	}


	public String getIp() {
		return ip;
	}


	public Calendar getLastRequest() {
		return lastRequest;
	}
	
	
	public void registerRequest() {
		lastRequest = new GregorianCalendar();  // current Time is used
	}


	public Calendar getStarted() {
		return started;
	}


	public HashMap<String, Object> getObjectMap() {
		return objectMap;
	}
}
