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
package info.bioinfweb.commons.graphics;


import java.awt.*;
import java.awt.font.*;
import java.awt.geom.Rectangle2D;
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
		return new TextLayout("Ög", font, frc).getDescent();
	}
	
	
	public float getAscent(Font font) {
		return new TextLayout("Ög", font, frc).getAscent();
	}
	
	
	public float getHeight(Font font) {
		TextLayout tl = new TextLayout("Ög", font, frc);
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
	 * Returns the width of the given text divided by the height.
	 * <p>
	 * The value is identical with {@code getWidth() / getHeight()} but is calculated more efficiently.
	 * 
	 * @return the aspect ratio of the specified text using the specified font
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
		TextLayout tl = new TextLayout("Ög", new Font(fontName, fontStyle, 12), frc);
		return height * (tl.getDescent() / (tl.getDescent() + tl.getAscent()));
	}
	
	
	public float getAscentToHeight(String fontName, int fontStyle, float height) {
		TextLayout tl = new TextLayout("Ög", new Font(fontName, fontStyle, 12), frc);
		return height * (tl.getAscent() / (tl.getDescent() + tl.getAscent()));
	}
	
	
  /**
   * Returns a font object that has the correct height to draw the specified text in the specified rectangle using
   * no minimal font height.
   * 
   * @param rectangle the rectangle to fit the text into
   * @param scaleFactor the factor by which the font height should be scaled (If 1.0 is provided here, the text will
   *        will the width or the height of the rectangle completely. Note that due to possible rounding imprecision 
   *        a value below 1.0 is recommended so that the text fits completely into the rectangle.)
   * @param text the text to be drawn
   * @param fontName the name of the font to be used
   * @param fontStyle the style of the font to be used
   * @return the new font object
   * @see GraphicsUtils#drawStringInRectangle(Graphics2D, Rectangle2D, String)
   */
  public Font fontToFitRectangle(Rectangle2D rectangle, double scaleFactor, String text, 
  		String fontName, int fontStyle) {
  	
  	return fontToFitRectangle(rectangle, scaleFactor, text, fontName, fontStyle, 0);
  }
  
  	
  /**
   * Returns a font object that has the correct height to draw the specified text in the specified rectangle.
   * 
   * @param rectangle the rectangle to fit the text into
   * @param scaleFactor the factor by which the font height should be scaled (If 1.0 is provided here, the text will
   *        will the width or the height of the rectangle completely. Note that due to possible rounding imprecision 
   *        a value below 1.0 is recommended so that the text fits completely into the rectangle.)
   * @param text the text to be drawn
   * @param fontName the name of the font to be used
   * @param fontStyle the style of the font to be used
   * @param minHeight the minimal font height the text should have
   * @return the new font object of {@code null} if the font would have to be smaller than the specified minimal height
   * @see GraphicsUtils#drawStringInRectangle(Graphics2D, Rectangle2D, String)
   */
  public Font fontToFitRectangle(Rectangle2D rectangle, double scaleFactor, String text, 
  		String fontName, int fontStyle, int minHeight) {
  	
		Font result = null;
		int height = (int)Math.round(rectangle.getHeight() * scaleFactor);
		if (height < minHeight) {
			return null;
		}
		else {
			result = new Font(fontName, fontStyle, height);  // Font with maximum height.
			double aspectRatio = getAspectRatio(result, text);
			if (aspectRatio * rectangle.getHeight() > rectangle.getWidth()) {  // Maximum height cannot be used, because string would be to wide then.
				height = (int)Math.round((rectangle.getWidth() / aspectRatio) * scaleFactor);
				if (height < minHeight) {
					return null;
				}
				else {
					result = new Font(fontName, fontStyle, height);
				}
			}
		}
		return result;
  }
}
