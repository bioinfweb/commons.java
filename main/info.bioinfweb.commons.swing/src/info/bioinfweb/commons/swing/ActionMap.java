/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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
package info.bioinfweb.commons.swing;


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
