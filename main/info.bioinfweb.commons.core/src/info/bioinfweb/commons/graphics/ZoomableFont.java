/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.graphics;


import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



/**
 * Bean class that allow to define a font with a zoom independent size. Property changes can be
 * tracked using a {@link PropertyChangeListener}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class ZoomableFont {
	private double originalHeight = 10;
	private int style = Font.PLAIN;
	private String name = Font.SANS_SERIF;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	
	/**
	 * Creates a new instance of this class with a height of 10 and the font style {@link Font#PLAIN}
	 * and the font name {@link Font#SANS_SERIF}.
	 */
	public ZoomableFont() {
		super();
	}


	/**
	 * Creates a new instance of this class.
	 * 
	 * @param name the font name
	 * @param style the font style
	 * @param originalHeight the font size for a zoom factor of 100 %
	 */
	public ZoomableFont(String name, int style, double originalHeight) {
		super();
		this.originalHeight = originalHeight;
		this.style = style;
		this.name = name;
	}


	/**
	 * Returns the font size for a zoom factor of 100 %.
	 * 
	 * @return the original font size
	 * @see Font#getSize()
	 */
	public double getOriginalHeight() {
		return originalHeight;
	}


	/**
	 * Sets a new font size for a zoom factor of 100 %.
	 * 
	 * @param originalHeight the new font height
	 * @see Font#getSize()
	 */
	public void setOriginalHeight(double originalHeight) {
		if (this.originalHeight != originalHeight) {
			double oldValue = this.originalHeight;
			this.originalHeight = originalHeight;
			propertyChangeSupport.firePropertyChange("originalHeight", oldValue, originalHeight);
		}
	}


	/**
	 * Returns the font style.
	 * 
	 * @return the font style
	 * @see Font#getStyle()
	 */
	public int getStyle() {
		return style;
	}


	/**
	 * Sets a new font style.
	 * 
	 * @param style the new font style
	 * @see Font#getStyle()
	 */
	public void setStyle(int style) {
		if (this.style != style) {
			int oldValue = this.style;
			this.style = style;
			propertyChangeSupport.firePropertyChange("style", oldValue, style);
		}
	}


	/**
	 * Returns the font name.
	 * 
	 * @return the font name
	 * @see Font#getName()
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets a new font name.
	 * 
	 * @param name the new font name
	 * @see Font#getName()
	 */
	public void setName(String name) {
		if (this.name != name) {
			String oldValue = this.name;
			this.name = name;
			propertyChangeSupport.firePropertyChange("name", oldValue, name);
		}
	}
	
	
	/**
	 * Creates a new {@link Font} instance according to the properties if this object and the provided zoom factor.
	 * 
	 * @param zoom the zoom factor to determine the font size of the returned object
	 * @return a new font object
	 */
	public Font createFont(double zoom) {
		return new Font(getName(), getStyle(), 10).deriveFont((float)(getOriginalHeight() * zoom));
	}
	
	
	public void adjustFromFont(Font font) {
		setName(font.getName());
		setStyle(font.getStyle());
		setOriginalHeight(font.getSize2D());
	}


	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}


	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}


	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}


	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
}
