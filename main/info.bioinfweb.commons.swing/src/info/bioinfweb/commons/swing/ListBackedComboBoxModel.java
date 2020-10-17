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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;



/**
 * An implementation of {@link ComboBoxModel} that always reflects the contents of a {@link List}.
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
public class ListBackedComboBoxModel<E> extends ListBackedListModel<E> implements ComboBoxModel<E> {
	private Object selectedItem;
	

	public ListBackedComboBoxModel(List<E> list) {
		super(list);
	}


	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	
	/**
	 * Sets the selected item of this model.
	 * <p>
	 * Selected items do not necessarily have to be part of the underlying list.
	 * <p>
	 * When this method is called a the event {@link ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)} is fired with the 
	 * start and end indices {@code -1}. (This behaviour is identical with {@link DefaultComboBoxModel}). 
	 * 
	 * @param selectedItem the item to be selected from now on
	 */
	@Override
	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = selectedItem;
		fireContentsChanged(this, -1, -1);  // This is equivalent to the implementation of DefaultComboBoxModel.
	}
}
