package info.bioinfweb.commons.log;


import info.bioinfweb.commons.collections.ParameterMap;



public class ApplicationLoggerParameterMap extends ParameterMap {
	public static final String KEY_APPLICATION_LOGGER = 
			"info.webinsel.util.log.ApplicationLogger_ApplicationLoggerParameterMap";
	
	
	/**
	 * Returns a stored {@link ApplicationLogger} object or a new instance of {@link VoidApplicationLogger} 
	 * if no appropriate object is stored.
	 * 
	 * @param key - the key under which the result is stored
	 * @return an appropriate object or a new instance of {@link VoidApplicationLogger}
	 */
	public ApplicationLogger getApplicationLogger() {
		Object result = get(KEY_APPLICATION_LOGGER);
		if (result instanceof ApplicationLogger) {
			return (ApplicationLogger)result;
		}
		else {
			return new VoidApplicationLogger();
		}
	}
	
	
	public void putApplicationLogger(ApplicationLogger logger) {
		put(KEY_APPLICATION_LOGGER, logger);
	}
	
	
	public void removeApplicationLogger() {
		Object value = get(KEY_APPLICATION_LOGGER);
		if (value != null) {
			remove(value);
		}
	}
}
