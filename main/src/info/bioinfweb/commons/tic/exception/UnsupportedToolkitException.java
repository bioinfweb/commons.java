/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
 * <http://bioinfweb.info/Commons>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.tic.exception;

import info.bioinfweb.commons.tic.TargetToolkit;



/**
 * This exceptions is throw if a value of {@link TargetToolkit} is specified, which is not
 * supported by the called method.
 * 
 * @author Ben St&ouml;ver
 *
 */
public class UnsupportedToolkitException extends RuntimeException {
	/**
	 * Crates a new instance with the default message.
	 * 
	 * @param toolkit - the toolkit that is not supported
	 */
	public UnsupportedToolkitException(TargetToolkit toolkit) {
		super("The toolkit " + toolkit.toString() + " is not supported.");
	}

	
	public UnsupportedToolkitException(String message, Throwable cause) {
		super(message, cause);
	}

	
	public UnsupportedToolkitException(String message) {
		super(message);
	}

	
	public UnsupportedToolkitException(Throwable cause) {
		super(cause);
	}
}
