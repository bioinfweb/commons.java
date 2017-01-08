/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Implements a packed list of objects which uses an underlying instance of {@link PackedIntegerArrayList}.
 * All equal objects in the list are represented by the an integer number stored in the underlying list and
 * the methods reading and writing objects perform a conversion between these numbers and the concrete object
 * instances.
 * <p>
 * The compression method of this class works best of the list contains a limited set of different objects
 * multiple times. If different equal object instances are add to this list only the object that was add 
 * first will be returned for all positions occupied by an equal object (which should not be a problem with
 * equal objects.)
 * <p>
 * Due to the compression method the maximum number of expected different objects has to be specified when
 * creating instances of this class. Each a new type of object is add to the list an specific integer value
 * will be assigned to it. Note that such values will always remain associated with this object even if
 * all objects of this kind (objects that are equal to each other) are removed from the list. So each additional
 * object that differs from all previously added objects will contribute to the number of supported different 
 * objects that can be stored, no matter if any of these objects is removed again later on. 
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> - the type of element this list shall contain
 */
public class PackedObjectArrayList<E> extends AbstractList<E> implements List<E> {
	private PackedIntegerArrayList packedList;
	private List<E> objectList;
	private Map<E, Integer> intMap;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param objectTypeCount - the number of different objects that will at most be contained in the returned list
	 * @param initialCapacity - the number of entries the list will be able to store before the underlying array
	 *                          will be resized
	 */
	public PackedObjectArrayList(int objectTypeCount, int initialCapacity) {
		super();
		if (objectTypeCount < 0) {
			throw new IllegalArgumentException("A negative objectTypeCount (" + objectTypeCount + ") is not valid.");
		}
		packedList = new PackedIntegerArrayList(PackedIntegerArrayList.calculateBitsPerValue(objectTypeCount), 0, initialCapacity);
		objectList = new ArrayList<E>(objectTypeCount);
		intMap = new HashMap<E, Integer>();
	}

	
	private int getIntByObject(E object) {
		Integer result = intMap.get(object);
		if (result == null) {
			if (objectList.size() <= packedList.getMaxValue()) {
				objectList.add(object);
				result = objectList.size() - 1;
				intMap.put(object, result);
			}
			else {
				throw new IndexOutOfBoundsException("There are already " + objectList.size() + 
						" different object types registered in this list which is the maximum number of different objects this " +
						"list can manage. The specified object is not equal to any of these objects and therefore cannot be add.");
			}
		}
		return result;
	}
	
	
	private E getObjectByInt(int intRepresentation) {
		return objectList.get(intRepresentation);
	}
	

	@Override
	public boolean add(E e) {
    packedList.add(getIntByObject(e));
    return true;
	}

	
	@Override
	public void add(int index, E element) {
    packedList.add(index, getIntByObject(element));
	}

	
	@Override
	public E get(int index) {
		return getObjectByInt((int)packedList.get(index));
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
    packedList.set(index, getIntByObject(element));
		return result;
	}
	

	@Override
	public int size() {
		return (int)packedList.size();
	}
	
	
	/**
	 * Returns the number of different objects this list can manage. Note that this value might be higher than
	 * the value passed to the constructor depending on the maximum integer value that can be represented by
	 * the number of necessary bits.
	 * <p>
	 * <b>Example:</b> A number of 12 different objects is passed to the constructor.
	 * 4 bits will be necessary to store 12 different values (0..11) 16 different values (0..15) would be possible 
	 * in 4 bits. Therefore this method would return 16 instead of 12 in this case and the list could also manage
	 * 16 different objects.
	 *  
	 * @return the number of different objects that can be managed (This is not equal to maximum length this list
	 *         could have.)
	 */
	public int getMaxObjectTypeCount() {
		return (int)(packedList.getMaxValue() + 1);  // One must be add before the conversion to avoid possible overflow. 
	}
}
