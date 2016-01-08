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
package info.bioinfweb.commons.text;


import java.text.NumberFormat;
import java.util.ArrayList;
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
  
  
  /**
   * Checks if the specified character is a new line character
   * 
   * @param c the character to be tested
   * @return {@code true} if {@code c} is either {@code '\n'} or {@code '\r'}, {@code false} otherwise.  
   */
  public static boolean isNewLineChar(char c) {
  	return (c == '\n') || (c == '\r');
  }
  
  
  /**
   * Returns the first index of any whitespace character.
   * 
   * @param sequence the character sequence to be searched
   * @return the index of the first white space character (as defined by {@link Character#isWhitespace(char)}) 
   */
  public static int indexOfWhiteSpace(CharSequence sequence) {
  	return indexOfWhiteSpace(sequence, 0);
  }
  
  
  /**
   * Returns the first index of any whitespace character.
   * 
   * @param sequence the character sequence to be searched
   * @param fromIndex the index to start the search
   * @return the index of the first white space character (as defined by {@link Character#isWhitespace(char)}) 
   *         at or after {@code fromIndex}  
   */
  public static int indexOfWhiteSpace(CharSequence sequence, int fromIndex) {
  	for (int i = fromIndex; i < sequence.length(); i++) {
			if (Character.isWhitespace(sequence.charAt(i))) {
				return i;
			}
		}
  	return -1;
  }
  
  
  /**
   * Tests whether a character sequence ends with a specified character.
   * 
   * @param sequence the sequence to be tested
   * @param terminalSymbol the expected last character of the sequence
   * @return {@code true} if the sequence is at least one character long and last character is equal to
   *         {@code terminalSymbol}, {@code false} otherwise. 
   */
  public static boolean endsWith(CharSequence sequence, char terminalSymbol) {
  	return (sequence.length() == 0) || (sequence.charAt(sequence.length() - 1) == terminalSymbol);
  }
  
  
  /**
   * Cuts off the specified number of characters from the end of the specified character sequence.
   * <p>
   * If {@code length} is higher than the length of {@code sequence} an empty string is returned.
   * 
   * @param sequence the character sequence to be cut
   * @param length the number of characters to be removed from the end
   * @return the shorter sequence
   */
  public static CharSequence cutEnd(CharSequence sequence, int length) {
  	if (length > sequence.length()) {
  		return "";
  	}
  	else {
  		return sequence.subSequence(0, sequence.length() - length);
  	}
  }
  
  
  /**
   * Cuts off the specified number of characters from the end of the specified string.
   * <p>
   * If {@code length} is higher than the length of {@code sequence} an empty string is returned.
   * 
   * @param sequence the string to be cut
   * @param length the number of characters to be removed from the end
   * @return the shorter string
   */
  public static String cutEnd(String sequence, int length) {
  	if (length > sequence.length()) {
  		return "";
  	}
  	else {
  		return sequence.substring(0, sequence.length() - length);
  	}
  }
  
  
  /**
   * Returns the last character of the specified sequence.
   * 
   * @param sequence the character sequence to extract the last character from
   * @return the last character of the sequence
   * @throws IllegalArgumentException if {@code sequence} is not at least one character long
   */
  public static char lastChar(CharSequence sequence) {
  	if (sequence.length() == 0) {
  		throw new IllegalArgumentException("The specified character sequence needs to be at least one character long.");
  	}
  	else {
  		return sequence.charAt(sequence.length() - 1);
  	}
  }
  
  
  /**
   * Converts the specified character sequence to a list strings, where each character of the sequence is 
   * represented as a single string object.
   * 
   * @param sequence the sequence to be converted
   * @return the resulting list of strings
   * @since 2.0.0
   */
  public static List<String> charSequenceToStringList(CharSequence sequence) {
  	List<String> result = new ArrayList<String>();
  	for (int i = 0; i < sequence.length(); i++) {
			result.add(Character.toString(sequence.charAt(i)));
		}
  	return result;
  }
}
