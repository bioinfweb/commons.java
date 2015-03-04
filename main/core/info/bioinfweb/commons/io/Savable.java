/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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
package info.bioinfweb.commons.io;


import info.bioinfweb.commons.changemonitor.ChangeMonitorable;
import info.bioinfweb.commons.*;

import java.io.*;



public interface Savable extends ChangeMonitorable {
	/**The file extension which should be added to file name chosen in the save as dialog,
	 * if not one of the allowed ones is already present.
	 * @return the file extension starting with a "."
	 */
	public String getDefaultExtension();
	
	
	public void setDefaultExtension(String ext);
	
	
	/**Adds an file extension to the list of the allowed file extensions which can be entered
	 * by the user in the save as dialog.
	 * @param ext - the extension to add starting with a "."
	 */
	public void addFileExtension(String ext);
	
	
	public void removeFileExtention(String ext);
	
	
	public String[] getFileExtensions();
	
	
	/**
	 * Tests if the given string ends with one of the valid file extensions stored.
	 * @param path the path or file name to test
	 * @return true, if one valid extension is present
	 */
	public boolean endsWithValidExt(String path);
	
	
	/**
	 * Tests if the given string ends with the default file extension.
	 * @param path  the path or file name to test
	 * @return true, if the default extension in present
	 */
	public boolean endsWithDefaultExt(String path);
	
	
	/**
	 * Checks weather a file has been assigned
	 * @return true if a file has been assigned to the data 
	 */
	public boolean hasFile();
	

	/**
	 * Returns the assigned file.
	 * @return The path or null if none was assigned. 
	 */
	public File getFile();

	
	/**Manually assignes a path. */
	public void setFile(File file);
	
	
	/**
	 * The path that should be proposed by the saveAs()-method if no path has been assigned.
	 * @return The path or null if none was assigned. 
	 */
  public String getDefaultName();

  
	/**
	 * Sets the path that should be proposed by the saveAs()-method if no path has been 
	 * assigned.
	 */
  public void setDefaultName(String path);
  
  
	/**The path of the assigned file or the default name if no file has been specified. */
  public String getDefaultNameOrPath();
	

	/**
	 * This method should save the data to a file defined by the path and should call 
	 * saveAs() if no path was assigned. 
	 * @return true bei Erfolg, false bei Abbruch durch den Benutzer.
	 */
  public boolean save();

  
	/**
	 * Promts the user der select a path to save the data. The values of getPAth() or 
	 * getDefaultPath() should be suggestet. 
	 * @return true if successful and false if the user cancels
	 */
  public boolean saveAs();
	
	
	/**
	 * Promts the user wheather to save changed data and calls save() if necessary.
	 * If nothing has changed, there will be no prompt and the result is <code>true</code>.
	 * @return <code>true</code> if the file can be closed or <code>false</code> if the user 
	 * wants to cancel
	 */
  public boolean askToSave();
}
