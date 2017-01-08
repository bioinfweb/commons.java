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


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;



/**
 * 
 * @author Ben St&ouml;ver
 */
public abstract class ExtendedAbstractAction extends AbstractAction {
	private String pathPrefix = "/resources/symbols/";
	
	
  /**
   * Constructs an instance of this class with the default path prefix {@code /resources/symbols/}.
   */
  public ExtendedAbstractAction() {
		super();
	}


  /**
   * Constructs an instance of this class with the specified path prefix.
   */
	public ExtendedAbstractAction(String pathPrefix) {
		super();
		this.pathPrefix = pathPrefix;
	}


	public String getPathPrefix() {
		return pathPrefix;
	}


	/**
   * Loads the small and large icon for this action from <i>/resources/symbols/</i> in the
   * the JAR file.
   * @param name - the prefix of the file name of the image files
   */
  protected void loadSymbols(String name) {
	  putValue(Action.SMALL_ICON, new ImageIcon(Object.class.getResource(getPathPrefix() + name + "16.png")));
	  putValue(Action.LARGE_ICON_KEY, new ImageIcon(Object.class.getResource(getPathPrefix() + name + "22.png")));
  }
}
