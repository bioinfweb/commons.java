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


import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.eclipse.swt.SWT;

import info.bioinfweb.commons.tic.TICComponent;



/**
 * All toolkit independent input events in TIC should be inherited from this class.
 * 
 * @author Ben St&ouml;ver
 */
public class TICInputEvent {
	private TICComponent source;
	private long time;
	private int modifiers;
	
	
	/**
	 * Creates a new instance of this class. Objects of this type should not be instantiated directly but an inherited
	 * class should be used instead.
	 * 
	 * @param source - the TIC component triggering this event
	 * @param time - the time (in milliseconds) when the event happened
	 * @param modifiers - the modifier keys in AWT format (see constants in {@link KeyEvent})
	 */
	public TICInputEvent(TICComponent source, long time, int modifiers) {
		super();
		this.source = source;
		this.time = time;
		this.modifiers = modifiers;
	}
	
	
	/**
	 * Converts a SWT event time value to a Swing time value.
	 * 
	 * @param time - the SWT time value (unsigned {@code int})
	 * @return a Swing time value (signed {@code long})
	 */
	public static long convertSWTEventTime(int time) {
		return time & 0xFFFFFFFFL;
	}
	
	
	public static int convertSWTStateMask(int stateMask, int mouseButton) {
		int result = 0;
		if ((stateMask & SWT.SHIFT) != 0) {
			result |= InputEvent.SHIFT_DOWN_MASK;
		}
		if ((stateMask & SWT.CONTROL) != 0) {
			result |= InputEvent.CTRL_DOWN_MASK;
		}
		if ((stateMask & SWT.ALT) != 0) {
			result |= InputEvent.ALT_DOWN_MASK;
		}
		if ((stateMask & SWT.COMMAND) != 0) {  // TODO Does this really only happen, if this is the MenuShortcutKeyMask?
			result |= InputEvent.META_DOWN_MASK;
		}
		if (((stateMask & SWT.BUTTON1) != 0) || (mouseButton == 1)) {
			result |= InputEvent.BUTTON1_DOWN_MASK;
		}
		if (((stateMask & SWT.BUTTON2) != 0) || (mouseButton == 2)) {
			result |= InputEvent.BUTTON2_DOWN_MASK;
		}
		if (((stateMask & SWT.BUTTON3) != 0) || (mouseButton == 3)) {
			result |= InputEvent.BUTTON3_DOWN_MASK;
		}
		//TODO Add more cases?
		return result;
	}
	
	
	public TICComponent getSource() {
		return source;
	}


	public long getTime() {
		return time;
	}

	
	public int getModifiers() {
		return modifiers;
	}
	
	
	/**
	 * Determines whether a shift button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isShiftDown() {
		return (modifiers & InputEvent.SHIFT_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether a control button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isControlDown() {
		return (modifiers & InputEvent.CTRL_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether the alt button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isAltDown() {
		return (modifiers & InputEvent.ALT_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether the alt graph button was pressed during this event.
	 * <p>
	 * <i>Note that in the current implementation this method will always return {@code false} if this event was triggered
	 * by a SWT component.</i>  
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isAltGraphDown() {
		return (modifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines platform independent whether a meta button was pressed during this event.
	 * <p>
	 * <i>Note that in the current implementation this method will always return {@code false} if this event was triggered
	 * by a SWT component.</i>  
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMetaDown() {
		return (modifiers & InputEvent.META_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 1 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton1Down() {
		return (modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 2 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton2Down() {
		return (modifiers & InputEvent.BUTTON2_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 3 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton3Down() {
		return (modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0;
	}
}
