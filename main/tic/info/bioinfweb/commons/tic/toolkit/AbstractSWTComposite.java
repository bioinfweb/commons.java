/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014-2015  Ben Stöver
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


import info.bioinfweb.commons.tic.TICComponent;

import org.eclipse.swt.widgets.Composite;



/**
 * The Swing component implementing {@link ToolkitComponent}. Custom Swing components can be inherited from this class.
 *
 * @author Ben St&ouml;ver
 * @since 1.1.0
 */
public class AbstractSWTComposite extends Composite implements ToolkitComponent {
	private TICComponent independentComponent;

	
	public AbstractSWTComposite(Composite parent, int style, TICComponent ticComponent) {
		super(parent, style);
		independentComponent = ticComponent;
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public void repaint() {
		redraw();
	}
}
