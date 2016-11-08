/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.changemonitor;



/**
 * A common interface that can be implemented by classes that track whether they have undergone 
 * changes.
 * 
 * @author Ben St&ouml;ver
 * @see ChangeMonitor
 */
public interface ChangeMonitorable {
	/**
	 * Implementing classes should return if one or more changes (calls of {@link #registerChange()}) 
	 * have happened since the last call of {@link #reset()} here.
	 * 
	 * @return {@code true} if a change happened, {@code false} otherwise
	 */
	public boolean hasChanged();
	
	/**
	 * This method should be called of a change happened. (Implementing classes should call this method
	 * e.g. in according setter methods.)
	 */
	public void registerChange();
	
	/**
	 * Implementing classes should make sure that {@link #hasChanged()} will return {@code false} after
	 * the call of this method until the next call of {@link #registerChange()}.
	 */
	public void reset();	
}
