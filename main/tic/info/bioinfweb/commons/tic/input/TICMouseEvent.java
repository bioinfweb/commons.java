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


import java.awt.event.MouseEvent;

import info.bioinfweb.commons.tic.TICComponent;



public class TICMouseEvent extends TICInputEvent {
	private int button;
	private int clickCount;
	private boolean popupTrigger;
	private int componentX;
	private int componentY;
	
	
	public TICMouseEvent(TICComponent source, long time, int modifiers,	int button, int clickCount, boolean popupTrigger, 
			int componentX,	int componentY) {
		
		super(source, time, modifiers);
		this.button = button;
		this.clickCount = clickCount;
		this.popupTrigger = popupTrigger;
		this.componentX = componentX;
		this.componentY = componentY;
	}
	
	
	public TICMouseEvent(TICComponent source, MouseEvent swingEvent) {
		this(source, swingEvent.getWhen(), swingEvent.getModifiersEx(), swingEvent.getButton(), swingEvent.getClickCount(), 
				swingEvent.isPopupTrigger(), swingEvent.getX(), swingEvent.getY());
	}


	public TICMouseEvent(TICComponent source, org.eclipse.swt.events.MouseEvent swtEvent) {
		this(source, convertSWTEventTime(swtEvent.time), convertSWTStateMask(swtEvent.stateMask, swtEvent.button), swtEvent.button, 
				swtEvent.count,	false, swtEvent.x, swtEvent.y);
		//TODO Determine popup trigger
	}


	public int getButton() {
		return button;
	}


	public int getClickCount() {
		return clickCount;
	}


	/**
	 * Determines whether is event is the popup trigger of the current platform.
	 * <p>
	 * <i>Note that in the current implementation this method always returns {@code false} in events coming from SWT
	 * components.</i> 
	 * 
	 * @return {@code true} if the current event is a popup trigger, {@code false} otherwise
	 */
	public boolean isPopupTrigger() {
		return popupTrigger;
	}


	public int getComponentX() {
		return componentX;
	}


	public int getComponentY() {
		return componentY;
	}
}
