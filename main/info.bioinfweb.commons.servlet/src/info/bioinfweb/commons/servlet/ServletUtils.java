/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
