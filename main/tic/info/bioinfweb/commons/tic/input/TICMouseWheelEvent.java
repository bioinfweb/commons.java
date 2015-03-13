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


import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import info.bioinfweb.commons.tic.TICComponent;



/**
 * TIC event object that is used to represent a toolkit independent mouse wheel event. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class TICMouseWheelEvent extends TICMouseEvent {
	private double preciseWheelRotation;
	private int wheelRotation;
	
	
	public TICMouseWheelEvent(TICComponent source, long time, int modifiers, int button, int clickCount, boolean popupTrigger, 
			int componentX,	int componentY, int wheelRotation, double preciseWheelRotation) {
		
		super(source, time, modifiers, button, clickCount, popupTrigger, componentX, componentY);
		this.wheelRotation = wheelRotation;
		this.preciseWheelRotation = preciseWheelRotation;
	}


	public TICMouseWheelEvent(TICComponent source, MouseWheelEvent swingEvent) {
		super(source, swingEvent);
		wheelRotation = swingEvent.getWheelRotation();
		preciseWheelRotation = swingEvent.getPreciseWheelRotation();
	}


	public TICMouseWheelEvent(TICComponent source, org.eclipse.swt.events.MouseEvent swtEvent) {
		super(source, swtEvent);
		wheelRotation = swtEvent.count;  //TODO Is this the correct field?
		preciseWheelRotation = swtEvent.count;  //TODO Is this the correct field?
	}


	/**
	 * Returns the number of "clicks" the mouse wheel was rotated, as a {@code double}. A partial rotation may occur if the mouse 
	 * supports a high-resolution wheel and the underlying toolkit is Swing. In this case, the return value will include a 
	 * fractional "click". If an underlying SWT component is used the return value of this method will always be identical with
	 * {@link #getWheelRotation()}.
	 * 
	 * @return negative values if the mouse wheel was rotated up or away from the user, and positive values if the mouse wheel 
	 *         was rotated down or towards the user
	 */
	public double getPreciseWheelRotation() {
		return preciseWheelRotation;
	}


	/**
	 * Returns the number of "clicks" the mouse wheel was rotated.
	 * 
	 * @return negative values if the mouse wheel was rotated up or away from the user, and positive values if the mouse wheel 
	 *         was rotated down or towards the user
	 * @see #getPreciseWheelRotation()
	 */
	public int getWheelRotation() {
		return wheelRotation;
	}
}
