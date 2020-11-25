/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018  Ben Stöver, Sarah Wiechers
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



/**
 * An implementation of {@link ApplicationLogger} that outputs all messages to {@link System#out} and {@link System#err}.
 * <p>
 * {@link #isUseErrorStream()} determines to which stream error messages are written. The default value is {@code true}. All other messages are always written
 * to {@link System#out}.
 * 
 * @author Ben St&ouml;ver
 * @since 3.2.0
 */
public class ConsoleApplicationLogger extends AbstractApplicationLogger {
	private boolean useErrorStream = true;
	
	
	/**
	 * Determines whether error messages received by this logger should be written to {@link System#err} or {@link System#out}.
	 * 
	 * @return {@code true} if error messages are currently written to {@link System#err} or {@code false} if they are written to {@link System#out} instead
	 */
	public boolean isUseErrorStream() {
		return useErrorStream;
	}

	
	/**
	 * Determines whether error messages received by this logger should be written to {@link System#err} or {@link System#out}.
	 * 
	 * @param useErrorStream Specify {@code true} here if error messages should be written to {@link System#err} or {@code false} if they should be written to
	 *        {@link System#out}.
	 */
	public void setUseErrorStream(boolean useErrorStream) {
		this.useErrorStream = useErrorStream;
	}

	
	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		if (ApplicationLoggerMessageType.ERROR.equals(message.getType())) {
			System.err.println(message);
		}
		else {
			System.out.println(message);
		}
	}
}
