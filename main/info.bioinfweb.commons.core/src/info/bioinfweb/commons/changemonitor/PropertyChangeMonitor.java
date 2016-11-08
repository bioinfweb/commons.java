/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.changemonitor;


import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



/**
 * This class can be used to assign a {@link ChangeMonitor} to an object that accepts
 * {@code PropertyChangeListener}s.
 * 
 * @author Ben St&ouml;ver
 */
public class PropertyChangeMonitor extends ChangeMonitor implements PropertyChangeListener {
	private String propertyName;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param propertyName - the name of the property to listen to
	 */
	public PropertyChangeMonitor(String propertyName) {
		super();
		this.propertyName = propertyName;
	}


	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(e.getPropertyName());
		if (e.getPropertyName().equals(propertyName)) {
			registerChange();
		}
	}


	/**
	 * Returns the name of the property this monitor listens to.
	 * 
	 * @return the property name
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	
	public void addTo(Container container) {
		//container.addPropertyChangeListener(getPropertyName(), this);
		container.addPropertyChangeListener(this);
	}
}
