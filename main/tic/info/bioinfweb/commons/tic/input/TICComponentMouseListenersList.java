/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014-2015  Ben Stöver
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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;



/**
 * Contains a list of {@link TICMouseListener}s and delegates all Swing and SWT events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 */
public class TICComponentMouseListenersList implements MouseListener, MouseMotionListener,
		org.eclipse.swt.events.MouseListener, MouseMoveListener, MouseTrackListener {
	
	private TICComponent owner;
	private List<TICMouseListener> listeners = new ArrayList<TICMouseListener>();
	private int lastPressedButton = 0;
	
	
	public TICComponentMouseListenersList(TICComponent owner) {
		super();
		this.owner = owner;
	}


	public TICComponent getOwner() {
		return owner;
	}


	public List<TICMouseListener> getListeners() {
		return listeners;
	}


	@Override
	public void mouseDragged(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseDragged(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseMoved(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseMoved(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseClicked(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseEntered(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseEntered(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseExited(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseExited(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mousePressed(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mousePressed(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseReleased(MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseReleased(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseEnter(org.eclipse.swt.events.MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseEntered(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseExit(org.eclipse.swt.events.MouseEvent event) {
		for (TICMouseListener listener: listeners) {
			listener.mouseExited(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseHover(org.eclipse.swt.events.MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseMove(org.eclipse.swt.events.MouseEvent event) {
		if (lastPressedButton > 0) {
			for (TICMouseListener listener: listeners) {
				listener.mouseDragged(new TICMouseEvent(getOwner(), event));
			}
		}
		else {
			for (TICMouseListener listener: listeners) {
				listener.mouseMoved(new TICMouseEvent(getOwner(), event));
			}
		}
	}


	@Override
	public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseDown(org.eclipse.swt.events.MouseEvent event) {
		lastPressedButton = event.button;
		for (TICMouseListener listener: listeners) {
			listener.mousePressed(new TICMouseEvent(getOwner(), event));
		}
	}


	@Override
	public void mouseUp(org.eclipse.swt.events.MouseEvent event) {
		lastPressedButton = 0;
		for (TICMouseListener listener: listeners) {
			listener.mouseReleased(new TICMouseEvent(getOwner(), event));
		}
	}
}
