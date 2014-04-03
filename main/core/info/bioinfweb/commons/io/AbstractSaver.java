package info.bioinfweb.commons.io;


import info.bioinfweb.commons.ChangeMonitor;
import info.bioinfweb.commons.ChangeMonitorable;
import info.webinsel.util.*;

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
	 * @see info.webinsel.util.io.Savable#askToSave()
	 */
	public abstract boolean askToSave();

	
	/* (non-Javadoc)
	 * @see info.webinsel.util.io.Savable#getDefaultPath()
	 */
	public String getDefaultName() {
		return defaultName;
	}
	

	/* (non-Javadoc)
	 * @see info.webinsel.util.io.Savable#getPath()
	 */
	public File getFile() {
		return file;
	}

	
	/* (non-Javadoc)
	 * @see info.webinsel.util.io.Savable#hasPath()
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
	 * @see info.webinsel.util.io.Savable#save()
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
	 * @see info.webinsel.util.io.Savable#saveAs()
	 */
	public abstract boolean saveAs();
	

	/* (non-Javadoc)
	 * @see info.webinsel.util.io.Savable#setDefaultPath(java.lang.String)
	 */
	public void setDefaultName(String path) {
		defaultName = path;
	}

	
	/* (non-Javadoc)
	 * @see info.webinsel.util.io.Savable#setPath(java.lang.String)
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
