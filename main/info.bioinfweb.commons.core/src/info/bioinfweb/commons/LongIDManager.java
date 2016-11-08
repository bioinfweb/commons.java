/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons;



/**
 * A tool class that creates unique {@code long} IDs. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class LongIDManager {
	private long nextID = 0l;
	
	
	/**
	 * Returns a new sequence identifier that has not been returned before.
	 * 
	 * @return an integer greater or equal to zero
	 */
	public long createNewID() {
		nextID++;
		return nextID - 1;
	}
	
	
	/**
	 * Peeks the value that would be returned by the next call of {@link #createNewID()}. 
	 * 
	 * @return an integer greater or equal to zero
	 */
	public long peekNextID() {
		return nextID;
	}
}
