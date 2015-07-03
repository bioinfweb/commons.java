/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.log;


import java.io.PrintWriter;
import java.io.StringWriter;



/**
 * Implements basic functionality (especially method delegation) of a application logger.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class AbstractApplicationLogger implements ApplicationLogger {
	@Override
	public void addMessage(String message, int helpCode) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.MESSAGE, message, helpCode));
	}
	

	@Override
	public void addMessage(String message) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.MESSAGE, message));
	}

	
	@Override
	public void addWarning(String message, int helpCode) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.WARNING, message, helpCode));
	}

	
	@Override
	public void addWarning(String message) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.WARNING, message));
	}


	@Override
	public void addError(String message, int helpCode) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.ERROR, message, helpCode));
	}


	@Override
	public void addError(String message) {
		addMessage(new ApplicationLoggerMessage(ApplicationLoggerMessageType.ERROR, message));
	}


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace) {
		addError(throwable, includeStackTrace, ApplicationLoggerMessage.NO_HELP_CODE);
	}


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace, int helpCode) {
		if (includeStackTrace) {
			StringWriter stringWriter = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(stringWriter);
	    try {
	    	throwable.printStackTrace(printWriter);
	    }
	    finally {
	      printWriter.close();
	    }
	    addError(stringWriter.toString(), helpCode);
		}
		else {
			addError(throwable.getLocalizedMessage(), helpCode);
		}
	}
}