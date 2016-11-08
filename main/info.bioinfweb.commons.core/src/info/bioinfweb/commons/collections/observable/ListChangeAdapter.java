/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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



/**
 * Adapter class that provides empty default implementation for all events. Listeners that only listen
 * to few or single events can inherit from this class and overwrite only single methods.
 * 
 * @author Ben St&ouml;ver
 * @since 1.1.0
 *
 * @see ObservableList
 *
 * @param <E> the type of elements in the list where the change occurred
 */
public class ListChangeAdapter<E> implements ListChangeListener<E> {
	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void beforeElementsAdded(ListAddEvent<E> event) {}

	
	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void afterElementsAdded(ListAddEvent<E> event) {}

	
	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void beforeElementReplaced(ListReplaceEvent<E> event) {}

	
	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void afterElementReplaced(ListReplaceEvent<E> event) {}
	

	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void beforeElementsRemoved(ListRemoveEvent<E, Object> event) {}
	

	/**
	 * Empty implementation to be overwritten if necessary.
	 * 
	 * @param event - the event object containing further information on the modification
	 */
	@Override
	public void afterElementsRemoved(ListRemoveEvent<E, E> event) {}	
}
