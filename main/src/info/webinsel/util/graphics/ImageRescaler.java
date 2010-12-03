package info.webinsel.util.graphics;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;



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
