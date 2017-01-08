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
package info.bioinfweb.commons.swing.scrollpaneselector;


import java.awt.Graphics2D;



/**
 * Classes implementing this interface are able to paint their own preview thumbnail if they are 
 * positioned inside a <code>JScrollPane</code> which uses <code>ExetededScrollPaneSelector</code>.<br>
 * Although componenets which do not implement this interface can be previewed too, the advantige 
 * of is that the component is able to paint a more sharp preview or to highlight special elements. 
 * @author Ben St&ouml;ver
 */
public interface ScrollPaneSelectable {
  /**
   * This method is called by <code>ExetededScrollPaneSelector</code> to paint its preview field.
   * @param g - the graphics context
   * @param scale - the scale of the preview (the aspect ratio remains, painting starts at (0|0))
   */
  public void paintPreview(Graphics2D g, double scale);  
}
