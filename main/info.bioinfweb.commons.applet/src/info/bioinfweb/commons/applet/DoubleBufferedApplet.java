/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.applet;


import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;



public class DoubleBufferedApplet extends Applet {
	private BufferedImage offscreenImage = null;
	
	
	private BufferedImage getOffscreenImage() {
		if (offscreenImage == null) {
			Dimension d = getSize();
		  resizeOffscreenImage(d.width, d.height);
		}
		return offscreenImage;
	}


	protected void clearOffscreeImage() {
		if (getOffscreenImage() != null) {
			Graphics g2 = getOffscreenImage().getGraphics();
			Color color = g2.getColor();
			g2.setColor(getBackground());
			g2.drawRect(0, 0, getOffscreenImage().getWidth(), getOffscreenImage().getHeight());
			g2.setColor(color);
		}
	}
	
	
	@Override
	public void paint(Graphics g) {
		if (getOffscreenImage() != null) {
			clearOffscreeImage();
			paintOffscreen(getOffscreenImage().getGraphics());
			g.drawImage(getOffscreenImage(), 0, 0, this);
		}
		// super.paint() aufrufen, um evtl. Kinder zu zeichnen?
	}
	
	
	public void paintOffscreen(Graphics g) {
		//Grundimpl. leer
	}


	public void update(Graphics g) { 
    paint(g); 
  }


	private void resizeOffscreenImage(int width, int height) {
		if ((offscreenImage == null) || (width != getOffscreenImage().getWidth()) || (height != getOffscreenImage().getHeight())) {
			if ((width <= 0) || (height <= 0)) {
				offscreenImage = null;
			}
			else { 
				offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			}
		}
	}
	
	
	@Override
	public void resize(Dimension d) {
		resizeOffscreenImage((int)d.getWidth(), (int)d.getHeight());
		super.resize(d);
	}


	@Override
	public void resize(int width, int height) {
		resizeOffscreenImage(width, height);
		super.resize(width, height);
	}
}
