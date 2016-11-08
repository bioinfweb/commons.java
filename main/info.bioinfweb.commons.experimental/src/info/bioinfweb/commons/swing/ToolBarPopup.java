/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.swing;


import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDialog;



public class ToolBarPopup extends JDialog {
	private Component origin;
	
	
	public ToolBarPopup(Component origin) {
		super();
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Dialog owner) {
		super(owner);
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Frame owner) {
		super(owner);
		this.origin = origin;
		init();
	}

	
	public ToolBarPopup(Component origin, Window owner) {
		super(owner);
		this.origin = origin;
		init();
	}
	
	
	private void init() {
		setUndecorated(true);
	}
	
	
	private void position() {
		Point originLocation = origin.getLocationOnScreen();
		setLocation((int)Math.round(originLocation.getX()), (int)Math.round(originLocation.getY() + origin.getHeight()));
	}


	@Override
	public void setVisible(boolean flag) {
		if (flag) {
			position();
		}
		super.setVisible(flag);
	}


	@Override
	@Deprecated
	public void show() {
		position();
		super.show();
	}


	@Override
	@Deprecated
	public void show(boolean flag) {
		if (flag) {
			position();
		}
		super.show(flag);
	}
}