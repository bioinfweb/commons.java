/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2014  Ben Stöver
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
package info.bioinfweb.commons.swing;


import info.bioinfweb.commons.changemonitor.ChangeMonitorable;
import info.bioinfweb.commons.io.AbstractSaver;
import info.bioinfweb.commons.io.Savable;
import info.bioinfweb.commons.*;
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
