package info.webinsel.util.graphics;


import java.awt.*;
import java.awt.font.*;
import java.awt.image.BufferedImage;



public class FontCalculator {
	private static FontCalculator firstInstance = null;
	private final FontRenderContext frc = ((Graphics2D)new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY).getGraphics()).getFontRenderContext(); 
  
	
	private FontCalculator() {}
	
	
	public static FontCalculator getInstance() {
		if (firstInstance == null) {
			firstInstance = new FontCalculator();
		}
		return firstInstance;
	}
	
	
	public float getDescent(Font font) {
		return new TextLayout("Üg", font, frc).getDescent();
	}
	
	
	public float getAscent(Font font) {
		return new TextLayout("Üg", font, frc).getAscent();
	}
	
	
	public float getHeight(Font font) {
		TextLayout tl = new TextLayout("Üg", font, frc);
		return tl.getDescent() + tl.getAscent();
	}
	
	
	public float getWidth(Font font, String text) {
		if (text.length() > 0) {
			return (float)new TextLayout(text, font, frc).getBounds().getWidth();
		}
		else {
			return 0f;
		}
	}
	
	
	/**
	 * Returns the with of the given text divided by the height.
	 * The value is identical with <code>getWidth() / getHeight()</code> although it
	 * is calculated more efficiently.
	 */
	public float getAspectRatio(Font font, String text) {
		TextLayout tl = new TextLayout(text, font, frc);
		return ((float)tl.getBounds().getWidth()) / (tl.getDescent() + tl.getAscent());
	}
	
	
	public float getWidthToHeigth(String fontName, int fontStyle, String text, float height) {
		if (text.length() > 0) {
			TextLayout tl = new TextLayout(text, new Font(fontName, fontStyle, 12), frc);
			return ((float)tl.getBounds().getWidth()) * (height / (tl.getDescent() + tl.getAscent()));
		}
		else {
			return 0f;
		}
	}
	
	
	public float getDescentToHeight(String fontName, int fontStyle, float height) {
		TextLayout tl = new TextLayout("Üg", new Font(fontName, fontStyle, 12), frc);
		return height * (tl.getDescent() / (tl.getDescent() + tl.getAscent()));
	}
	
	
	public float getAscentToHeight(String fontName, int fontStyle, float height) {
		TextLayout tl = new TextLayout("Üg", new Font(fontName, fontStyle, 12), frc);
		return height * (tl.getAscent() / (tl.getDescent() + tl.getAscent()));
	}
}
