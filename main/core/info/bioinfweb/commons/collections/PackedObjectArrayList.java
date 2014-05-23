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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.lucene.util.packed.PackedInts;



public class PackedObjectArrayList<E> extends AbstractList<E> implements List<E> {
	private PackedInts.Mutable packedInts;
	private List<E> objectList;
	private Map<E, Integer> indexMap;
	
	
	public PackedObjectArrayList() {
		super();
		packedInts = PackedInts.getMutable(valueCount, bitsPerValue, acceptableOverheadRatio);
	}


	@Override
	public boolean add(E e) {

	}

	
	@Override
	public void add(int index, E element) {

	}

	
	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}
	

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		super.removeRange(fromIndex, toIndex);
	}
	

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return super.set(index, element);
	}
	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
