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
package info.bioinfweb.commons.swt;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;



/**
 * Provides methods of general use in SWT.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0 
 * @bioinfweb.module info.bioinfweb.commons.swt
 */
public class SWTUtils {
  public static boolean childHasFocus(Composite parent) {
  	return isChildComponent(parent, Display.getCurrent().getFocusControl());
  }
  
  
  public static boolean isChildComponent(Composite parent, Control child) {
		while ((child != parent) && (child != null)) {
	    child = child.getParent();
		}
		return (child == parent); 
  }	
}
