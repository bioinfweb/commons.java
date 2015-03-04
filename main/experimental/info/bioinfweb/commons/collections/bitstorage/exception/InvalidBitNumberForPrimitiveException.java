/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014-2015  Ben Stöver
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
package info.bioinfweb.commons.collections.bitstorage.exception;

import info.bioinfweb.commons.collections.bitstorage.BitField;



public class InvalidBitNumberForPrimitiveException extends RuntimeException implements BitStorageException {
	private BitField source;
	private long numberOfBits;
  private int primitiveSize;	

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param message - the error message
	 * @param source -  the bit field throwing this exception
	 * @param numberOfBits - the invalid number of bit that were tried to store in the primitive
	 * @param primitiveSize - the number of bits the primitive can contain 
	 */
	public InvalidBitNumberForPrimitiveException(String message, BitField source,	long numberOfBits, 
			int primitiveSize) {
		
		super(message);
		this.source = source;
		this.numberOfBits = numberOfBits;
		this.primitiveSize = primitiveSize;
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
	public long getNumberOfBits() {
		return numberOfBits;
	}


	/**
	 * Returns the number of bits the primitive where the bit should have been stored can contain. 
	 * 
	 * @return the number of bits including the sign bit, fixed-point part or exponent part 
	 */
	public int getPrimitiveSize() {
		return primitiveSize;
	}
}
