/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014 - 2015  Ben Stöver
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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.eclipse.swt.events.MouseEvent;



public class TICComponentMouseWheelListenerList extends AbstractListenerList<TICMouseWheelListener> 
		implements MouseWheelListener, org.eclipse.swt.events.MouseWheelListener {
	
	
	public TICComponentMouseWheelListenerList(TICComponent owner) {
		super(owner);
	}


	@Override
	public void mouseScrolled(MouseEvent event) {
		for (TICMouseWheelListener listener: getListeners()) {
			listener.mouseWheelMoved(new TICMouseWheelEvent(getOwner(), event));
		}
	}

	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		for (TICMouseWheelListener listener: getListeners()) {
			listener.mouseWheelMoved(new TICMouseWheelEvent(getOwner(), event));
		}
	}
}