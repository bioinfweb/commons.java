package info.webinsel.util.swing;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;



/**
 * 
 * @author Ben St&ouml;ver
 */
public abstract class ExtendedAbstractAction extends AbstractAction {
  /**
   * Loads the small and large icon for this action from <i>/resources/symbols/</i> in the
   * the JAR file.
   * @param name - the prefix of the file name of the image files
   */
  protected void loadSymbols(String name) {
	  putValue(Action.SMALL_ICON, new ImageIcon(Object.class.getResource("/resources/symbols/" + name + "16.png")));
	  putValue(Action.LARGE_ICON_KEY, new ImageIcon(Object.class.getResource("/resources/symbols/" + name + "22.png")));
  }
}
