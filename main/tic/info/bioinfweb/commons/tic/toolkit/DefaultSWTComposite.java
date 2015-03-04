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


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import info.bioinfweb.commons.tic.TICComponent;
import info.bioinfweb.commons.tic.TICPaintEvent;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
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
	

	/**
	 * Fire a TIC paint event internally to let the implementing class draw on a buffered image and than draws
	 * that image into the SWT graphics context.
	 * <p>
	 * Note that the TIC paint event will always cover a rectangle which is extended by one pixel to the left
	 * and top compared to the SWT paint event. That is done to solve problems with anti-aliased lines passing
	 * the bounds of that rectangle.
	 */
	@Override
	public void paintControl(PaintEvent e) {
		BufferedImage refreshArea = new BufferedImage(e.width + 1, e.height + 1, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = refreshArea.createGraphics();
		try {
			g.translate(-e.x + 1, -e.y + 1);
			getIndependentComponent().paint(new TICPaintEvent(this, g, new Rectangle(e.x - 1, e.y - 1, e.width + 1, e.height + 1)));
      ImageData data = new ImageData(refreshArea.getWidth(), refreshArea.getHeight(), 24, 
      		new PaletteData(0xff, 0xff00, 0xff0000), 3 * refreshArea.getWidth(), 
      		((DataBufferByte)refreshArea.getRaster().getDataBuffer()).getData());
      e.gc.drawImage(new Image(e.gc.getDevice(), data), e.x - 1, e.y - 1);
		}
		finally {
			g.dispose();
		}
	}
}
