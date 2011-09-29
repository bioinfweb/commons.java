package info.webinsel.util.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



/**
 * Util class for common tasks related to servlets.
 * 
 * @author Ben St&ouml;ver
 */
public class ServletUtils {
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
