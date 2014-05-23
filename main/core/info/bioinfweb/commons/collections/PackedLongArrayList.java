/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben St�ver
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
			int firstBlockIndex = calculateArrayLength(index) - 1;
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
      	System.arraycopy(array, start + blockShift, array, start, lastBlockIndex - start + 1);
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
			
      if (remainingFirstBlock) {
      	long mask = 0;
      	if (initialBitShift < BLOCK_SIZE) {
      		mask = -1l >>> (BLOCK_SIZE - initialBitShift);
      	}
      	array[firstBlockIndex] = array[firstBlockIndex] & mask | initialBits;  //TODO Warum muss initialBits nicht 0 sein bei initialBitShift = 64?
      }
			
			size -= length;
		}
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
