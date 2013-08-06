package info.webinsel.util.swing;


import java.util.Map;
import java.util.TreeMap;

import javax.swing.Action;



/**
 * This class allows to store various {@link Action} objects in a {@link Map}
 * and access them with a unique {@link String}.
 * 
 * @author Ben St&ouml;ver
 */
public class ActionMap {
	private Map<String, Action> map = new TreeMap<String, Action>();
	
	
	protected Map<String, Action> getMap() {
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
}
