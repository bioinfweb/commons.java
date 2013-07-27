package info.webinsel.util.log;



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
}