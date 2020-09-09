/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.progress;



/**
 * Basic implementation of the {@link ProgressMonitor} interface.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class AbstractProgressMonitor implements ProgressMonitor {
	private double progress = 0;
	private String text = "";
	
	
	@Override
	public double getProgressValue() {
  	return progress;
	}

	
	@Override
  public String getProgressText() {
	  return text;
  }


	protected abstract void onProgress(double value, String text);
	
	
	@Override
  public void setProgressValue(double value, String text) {
		progress = value;
	  this.text = text;
	  onProgress(value, text);
  }


	@Override
	public void addToProgressValue(double addend) {
		setProgressValue(getProgressValue() + addend);
	}
	

	@Override
  public void setProgressValue(double value) {
	  setProgressValue(value, getProgressText());
  }

	
	@Override
  public void addToProgressValue(double addend, String text) {
	  setProgressValue(getProgressValue() + addend, text);
  }
}
