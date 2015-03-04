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
package info.bioinfweb.commons.progress;



/**
 * Implementation of {@link ProgressMonitor} that only stores the progress and does not output it anywhere.
 * 
 * @author Ben St&ouml;ver
 */
public class VoidProgressMonitor extends AbstractProgressMonitor implements ProgressMonitor {
	private double progress = 0;
	
	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.progress.ProgressMonitor#getProgressValue()
	 */
	@Override
	public double getProgressValue() {
  	return progress;
	}

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.progress.ProgressMonitor#setProgressValue(double)
	 */
	@Override
	public void setProgressValue(double value) {
		progress = value;
	}
	
	
	/**
	 * This method always returnes <code>false</code>.
	 * 
	 * @see info.bioinfweb.commons.progress.ProgressMonitor#isCanceled()
	 */
	@Override
	public boolean isCanceled() {
		return false;
	}
}
