package info.webinsel.util.log;

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