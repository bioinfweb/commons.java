package info.bioinfweb.commons.text;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



/**
 * Provides general methods to use with character sequences. 
 * @author Ben St&ouml;ver
 */
public class StringUtils {
	public static final NumberFormat DOUBLE_FORMAT = NumberFormat.getNumberInstance(Locale.getDefault());
	public static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance(Locale.getDefault());  //new DecimalFormat("#########");
	
	
  public static String invert(CharSequence s)  {
  	StringBuffer result = new StringBuffer(s.length());
  	for (int i = 0; i < s.length(); i++) {
			result.append(s.charAt(s.length() - i - 1));
		}
  	return result.toString();
  }
  
  
  public static String repeat(CharSequence element, int count) {
  	StringBuffer result = new StringBuffer(element.length() + count);
  	for (int i = 0; i < count; i++) {
			result.append(element);
		}
  	return result.toString();
  }
  
  
  public static void renameRepeatedEntries(String[] array) {
  	HashMap<String, Integer> counts = new HashMap<String, Integer>();
  	for (int i = 0; i < array.length; i++) {
  		String id = array[i];
			Integer count = counts.get(id);
			if (count == null) {
				count = 1;
			}
			else {
				count++;
				array[i] = id + " (" + count + ")";
			}
			counts.put(id, count);
		}
  }
  
  
  public static void renameRepeatedEntries(List<String> list) {
  	HashMap<String, Integer> counts = new HashMap<String, Integer>();
  	for (int i = 0; i < list.size(); i++) {
  		String id = list.get(i);
			Integer count = counts.get(id);
			if (count == null) {
				count = 1;
			}
			else {
				count++;
				list.set(i, id + " (" + count + ")");
			}
			counts.put(id, count);
		}
  }
  
  
  /**
   * <p>Concatenates two expressions with an operator if both are not empty.</p>
   * <p><b>Example:</b></p>
   * <ul>
   *   <li>
   *     <code>concatWithOperator("A", "B", " + ", "(", ")")</code> would return 
   *     "<i>(A + B)</i>". 
   *   </li>
   *   <li>
   *     <code>concatWithOperator("A", "", " + ", "(", ")")</code> would return 
   *     "<i>A</i>". 
   *   </li>
   * </ul>
   *  
   * @param leftPart - the left expression
   * @param rightPart - the right expression 
   * @param operator - the operator (including white spaces if necessary)
   * @param leftBracket - the left bracket to be used (e.g. "{")
   * @param rightBracket - the right bracket to be used (e.g. "}")
   * @return the concatenated expression
   */
  public static String concatWithOperator(String leftPart, String rightPart, 
  		String operator, String leftBracket, String rightBracket) {
  	
  	String result;
		if (!leftPart.equals("") && !rightPart.equals("")) {
			result = leftBracket + leftPart + operator + rightPart + rightBracket;
		}
		else {
			result = leftPart + rightPart;  // at least one is ""
		}
    return result;
  }
  
  
  /**
   * Concatenates two expressions with an operator if both are not empty.
   * 
   * @param leftPart - the left expression
   * @param rightPart - the right expression 
   * @param operator - the operator (including white spaces if necessary)
   * @return the concatenated expression
   * 
   * @see StringUtils#concatWithOperator(String, String, String, String, String)
   */
  public static String concatWithOperator(String leftPart, String rightPart, 
  		String operator) {
  	
  	return concatWithOperator(leftPart, rightPart, operator, "", "");
  }
  
  
  public static String firstCharToLowerCase(String text) {
  	if (text.length() > 1) {
  		return Character.toLowerCase(text.charAt(0)) + text.substring(1);
  	}
  	else {
  		return text.toLowerCase();
  	}
  }
  
  
  public static String firstCharToUpperCase(String text) {
  	if (text.length() > 1) {
  		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
  	}
  	else {
  		return text.toUpperCase();
  	}
  }
  
  
  /**
   * Converts a camel case keyword to a string with the specified separator where all characters are in lower case.<br />
   * Example: <code>anExampleKeyword</code> would be converted to <code>an-example-keyword</code> and <code>A</code> 
   * to <code>a</code>.
   * @param text - the string to be converted
   * @param separator - the separator to be inserted
   * @return the converted keyword string
   */
  public static String convertCamelCase(String text, String separator) {
  	if (text.length() > 1) {
  		StringBuffer result = new StringBuffer(text.length() * (separator.length() + 1) / 5);  // Average length of 5 characters per word expected
  		result.append(Character.toLowerCase(text.charAt(0)));
  		for (int i = 1; i < text.length(); i++) {
				if (Character.isUpperCase(text.charAt(i))) {
					result.append(separator);
				}
				result.append(Character.toLowerCase(text.charAt(i)));
			}
    	return result.toString();
  	}
  	else {
  		return text.toLowerCase();
  	}
  }
}
