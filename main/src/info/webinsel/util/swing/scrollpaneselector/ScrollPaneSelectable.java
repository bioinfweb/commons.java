package info.webinsel.util.swing.scrollpaneselector;

import java.awt.Graphics2D;



/**
 * Classes implementing this interface are able to paint their own preview thumbnail if they are 
 * positioned inside a <code>JScrollPane</code> which uses <code>ExetededScrollPaneSelector</code>.<br>
 * Although componenets which do not implement this interface can be previewed too, the advantige 
 * of is that the component is able to paint a more sharp preview or to highlight special elements. 
 * @author Ben St&ouml;ver
 */
public interface ScrollPaneSelectable {
  /**
   * This method is called by <code>ExetededScrollPaneSelector</code> to paint its preview field.
   * @param g - the graphics context
   * @param scale - the scale of the preview (the aspect ratio remains, painting starts at (0|0))
   */
  public void paintPreview(Graphics2D g, double scale);  
}
