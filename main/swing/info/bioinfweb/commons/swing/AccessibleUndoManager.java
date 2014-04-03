package info.bioinfweb.commons.swing;


import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;



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
