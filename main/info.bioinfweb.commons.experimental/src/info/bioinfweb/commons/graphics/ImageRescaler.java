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


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;



public class ImageRescaler {
  /**
   * Rescales an image so that is fits to the specified rectangle (without changing the
   * aspect ratio).
   * @param original - the original image
   * @param maxWidth - the width of the rectangle (the widht of the returned image
   *        will be smaller or equal to <code>maxWidth</code>) 
   * @param maxHeight - the height of the rectangle (the height of the returned image
   *        will be smaller or equal to <code>maxHeight</code>) 
   * @param enlarge - determines whether the image should be enlarged if it is smaller
   *        than the specified rectangle or if the original size shall be returned
   * @return the rescaled image
   */
  public static BufferedImage rescaleToRect(BufferedImage original, int maxWidth, 
  		int maxHeight, boolean enlarge) {
  	
  	double factor = Math.min(maxWidth / (double)original.getWidth(), 
  			maxHeight / (double)original.getHeight());
  	if (!enlarge && (factor >= 1)) {
  		factor = 1;
  	}
  	BufferedImage result = new BufferedImage(
  			(int)Math.round(original.getWidth() * factor), 
  			(int)Math.round(original.getHeight() * factor), BufferedImage.TYPE_INT_RGB);
  	AffineTransform transform = new AffineTransform();
  	transform.setToScale(factor, factor);
  	((Graphics2D)result.getGraphics()).drawImage(original, 
  			new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC), 
  			0, 0);
  	return result;
  }
}
