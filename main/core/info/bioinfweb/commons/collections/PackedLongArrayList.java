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
    if ((bitsPerValue <= 0) || (bitsPerValue > 64)) {
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
  
  
  private long shiftBitsLeft(long value, long shift) {
  	if (shift >= 64) {
  		return 0;
  	}
  	else {
  		return value << shift;
  	}
  }
  

  private long shiftBitsRight(long value, long shift) {
  	if (shift >= 64) {
  		return 0;
  	}
  	else {
  		return value >>> shift;
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
		if (length > 0) {
			long bitLength1 = index * bitsPerValue % BLOCK_SIZE;
			long bitLength2a = BLOCK_SIZE - bitLength1;
			long bitLengthHelper1 = (index + length) * bitsPerValue;
			long bitLength2b = BLOCK_SIZE - bitLengthHelper1 % BLOCK_SIZE;
			
			int blockShift = (int)(length * bitsPerValue / BLOCK_SIZE);  // If integer conversion would not be possible, the specified length would be longer than the list.
//			if (length * bitsPerValue % BLOCK_SIZE == 0) {
//				blockShift--;
//			}
	
			int minBlockIndex = calculateArrayLength(index) - 1;
			int maxBlockIndex = calculateArrayLength(size - length) - 1;
			//System.out.println("block indices: " + minBlockIndex + " " + maxBlockIndex + " " + blockShift);
			
			long remainingBitsInFirstBlock = 0;
			if ((bitLength1 > 0) && (bitLength2a < 64)) {  // If bitLength2a is 64 no bit shall remain, but Java uses bitLength2a mod 64 for shift operations.
				remainingBitsInFirstBlock = array[minBlockIndex] >>> bitLength2a << bitLength2a;  // array[minBlockIndex] cannot be used to store this values because this value will still be needed if blockShift is 0. 
			}
			if (bitLength2b >= bitLength2a) {  // Fall 1
				long bitLength3a = BLOCK_SIZE - (bitLengthHelper1 + bitLength2a) % BLOCK_SIZE;
				//long bitLength3a = (bitLengthHelper1 + bitLength2a) % BLOCK_SIZE;
				//if (bitLength3a > 0) {
				//	bitLength3a = BLOCK_SIZE - bitLength3a;
				//}
//				else {
//					System.out.println("bitLength3a = 0" + bitLength3a);
//				}
				long bitLength4a = BLOCK_SIZE - bitLength3a;
				
				System.out.println("Fall 1: " + minBlockIndex + " " + maxBlockIndex + " " + blockShift);
				System.out.println(bitLength1 + " " + bitLength2a + " " + bitLength2b + " " + bitLength3a + " " + bitLength4a);			
    	  System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex]));
    	  System.out.println(TestTools.toBinaryRepresentation(remainingBitsInFirstBlock));
	  		System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex + blockShift + 1] << (bitLength1 - bitLength3a) >>> bitLength1));
				if (bitLength1 > 0) {
					array[minBlockIndex] = remainingBitsInFirstBlock | 
							array[minBlockIndex + blockShift + 1] << (bitLength1 - bitLength3a) >>> bitLength1;
				}
	  		//System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex]));
				if ((bitLength3a == 64) || (bitLength4a == 64)) {
					System.out.println("Complete bit shift: " + bitLength3a + " " + bitLength4a + " " + minBlockIndex + " " + maxBlockIndex + " " + index + " " + length + " " + bitsPerValue);
				}
				if (maxBlockIndex > minBlockIndex) {
//					if (bitLength2b == bitLength2a) {  // Fall 3
//						System.out.println("Fall 3");
//						for (int blockIndex = minBlockIndex + 1; blockIndex <= maxBlockIndex; blockIndex++) {
//							array[blockIndex] = array[blockIndex + blockShift];
//						}
//					}
//					else {  // Fall 1
						for (int blockIndex = minBlockIndex + 1; blockIndex < maxBlockIndex; blockIndex++) {
							array[blockIndex] = array[blockIndex + blockShift] << bitLength4a |
									shiftBitsRight(array[blockIndex + blockShift + 1], bitLength3a);
						}
						array[maxBlockIndex] = array[maxBlockIndex + blockShift] << bitLength4a;
						if (array.length > maxBlockIndex + blockShift + 1) {
							array[maxBlockIndex] |= shiftBitsRight(array[maxBlockIndex + blockShift + 1], bitLength3a);
						}
//					}
				}
			}
			else {  // Fall 2
				long bitLength3b = BLOCK_SIZE - bitLength1 - bitLength2b;
				long bitLength4b = BLOCK_SIZE - bitLength3b;
				
				System.out.println("Fall 2");
//	  	  System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex]));
//				System.out.println(bitLength1 + " " + bitLength2a + " " + bitLength2b + " " + bitLength3b + " " + bitLength4b);			
//	  	  System.out.println(TestTools.toBinaryRepresentation(remainingBitsInFirstBlock));
//	  	  System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex + blockShift] << (BLOCK_SIZE - bitLength2b) >>> bitLength1));
//	  	  System.out.println(TestTools.toBinaryRepresentation(array[minBlockIndex + blockShift + 1] >>> bitLength4b));
	
				if (bitLength1 > 0) {
					array[minBlockIndex] = remainingBitsInFirstBlock | 
							array[minBlockIndex + blockShift] << (BLOCK_SIZE - bitLength2b) >>> bitLength1 |
							array[minBlockIndex + blockShift + 1] >>> bitLength4b;
				}
//	  	  System.out.println("1: " + TestTools.toBinaryRepresentation(array[minBlockIndex]));
				if (maxBlockIndex > minBlockIndex) {
					for (int blockIndex = minBlockIndex + 1; blockIndex < maxBlockIndex; blockIndex++) {
						array[blockIndex] = array[blockIndex + blockShift] << bitLength3b |
								array[blockIndex + blockShift + 1] >>> bitLength4b;
					}
					array[maxBlockIndex] = array[maxBlockIndex + blockShift] << bitLength3b;
					if (array.length > maxBlockIndex + blockShift + 1) {
						array[maxBlockIndex] |= array[maxBlockIndex + blockShift + 1] >>> bitLength4b;
					}
				}
//	  	  System.out.println("2: " + TestTools.toBinaryRepresentation(array[minBlockIndex]));
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
