package info.webinsel.util.log;



/**
 * This implementation of {@link ApplicationLogger} does not display or store the received messages anywhere. 
 * It can be used as a default implementation, if no logger is necessary in a process that expects a logger
 * parameter.
 * 
 * @author Ben St&ouml;ver
 */
public class VoidApplicationLogger implements ApplicationLogger {
	@Override
  public void addMessage(ApplicationLoggerMessage message) {}

	
	@Override
  public void addMessage(String message) {}

	
	@Override
  public void addMessage(String message, int helpCode) {}

	
	@Override
  public void addWarning(String message) {}

	
	@Override
  public void addWarning(String message, int helpCode) {}


	@Override
	public void addError(String message) {}


	@Override
	public void addError(String message, int helpCode) {}
}
