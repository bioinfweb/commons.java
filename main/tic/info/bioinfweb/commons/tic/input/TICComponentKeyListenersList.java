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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;



/**
 * Contains a list of {@link TICMouseListener}s and delegates all Swing and SWT events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 */
public class TICComponentKeyListenersList implements KeyListener, org.eclipse.swt.events.KeyListener {
	private TICComponent owner;
	private List<TICKeyListener> listeners = new ArrayList<TICKeyListener>();
	
	
	public TICComponentKeyListenersList(TICComponent owner) {
		super();
		this.owner = owner;
	}


	public TICComponent getOwner() {
		return owner;
	}


	public List<TICKeyListener> getListeners() {
		return listeners;
	}


	@Override
	public void keyPressed(org.eclipse.swt.events.KeyEvent event) {
		for (TICKeyListener listener: listeners) {
			listener.keyPressed(new TICKeyEvent(getOwner(), event));
		}
	}

	
	@Override
	public void keyReleased(org.eclipse.swt.events.KeyEvent event) {
		for (TICKeyListener listener: listeners) {
			listener.keyReleased(new TICKeyEvent(getOwner(), event));
		}
	}

	
	@Override
	public void keyPressed(KeyEvent event) {
		for (TICKeyListener listener: listeners) {
			listener.keyPressed(new TICKeyEvent(getOwner(), event));
		}
	}

	
	@Override
	public void keyReleased(KeyEvent event) {
		for (TICKeyListener listener: listeners) {
			listener.keyReleased(new TICKeyEvent(getOwner(), event));
		}
	}

	
	@Override
	public void keyTyped(KeyEvent event) {}  // Event not present in TICKeyListener
}
