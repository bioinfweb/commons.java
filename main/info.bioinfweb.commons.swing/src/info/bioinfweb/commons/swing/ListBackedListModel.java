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
import javax.swing.JList;
import javax.swing.ListModel;



/**
 * An implementation of {@link ListModel} that always reflects the contents of a {@link List}.
 * <p>
 * Note that this model does reflect changes in the underlying list that occur after the creation of this object but will not inform its 
 * listeners on these. (The underlying list cannot be monitored.) The methods {@link #add(Object)}, {@link #add(int, Object)}, 
 * {@link #set(int, Object)}, {@link #remove(int)} and {@link #clear()} allow to edit the underlying list and notify all listeners 
 * registered to this model and the same time. It is recommended to use these methods instead of modifying the list directly if changes
 * should be made to a list that is currently displayed in the GUI, e.g., in an instance of {@link JList}. 
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> the element type of the underlying list and this model
 */
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


	public E set(int index, E element) {
		E result = list.set(index, element);
		fireContentsChanged(this, index, index);
		return result;
	}

	
	public E remove(int index) {
		E result = list.remove(index);
		fireIntervalRemoved(this, index, index);
		return result;
	}


	public void clear() {
		int lastIndex = list.size() - 1;
		list.clear();
		fireIntervalRemoved(this, 0, lastIndex);
	}
}
