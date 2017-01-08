/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.io;



/**
 * Classes that implement this interface can be informed about the progress of reading and writing
 * data.
 * 
 * @author Ben St&ouml;ver
 */
public interface IOProgressListener {
  /**
   * This method is called when a reading or writing of data is about to begin.
   */
	public void ioStarts();
	
	
  /**
   * This method is called when a progress in reading or writing data has been made.
   * 
   * @param bytes - the number of bytes that have been read or written until yet.
   */
  public void ioProgress(long bytes);
  
  
  /**
   * This method is called only if the progress listeners is passed to a method that processes
   * several files. Every time before a new file is processed, this method is invoked.
   * 
   * @param sourceName - the name of the current source file.
   * @param destName - the name of the current destination file
   * @param size - the size in bytes of the current file
   */
  public void newFile(String sourceName, String destName, long size);
  
  
  /**
   * This method is called when a reading or writing of data has been finished.
   */
	public void ioFinished();
}
