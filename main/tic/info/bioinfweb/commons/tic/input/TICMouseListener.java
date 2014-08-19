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
package info.bioinfweb.commons.tic.input;


import info.bioinfweb.commons.tic.TICComponent;



/**
 * Implement this interface to listen to mouse events of {@link TICComponent}s.
 * 
 * @author Ben St&ouml;ver
 */
public interface TICMouseListener {
  public void mousePressed(TICMouseEvent event);
  
  public void mouseReleased(TICMouseEvent event);
  
  public void mouseEntered(TICMouseEvent event);
  
  public void mouseExited(TICMouseEvent event);
  
  public void mouseMoved(TICMouseEvent event);
  
  public void mouseDragged(TICMouseEvent event);
}
