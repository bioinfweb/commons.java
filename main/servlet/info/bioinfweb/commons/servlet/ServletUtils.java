package info.bioinfweb.commons.servlet;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



/**
 * Utility class for common tasks related to servlets.
 * 
 * @author Ben St&ouml;ver
 */
public class ServletUtils {
	public static int getIntParameter(HttpServletRequest request, String name, 
			int defaultValue) {
		
		if (request.getParameter(name) != null) {
			try {
				return Integer.parseInt(request.getParameter(name));
			}
			catch (NumberFormatException e) {}
		}
		return defaultValue;
	}
	
	
	public static long getLongParameter(HttpServletRequest request, String name, 
			long defaultValue) {
		
		if (request.getParameter(name) != null) {
			try {
				return Long.parseLong(request.getParameter(name));
			}
			catch (NumberFormatException e) {}
		}
		return defaultValue;
	}
	
	
	public static boolean getBooleanParameter(HttpServletRequest request, String name, 
			boolean defaultValue) {
		
		if (request.getParameter(name) != null) {
			try {
				return Boolean.parseBoolean(request.getParameter(name));
			}
			catch (NumberFormatException e) {}
		}
		return defaultValue;
	}
	
	
  /**
   * Reads a set of integer values specified by GET or POST parameters. Non numeric values are skipped.
   */
  public static List<Integer> readIntParamList(HttpServletRequest request, String paramName) {
  	String[] params = request.getParameterValues(paramName);
  	if (params == null) {
  		return new ArrayList<Integer>();
  	}
  	else {
	  	List<Integer> result = new ArrayList<Integer>(params.length);
	  	for (int i = 0; i < params.length; i++) {
				try {
					result.add(Integer.parseInt(params[i]));
				}
				catch (NumberFormatException e) {} // skip invalid values
			}
	  	return result;
  	}
  }
}
