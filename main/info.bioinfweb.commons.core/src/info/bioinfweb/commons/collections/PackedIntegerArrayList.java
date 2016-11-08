/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 *   This file is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 * 
 *   This file is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 * 
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program. If not, see <http://www.gnu.org/licenses/>.
 *   
 * This file incorporates work covered by the following copyright and  
 * permission notice (Mainly but not exclusively the implementations of
 * the get() and set() methods are based on the Apache Lucene source code.):
 * 
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements. See the NOTICE.txt file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.Math2;

import java.util.Arrays;



/**
 * Implements a list of packed integer values which can occupy a space between 1 and 63 bits per value
 * depending on the specified constructor parameter. The number space represented by these integers may
 * but does not have to include negative values, depending on the constructor parameter {@code minValue}.
 * <p>
 * All elements are stored in an array of long values. If the number of bits in this array is not sufficient
 * anymore to take up the values of this list, a new array is created automatically. The initial number of 
 * elements (not equal to the number of longs in the underlying array object) the list can take up before
 * creating a new array object can be specified in the constructor.
 * <p>
 * This class can be used to store large lists of integer values in a specific range in a memory efficient 
 * way.
 * 
 * @author Ben St&ouml;ver
 */
public class PackedIntegerArrayList {
	public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
	public static final int BLOCK_SIZE = 64; // 32 = int, 64 = long
	public static final int BLOCK_BITS = 6; // The #bits representing BLOCK_SIZE
	public static final int MOD_MASK = BLOCK_SIZE - 1; // x % BLOCK_SIZE


  /** The array containing the bits used to store values contained in this list. */
  protected long[] array;
  
  /** The number of bits one value uses in {@link #array}. */
	private long bitsPerValue;
	
	/** The minimum value that can be represented by an element of this list. */
	private long minValue;

	/** The maximum value that can be represented by an element of this list. */
	private long maxValue;
	
  /** A right-aligned mask of width BitsPerValue used by {@link #get(long)}. */
  private final long maskRight;
  
  /** Optimization: Saves one lookup in {@link #get(long)}. */
  private final int bpvMinusBlockSize;
  
  /** The current size of the list. (Can be lower than the number of values {@link #array} can currently take up. */
	private long size;

	
  /**
   * Creates a new instance of this class.
   * 
   * @param bitsPerValue - the number of bit each value will consume in memory
   * @param minValue - the minimum value to be stored in the list
   * @param initialCapacity - the number of values the underlying array can take up initially
   */
  public PackedIntegerArrayList(int bitsPerValue, long minValue, long initialCapacity) {
    this.size = 0;
    if ((bitsPerValue <= 0) || (bitsPerValue >= 64)) {  // Unsigned 64 bits cannot be stored. 
    	throw new IllegalArgumentException("At least 1 and at most 63 bits per value are allowed to be used.");
    }
    else {
	    this.bitsPerValue = bitsPerValue;
	    this.minValue = minValue;
	    maxValue = minValue + Math2.longPow(2, bitsPerValue) - 1;
	    if (maxValue < this.minValue) {  // overflow, maxValue was higher than Long.MAX_VALUE
	    	throw new IllegalArgumentException("The specified combination of bitsPerValue (" + bitsPerValue + 
	    			") and minValue (" + minValue + ") cannot be represented by a long value.");
	    }
	    
	    this.array = new long[calculateArrayLength(initialCapacity)];
	    maskRight = ~0L << (BLOCK_SIZE - bitsPerValue) >>> (BLOCK_SIZE - bitsPerValue);
	    bpvMinusBlockSize = bitsPerValue - BLOCK_SIZE;
    }
  }
  
  
	/**
	 * Calculates how many bits are necessary to represent the specified number of different values.
	 * 
	 * @param elementCount the number of elements to be represented
	 * @return the necessary length in bits for a representation of {@code elementCount} different values/objects
	 */
	public static int calculateBitsPerValue(int elementCount) {
		int result = 1;
		int maxCount = 2;
		while (maxCount < elementCount) {  // 63 bit border cannot be reached with int values
			result++;
			maxCount *= 2;
		}
		return result;
	}

	
	/**
   * Returns the minimum value that can be represented by an element of this list.
   * 
   * @return the minimum value specified in the constructor
   */
  public long getMinValue() {
		return minValue;
	}


