/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.swing;


import java.util.List;

import javax.swing.AbstractListModel;



public class ListBackedListModel<E> extends AbstractListModel<E>{
	private List<E> list;
	
	
	public ListBackedListModel(List<E> list) {
		super();
		this.list = list;
	}
	

	public List<E> getList() {
		return list;
	}

	
	public void setList(List<E> list) {
		int previousSize = this.list.size();
		
		this.list = list;
		
		int sharedLength = Math.min(list.size(), previousSize);
		if (sharedLength > 0) {
			fireContentsChanged(this, 0, sharedLength - 1);
		}
		if (list.size() > previousSize) {
			fireIntervalAdded(this, previousSize, list.size() - 1);
		}
		else if (list.size() < previousSize) {
			fireIntervalRemoved(this, list.size(), previousSize - 1);
		}
	}
	

	@Override
	public E getElementAt(int index) {
		return list.get(index);
	}

	
	@Override
	public int getSize() {
		return list.size();
	}


	public boolean add(E e) {
		list.add(e);
		int pos = list.size() - 1;
		fireIntervalAdded(this, pos, pos);
		return true;
	}


	public void add(int index, E element) {
		list.add(index, element);
		
		int lastIndex = list.size() - 1;
		fireContentsChanged(this, index, lastIndex - 1);
		fireIntervalAdded(this, lastIndex, lastIndex);
	}


	public void clear() {
		int lastIndex = list.size() - 1;
		list.clear();
		fireIntervalRemoved(this, 0, lastIndex);
	}


	public E remove(int index) {
		E result = list.remove(index);
		fireIntervalRemoved(this, index, index);
		return result;
	}


	public E set(int index, E element) {
		E result = list.set(index, element);
		fireContentsChanged(this, index, index);
		return result;
	}
}
