package info.bioinfweb.commons.swing;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;



/**
 * 
 * @author Ben St&ouml;ver
 */
public abstract class ExtendedAbstractAction extends AbstractAction {
	private String pathPrefix = "/resources/symbols/";
	
	
  /**
   * Constructs an instance of this class with the default path prefix {@code /resources/symbols/}.
   */
  public ExtendedAbstractAction() {
		super();
	}


  /**
   * Constructs an instance of this class with the specified path prefix.
   */
	public ExtendedAbstractAction(String pathPrefix) {
		super();
		this.pathPrefix = pathPrefix;
	}


	public String getPathPrefix() {
		return pathPrefix;
	}


	/**
   * Loads the small and large icon for this action from <i>/resources/symbols/</i> in the
   * the JAR file.
   * @param name - the prefix of the file name of the image files
   */
  protected void loadSymbols(String name) {
	  putValue(Action.SMALL_ICON, new ImageIcon(Object.class.getResource(getPathPrefix() + name + "16.png")));
	  putValue(Action.LARGE_ICON_KEY, new ImageIcon(Object.class.getResource(getPathPrefix() + name + "22.png")));
  }
}
