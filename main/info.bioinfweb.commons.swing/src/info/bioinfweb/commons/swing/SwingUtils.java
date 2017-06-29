/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.swing;


import java.awt.Component;
import java.awt.KeyboardFocusManager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



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
  
  
	/**
	 * Sets the native look and feel of the current operating system by invoking 
	 * {@code UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())}.
	 * <p>
	 * Possible exceptions are ignored.
	 */
	public static void setNativeLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException e) {} 
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
	}
}
