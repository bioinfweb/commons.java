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


import info.bioinfweb.commons.tic.toolkit.AbstractSWTWidget;
import info.bioinfweb.commons.tic.toolkit.AbstractSwingComponent;
import info.bioinfweb.commons.tic.toolkit.DefaultSWTWidget;
import info.bioinfweb.commons.tic.toolkit.DefaultSwingComponent;
import info.bioinfweb.commons.tic.toolkit.ToolkitComponent;

import java.awt.geom.Dimension2D;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Composite;



/**
 * All classes implementing TIC components must inherit from this class.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class TICComponent {
	private ToolkitComponent toolkitComponent = null;
	
	
	/**
	 * Creates a new instance of this class.
	 */
	public TICComponent() {
		super();
	}

	
	/**
	 * Returns the toolkit the associated toolkit class belongs to.  
	 * 
	 * @return the toolkit type or {@link TargetToolkit#UNDEFINED} if no toolkit specific 
	 *         implementation has yet been assigned
	 */
	public TargetToolkit getCurrentToolkit() {
		if (toolkitComponent instanceof AbstractSwingComponent) {
			return TargetToolkit.SWING;
		}
		else if (toolkitComponent instanceof AbstractSWTWidget) {
			return TargetToolkit.SWT;
		}
		else {
			return TargetToolkit.UNDEFINED;
		}
	}

	
	/**
	 * Painting operations of the implementing class should be performed here. The coordinates in the 
	 * provided context are relative to this are. (0, 0) represents the top left corner of this area.
	 * 
	 * @param graphics - the graphics context used to perform the paint operations in Swing and SWT
	 */
	public abstract void paint(TICPaintEvent event);
	
	
	/**
	 * Returns the size this objects uses to be painted completely.
	 * <p>
	 * Note that the associated graphical component might be contained in a scroll container and the
	 * actual area displayed in the screen can be smaller than the dimension returned here.
	 * </p>  
	 * 
	 * @return the dimension in pixels
	 */
	public abstract Dimension2D getSize();
	
	
	/**
	 * Forces the underlying toolkit component to be repainted. This method will only have an effect 
	 * if a toolkit specific component has already been assigned ({@link #hasToolkitComponent()} return
	 * {@code true}).
	 */
	public void repaint() {
		if (hasToolkitComponent()) {
			getToolkitComponent().repaint();
		}
	}
	
	
	/**
	 * Returns a toolkit specific component used to display the contents of this class.
	 * 
	 * @return the toolkit specific component or {@code null} if neither {@link #createSwingComponent()} nor 
	 *         {@link #createSWTWidget(Composite, int)} have been called previously
	 */
	public ToolkitComponent getToolkitComponent() {
		return toolkitComponent;
	}
	
	
	/**
	 * Checks if an associated toolkit specific component has already been assigned to this instance.
	 * 
	 * @return {@code true} if a component has been assigned, {@code false} otherwise
	 */
	public boolean hasToolkitComponent() {
		return toolkitComponent != null;
	}
	
	
	/**
	 * Creates the Swing component that will be associated with this instance. The returned instance
	 * will be returned by {@link #getToolkitComponent()} from now on.
	 * <p>
	 * Note that this method can only be called once and only if {@link #createSWTWidget(Composite, int)}
	 * has not been called before.
	 * <p>
	 * Overwrite this method if you want to provide custom toolkit specific implementations.
	 * <p>
	 * <b>IMPORTANT:</b> The returned instances must implement the interface {@link ToolkitComponent}. 
	 * 
	 * @return the associated Swing component that has been created
	 * @throws IllegalStateException if this method or {@link #createSWTWidget(Composite, int)} has already
	 *         been called before 
	 */
	public JComponent createSwingComponent() {
		if (toolkitComponent != null) {
			toolkitComponent = new DefaultSwingComponent(this);
			return (AbstractSwingComponent)toolkitComponent;
		}
		else {
			throw new IllegalStateException("A component has already been created for this instance.");
		}
	}
	
	
	/**
	 * Creates the SWT component that will be associated with this instance. The returned instance
	 * will be returned by {@link #getToolkitComponent()} from now on.
	 * <p>
	 * Note that this method can only be called once and only if {@link #createSwingComponent())}
	 * has not been called before.
	 * <p>
	 * Overwrite this method if you want to provide custom toolkit specific implementations. 
	 * <p>
	 * <b>IMPORTANT:</b> The returned instances must implement the interface {@link ToolkitComponent}. 
	 * 
	 * @return the associated SWT component that has been created
	 * @throws IllegalStateException if this method or {@link #createSwingComponent()} has already
	 *         been called before 
	 */
	public Composite createSWTWidget(Composite parent, int style) {
		if (toolkitComponent != null) {
			toolkitComponent = new DefaultSWTWidget(parent, style, this);
			return (AbstractSWTWidget)toolkitComponent;
		}
		else {
			throw new IllegalStateException("A component has already been created for this instance.");
		}
	}
}
