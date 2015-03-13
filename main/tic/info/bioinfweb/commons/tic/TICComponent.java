/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben Stöver
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
package info.bioinfweb.commons.tic;


import info.bioinfweb.commons.tic.input.TICComponentKeyListenersList;
import info.bioinfweb.commons.tic.input.TICComponentMouseListenersList;
import info.bioinfweb.commons.tic.input.TICComponentMouseWheelListenerList;
import info.bioinfweb.commons.tic.input.TICKeyListener;
import info.bioinfweb.commons.tic.input.TICMouseListener;
import info.bioinfweb.commons.tic.input.TICMouseWheelListener;
import info.bioinfweb.commons.tic.toolkit.DefaultSWTComposite;
import info.bioinfweb.commons.tic.toolkit.DefaultSwingComponent;
import info.bioinfweb.commons.tic.toolkit.ToolkitComponent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;



/**
 * All classes implementing TIC components must inherit from this class.
 * <p>
 * Note that depending on your GUI design {@link #assignSize()} might not have the desired if 
 * {@link #assignSizeToSWTLayoutData(org.eclipse.swt.graphics.Point, Composite)} is not overwritten with an according 
 * implementation.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class TICComponent {
	private ToolkitComponent toolkitComponent = null;
	private TICComponentKeyListenersList keyListenersList = new TICComponentKeyListenersList(this);
	private TICComponentMouseListenersList mouseListenersList = new TICComponentMouseListenersList(this);
	private TICComponentMouseWheelListenerList mouseWheelListenersList = new TICComponentMouseWheelListenerList(this);
	
	
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
		if (toolkitComponent instanceof Component) {
			return TargetToolkit.SWING;
		}
		else if (toolkitComponent instanceof Widget) {
			return TargetToolkit.SWT;
		}
		else {
			return TargetToolkit.UNDEFINED;
		}
	}

	
	/**
	 * Painting operations of the implementing class should be performed here, if a toolkit independent
	 * painting method is provided. The coordinates in the provided context are relative to this are. 
	 * (0, 0) represents the top left corner of this area.
	 * <p>
	 * If implementing classes provide custom toolkit specific components by overwriting 
	 * {@link #doCreateSwingComponent()} and {@link #doCreateSWTWidget(Composite, int)} the 
	 * implementation of this method can be empty.
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
	public abstract Dimension getSize();
	
	
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
	 * Adopts the current component size to the underlying GUI toolkit, if a toolkit specific component
	 * has already been created.
	 */
	public void assignSize() {
		if (hasToolkitComponent()) {
			Dimension size = getSize();
			if (getCurrentToolkit().equals(TargetToolkit.SWT)) {
				Composite composite = (Composite)getToolkitComponent();
				composite.setSize(size.width, size.height);
				assignSizeToSWTLayoutData(composite.getSize(), composite);
			}
			else {
				JComponent component = (JComponent)getToolkitComponent(); 
				component.setSize(size);
				component.setPreferredSize(size);  //TODO Also set minSize?
			}
		}
	}
	
	
	/**
	 * Method called by {@link #assignSize()} to be used to adopt the size of the TIC component to the 
	 * underlying SWT component's layout data. This method is only called if this component is backed
	 * by an SWT composite and not is the Swing version is used or no toolkit specifc component has
	 * been created yet.
	 * <p>
	 * It can be overwritten by inherited classes that know the class the layout data will have. This 
	 * default implementation is empty. Note that depending on your GUI design {@link #assignSize()}
	 * might not have the desired if this method is not overwritten with an according implementation.   
	 * 
	 * @param size - the size the composite shall have
	 * @param composite - the SWT composite that backs this TIC component
	 * 
	 * @since 1.1.0
	 */
	public void assignSizeToSWTLayoutData(org.eclipse.swt.graphics.Point size, Composite composite) {}
	
	
	/**
	 * Returns the location of this component in the coordinate space of the parent component.
	 * 
	 * @return the location or {@code null} if neither a Swing nor a SWT component has yet been created.
	 */
	public Point getLocationInParent() {
		switch (getCurrentToolkit()) {
			case SWING:
				return ((JComponent)getToolkitComponent()).getLocation();
			case SWT:
				org.eclipse.swt.graphics.Point location = ((Composite)getToolkitComponent()).getLocation();
				return new Point(location.x, location.y);
			default:
				return null;
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
	 * Method that creates a new instance of the associated Swing component. It is called by
	 * {@link #createSwingComponent()} internally.
	 * <p>
	 * Overwrite this method if you want to provide custom toolkit specific implementations.
	 * <p>
	 * <b>IMPORTANT:</b> The returned instances must implement the interface {@link ToolkitComponent}. 
	 * 
	 * @return a new instance implementing {@link ToolkitComponent}
	 */
	protected JComponent doCreateSwingComponent() {
		return new DefaultSwingComponent(this);
	}
	
	
	/**
	 * Creates the Swing component that will be associated with this instance if it was not created before. 
	 * The returned instance will be returned by {@link #getToolkitComponent()} from now on. Subsequent
	 * calls of this method will return the same instance again. 
	 * <p>
	 * Note that this method can only be called if {@link #createSWTWidget(Composite, int)} has not been called 
	 * before.
	 * <p>
	 * If you want to provide a custom Swing component overwrite {@link #doCreateSwingComponent()} 
	 * instead of this method.
	 * 
	 * @return the associated Swing component that has been created
	 * @throws IllegalStateException if {@link #createSWTWidget(Composite, int)} has been called before 
	 */
	public JComponent createSwingComponent() {
		if (toolkitComponent == null) {
			JComponent component = doCreateSwingComponent();
			component.addKeyListener(keyListenersList);
			component.addMouseListener(mouseListenersList);
			component.addMouseMotionListener(mouseListenersList);
			component.addMouseWheelListener(mouseWheelListenersList);
			toolkitComponent = (ToolkitComponent)component;
		}
		else if (!getCurrentToolkit().equals(TargetToolkit.SWING)) {
			throw new IllegalStateException("A non Swing component has already been created.");
		}
		return (JComponent)toolkitComponent;
	}
	
	
	/**
	 * Method that creates a new instance of the associated SWT component. It is called by
	 * {@link #createSWTWidget} internally.
	 * <p>
	 * Overwrite this method if you want to provide custom toolkit specific implementations.
	 * <p>
	 * <b>IMPORTANT:</b> The returned instances must implement the interface {@link ToolkitComponent}. 
	 * 
	 * @return a new instance implementing {@link ToolkitComponent}
	 */
	protected Composite doCreateSWTWidget(Composite parent, int style) {
		return new DefaultSWTComposite(parent, style, this);
	}
	
	
	private ToolkitComponent createAndRegisterSWTWidget(Composite parent, int style) {
		Composite result = doCreateSWTWidget(parent, style);
		result.addKeyListener(keyListenersList);
		result.addMouseListener(mouseListenersList);
		result.addMouseMoveListener(mouseListenersList);
		result.addMouseTrackListener(mouseListenersList);
		result.addMouseWheelListener(mouseWheelListenersList);
		return (ToolkitComponent)result;
	}
	
	
	/**
	 * Creates the SWT component that will be associated with this instance if it was not created before. 
	 * The returned instance will be returned by {@link #getToolkitComponent()} from now on. Subsequent
	 * calls of this method will return the same instance again. The specified parameters will not be
	 * considered in that case. 
	 * <p>
	 * Note that this method can only be called if {@link #createSWTWidget(Composite, int)} has not been called 
	 * before.
	 * <p>
	 * If you want to provide a custom Swing component overwrite {@link #doCreateSwingComponent()} 
	 * instead of this method.
	 * 
	 * @return the associated Swing component that has been created
	 * @throws IllegalStateException if {@link #createSWTWidget(Composite, int)} has been called before 
	 */
	public Composite createSWTWidget(Composite parent, int style) {
		if (toolkitComponent == null) {
			toolkitComponent = createAndRegisterSWTWidget(parent, style);
		}
		else if (!getCurrentToolkit().equals(TargetToolkit.SWT)) {
			throw new IllegalStateException("A non Swing component has already been created.");
		}
		else if (((Composite)toolkitComponent).isDisposed()) {  // && getCurrentToolkit().equals(TargetToolkit.SWT)
			toolkitComponent = createAndRegisterSWTWidget(parent, style);  // Create new component if previous one was disposed.
			//TODO Does this make sense this way? Anything else to be done about disposing of SWT elements?
		}
		return (Composite)toolkitComponent;
	}
	
	
	/**
	 * Adds a key listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to key events
	 */
	public void addKeyListener(TICKeyListener listener) {
		keyListenersList.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified key listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeKeyListener(TICKeyListener listener) {
		return keyListenersList.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to mouse events
	 * @see #addMouseWheelListener(TICMouseWheelListener)
	 */
	public void addMouseListener(TICMouseListener listener) {
		mouseListenersList.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeMouseListener(TICMouseListener listener) {
		return mouseListenersList.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse wheel listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to mouse wheel events
	 * @see #addMouseListener(TICMouseListener)
	 */
	public void addMouseWheelListener(TICMouseWheelListener listener) {
		mouseWheelListenersList.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse wheel listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeWheelMouseListener(TICMouseWheelListener listener) {
		return mouseWheelListenersList.getListeners().remove(listener);
	}
}
