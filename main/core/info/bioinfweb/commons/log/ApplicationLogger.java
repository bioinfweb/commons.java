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