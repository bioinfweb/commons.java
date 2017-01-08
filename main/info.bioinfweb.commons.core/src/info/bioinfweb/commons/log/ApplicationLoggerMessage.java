/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.log;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Represents a message send to an {@link ApplicationLogger}. 
 * 
 * @author Ben St&ouml;ver
 */
public class ApplicationLoggerMessage {
	public static final int NO_HELP_CODE = -1; 
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	
	private ApplicationLoggerMessageType type = null;
	private Date time = new Date(System.currentTimeMillis());
  private String message = "";
  private int helpCode = NO_HELP_CODE;
  
  
	public ApplicationLoggerMessage(ApplicationLoggerMessageType type, String message) {
		this(type, message, NO_HELP_CODE);
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
	
	
	public boolean hasHelpCode() {
		return getHelpCode() != NO_HELP_CODE;
	}


	@Override
	public String toString() {
		String result = getMessage();
		if (!getType().equals(ApplicationLoggerMessageType.MESSAGE)) {
			result = getType().toString() + ": " + result;
		}
		return TIME_FORMAT.format(getTime()) + " " + result;
	}
}