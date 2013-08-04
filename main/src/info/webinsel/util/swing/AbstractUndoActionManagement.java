package info.webinsel.util.swing;


import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoableEdit;



/**
 * Implements basic functionality for applications using an {@link AccessibleUndoManager} to display undoable (and
 * redoable) edits in swing submenus.  
 * 
 * @author Ben St&ouml;ver
 */
public abstract class AbstractUndoActionManagement extends ActionMap {
	protected abstract AccessibleUndoManager getUndoManager();
	
	protected abstract JMenu getUndoMenu();
	
	protected abstract JMenu getRedoMenu();
	
	protected abstract Action createUndoAction(UndoableEdit edit);
	
	protected abstract Action createRedoAction(UndoableEdit edit);
	
	
	/**
	 * Sets the contents undo and redo menus or disables them.
   */
	protected void editUndoRedoMenus() {
		AccessibleUndoManager undoManager = getUndoManager();
		
		getUndoMenu().setEnabled(undoManager.canUndo());
		getUndoMenu().removeAll();
		if (undoManager.canUndo()) {
			Action action = createUndoAction(undoManager.getUndoEdit(0));
			action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('Z', 
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			getUndoMenu().add(action);
			
			for (int i = 1; i < undoManager.undoCount(); i++) {
				getUndoMenu().add(createUndoAction(undoManager.getUndoEdit(i)));
			}
		}

		getRedoMenu().setEnabled(undoManager.canRedo());
		getRedoMenu().removeAll();
		if (undoManager.canRedo()) {
			Action action = createRedoAction(undoManager.getRedoEdit(0));
			action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('Y', 
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			getRedoMenu().add(action);
			
			for (int i = 1; i < undoManager.redoCount(); i++) {
				getRedoMenu().add(createRedoAction(undoManager.getRedoEdit(i)));
			}
		}
	}
}
