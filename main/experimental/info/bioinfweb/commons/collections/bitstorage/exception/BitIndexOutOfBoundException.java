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
package info.bioinfweb.commons.collections.bitstorage.exception;


import info.bioinfweb.commons.collections.bitstorage.BitField;



/**
 * Exception that is thrown if a bit index of a {@link BitField} is used which lies outside the current size
 * of that field.
 * 
 * @author Ben St&ouml;ver
 */
public class BitIndexOutOfBoundException extends IndexOutOfBoundsException implements BitStorageException {
	private BitField source;
	private long index;

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param message - the error message
	 * @param source -  the bit field throwing this exception
	 * @param index - the invalid bit index
	 */
	public BitIndexOutOfBoundException(String message, BitField source, long index) {
		super(message);
		this.source = source;
		this.index = index;
	}


	@Override
	public BitField getSource() {
		return source;
	}


	/**
	 * Returns the invalid bit index.
	 *  
	 * @return the index that was provided when calling the constructor
	 */
	public long getIndex() {
		return index;
	}
}