	/**
   * Returns the maximum value that can be represented by an element of this list.
   * 
	 * @return a value depending on {@link #getMinValue()} and the specified number of
	 *         bits per value
	 */
	public long getMaxValue() {
		return maxValue;
	}


	/**
	 * Returns the length in bits of an entry in this list.
	 * 
	 * @return the number of bits each element in this list uses
	 */
	public long getBitsPerValue() {
		return bitsPerValue;
	}


	/**
	 * Calculates the number of {@code long} array elements necessary to store the specified
	 * number of elements.
	 * 
	 * @param capacity - the number of list elements to be stored
	 * @return the needed array size
	 */
	protected int calculateArrayLength(long capacity) {
    long bitLength = capacity * bitsPerValue;
    long result = bitLength / BLOCK_SIZE;
    if (bitLength % BLOCK_SIZE > 0) {
    	result++;
    }
    if (result > MAX_ARRAY_LENGTH) {
    	throw new IllegalArgumentException("The value count of " + capacity + " is to high for " + 
          bitsPerValue + " bits per value.");
    }
    return (int)result;
  }
  
  
  /**
   * Makes sure that the underlying array can take up at least the specified number of elements.
   * If the current array is too small a new array which is at least 1.5 times as large as the 
   * current one (or larger if the requested capacity requires it) is created and the current 
   * contents are copied there.
   * 
   * @param newCapacity - the number of elements that need to be stored in the array
   */
  public void ensureCapacity(long newCapacity) {
  	int newArrayLength = calculateArrayLength(newCapacity);
  	if (newArrayLength > array.length) {
      array = Arrays.copyOf(array, Math.max(newArrayLength, 
      		Math.min(MAX_ARRAY_LENGTH, (array.length * 3) / 2 + 1)));
    }
  }
  
  
	/**
	 * Reserves space for the specified number of elements at the specified position. Elements right of
	 * the specified position are moved accordingly. The new elements are not initialized. The underlying
	 * array is resized if necessary.
	 * 
	 * @param index - the index of the first element to be inserted
	 * @param length - the number of elements to be inserted
	 */
	protected void insertRange(long index, long length) {
		if (length > 0) {
			ensureCapacity(size + length);  // Throws an IllegalArgumentException if the array will become longer than 2 GB
			
			long lengthInBits = length * bitsPerValue;
			long indexInBits = index * bitsPerValue;
			long bitShiftInRightBlock = lengthInBits % BLOCK_SIZE;
			long bitShiftInLeftBlock = BLOCK_SIZE - bitShiftInRightBlock;
			int blockShift = (int)(lengthInBits / BLOCK_SIZE);  // If integer conversion would not be possible, ensureCapacity() would already have thrown an exception.
			int minBlockIndex = (int)(index * bitsPerValue / BLOCK_SIZE) + blockShift;
	    int maxBlockIndex = Math.min(array.length - 1, (int)((size + length) * bitsPerValue / BLOCK_SIZE));
			
	    long initialBitShift = BLOCK_SIZE - indexInBits % BLOCK_SIZE;
			long initialBits = array[minBlockIndex] >>> initialBitShift << initialBitShift;

      if (bitShiftInRightBlock == 0) {
      	for (int blockIndex = maxBlockIndex; blockIndex > minBlockIndex; blockIndex--) {  // System.arraycopy() does not work here.
					array[blockIndex] = array[blockIndex - blockShift];
				}
      }
      else { 
				for (int blockIndex = maxBlockIndex; blockIndex > minBlockIndex; blockIndex--) {
					array[blockIndex] = (array[blockIndex - blockShift] >>> bitShiftInRightBlock) |
							(array[blockIndex - blockShift - 1] << bitShiftInLeftBlock);
				}
      }
			array[minBlockIndex] = array[minBlockIndex - blockShift] >>> bitShiftInRightBlock;
			if ((blockShift == 0) && (initialBitShift < BLOCK_SIZE)) {  // array[minBlockIndex] also contains initial bits
				array[minBlockIndex] = array[minBlockIndex] & (-1l >>> (BLOCK_SIZE - initialBitShift)) | initialBits;
			}
			size += length;
		}
	}

	
	/**
	 * Removes the specified elements from this list. All elements to the right of the removed sequence
	 * are copied to the left accordingly.
	 * 
	 * @param index - the index of the first element to be removed
	 * @param length - the number of elements to be removed
	 * @throws IllegalArgumentException if the specified range is outside the current size of the list
	 */
	protected void removeRange(long index, long length) {
		if ((index < 0) || (index + length > size)) {
			throw new IllegalArgumentException("The specified range starting at " + index + " with the length " + 
		      length + " cannot be removed because it is not completly contained in the current list.");
		}
		else if (length == size) {
			size = 0;
		}
		else if (length > 0) {
			long lengthInBits = length * bitsPerValue;
			int firstBlockIndex = calculateArrayLength(index + 1) - 1;
			int lastBlockIndex = calculateArrayLength(size - length) - 1;
			int blockShift = (int)lengthInBits / BLOCK_SIZE;
			
			long initialBitShift = BLOCK_SIZE - index * bitsPerValue % BLOCK_SIZE;
			boolean remainingFirstBlock = firstBlockIndex >= 0;
			long initialBits = 0;
			if (remainingFirstBlock) {
				initialBits = array[firstBlockIndex] >>> initialBitShift << initialBitShift;  // If blockMinusInitialBitLength is 64 no shift is performed.
			}

			long bitShiftInLeftBlock = lengthInBits % BLOCK_SIZE;
			long bitShiftInRightBlock = BLOCK_SIZE - bitShiftInLeftBlock;

    	int start = Math.max(0, firstBlockIndex);
      if (bitShiftInLeftBlock == 0) {
				for (int blockIndex = start; blockIndex <= lastBlockIndex; blockIndex++) {  // System.arraycopy() would make a copy to a temporary array.
					array[blockIndex] = array[blockIndex + blockShift];
				}
      }
      else { 
				for (int blockIndex = start; blockIndex < lastBlockIndex; blockIndex++) {
					array[blockIndex] = array[blockIndex + blockShift] << bitShiftInLeftBlock | 
							array[blockIndex + blockShift + 1] >>> bitShiftInRightBlock;
				}
				array[lastBlockIndex] = array[lastBlockIndex + blockShift] << bitShiftInLeftBlock;
				if (array.length > lastBlockIndex + blockShift + 1) {
					array[lastBlockIndex] |= array[lastBlockIndex + blockShift + 1] >>> bitShiftInRightBlock;
				}
      }
			
      if (remainingFirstBlock && (initialBitShift < BLOCK_SIZE)) {
      	array[firstBlockIndex] = array[firstBlockIndex] & (-1l >>> (BLOCK_SIZE - initialBitShift)) | initialBits;
      }
			
			size -= length;
		}
	}
	
	
	private void checkIndex(long index, long additionalSpace) {
		if (!Math2.isBetween(index, 0, size + additionalSpace - 1)) {
			throw new IndexOutOfBoundsException("The index " + index + " is out of bounds (" + 0 + ", " + 
		      (size + additionalSpace - 1) + ").");
		}
	}
	
	
	private void checkValue(long value) {
		if (!Math2.isBetween(value, minValue, maxValue)) {
			throw new IllegalArgumentException("The specified value " + value + " is not in the element range of this list (" + 
		      minValue + ", " + maxValue + ").");
		}
	}

	
	/**
	 * Adds the specified element to this list and moves other elements to the right if necessary.
	 * 
	 * @param index - the index where the new element shall be inserted
	 * @param value - the value to be inserted
	 * 
	 * @throws IndexOutOfBoundsException if {@code index} is lower than 0 or greater than the current size of 
	 *         the list
	 * @throws IllegalArgumentException if {@code value} does not fit into the range specified by the constructor
	 *         parameters {@code bitsPerValue} and {@code minValue}
	 */
	public void add(long index, long value) {
		checkIndex(index, 1);
		checkValue(value);
		insertRange(index, 1);
		set(index, value);
	}
	

