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
