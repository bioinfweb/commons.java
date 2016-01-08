/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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


import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;



/**
 * This implementation of {@link ApplicationLogger} delegates to a set of other classes implementing
 * this interface. It can be used of several different implementations of {@link ApplicationLogger} 
 * should monitor a process at the same time.
 * 
 * @author Ben St&ouml;ver
 */
public class MultipleApplicationLoggersAdapter implements ApplicationLogger {
	private Collection<ApplicationLogger> loggers = new Vector<ApplicationLogger>(8);
	

	public Collection<ApplicationLogger> getLoggers() {
		return loggers;
	}


	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addMessage(message);
		}
	}
	

	@Override
	public void addMessage(String message) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addMessage(message);
		}
	}

	
	@Override
	public void addMessage(String message, int helpCode) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addMessage(message, helpCode);
		}
	}

	
	@Override
	public void addWarning(String message) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addWarning(message);
		}
	}

	
	@Override
	public void addWarning(String message, int helpCode) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addWarning(message, helpCode);
		}
	}
	

	@Override
	public void addError(String message) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addError(message);
		}
	}

	
	@Override
	public void addError(String message, int helpCode) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addError(message, helpCode);
		}
	}


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addError(throwable, includeStackTrace);
		}
	}


	@Override
	public void addError(Throwable throwable, boolean includeStackTrace, int helpCode) {
		Iterator<ApplicationLogger> iterator = loggers.iterator();
		while (iterator.hasNext()) {
			iterator.next().addError(throwable, includeStackTrace, helpCode);
		}
	}
}
