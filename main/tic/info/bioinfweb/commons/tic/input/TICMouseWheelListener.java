/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014-2015  Ben Stöver
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
package info.bioinfweb.commons.tic.input;


import java.util.EventListener;

import info.bioinfweb.commons.tic.TICComponent;



/**
 * Implement this interface to listen to mouse wheel events of {@link TICComponent}s.
 * 
 * @author Ben St&ouml;ver
 */
public interface TICMouseWheelListener extends EventListener {
  /**
   * This method is called when the mouse wheel is moved.
   * 
   * @param event - the object containing information about the event
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
	public boolean mouseWheelMoved(TICMouseWheelEvent e);
}
