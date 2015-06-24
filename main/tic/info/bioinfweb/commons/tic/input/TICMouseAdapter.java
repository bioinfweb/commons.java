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



/**
 * Adapter class that implements all methods of an {@link TICMouseListener}. All implementing
 * methods return {@code false} to indicate that the event was not consumed. This is a convenience
 * class allowing inherited classes to overwrite single methods, without having to provide 
 * additional empty implementations for unused methods.
 * 
 * @author Ben St&ouml;ver
 */
public class TICMouseAdapter implements TICMouseListener {
	@Override
	public boolean mousePressed(TICMouseEvent event) {
		return false;
	}

	
	@Override
	public boolean mouseReleased(TICMouseEvent event) {
		return false;
	}

	
	@Override
	public boolean mouseEntered(TICMouseEvent event) {
		return false;
	}

	
	@Override
	public boolean mouseExited(TICMouseEvent event) {
		return false;
	}

	
	@Override
	public boolean mouseMoved(TICMouseEvent event) {
		return false;
	}

	
	@Override
	public boolean mouseDragged(TICMouseEvent event) {
		return false;
	}
}
