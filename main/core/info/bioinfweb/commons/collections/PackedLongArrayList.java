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
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.testing.TestTools;

import java.util.Arrays;



public class PackedLongArrayList {
	public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
	public static final int BLOCK_SIZE = 64; // 32 = int, 64 = long
	public static final int BLOCK_BITS = 6; // The #bits representing BLOCK_SIZE
	public static final int MOD_MASK = BLOCK_SIZE - 1; // x % BLOCK_SIZE


  /** The array containing the bits used to store values contained in this list. */
  protected long[] array;
  
  /** The number of bits one value uses in {@link #array}. */
	private long bitsPerValue;
	
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
   * @param initialCapacity - the number of values the underlying array can take up initially
   */
  public PackedLongArrayList(int bitsPerValue, long initialCapacity) {
    this.size = 0;
    if ((bitsPerValue <= 0) || (bitsPerValue >= 64)) {
    	throw new IllegalArgumentException("At least 1 and at most 64 bits per value are allowed to be used.");
    }
    else {
	    this.bitsPerValue = bitsPerValue;
	    this.array = new long[calculateArrayLength(initialCapacity)];
	    maskRight = ~0L << (BLOCK_SIZE - bitsPerValue) >>> (BLOCK_SIZE - bitsPerValue);
	    bpvMinusBlockSize = bitsPerValue - BLOCK_SIZE;
    }
  }
  
  
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
  
  
  protected void ensureCapacity(long newCapacity) {
  	int newArrayLength = calculateArrayLength(newCapacity);
  	if (newArrayLength > array.length) {
      array = Arrays.copyOf(array, Math.max(newArrayLength, 
      		Math.min(MAX_ARRAY_LENGTH, (array.length * 3) / 2 + 1)));
    }
  }
  

	protected void insertRange(long index, long length) {
		ensureCapacity(size + length);  // Thrown an IllegalArgumentException if the array will become longer than 2 GB
		
		long bitShiftInRightBlock = length * bitsPerValue % BLOCK_SIZE;
		long bitShiftInLeftBlock = BLOCK_SIZE - bitShiftInRightBlock;
		int blockShift = (int)(length * bitsPerValue / BLOCK_SIZE) + 1;  // If integer conversion would not be possible, ensureCapacity() would already have thrown an exception.
		int minBlockIndex = calculateArrayLength(index) + blockShift - 1;

		for (int blockIndex = calculateArrayLength(size + length); blockIndex >= minBlockIndex; blockIndex--) {
			array[blockIndex] = (array[blockIndex - blockShift] << bitShiftInLeftBlock) | 
					(array[blockIndex - blockShift + 1] >>> bitShiftInRightBlock);
		}
		size += length;
	}

	
	protected void removeRange(long index, long length) {
		long bitLength1 = index * bitsPerValue % BLOCK_SIZE;
		long bitLength2 = BLOCK_SIZE - bitLength1;
		long bitLength3 = (index + length) * bitsPerValue % BLOCK_SIZE;
		long bitLength4 = BLOCK_SIZE - bitLength3;
		long bitLength5 = length * bitsPerValue % BLOCK_SIZE;
		long bitLength6 = BLOCK_SIZE - bitLength5;
		long bitLength7 = bitLength3 - bitLength1;
		long bitLength8 = BLOCK_SIZE - bitLength7;
		
		int blockShift = (int)(length * bitsPerValue / BLOCK_SIZE);  // If integer conversion would not be possible, the specified length would be longer than the list.

		int minBlockIndex = calculateArrayLength(index) - 1;
		int maxBlockIndex = calculateArrayLength(index + length) - 1;
		//System.out.println("block indices: " + minBlockIndex + " " + maxBlockIndex + " " + blockShift);
   
		array[minBlockIndex] = array[minBlockIndex] >>> bitLength2 << bitLength2;
		if (bitLength1 > bitLength5) {  // First block has to be calculated from three other blocks
			System.out.println("Fall 1: " + bitLength1 + " " + bitLength5);
			array[minBlockIndex] |= array[minBlockIndex + blockShift] << bitLength4 >>> bitLength1;
			if (minBlockIndex + blockShift + 1 < array.length) {
				array[minBlockIndex] |= (array[minBlockIndex + blockShift + 1] >>> bitLength6);
	
				for (int blockIndex = minBlockIndex + 1; blockIndex < maxBlockIndex; blockIndex++) {
					array[blockIndex] = array[blockIndex + blockShift] << bitLength5 |
							array[blockIndex + blockShift + 1] >>> bitLength6;
				}
				array[maxBlockIndex] = array[maxBlockIndex + blockShift] << bitLength5;
			}
		}
		else {
			System.out.println("Fall 2");
			array[minBlockIndex] |= array[minBlockIndex + blockShift] >>> bitLength7 << bitLength6 >>> bitLength4;
			for (int blockIndex = minBlockIndex + 1; blockIndex <= maxBlockIndex; blockIndex++) {
				array[blockIndex] = array[blockIndex + blockShift - 1] << bitLength8 |
						array[blockIndex + blockShift] >>> bitLength7;
			}
		}
		
		size -= length;
	}

	
	public void add(long index, long value) {
		insertRange(index, 1);
		set(index, value);
	}
	

	public void add(long value) {
		size++;
		ensureCapacity(size);
		set(size - 1, value);
	}
	

	public long get(long index) {
    final long majorBitPos = index * bitsPerValue;  // The abstract index in a contiguous bit stream
    final int elementPos = (int)(majorBitPos >>> BLOCK_BITS);  // divide by BLOCK_SIZE  // The index in the backing long-array
    final long endBits = (majorBitPos & MOD_MASK) + bpvMinusBlockSize;  // The number of value-bits in the second long

    if (endBits <= 0) { // Single block
      return (array[elementPos] >>> -endBits) & maskRight;
    }
    // Two blocks
    return ((array[elementPos] << endBits)
        | (array[elementPos+1] >>> (BLOCK_SIZE - endBits)))
        & maskRight;
	}
	

	public void remove(long index) {
		removeRange(index, 1);
	}
	

	public void set(long index, long value) {
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

	
	public long size() {
		return size;
	}
}
