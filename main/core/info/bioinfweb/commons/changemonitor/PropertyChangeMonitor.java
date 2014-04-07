package info.bioinfweb.commons.changemonitor;


import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



/**
 * This class can be used to assign a {@link ChangeMonitor} to an object that accepts
 * {@code PropertyChangeListener}s.
 * 
 * @author Ben St&ouml;ver
 */
public class PropertyChangeMonitor extends ChangeMonitor implements PropertyChangeListener {
	private String propertyName;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param propertyName - the name of the property to listen to
	 */
	public PropertyChangeMonitor(String propertyName) {
		super();
		this.propertyName = propertyName;
	}


	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(e.getPropertyName());
		if (e.getPropertyName().equals(propertyName)) {
			registerChange();
		}
	}


	/**
	 * Returns the name of the property this monitor listens to.
	 * 
	 * @return the property name
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	
	public void addTo(Container container) {
		//container.addPropertyChangeListener(getPropertyName(), this);
		container.addPropertyChangeListener(this);
	}
}
