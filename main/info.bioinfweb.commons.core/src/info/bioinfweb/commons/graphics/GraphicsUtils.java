/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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


import info.bioinfweb.commons.Math2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


/**
 * Contains method that implement general graphic tasks.
 * @author Ben St&ouml;ver
 */
public class GraphicsUtils {
	/**
	 * Returns the according gray scale value to the specified color. That value is calculated as the arithmetic
	 * mean if the red, green and blue color values.
	 * 
	 * @param color - the color to be converted
	 * @return an integer between 0 and 255
	 * @see #rgbToGrayColor(Color)
	 */
	public static int rgbToGrayValue(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	
	/**
	 * Returns the according gray scale color to the specified color. The values for all channels are calculated as 
	 * the arithmetic mean of the red, green and blue color values of the specified color. The alpha channel is 
	 * preserved.
	 * 
	 * @param color - the color to be converted
	 * @return a color where all color channels have an equal value
	 * @see #rgbToGrayValue(Color)
	 */
	public static Color rgbToGrayColor(Color color) {
		int gray = rgbToGrayValue(color);
    return new Color(gray, gray, gray, color.getAlpha());
	}
	
	
	/**
	 * Multiplies all color channels of the specified color with the specified value. (The alpha channel is preserved).
	 * Channel values will never become higher than 255.
	 * <p>
	 * This method can be used as an alternative to {@link Color#brighter()} and {@link Color#darker()}n which
	 * always multiply with a constant factor.
	 * 
	 * @param color - the color to changed
	 * @param factor - the factor to be multiplied with the channel values
	 * @return the changed color
	 * 
	 * @see GraphicsUtils#moveColorToCenter(Color, float)
	 */
	public static Color multiplyColorChannels(Color color, float factor) {
		return new Color(
				Math2.moveBetween(Math.round(color.getRed() * factor), 0, 255), 
				Math2.moveBetween(Math.round(color.getGreen() * factor), 0, 255), 
				Math2.moveBetween(Math.round(color.getBlue() * factor), 0, 255), 
				color.getAlpha());
	}
	
	
	/**
	 * Adds a constant value to all color channels of the specified color. (The alpha channel is preserved).
	 * <p>
	 * Channel values will never become higher than 255 or lower than 0.  
	 * 
	 * @param color the color to changed
	 * @param addend the value to be add to all color channels
	 * @return the changed color
	 */
	public static Color addToColorChannels(Color color, int addend) {
		return new Color(
				Math2.moveBetween(color.getRed() + addend, 0, 255), 
				Math2.moveBetween(color.getGreen() + addend, 0, 255), 
				Math2.moveBetween(color.getBlue() + addend, 0, 255), 
				color.getAlpha());
	}
	
	
	/**
	 * The result of the method depends on the specified color. If the gray value of {@code color} is greater than
	 * 127 all color channels are multiplied by {@code factor}. If the gray value is lower all color channels are
	 * divided by {@code factor}. The alpha channel is not changed.
	 * <p>
	 * Channel values will never become higher than 255 or lower than 0.  
	 * 
	 * @param color - the color to be modified
	 * @param factor - the scale factor for the color channels
	 * @return the modified color
	 * 
	 * @see #multiplyColorChannels(Color, float)
	 */
	public static Color moveColorToCenter(Color color, float factor) {
		if (rgbToGrayValue(color) > 127) {
			factor = 1f / factor;
		}
		return multiplyColorChannels(color, factor);
	}
	
	
  /**
   * Returns the sum of the absolute differences the three channels of the two colors.
   * @param c1
   * @param c2
   * @return
   */
  public static int brightnessDifference(Color c1, Color c2) {
  	return Math.abs(c1.getBlue() - c2.getBlue()) + 
  			Math.abs(c1.getRed() - c2.getRed()) + Math.abs(c1.getGreen() - c2.getGreen()); 
  }
  
  
  public static Color invertColor(Color color) {
  	return new Color(-(color.getRed() - 255), -(color.getGreen() - 255), 
  			-(color.getBlue() - 255));
  }
  
  
  /**
   * Returns the specified color if it differs enough from the given background color.
   * Otherwise the inverted color is returned.
   * 
   * @param color - the designated foreground color
   * @param background - the background color
   * @param minDifference - the minimal difference the two colors must have so that the
   *        designated color is returned 
   * @return the given color or its inversion
   */
  public static Color getContrastColor(Color color, Color background, int minDifference) {
  	if (brightnessDifference(color, background) < minDifference) {
  		return invertColor(color);
  	}
  	else {
  		return color;
  	}
  }
  
  
  /**
   * Blends different colors. (Each color adds the same percentage to the resulting color.)
   *  
   * @param colors an array of colors (should contain at least two elements) 
   * @return the blended color or black if an empty array was specified
   */
  public static Color blend(Color... colors) {
  	int r = 0;
  	int g = 0;
  	int b = 0;
  	for (int i = 0; i < colors.length; i++) {
			r += colors[i].getRed();
			g += colors[i].getGreen();
			b += colors[i].getBlue();
		}
  	return new Color(r / colors.length, g / colors.length, b / colors.length);
  }
  
  
  public static String colorToHexString(Color color) {
  	return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
  }
  
  
  /**
   * Draws the specified string into the center of the specified rectangle.
   * 
   * @param g the graphics context to paint to
   * @param rectangle the rectangle to fit the text into
   * @param text the text to be drawn
   * @see FontCalculator#fontToFitRectangle(Rectangle2D, double, String, String, int, int)
   */
  public static void drawStringInRectangle(Graphics2D g, Rectangle2D rectangle, String text) {
		FontMetrics fm = g.getFontMetrics();
  	g.drawString(text, (int)Math.round(rectangle.getX() + 0.5 * (rectangle.getWidth() - fm.stringWidth(text))), 
  			(int)Math.round(rectangle.getY() + 0.5 * (rectangle.getHeight() - fm.getHeight()) + fm.getAscent()));  // int needs to be used because the precision of float is not sufficient to paint to high coordinate space
  }
}
