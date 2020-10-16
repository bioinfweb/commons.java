/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.swing;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;



/**
 * <An abstract adapter class for receiving events of a {@link TableColumnModel}. 
 * The methods in this class are empty. This class exists as convenience for creating 
 * listener objects.
 * <p>
 * Extend this class to create a {@link TableColumnModelListener} listener and 
 * override the methods for the events of interest. (If you implement the 
 * {@link TableColumnModelListener} interface, you have to define all of the 
 * methods in it. This abstract class defines null methods for them all, so 
 * you can only have to define methods for events you care about.)
 *  
 * @author Ben St&ouml;ver
 */
public abstract class TableColumnModelAdapter implements TableColumnModelListener {
	public void columnAdded(TableColumnModelEvent e) {}

	
	public void columnMarginChanged(ChangeEvent e) {}

	
	public void columnMoved(TableColumnModelEvent e) {}

	
	public void columnRemoved(TableColumnModelEvent e) {}

	
	public void columnSelectionChanged(ListSelectionEvent e) {}
}
