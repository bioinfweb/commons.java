/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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


import info.bioinfweb.commons.changemonitor.ChangeMonitor;
import info.bioinfweb.commons.changemonitor.ChangeMonitorable;

import java.io.*;
import java.util.Vector;



/**
 * @author Ben S&ouml;ever
 *
 */
public abstract class AbstractSaver extends ChangeMonitor implements ChangeMonitorable, Savable {
	private File file;
	private String defaultName;
	private Vector<String> fileExtensions = new Vector<String>();
	private String defaultExtension = "";
	

	public void addFileExtension(String ext) {
		fileExtensions.add(ext.toLowerCase());
	}


	public String getDefaultExtension() {
		return defaultExtension;
	}


	public String[] getFileExtensions() {
		return fileExtensions.toArray(new String[fileExtensions.size()]);
	}


	public void removeFileExtention(String ext) {
		fileExtensions.remove(ext.toLowerCase());
	}


	public void setDefaultExtension(String ext) {
		removeFileExtention(getDefaultExtension());
		defaultExtension = ext;
		addFileExtension(ext);
	}


	public boolean endsWithDefaultExt(String path) {
		return path.toLowerCase().endsWith(getDefaultExtension());
	}


	public boolean endsWithValidExt(String path) {
		path = path.toLowerCase();
		for (int i = 0; i < fileExtensions.size(); i++) {
			if (path.endsWith(fileExtensions.get(i))) {
				return true;
			}
		}
		return false;
	}


	protected abstract void saveDataToFile(File file);

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#askToSave()
	 */
	public abstract boolean askToSave();

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#getDefaultPath()
	 */
	public String getDefaultName() {
		return defaultName;
	}
	

	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#getPath()
	 */
	public File getFile() {
		return file;
	}

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#hasPath()
	 */
	public boolean hasFile() {
		return (file != null);
	}

	
  public String getDefaultNameOrPath() {
  	if (hasFile()) {
  		return getFile().getAbsolutePath();
  	}
  	else {
  		return getDefaultName();
  	}  		
  }
  
  
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#save()
	 */
	public boolean save() {
		if (hasFile()) {
			saveDataToFile(getFile());
			reset();
			return true;
		}
		else {
			return saveAs();
		}
	}
	

	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#saveAs()
	 */
	public abstract boolean saveAs();
	

	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#setDefaultPath(java.lang.String)
	 */
	public void setDefaultName(String path) {
		defaultName = path;
	}

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.io.Savable#setPath(java.lang.String)
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
