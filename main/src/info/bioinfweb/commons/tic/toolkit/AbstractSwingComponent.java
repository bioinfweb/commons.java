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
package info.bioinfweb.commons.tic.toolkit;


import info.bioinfweb.commons.tic.TICComponent;

import javax.swing.JComponent;




/**
 * The Swing component displaying a {@link PaintableArea}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 */
public abstract class AbstractSwingComponent extends JComponent implements ToolkitComponent {  //TODO Does JPanel have to be used here?
	private TICComponent independentComponent;

	
	public AbstractSwingComponent(TICComponent ticComponent) {
		super();
		this.independentComponent = ticComponent;
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}
}
