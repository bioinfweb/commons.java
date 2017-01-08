/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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


import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;



/**
 * An extension of {@link UndoManager} that allows to access all edit objects contained in the underlying list.
 * 
 * @author Ben St&ouml;ver
 */
public class AccessibleUndoManager extends UndoManager {
	/**
	 * Returns the position of the edit to undone next.
	 */
	public int firstUndoPosition() {
  	UndoableEdit first = editToBeUndone();
  	if (first != null) {
  		return edits.indexOf(first);
  	}
  	else {
  		return -1;
  	}
	}
	
	
	/**
	 * Returns the position of the edit to redone next.
	 */
	public int firstRedoPosition() {
  	UndoableEdit first = editToBeRedone();
  	if (first != null) {
  		return edits.indexOf(first);
  	}
  	else {
  		return -1;
  	}
	}
	
	
	public int undoCount() {
		return firstUndoPosition() + 1;  // Liefert auch bei -1 das richtige Ergebnis (0)
	}
	
	
	public int redoCount() {
		int pos = firstRedoPosition();
		if (pos == -1) {
			return 0;
		}
		else {
			return edits.size() - pos;
		}
	}
	
	
	public UndoableEdit getEdit(int pos) {
		return edits.get(pos);
	}
	
	
	/**
	 * Returns the specified undo edit where the first to be undone has the index 0.
	 * @param pos
	 * @return
	 */
	public UndoableEdit getUndoEdit(int pos) {
		int first = firstUndoPosition();
		if (first == -1) {
			throw new IndexOutOfBoundsException("There no undo edits in the list.");
		}
		else {
			return edits.get(first - pos);
		}
	}
	
	
	/**
	 * Returns the specified redo edit where the first to be redone has the index 0.
	 * @param pos
	 * @return
	 */
	public UndoableEdit getRedoEdit(int pos) {
		int first = firstRedoPosition();
		if (first == -1) {
			throw new IndexOutOfBoundsException("There no undo edits in the list.");
		}
		else {
			return edits.get(first + pos);
		}
	}


	@Override
	public void redoTo(UndoableEdit edit) throws CannotRedoException {
		super.redoTo(edit);
	}


	@Override
	public void undoTo(UndoableEdit edit) throws CannotUndoException {
		super.undoTo(edit);
	}
	
	
	public boolean contains(UndoableEdit edit) {
		return edits.contains(edit);
	}
}
