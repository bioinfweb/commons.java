/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
 * <http://bioinfweb.info/LibrAlign>
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
package info.bioinfweb.commons.swt;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
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
  
  
  /**
   * Creates a new {@link Composite} with an embedded <i>AWT</i> or <i>Swing</i> component.
   * <p>
   * This method makes use of {@link SWT_AWT#new_Frame(Composite)} internally.
   * 
   * @param awtComponent the <i>AWT</i> or <i>Swing</i> component to be embedded
   * @param parentComposite the parent <i>SWT</i> composite
   * @return the new <i>SWT</i> composite containing the embedded component
   */
  public static Composite embedAWTComponent(Component awtComponent, Composite parentComposite) {
  	//TODO Does any care need to be taken here to run certain methods within the Swing or SWT thread?
		Composite result = new Composite(parentComposite, SWT.EMBEDDED);  // Create a child composite to make sure the EMBEDDED flag is set, which is necessary to nest AWT components.
		Frame frame = SWT_AWT.new_Frame(result);
		Panel panel = new Panel(new BorderLayout());  // Parent heavyweight panel is necessary since Java 1.5 to be able to focus and receive mouse events in nested Swing components.
		frame.add(panel);
		panel.add(awtComponent, BorderLayout.CENTER);
		return result;
  }
}
