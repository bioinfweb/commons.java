package info.webinsel.util.swing;


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


	public boolean addEdit(UndoableEdit arg0) {
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


	public abstract void registerDocumentChange();
	
	
	public void redo() throws CannotRedoException {
		if (!getIsSubedit()) {
			registerDocumentChange();
		}
	}


	public void undo() throws CannotUndoException {
		if (!getIsSubedit()) {
			registerDocumentChange();
  	}
	}
}
