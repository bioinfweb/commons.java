package info.webinsel.util.sql;

import java.util.List;



/**
 * Util class for common tasks related to SQL operations.
 * 
 * @author Ben St&ouml;ver
 */
public class SQLUtils {
  public static String createIntListCond(String columnName, List<Integer> values, String operator) {
  	String result = "";
  	if (values.size() > 0) {
	  	for (int i = 0; i < values.size(); i++) {
				result += columnName + " = " + values.get(i) + " " + operator + " ";
			}
	  	result = "(" + result.substring(0, result.length() - 2 - operator.length()) + ")";
  	}
  	return result;
  }
  
  
	public static String addLeadingKeyword(String cond, String keyword) {
		if (!"".equals(cond)) {
			cond = " " + keyword + " " + cond;
		}
		return cond;
	}
  
  
	public static String addTrailingKeyword(String cond, String keyword) {
		if (!"".equals(cond)) {
			cond += " " + keyword + " ";
		}
		return cond;
	}
}
