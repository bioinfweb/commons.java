package info.webinsel.util.collections;


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