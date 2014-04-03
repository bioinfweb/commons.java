package info.bioinfweb.commons.log;



/**
 * Provides a simple logging API that can be used by applications to output log messages to the user.
 * 
 * @author Ben St&ouml;ver
 */
public interface ApplicationLogger {
  /**
   * Outputs a usual message to the user.
   * @param message
   */
  public void addMessage(ApplicationLoggerMessage message);
  
  
  /**
   * Outputs a usual message to the user.
   * @param message
   */
  public void addMessage(String message);
  
  
  /**
   * Outputs a usual message to the user.
   * @param message
   * @param helpCode - the help code associated with this messages
   */
  public void addMessage(String message, int helpCode);
  
  
  /**
   * Outputs a warning message to the user.
   * @param message
   */
  public void addWarning(String message);
  
  
  /**
   * Outputs a warning message to the user.
   * @param message
   * @param helpCode - the help code associated with this warning
   */
  public void addWarning(String message, int helpCode);
  
  
  /**
   * Outputs an error message to the user.
   * @param message
   */
  public void addError(String message);
  
  
  /**
   * Outputs an error message to the user.
   * @param message
   * @param helpCode - the help code associated with this warning
   */
  public void addError(String message, int helpCode);

  
  /**
   * Outputs an error message to the user.
   * 
   * @param throwable - the Throwable describing the error
   * @param includeStackTrace - Specify {@code true} here, if you want the whole stack trace to 
   *        be included in the error message or {@code false}, if only the message shall be 
   *        contained in the output.
   */
  public void addError(Throwable throwable, boolean includeStackTrace);
  
  
  /**
   * Outputs an error message to the user.
   * 
   * @param throwable - the Throwable describing the error
   * @param includeStackTrace - Specify {@code true} here, if you want the whole stack trace to 
   *        be included in the error message or {@code false}, if only the message shall be 
   *        contained in the output.
   * @param helpCode - the help code associated with this warning
   */
  public void addError(Throwable throwable, boolean includeStackTrace, int helpCode);
}