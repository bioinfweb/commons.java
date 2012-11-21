package info.webinsel.util.graphics;


import java.awt.Color;


/**
 * Contains method that implement general graphic tasks.
 * @author Ben St&ouml;ver
 */
public class GraphicsUtils {
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
}
