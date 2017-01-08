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
	 * Called before one or more elements are add to monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void beforeElementsAdded(ListAddEvent<E> event);
	
	/**
	 * Called after one or more elements have been add to monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void afterElementsAdded(ListAddEvent<E> event);
	
	/**
	 * Called before an element in the monitored list will be replaced by another one.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void beforeElementReplaced(ListReplaceEvent<E> event);
	
	/**
	 * Called after an element in the monitored list has been replaced by another one.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void afterElementReplaced(ListReplaceEvent<E> event);
	
	/**
	 * Called before one or more elements will be removed from monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void beforeElementsRemoved(ListRemoveEvent<E, Object> event);
	
	/**
	 * Called after one or more elements have been removed from monitored list.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	public void afterElementsRemoved(ListRemoveEvent<E, E> event);
}
