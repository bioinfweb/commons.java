/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
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
package info.bioinfweb.commons.collections.bitstorage;


import java.util.List;



public abstract class AbstractIntegerBitList<N extends Number> implements List<N> {
	private static final int BITS_PER_ARRAY_ELEMENT = 32;
	
	private int bitsPerStoredElement;
	private int storedPerArrayElement;
	private int[] masks0;
	private int[] masks1;
	private int[] array;
	
	
	/**
	 * Increases the number of bits per stored element in a way that the remaining bits per array element are
	 * optimally used.
	 */
	private int increaseBitsPerStoredElement(int bitsPerElement) {
		return bitsPerElement + (BITS_PER_ARRAY_ELEMENT % bitsPerElement) / (BITS_PER_ARRAY_ELEMENT / bitsPerElement);
	}
	
	
	public AbstractIntegerBitList(int bitsPerStoredElement) {
		super();
		this.bitsPerStoredElement = increaseBitsPerStoredElement(bitsPerStoredElement);
		storedPerArrayElement = BITS_PER_ARRAY_ELEMENT / this.bitsPerStoredElement;
		// Work currently stopped, because a byte array might be sufficient for LibrAlign.
		// Relevant information for implementation can be found in BitShiftTest class in LibrAlign test project.
	}


	protected int getInt(int index) {
    return 0;		
	}
}
