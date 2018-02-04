/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.io;


import java.io.IOException;



/**
 * This exception is thrown by {@link LimitedReader} or {@link LimitedInputStream} if their {@code close()} method is called
 * while closing these streams is prohibited.
 * <p>
 * It does not inherit from {@link IOException} to make sure, that this exception is not caught in a deeper method that catches
 * all {@link IOException}s and can be handled separately. 
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class ClosingNotAllowedException extends IllegalStateException {
	public ClosingNotAllowedException() {
		super("Closing this stream is currently not allowed.");
	}

	
	public ClosingNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	
	public ClosingNotAllowedException(String s) {
		super(s);
	}

	
	public ClosingNotAllowedException(Throwable cause) {
		super(cause);
	}
}
