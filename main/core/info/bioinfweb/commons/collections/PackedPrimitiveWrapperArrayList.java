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


import info.bioinfweb.commons.Math2;

import java.util.AbstractList;



public abstract class PackedPrimitiveWrapperArrayList<E extends Number> extends AbstractList<E>{
	protected PackedIntegerArrayList packedList;

	
	protected PackedPrimitiveWrapperArrayList(int bitsPerValue, long minValue, int initialCapacity) {
		super();
		packedList = new PackedIntegerArrayList(bitsPerValue, minValue, initialCapacity);
	}
	
	
	private static void checkParameters(int bitsPerValue, int maxBitsPerValue, long minValue, long maxValueForType) {
    if ((bitsPerValue <= 0) || (bitsPerValue > maxBitsPerValue)) {    	
    	throw new IllegalArgumentException("At least 1 and at most " + maxBitsPerValue + 
    			" bits per value are allowed to be used.");
    }
    else {
  		long maxValue = minValue + Math2.longPow(2, bitsPerValue) - 1;
    	if (maxValue > maxValueForType) {
	    	throw new IllegalArgumentException("The specified combination of bitsPerValue (" + bitsPerValue + 
	    			") and minValue (" + minValue + ") cannot be represented by the specified primitive type.");
      }
    }
	}
	
	
	public static PackedPrimitiveWrapperArrayList<Byte> getByteInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 8, minValue, Byte.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Byte>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Byte get(int index) {
						return new Byte((byte)packedList.get(index));
					}
				};
	}

	
	public static PackedPrimitiveWrapperArrayList<Short> getShortInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 16, minValue, Short.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Short>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Short get(int index) {
						return new Short((short)packedList.get(index));
					}
				};
	}

	
	public static PackedPrimitiveWrapperArrayList<Integer> getIntegerInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 32, minValue, Integer.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Integer>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Integer get(int index) {
						return new Integer((int)packedList.get(index));
					}
				};
	}

	
	public static PackedPrimitiveWrapperArrayList<Long> getLongInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 63, minValue, Long.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Long>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Long get(int index) {
						return new Long(packedList.get(index));
					}
				};
	}

	
	@Override
	public void add(int index, E element) {
		packedList.add(index, element.longValue());
	}
	

	@Override
	public E remove(int index) {
		E result = get(index);
		packedList.remove(index);
		return result;
	}

	
	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		packedList.removeRange(fromIndex, toIndex - fromIndex);
	}
	

	@Override
	public E set(int index, E element) {
		E result = get(index);
		packedList.set(index, element.longValue());
		return result;
	}

	
	@Override
	public int size() {
		return (int)packedList.size();
	}
}
