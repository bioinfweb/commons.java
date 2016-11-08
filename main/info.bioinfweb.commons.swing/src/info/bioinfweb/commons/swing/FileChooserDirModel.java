/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;



/**
 * This class allows several instances of <code>JFileChooser</code> to use the same directory. 
 * If one registered component changes its current directory, all the others are updated.
 * 
 * @author Ben St&ouml;ver
 */
public class FileChooserDirModel {
  private Vector<JFileChooser> fileChoosers = new Vector<JFileChooser>();
  private Listener listener = new Listener();
  private File currentDirectory = null;
  private boolean saveCanceled = true;
  
  
  public FileChooserDirModel() {
		super();
  }


	public FileChooserDirModel(File currentDirectory) {
		super();
		this.currentDirectory = currentDirectory;
	}
	
	
	private class Listener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
  		if (fileChoosers.contains(e.getSource()) &&
  				(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION) ||
  				 (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION) && saveCanceled))) {
  			setCurrentDirectory(((JFileChooser)e.getSource()).getCurrentDirectory());
  		}
  	}
  }
  
  
	public File getCurrentDirectory() {
		return currentDirectory;
	}


	public void setCurrentDirectory(File currentDirectory) {
		this.currentDirectory = currentDirectory;

		for (int i = 0; i < fileChoosers.size(); i++) {
			fileChoosers.get(i).setCurrentDirectory(currentDirectory);
		}
	}


	public boolean isSaveCanceled() {
		return saveCanceled;
	}


	/**
	 * If <code>setSaveCanceled(true)</code> is called the current directory is also updated if the 
	 * user cancels the file chooser dialog.<br>
	 * Default value is <code>true</code>.
	 * 
	 * @param saveCanceled
	 */
	public void setSaveCanceled(boolean saveCanceled) {
		this.saveCanceled = saveCanceled;
	}


	public boolean contains(JFileChooser fileChooser) {
		return fileChoosers.contains(fileChooser); 
	}
	
	
	public void addFileChooser(JFileChooser fileChooser) {
		fileChooser.setCurrentDirectory(getCurrentDirectory());
  	fileChooser.addActionListener(listener);
  	fileChoosers.add(fileChooser);
  }
  
  
	public void removeFileChooser(JFileChooser fileChooser) {
  	fileChooser.removeActionListener(listener);
  	fileChoosers.remove(fileChooser);
  }
}
