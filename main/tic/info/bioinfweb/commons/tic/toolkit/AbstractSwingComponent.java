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
package info.bioinfweb.commons.tic.toolkit;


import java.awt.Dimension;

import info.bioinfweb.commons.tic.TICComponent;

import javax.swing.JComponent;
import javax.swing.JPanel;



/**
 * The Swing component implementing {@link ToolkitComponent}. Custom Swing components can be inherited from this class.
 * <p>
 * This class also overwrites {@link #getMaximumSize()} in a way that all values specified for the maximum width or height
 * that are below the returned preferred width or height are ignored.
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 */
public abstract class AbstractSwingComponent extends JPanel implements ToolkitComponent {  // If JComponent is used as the base class, it won't be be possible to have a width above 16384 (or more with a differen max size but less than JPanel) pixels. JPanel allows this.
	private TICComponent independentComponent;

	
	public AbstractSwingComponent(TICComponent ticComponent) {
		super();
		this.independentComponent = ticComponent;
		setFocusable(true);
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension preferredSize = getPreferredSize();
		return new Dimension(Math.max(maxSize.width, preferredSize.width), Math.max(maxSize.height, preferredSize.height));
	}
}
