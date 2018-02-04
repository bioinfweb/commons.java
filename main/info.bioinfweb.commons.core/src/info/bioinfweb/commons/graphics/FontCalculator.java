/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.graphics;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;



public class FontCalculator {
	private static final int TEST_FONT_HEIGHT = 512;
	
	private static FontCalculator firstInstance = null;
	
	private final FontRenderContext frc = new FontRenderContext(null, true, true);
	private final Map<String, Float> sizeHeightRatioMap = new HashMap<String, Float>();
  
	
	private FontCalculator() {
		super();
	}
	
	
	public static FontCalculator getInstance() {
		if (firstInstance == null) {
			firstInstance = new FontCalculator();
		}
		return firstInstance;
	}
	
	
	private String getMapKey(String fontName, int style) {
		return fontName + "\t\t\t" + style;
	}
	
	
	public float getHeightSizeRatio(String fontName, int style) {
		String key = getMapKey(fontName, style);
		Float result = sizeHeightRatioMap.get(key);
		if (result == null) {
			Font font = new Font(fontName, style, TEST_FONT_HEIGHT);
			result = (float)(font.getStringBounds("Ög", frc).getHeight() / font.getSize2D());
			sizeHeightRatioMap.put(key, result);
		}
		return result;
	}
	
	
	public float getTextHeightByFontSize(float fontSize, String fontName, int style) {
		return getHeightSizeRatio(fontName, style) * fontSize;
	}
	
	
	public float getTextHeightByFontSize(Font font) {
		return getTextHeightByFontSize(font.getSize2D(), font.getFontName(), font.getStyle());
	}
	
	
	public float getFontSizeByTextHeight(float textHeight, String fontName, int style) {
		return textHeight / getHeightSizeRatio(fontName, style);
	}
	
	
	public float getDescent(Font font) {
		return new TextLayout("Ög", font, frc).getDescent();
	}
	
	
	public float getAscent(Font font) {
		return new TextLayout("Ög", font, frc).getAscent();
	}
	
	
	/**
	 * @deprecated As of release 2.2.0, replaced by {@link #getTextHeightByFontSize(Font)}.
	 */
	@Deprecated
	public float getHeight(Font font) {
		return (float)font.getStringBounds("Ög", frc).getHeight();
	}
	
	
	public float getWidth(Font font, String text) {
		if (text.length() > 0) {
			return (float)font.getStringBounds(text, frc).getWidth();
		}
		else {
			return 0f;
		}
	}
	
	
	/**
	 * Returns the width of the given text divided by the height. (Note that the text height and not the font size is used.)
	 * 
	 * @return the aspect ratio of the specified text using the specified font
	 */
	public float getAspectRatio(Font font, String text) {
		Rectangle2D r = font.getStringBounds(text, frc);
		return (float)(r.getWidth() / r.getHeight());
	}
	
	
	/**
	 * @deprecated As of release 2.2.0, replaced by {@link #getTextWidthToTextHeigth(String, int, String, float)}.
	 */
	@Deprecated
	public float getWidthToHeigth(String fontName, int fontStyle, String text, float height) {
		return getTextWidthToTextHeigth(fontName, fontStyle, text, height);
	}
	
	
	public float getTextWidthToTextHeigth(String fontName, int fontStyle, String text, float height) {
		return getTextWidthToFontSize(fontName, fontStyle, text, getFontSizeByTextHeight(height, fontName, fontStyle));
	}
	
	
	public float getTextWidthToFontSize(String fontName, int fontStyle, String text, float size) {
		if (text.length() > 0) {
			Font font = new Font(fontName, fontStyle, TEST_FONT_HEIGHT);
			return (float)(font.getStringBounds(text, frc).getWidth() * (size / font.getSize2D()));
		}
		else {
			return 0f;
		}
	}
	
	
	public float getDescentToHeight(String fontName, int fontStyle, float height) {  //TODO Replace method by versions that distinguish between font size and text height. 
		TextLayout tl = new TextLayout("Ög", new Font(fontName, fontStyle, TEST_FONT_HEIGHT), frc);
		return height * (tl.getDescent() / (tl.getDescent() + tl.getAscent()));
	}
	
	
	public float getAscentToHeight(String fontName, int fontStyle, float height) {  //TODO Replace method by versions that distinguish between font size and text height.
		TextLayout tl = new TextLayout("Ög", new Font(fontName, fontStyle, TEST_FONT_HEIGHT), frc);
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
