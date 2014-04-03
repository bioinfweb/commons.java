/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.tic;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.EventObject;

import org.eclipse.swt.graphics.GC;



/**
 * Event that notifies implementations of {@link PaintableArea} that a part of their associated
 * components have to be repainted. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 */
public class TICPaintEvent extends EventObject {
  private Graphics2D graphics;
  private GC gc;
  private Rectangle rectangle;
  
  
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param source - the object that triggered the event
	 * @param graphics - the swing graphics context
	 * @param rectangle - the rectangle that has to be repainted
	 */
	public TICPaintEvent(Object source, Graphics2D graphics, Rectangle rectangle) {
		this(source, graphics, null, rectangle);
	}

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param source - the object that triggered the event
	 * @param graphics - the swing graphics context
	 * @param gc - the SWT graphics context (Provided as an possible alternative for toolkit specific drawing.) 
	 * @param rectangle - the rectangle that has to be repainted
	 */
	public TICPaintEvent(Object source, Graphics2D graphics, GC gc, Rectangle rectangle) {
		super(source);
		this.graphics = graphics;
		this.gc = gc;
		this.rectangle = rectangle;
	}
	

	/**
	 * Returns the graphic context to paint on. It is guaranteed that this method will always return
	 * a graphic context independent of the toolkit that is currently used.
	 * 
	 * @return the {@link Graphics2D} object of the associated swing component or the adapter class of
	 *         the associated SWT component
	 */
	public Graphics2D getGraphics() {
		return graphics;
	}

	
	/**
	 * Checks if an SWT graphics context is available.
	 * 
	 * @return {@code true} if a SWT graphics context is available, {@code false} otherwise.
	 */
	public boolean hasGC() {
		return gc != null;
	}
	

	/**
	 * The SWT graphics context, if one is available. This method will only return an instance if SWT
	 * is currently used as the toolkit.
	 * 
	 * @return the SWT graphics context or {@code null} if SWT is not the toolkit currently used
	 */
	public GC getGC() {
		return gc;
	}


	/**
	 * Returns the rectangle that needs to be repainted.
	 * 
	 * @return the area to be painted determined the associated Swing or SWT class 
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}
}
