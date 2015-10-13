/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014-2015  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
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
package info.bioinfweb.commons.swing;


import java.awt.Component;
import java.awt.KeyboardFocusManager;



public class SwingUtils {
  public static boolean childHasFocus(Component parent) {
  	return isChildComponent(parent, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
  }
  
  
  public static boolean isChildComponent(Component parent, Component child) {
		while ((child != parent) && (child != null)) {
	    child = child.getParent();
		}
		return (child == parent); 
  }	
}
