package info.bioinfweb.commons;


import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



/**
 * This class can be used to assign a ChangeMonitor to an object that accepts 
 * <code>PropertyChangeListener</code>s.
 * @author Ben St&ouml;ver
 */
public class PropertyChangeMonitor extends ChangeMonitor implements PropertyChangeListener {
	private String propertyName;
	
	
	/**
	 * @param propertyName - the name of the property to listen to
	 */
	public PropertyChangeMonitor(String propertyName) {
		super();
		this.propertyName = propertyName;
	}


	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(e.getPropertyName());
		if (e.getPropertyName().equals(propertyName)) {
			registerChange();
		}
	}


	public String getPropertyName() {
		return propertyName;
	}
	
	
	public void addTo(Container container) {
		//container.addPropertyChangeListener(getPropertyName(), this);
		container.addPropertyChangeListener(this);
	}
}
