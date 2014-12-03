/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.collections.observable;


import java.util.EventListener;
import java.util.List;



/**
 * Classes that want to listen to modifications applied to an instance of {@link List} (e.g. {@link ObservableList}) 
 * should implement this interface.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <E> the type of elements in the list where the modifications are monitored
 */
public interface ListChangeListener<E> extends EventListener {
	/**
	 * Called if one or more elements have been added to monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void elementsAdded(ListAddEvent<E> event);
	
	/**
	 * Called if an element in the monitored list has been replaced by another one.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void elementReplaced(ListReplaceEvent<E> event);
	
	/**
	 * Called if one or more elements have been removed from monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void elementsRemoved(ListRemoveEvent<E> event);
}
