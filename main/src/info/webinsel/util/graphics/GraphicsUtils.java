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
   * Returns the specified color if differs enough from the given background color.
   * Otherwise the inverted color is returned.
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
}
