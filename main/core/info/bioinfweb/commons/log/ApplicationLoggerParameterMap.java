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


import info.bioinfweb.commons.collections.ParameterMap;



public class ApplicationLoggerParameterMap extends ParameterMap {
	public static final String KEY_APPLICATION_LOGGER = 
			"info.bioinfweb.commons.log.ApplicationLogger_ApplicationLoggerParameterMap";
	
	
	/**
	 * Returns a stored {@link ApplicationLogger} object or a new instance of {@link VoidApplicationLogger} 
	 * if no appropriate object is stored.
	 * 
	 * @param key - the key under which the result is stored
	 * @return an appropriate object or a new instance of {@link VoidApplicationLogger}
	 */
	public ApplicationLogger getApplicationLogger() {
		Object result = get(KEY_APPLICATION_LOGGER);
		if (result instanceof ApplicationLogger) {
			return (ApplicationLogger)result;
		}
		else {
			return new VoidApplicationLogger();
		}
	}
	
	
	public void putApplicationLogger(ApplicationLogger logger) {
		put(KEY_APPLICATION_LOGGER, logger);
	}
	
	
	public void removeApplicationLogger() {
		Object value = get(KEY_APPLICATION_LOGGER);
		if (value != null) {
			remove(value);
		}
	}
}
