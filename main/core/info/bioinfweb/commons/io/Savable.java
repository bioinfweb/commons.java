package info.bioinfweb.commons.io;


import info.bioinfweb.commons.changemonitor.ChangeMonitorable;
import info.webinsel.util.*;

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
