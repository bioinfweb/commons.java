/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
 * <http://bioinfweb.info/Commons>
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


import java.awt.Rectangle;

import info.bioinfweb.commons.tic.SWTGraphics2D;
import info.bioinfweb.commons.tic.TICComponent;
import info.bioinfweb.commons.tic.TICPaintEvent;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Composite;



/**
 * The default SWT widget implementation to be associated with a {@link TICComponent}.
 * <p>
 * It uses the {@link TICComponent#paint(TICPaintEvent)} to draw the widget.
 * 
 * @author Ben St&ouml;ver
 */
public class DefaultSWTComposite extends AbstractSWTWidget {
	public DefaultSWTComposite(Composite parent, int style, TICComponent ticComponent) {
		super(parent, style, ticComponent);
	}
	

	@Override
	public void paintControl(PaintEvent e) {
		SWTGraphics2D g = new SWTGraphics2D(e.gc);
		try {
			getIndependentComponent().paint(new TICPaintEvent(this, g, new Rectangle(e.x, e.y, e.width, e.height)));
		}
		finally {
			g.dispose();
		}
	}
}
