/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben St�ver
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

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;



/**
 * The SWT widget displaying a {@link PaintableArea}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 */
public abstract class AbstractSWTWidget extends Canvas implements PaintListener, ToolkitComponent {
	private TICComponent independentComponent;

	
	public AbstractSWTWidget(Composite parent, int style, TICComponent ticComponent) {
		super(parent, style);
		independentComponent = ticComponent;
		addPaintListener(this);
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
