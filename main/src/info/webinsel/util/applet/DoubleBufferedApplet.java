package info.webinsel.util.applet;


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
