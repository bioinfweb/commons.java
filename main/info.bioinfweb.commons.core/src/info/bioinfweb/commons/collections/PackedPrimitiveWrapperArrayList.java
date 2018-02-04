/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.collections;


import info.bioinfweb.commons.Math2;

import java.math.BigInteger;
import java.util.AbstractList;
import java.util.List;



/**
 * An adapter class providing access to an instance if {@link PackedIntegerArrayList} using the {@link List}
 * interface where the element type can be any numeric primitive wrapper class of the Java API, namely {@link Byte},
 * {@link Short}, {@link Integer} or {@link Long}.
 * <p>
 * The number of bits per value to be stored in the underlying packed list can be determined independently of 
 * the selected wrapper type. 
 * <p>
 * Using {@link Float} or {@link Double} would also be possible but they would be rounded to {@code long} before 
 * they are stored. If instances of {@link BigInteger} are used they would be treated as described in 
 * {@link BigInteger#longValue()}. 
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> - the primitive wrapper class to use (See above for details on how which classes are treated.)
 */
public abstract class PackedPrimitiveWrapperArrayList<E extends Number> extends AbstractList<E> {
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
	
	
	/**
	 * Creates a new instance of this class using {@link Byte} elements.
	 * 
	 * @param bitsPerValue - the number of bits per value that shall be stored
	 * @param minValue - the minimal value to be represented by the underlying packed list
	 * @param initialCapacity - the initial number of elements the underlying packed list can take up before 
	 *        resizing its array
	 * @return the new instance
	 * @throws IllegalArgumentException if the combination if bits per value and the minimal value could lead to
	 *         values that cannot be stored in a {@code byte}
	 */
	public static PackedPrimitiveWrapperArrayList<Byte> newByteInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 8, minValue, Byte.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Byte>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Byte get(int index) {
						return new Byte((byte)packedList.get(index));
					}
				};
	}

	
	/**
	 * Creates a new instance of this class using {@link Short} elements.
	 * 
	 * @param bitsPerValue - the number of bits per value that shall be stored
	 * @param minValue - the minimal value to be represented by the underlying packed list
	 * @param initialCapacity - the initial number of elements the underlying packed list can take up before 
	 *        resizing its array
	 * @return the new instance
	 * @throws IllegalArgumentException if the combination if bits per value and the minimal value could lead to
	 *         values that cannot be stored in a {@code short}
	 */
	public static PackedPrimitiveWrapperArrayList<Short> newShortInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 16, minValue, Short.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Short>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Short get(int index) {
						return new Short((short)packedList.get(index));
					}
				};
	}

	
	/**
	 * Creates a new instance of this class using {@link Integer} elements.
	 * 
	 * @param bitsPerValue - the number of bits per value that shall be stored
	 * @param minValue - the minimal value to be represented by the underlying packed list
	 * @param initialCapacity - the initial number of elements the underlying packed list can take up before 
	 *        resizing its array
	 * @return the new instance
	 * @throws IllegalArgumentException if the combination if bits per value and the minimal value could lead to
	 *         values that cannot be stored in a {@code int}
	 */
	public static PackedPrimitiveWrapperArrayList<Integer> newIntegerInstance(int bitsPerValue, 
			long minValue, int initialCapacity) {
		
		checkParameters(bitsPerValue, 32, minValue, Integer.MAX_VALUE);
		return new PackedPrimitiveWrapperArrayList<Integer>(bitsPerValue, minValue, initialCapacity) {
					@Override
					public Integer get(int index) {
						return new Integer((int)packedList.get(index));
					}
				};
	}

	
	/**
	 * Creates a new instance of this class using {@link Long} elements.
	 * 
	 * @param bitsPerValue - the number of bits per value that shall be stored
	 * @param minValue - the minimal value to be represented by the underlying packed list
	 * @param initialCapacity - the initial number of elements the underlying packed list can take up before 
	 *        resizing its array
	 * @return the new instance
	 * @throws IllegalArgumentException if the combination if bits per value and the minimal value could lead to
	 *         values that cannot be stored in a {@code long}
	 */
	public static PackedPrimitiveWrapperArrayList<Long> newLongInstance(int bitsPerValue, 
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
