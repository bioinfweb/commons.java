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



/**
 * An abstract adapter class for receiving IO progress events. The methods in this class are empty. 
 * This class exists as convenience for creating listener objects. 
 * @author Ben St&ouml;ver
 */
public abstract class IOProgressListenerAdapter implements IOProgressListener {
	public void ioFinished() {}

	
	public void ioProgress(long bytes) {}

	
	public void ioStarts() {}


	public void newFile(String name) {}
}
