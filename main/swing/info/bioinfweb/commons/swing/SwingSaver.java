package info.bioinfweb.commons.swing;


import info.bioinfweb.commons.changemonitor.ChangeMonitorable;
import info.bioinfweb.commons.io.AbstractSaver;
import info.bioinfweb.commons.io.Savable;
import info.webinsel.util.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;



/**
 * Implements the interface Savable with Swing-Dialogs.
 * @author Ben St&ouml;ver
 */
public abstract class SwingSaver extends AbstractSaver implements ChangeMonitorable, Savable {
	private SwingSaverMessages messages = new SwingSaverMessages();
	private JFileChooser fileChooser = new JFileChooser();
	
	
	public SwingSaver() {
		super();
	}
	
	
	public SwingSaver(String defaultName) {
		super();
		setDefaultName(defaultName);
	}
	
	
	/* (non-Javadoc)
	 * @see de.nn.util.io.Savable#askToSave()
	 */
	public boolean askToSave() {
		return askToSave(null);
	}
	
	
	public boolean askToSave(Component parentComponent) {
		if (hasChanged()) {
			int choice = JOptionPane.showOptionDialog(parentComponent, 
					messages.getAskToSaveMessageBeginnung() + "\"" + getDefaultNameOrPath() + "\"" + messages.getAskToSaveMessageEnd(),
					messages.getAskToSaveTitle(),
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE,	null, null,	null);
			if (choice == JOptionPane.YES_OPTION) {
				return save();
			}
			
			return (choice != JOptionPane.CANCEL_OPTION) && (choice != JOptionPane.CLOSED_OPTION); 
		}
		else {
			return true;
		}
	}

	
	/* (non-Javadoc)
	 * @see de.nn.util.io.Savable#saveAs()
	 */
	public boolean saveAs() {
		return saveAs(null);
	}
	
	
	public boolean saveAs(Component parentComponent) {
		fileChooser.setSelectedFile(new File(getDefaultNameOrPath()));
		boolean result = 
			  (fileChooser.showSaveDialog(parentComponent) == JFileChooser.APPROVE_OPTION);
		
		if (result) {
			File file;
			if (endsWithValidExt(fileChooser.getSelectedFile().getName())) {
				file = fileChooser.getSelectedFile();
			}
			else {
				file = new File(fileChooser.getSelectedFile().getAbsolutePath() + getDefaultExtension());
			}
			
			if (file.exists()) {
				result = JOptionPane.showConfirmDialog(null, "The file \"" + file.getAbsolutePath() + "\" already exists.\nDo you want to overwrite it?", "Overwrite file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
			}
			
			if (result) {
				setFile(file);
				return save();
			}
		}
		
		return result;
	}
	
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}
