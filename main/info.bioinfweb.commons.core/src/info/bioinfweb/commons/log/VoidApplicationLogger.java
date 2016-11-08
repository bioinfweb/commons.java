/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace) {}


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace, int helpCode) {}
}
