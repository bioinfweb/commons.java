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


import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;

import info.bioinfweb.commons.tic.TICComponent;



/**
 * TIC event object that is used to represent a toolkit independent key event. 
 * 
 * @author Ben St&ouml;ver
 */
public class TICKeyEvent extends TICInputEvent {
	private static final Map<Integer, Integer> KEY_CODE_MAP = createKeyCodeMap(); 
	
	
	private int keyCode;
	private int keyLocation;
	private char keyCharacter;
	
	
	public TICKeyEvent(TICComponent source, long time, int modifiers,	int keyCode, int keyLocation, char keyCharacter) {
		super(source, time, modifiers);
		this.keyCode = keyCode;
		this.keyLocation = keyLocation;
		this.keyCharacter = keyCharacter;
	}
	
	
	public TICKeyEvent(TICComponent source, KeyEvent swingEvent) {
		this(source, swingEvent.getWhen(), swingEvent.getModifiersEx(), swingEvent.getKeyCode(), swingEvent.getKeyLocation(), 
				swingEvent.getKeyChar());
	}


	public TICKeyEvent(TICComponent source, org.eclipse.swt.events.KeyEvent swtEvent) {
		this(source, convertSWTEventTime(swtEvent.time), convertSWTStateMask(swtEvent.stateMask, 0), 
				convertSWTKeyCode(swtEvent.keyCode), convertSWTKeyLocation(swtEvent.keyLocation),	swtEvent.character);
	}

	
	private static Map<Integer, Integer> createKeyCodeMap() {
		Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
		
		// Control keys:
		result.put(SWT.ARROW_LEFT, KeyEvent.VK_LEFT);
		result.put(SWT.ARROW_RIGHT, KeyEvent.VK_RIGHT);
		result.put(SWT.ARROW_UP, KeyEvent.VK_UP);
		result.put(SWT.ARROW_DOWN, KeyEvent.VK_DOWN);
		
		result.put(SWT.INSERT , KeyEvent.VK_INSERT);
		result.put(8 , KeyEvent.VK_BACK_SPACE);
		result.put((int)SWT.DEL, KeyEvent.VK_DELETE);
		result.put(SWT.HOME, KeyEvent.VK_HOME);
		result.put(SWT.END, KeyEvent.VK_END);
		result.put(SWT.PAGE_UP, KeyEvent.VK_PAGE_UP);
		result.put(SWT.PAGE_DOWN, KeyEvent.VK_PAGE_DOWN);

		result.put(SWT.SHIFT, KeyEvent.VK_SHIFT);
		result.put(SWT.CONTROL, KeyEvent.VK_CONTROL);
		result.put(SWT.ALT, KeyEvent.VK_ALT);
		result.put(SWT.INSERT, KeyEvent.VK_INSERT);
		
		result.put(SWT.CAPS_LOCK, KeyEvent.VK_CAPS_LOCK);
		result.put(SWT.NUM_LOCK, KeyEvent.VK_NUM_LOCK);
		result.put(SWT.SCROLL_LOCK, KeyEvent.VK_SCROLL_LOCK);
		
		result.put(SWT.PRINT_SCREEN, KeyEvent.VK_PRINTSCREEN);
		result.put(SWT.PAUSE, KeyEvent.VK_PAUSE);
		result.put(SWT.PRINT_SCREEN, KeyEvent.VK_PRINTSCREEN);
		result.put(SWT.PRINT_SCREEN, KeyEvent.VK_PRINTSCREEN);
		
		// Characters:
		for (int i = 0; i < 26; i++) {
			result.put((int)'a' + i, (int)'A' + i);
		}  // Swing uses capital letter ASCII values and SWT uses lower case letters.
		
		// Digits:
		final int digitStartCode = (int)'0';
		for (int i = 0; i < 10; i++) {
			result.put(digitStartCode + i, digitStartCode + i);  // Codes are equal in Swing and SWT.
		}

		// Other characters:
		result.put((int)'<', KeyEvent.VK_LESS);
		result.put((int)'#', KeyEvent.VK_NUMBER_SIGN);
		result.put((int)'.', KeyEvent.VK_PERIOD);
		result.put((int)':', KeyEvent.VK_COLON);
		result.put((int)',', KeyEvent.VK_COMMA);
		result.put((int)';', KeyEvent.VK_SEMICOLON);
		result.put((int)'[', KeyEvent.VK_OPEN_BRACKET);
		result.put((int)']', KeyEvent.VK_CLOSE_BRACKET);
		result.put((int)'{', KeyEvent.VK_BRACELEFT);
		result.put((int)'}', KeyEvent.VK_BRACERIGHT);
		result.put((int)'!', KeyEvent.VK_EXCLAMATION_MARK);
		
    // Function keys and Esc:
		result.put(KeyEvent.VK_ESCAPE, KeyEvent.VK_ESCAPE);  // Codes are equal in Swing and SWT and there is no int constant in SWT.
		result.put(SWT.F1, KeyEvent.VK_F1);
		result.put(SWT.F2, KeyEvent.VK_F2);
		result.put(SWT.F3, KeyEvent.VK_F3);
		result.put(SWT.F4, KeyEvent.VK_F4);
		result.put(SWT.F5, KeyEvent.VK_F5);
		result.put(SWT.F6, KeyEvent.VK_F6);
		result.put(SWT.F7, KeyEvent.VK_F7);
		result.put(SWT.F8, KeyEvent.VK_F8);
		result.put(SWT.F9, KeyEvent.VK_F9);
		result.put(SWT.F10, KeyEvent.VK_F10);
		result.put(SWT.F11, KeyEvent.VK_F11);
		result.put(SWT.F12, KeyEvent.VK_F12);
		result.put(SWT.F13, KeyEvent.VK_F13);
		result.put(SWT.F14, KeyEvent.VK_F14);
		result.put(SWT.F15, KeyEvent.VK_F15);
		result.put(SWT.F16, KeyEvent.VK_F16);
		result.put(SWT.F17, KeyEvent.VK_F17);
		result.put(SWT.F18, KeyEvent.VK_F18);
		result.put(SWT.F19, KeyEvent.VK_F19);
		result.put(SWT.F20, KeyEvent.VK_F20);
		// Note that no loop is used because the codes are not continues.
		
		// Keypad:
		result.put(SWT.KEYPAD_0, KeyEvent.VK_NUMPAD0);
		result.put(SWT.KEYPAD_1, KeyEvent.VK_NUMPAD1);
		result.put(SWT.KEYPAD_2, KeyEvent.VK_NUMPAD2);
		result.put(SWT.KEYPAD_3, KeyEvent.VK_NUMPAD3);
		result.put(SWT.KEYPAD_4, KeyEvent.VK_NUMPAD4);
		result.put(SWT.KEYPAD_5, KeyEvent.VK_NUMPAD5);
		result.put(SWT.KEYPAD_6, KeyEvent.VK_NUMPAD6);
		result.put(SWT.KEYPAD_7, KeyEvent.VK_NUMPAD7);
		result.put(SWT.KEYPAD_8, KeyEvent.VK_NUMPAD8);
		result.put(SWT.KEYPAD_9, KeyEvent.VK_NUMPAD9);
		result.put(SWT.KEYPAD_ADD, KeyEvent.VK_ADD);
		result.put(SWT.KEYPAD_CR, KeyEvent.VK_ENTER);  // Not differentiated in Swing
		result.put(SWT.KEYPAD_DECIMAL, KeyEvent.VK_DECIMAL);
		result.put(SWT.KEYPAD_DIVIDE, KeyEvent.VK_DIVIDE);
		result.put(SWT.KEYPAD_MULTIPLY, KeyEvent.VK_MULTIPLY);
		result.put(SWT.KEYPAD_SUBTRACT, KeyEvent.VK_SUBTRACT);
		result.put(SWT.KEYPAD_EQUAL, KeyEvent.VK_ENTER);  //TODO Is this correct?
		// Numpad cursor keys are not differentiated from the others in SWT.
		
		return result;
	}
	
	
	public static int convertSWTKeyCode(int swtCode) {
		Integer result = KEY_CODE_MAP.get(swtCode);
		if (result == null) {
			return KeyEvent.VK_UNDEFINED;
		}
		else {
			return result;
		}
	}
	
	
	public static int convertSWTKeyLocation(int swtLocation) {
		switch (swtLocation) {
			case SWT.NONE:
				return KeyEvent.KEY_LOCATION_STANDARD;
			case SWT.LEFT:
				return KeyEvent.KEY_LOCATION_LEFT;
			case SWT.RIGHT:
				return KeyEvent.KEY_LOCATION_RIGHT;
			case SWT.KEYPAD:
				return KeyEvent.KEY_LOCATION_NUMPAD;
			default:
				return KeyEvent.KEY_LOCATION_UNKNOWN;
		}
	}
	
	
	public int getKeyCode() {
		return keyCode;
	}


	/**
	 * Returns the location of the key that was pressed.
	 * 
	 * @return A Swing constant describing the key location (e.g. {@link KeyEvent#KEY_LOCATION_STANDARD}, 
	 *         {@link KeyEvent#KEY_LOCATION_LEFT}, {@link KeyEvent#KEY_LOCATION_RIGHT}, {@link KeyEvent#KEY_LOCATION_NUMPAD}) 
	 */
	public int getKeyLocation() {
		return keyLocation;
	}


	public char getKeyCharacter() {
		return keyCharacter;
	}
}
