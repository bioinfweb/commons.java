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
package info.bioinfweb.commons.swing;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import info.bioinfweb.commons.changemonitor.ChangeMonitor;



public class SwingChangeMonitor extends ChangeMonitor 
    implements ChangeListener, DocumentListener, ItemListener {
	
	@Override
	public void stateChanged(ChangeEvent e) {
		registerChange();
	}

	
	@Override
	public void changedUpdate(DocumentEvent e) {
		registerChange();
	}

	
	@Override
	public void insertUpdate(DocumentEvent e) {
		registerChange();
	}

	
	@Override
	public void removeUpdate(DocumentEvent e) {
		registerChange();
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		registerChange();
	}
}
