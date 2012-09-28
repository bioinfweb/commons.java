package info.webinsel.util.swing;


import java.util.HashMap;

import javax.swing.Action;



/**
 * This class allows to store varius <code>Action</code> objects in a <code>HashMap</code>
 * and acces them with a unique <code>String</code>.
 * @author Ben St&ouml;ver
 */
public abstract class ActionHashMap {
	private HashMap<String, Action> map = new HashMap<String, Action>();
	
	
	protected HashMap<String, Action> getMap() {
		return map;
	}
	
	
	public void clear() {
		map.clear();
	}


	public Action get(Object name) {
		return map.get(name);
	}


	public boolean isEmpty() {
		return map.isEmpty();
	}


	public Action put(String name, Action action) {
		return map.put(name, action);
	}


	public Action remove(Object name) {
		return map.remove(name);
	}


	public int size() {
		return map.size();
	}


	public abstract void refreshActionStatus();
}
