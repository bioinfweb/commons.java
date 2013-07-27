package info.webinsel.util.log;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Represents a message send to an {@link ApplicationLogger}. 
 * 
 * @author Ben St&ouml;ver
 */
public class ApplicationLoggerMessage {
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	
	private ApplicationLoggerMessageType type = null;
	private Date time = new Date(System.currentTimeMillis());
  private String message = "";
  private int helpCode = -1;
  
  
	public ApplicationLoggerMessage(ApplicationLoggerMessageType type, String message) {
		this(type, message, -1);
	}
  
  
	public ApplicationLoggerMessage(ApplicationLoggerMessageType type, String message, int helpCode) {
		super();
		this.type = type;
		this.message = message;
		this.helpCode = helpCode;
	}


	public ApplicationLoggerMessageType getType() {
		return type;
	}


	public void setType(ApplicationLoggerMessageType type) {
		this.type = type;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public void setTime(long millis) {
		time = new Date(millis);
	}
	
	
	public void setTimeToNow() {
		time = new Date(System.currentTimeMillis());
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getHelpCode() {
		return helpCode;
	}


	public void setHelpCode(int helpCode) {
		this.helpCode = helpCode;
	}


	@Override
	public String toString() {
		String result = TIME_FORMAT.format(getTime()) + " " + getMessage();
		if (!getType().equals(ApplicationLoggerMessageType.MESSAGE)) {
			result = getType().toString() + ": " + result;
		}
		return result;
	}
}