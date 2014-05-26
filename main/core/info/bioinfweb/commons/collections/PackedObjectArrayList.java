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


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class PackedObjectArrayList<E> extends AbstractList<E> implements List<E> {
	private PackedIntegerArrayList packedList;
	private List<E> objectList;
	private Map<E, Integer> intMap;
	
	
	private int calculateBitsPerValue(int objectTypeCount) {
		int result = 1;
		int maxCount = 2;
		while (maxCount < objectTypeCount) {  // 63 bit border cannot be reached with int values
			result++;
			maxCount *= 2;
		}
		return result;
	}
	
	
	public PackedObjectArrayList(int objectTypeCount, int initialCapacity) {
		super();
		if (objectTypeCount < 0) {
			throw new IllegalArgumentException("A negative objectTypeCount (" + objectTypeCount + ") is not valid.");
		}
		packedList = new PackedIntegerArrayList(calculateBitsPerValue(objectTypeCount), 0, initialCapacity);
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
						"list can manage. The specified object is not equal to any of these objects and therefore cannot be added.");
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
	
	
	public int getMaxObjectTypeCount() {
		return (int)packedList.getMaxValue() + 1;  //TODO Could the result be one greater than Integer.MAX_VALUE? 
	}
}
