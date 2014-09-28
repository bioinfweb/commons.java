/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2014  Ben Stöver
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
package info.bioinfweb.commons.collections;


import java.io.File;
import java.util.TreeMap;



/**
 * Class that stores different objects by string keys. Methods that return theses values directly as basic java 
 * types are available. Instances of this class can e.g. be used to specify varying and extensible sets of 
 * parameters to methods without having to change their signature.
 * 
 * @author Ben St&ouml;ver
 */
public class ParameterMap extends TreeMap<String, Object> {
	/**
	 * Checks if an object is stored under the specified key. If so the result of {@link Object#toString()} is
	 * returned, otherwise <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return the string representation of the stored object or <code>defaultValue</code>
	 */
	public String getString(String key, String defaultValue) {
		Object result = get(key);
		if (result != null) {
			return result.toString();
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if an object is stored under the specified key. If so the result of {@link Object#toString()} is
	 * returned, otherwise <code>null</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @return the string representation of the stored object or <code>null</code>
	 */
	public String getString(String key) {
		return getString(key, null);
	}
	
	
	/**
	 * Checks if a {@link Float} object is stored under the specified key. (Objects of the types {@link Integer} or
	 * {@link Long} are converted.) If the stored object has another type or no object is found, 
	 * <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public float getFloat(String key, float defaultValue) {
		Object result = get(key);
		if (result instanceof Float) {
			return (Float)result;
		}
		else if (result instanceof Integer) {
			return ((Integer)result).floatValue();
		}
		else if (result instanceof Long) {
			return ((Long)result).floatValue();
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if a {@link Double} object is stored under the specified key.If the stored object has another numeric type,
	 * it is converted to <code>double</code>. If it has a non-numeric type or no object is found, 
	 * <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public double getDouble(String key, double defaultValue) {
		Object result = get(key);
		if (result instanceof Double) {
			return (Double)result;
		}
		else if (result instanceof Float) {
			return ((Float)result).doubleValue();
		}
		else if (result instanceof Integer) {
			return ((Integer)result).doubleValue();
		}
		else if (result instanceof Long) {
			return ((Long)result).doubleValue();
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if a {@link Integer} object is stored under the specified key. If the stored object has another type 
	 * or no object is found, <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public int getInteger(String key, int defaultValue) {
		Object result = get(key);
		if (result instanceof Integer) {
			return (Integer)result;
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if a {@link Long} object is stored under the specified key. (Objects of the types {@link Integer} are 
	 * converted.) If the stored object has another type or no object is found, <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public long getLong(String key, long defaultValue) {
		Object result = get(key);
		if (result instanceof Long) {
			return (Long)result;
		}
		else if (result instanceof Integer) {
			return ((Integer)result).longValue();
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if a {@link Boolean} object is stored under the specified key. If the stored object has another type 
	 * or no object is found, <code>defaultValue</code> is returned.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		Object result = get(key);
		if (result instanceof Boolean) {
			return (Boolean)result;
		}
		else {
			return defaultValue;
		}
	}

	
	/**
	 * Checks if a {@link File} object is stored under the specified key. If the stored object has another type 
	 * or no object is found, <code>defaultValue</code> is returned. (This method does not convert String objects
	 * into File objects, even if the string represents a valid file path.)
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return an appropriate object or <code>defaultValue</code>
	 */
	public File getFile(String key, File defaultValue) {
		Object result = get(key);
		if (result instanceof File) {
			return (File)result;
		}
		else {
			return defaultValue;
		}
	}
	
	
	/**
	 * Checks if a {@link File} object is stored under the specified key. If the stored object has another type 
	 * or no object is found, <code>null</code> is returned. (This method does not convert String objects
	 * into File objects, even if the string represents a valid file path.)
	 * 
	 * @param key - the key under which the result is stored
	 * @return an appropriate object or <code>null</code>
	 */
	public File getFile(String key) {
		return getFile(key, null);
	}
	
	
	/**
	 * Returns the stored object for the key or <code>defaultValue</code> if no object is found.
	 * 
	 * @param key - the key under which the result is stored
	 * @param defaultValue - the value to be returned, if no appropriate object is found
	 * @return the stored object or <code>defaultValue</code>
	 */
	public Object getObject(String key, Object defaultValue) {
		Object result = get(key);
		if (result != null) {
			return result;
		}
		else {
			return defaultValue;
		}
	}
} 
