package info.bioinfweb.commons.swing;


import info.bioinfweb.commons.io.Savable;

import java.awt.Component;
import javax.swing.JFileChooser;



/**
 * Interface for a SwingSaver. Useful if a Class has to delegate the SwingSaver-
 * functionality because of multiple ancestors.
 * @author Ben St&ouml;ver 
 **/
public interface SwingSavable extends Savable {
	public boolean askToSave(Component parentComponent);
	public boolean saveAs(Component parentComponent);
	public JFileChooser getFileChooser();
}