	/**
	 * Adds the specified element to the end of this list.
	 * 
	 * @param value - the value to be appended
	 * @throws IllegalArgumentException if {@code value} does not fit into the range specified by the constructor
	 *         parameters {@code bitsPerValue} and {@code minValue}
	 */
	public void add(long value) {
		checkValue(value);
		size++;
		ensureCapacity(size);
		set(size - 1, value);
	}
	

	/**
	 * Returns the element stored at the specified index.
	 * 
	 * @param index - the index of the element to be read
	 * @return the integer value stored at this position
	 * @throws IndexOutOfBoundsException if {@code index} is lower than 0 or greater or equal to the current size 
	 *         of the list
	 */
	public long get(long index) {
		checkIndex(index, 0);
		
    final long majorBitPos = index * bitsPerValue;  // The abstract index in a contiguous bit stream
    final int elementPos = (int)(majorBitPos >>> BLOCK_BITS);  // divide by BLOCK_SIZE  // The index in the backing long-array
    final long endBits = (majorBitPos & MOD_MASK) + bpvMinusBlockSize;  // The number of value-bits in the second long

    long result;
    if (endBits <= 0) { // Single block
      result = (array[elementPos] >>> -endBits) & maskRight;
    }
    else {  // Two blocks
    	result = ((array[elementPos] << endBits)
          | (array[elementPos+1] >>> (BLOCK_SIZE - endBits)))
          & maskRight;
    }
    return result + minValue;
	}
	

