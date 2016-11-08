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


import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;



/**
 * Implements basic functionality for edits on a document that takes track of changes.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class AbstractDocumentEdit implements UndoableEdit {
	private boolean isSubedit = false;
	
		
	public boolean getIsSubedit() {
		return isSubedit;
	}


	/**
	 * Indicates whether this edit is independent or part of another edit. The value of this property
	 * determines if the document is informed if this edit was redone or undone. 
	 */
	public void setIsSubedit(boolean isSubedit) {
		this.isSubedit = isSubedit;
	}


	public boolean addEdit(UndoableEdit edit) {
		return false;
	}

	
	public boolean canRedo() {
		return true;
	}

	
	public boolean canUndo() {
		return true;
	}

	
	public void die() {
		// Grundimplementierung leer.
	}

	
	public String getRedoPresentationName() {
		return getPresentationName();
	}

	
	public String getUndoPresentationName() {
		return getPresentationName();
	}

	
	public boolean isSignificant() {
		return true;
	}

	
	public boolean replaceEdit(UndoableEdit edit) {
		return false;
	}


	/**
	 * Inheriting classes should e.g. inform document listeners here.
	 */
	protected abstract void registerDocumentChange();
	
	
	/**
	 * Default implementation that just calls {@link #registerDocumentChange()} if this edit is not a subedit.
	 * Inheriting classes would have to overwrite this method to perform there document changes but should
	 * call the inherited methods at the end of the implementation.
	 */
	public void redo() throws CannotRedoException {
		if (!getIsSubedit()) {
			registerDocumentChange();
		}
	}


	/**
	 * Default implementation that just calls {@link #registerDocumentChange()} if this edit is not a subedit.
	 * Inheriting classes would have to overwrite this method to undo there document changes but should
	 * call the inherited methods at the end of the implementation.
	 */
	public void undo() throws CannotUndoException {
		if (!getIsSubedit()) {
			registerDocumentChange();
  	}
	}
}
