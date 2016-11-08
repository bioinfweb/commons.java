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
package info.bioinfweb.commons.io;


import java.util.Vector;



/**
 * This class can be used if several profress listeners should be informed about the same process.
 * You have to add all listerns to an instance of this class and pass the instance as the only
 * progress lister. All included listeners will be informed by this class than.
 * @author Ben St&ouml;ver
 */
public class IOProgressListenerVector implements IOProgressListener {
  private Vector<IOProgressListener> vector = new Vector<IOProgressListener>();

  
	public void ioStarts() {
		for (int i = 0; i < size(); i++) {
			get(i).ioStarts();
		}
	}

	
	public void ioProgress(long bytes) {
		for (int i = 0; i < size(); i++) {
			get(i).ioProgress(bytes);
		}
	}

	
	public void newFile(String sourceName, String destName, long size) {
		for (int i = 0; i < size(); i++) {
			get(i).newFile(sourceName, destName, size);
		}
	}


	public void ioFinished() {
		for (int i = 0; i < size(); i++) {
			get(i).ioFinished();
		}
	}


	public boolean add(IOProgressListener listener) {
		return vector.add(listener);
	}

	
	public IOProgressListener get(int pos) {
		return vector.get(pos);
	}


	public void clear() {
		vector.clear();
	}

	
	public boolean remove(IOProgressListener listener) {
		return vector.remove(listener);
	}

	
	public int size() {
		return vector.size();
	}
}