	/**
	 * Removes the element at the specified position from this list.
	 * 
	 * @param index - the index of the element to be removed
	 * @throws IndexOutOfBoundsException if {@code index} is lower than 0 or greater or equal to the current size 
	 *         of the list
	 */
	public void remove(long index) {
		removeRange(index, 1);
	}
	

	/**
	 * Replaces the value at the specified position with the new value.
	 * 
	 * @param index - the index of the value to be replaced
	 * @param value - the new value for the specified position
	 * @throws IndexOutOfBoundsException if {@code index} is lower than 0 or greater or equal to the current size 
	 *         of the list
	 * @throws IllegalArgumentException if {@code value} does not fit into the range specified by the constructor
	 *         parameters {@code bitsPerValue} and {@code minValue}
	 */
	public void set(long index, long value) {
		checkIndex(index, 0);
		checkValue(value);
		value = value - minValue;
		
    final long majorBitPos = index * bitsPerValue;  // The abstract index in a contiguous bit stream
    final int elementPos = (int)(majorBitPos >>> BLOCK_BITS);  // divide by BLOCK_SIZE  // The index in the backing long-array
    final long endBits = (majorBitPos & MOD_MASK) + bpvMinusBlockSize;  // The number of value-bits in the second long

    if (endBits <= 0) {  // Single array element
      array[elementPos] = array[elementPos] & ~(maskRight << -endBits) | (value << -endBits);
    }
    else {  // Two array elements
	    array[elementPos] = array[elementPos] & ~(maskRight >>> endBits) | (value >>> endBits);
	    array[elementPos+1] = array[elementPos+1] & (~0L >>> endBits) | (value << (BLOCK_SIZE - endBits));
    }
	}

	
	/**
	 * Returns the number of elements currently contained in this list. (This is not equal to the size
	 * of the underlying {@code long} array.
	 * 
	 * @return the current size of the list
	 */
	public long size() {
		return size;
	}
}
