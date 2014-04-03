package info.bioinfweb.commons;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Pattern;



public class Math2 {
	public static final Pattern INT_PATTERN = Pattern.compile("-?\\d+");
	
	
  public static double log(double a, double base) {
  	return Math.log(a) / Math.log(base);
  }
  
  
  public static int intPow(int basis, int exponent) {
  	if (exponent < 0) {
      throw new IllegalArgumentException("The exponent must be greater or equal to zero (" + 
      		exponent + ").");
  	}
  	else {
  		int result = 1;
  		for (int i = 0; i < exponent; i++) {
				result *= basis;
			}
  		return result;
  	}
  }
  
  
  public static int divAbove(int divided, int divisor) {
	  int result = divided / divisor;
	  if (divided % divisor > 0) {
		  result++;
	  }
	  return result;
  }
	
	
	public static boolean isInt(String value) {
  	return INT_PATTERN.matcher(value).matches();
  }

  
  /**
   * Returns the whether the given string can be parsed to a <code>double</code> or 
   * <code>float</code>.
   * @param value - the string to be parsed
   * @return <code>true</code> if the given string could be parsed
   */
  public static boolean isDecimal(String value) {
  	boolean result = true;
  	try {
  		parseDouble(value);
  	}
  	catch (NumberFormatException e) {
  		result = false;
  	}
  	return result;
  }
  
  
  public static int minInt(int... values) {
  	if (values.length >= 2) {
  		int result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.min(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static long minLong(long... values) {
  	if (values.length >= 2) {
  		long result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.min(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static float minFloat(float... values) {
  	if (values.length >= 2) {
  		float result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.min(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static double minDouble(double... values) {
  	if (values.length >= 2) {
  		double result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.min(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static int maxInt(int... values) {
  	if (values.length >= 2) {
  		int result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.max(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static long maxLong(long... values) {
  	if (values.length >= 2) {
  		long result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.max(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static float maxFloat(float... values) {
  	if (values.length >= 2) {
  		float result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.max(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static double maxDouble(double... values) {
  	if (values.length >= 2) {
  		double result = values[0];
  		for (int i = 1; i < values.length; i++) {
				result = Math.max(result, values[i]);
			}
  		return result;
  	}
  	else {
  		throw new IllegalArgumentException("This function must have at least two parameters.");
  	}
  }

  
  public static boolean isBetween(byte value, byte min, byte max) {
  	return ((value >= min) && (value <= max));
  }
  
  
  public static boolean isBetween(int value, int min, int max) {
  	return ((value >= min) && (value <= max));
  }
  
  
  public static boolean isBetween(long value, long min, long max) {
  	return ((value >= min) && (value <= max));
  }
  
  
  public static boolean isBetween(float value, float min, float max) {
  	return ((value >= min) && (value <= max));
  }
  
  
  public static boolean isBetween(double value, double min, double max) {
  	return ((value >= min) && (value <= max));
  }
  
  
  public static boolean isBetweenNE(byte value, byte min, byte max) {
  	return ((value > min) && (value < max));
  }
  
  
  public static boolean isBetweenNE(int value, int min, int max) {
  	return ((value > min) && (value < max));
  }
  
  
  public static boolean isBetweenNE(long value, long min, long max) {
  	return ((value > min) && (value < max));
  }
  
  
  public static boolean isBetweenNE(float value, float min, float max) {
  	return ((value > min) && (value < max));
  }
  
  
  public static boolean isBetweenNE(double value, double min, double max) {
  	return ((value > min) && (value < max));
  }
  
  
  public static int moveBetween(int value, int min, int max) {
  	return Math.min(max, Math.max(min, value));
  }
  
  
  public static long moveBetween(long value, long min, long max) {
  	return Math.min(max, Math.max(min, value));
  }
  
  
  public static float moveBetween(float value, float min, float max) {
  	return Math.min(max, Math.max(min, value));
  }
  
  
  public static boolean overlaps(byte min1, byte max1, byte min2, byte max2) {
  	return isBetween(min1, min2, max2) || isBetween(max1, min2, max2) || isBetween(min2, min1, max1) || isBetween(max2, min1, max1);
  }
  
  
  public static boolean overlaps(int min1, int max1, int min2, int max2) {
  	return isBetween(min1, min2, max2) || isBetween(max1, min2, max2) || isBetween(min2, min1, max1) || isBetween(max2, min1, max1);
  }
  
  
  public static boolean overlaps(long min1, long max1, long min2, long max2) {
  	return isBetween(min1, min2, max2) || isBetween(max1, min2, max2) || isBetween(min2, min1, max1) || isBetween(max2, min1, max1);
  }
  
  
  public static boolean overlaps(float min1, float max1, float min2, float max2) {
  	return isBetween(min1, min2, max2) || isBetween(max1, min2, max2) || isBetween(min2, min1, max1) || isBetween(max2, min1, max1);
  }
  
  
  public static boolean overlaps(double min1, double max1, double min2, double max2) {
  	return isBetween(min1, min2, max2) || isBetween(max1, min2, max2) || isBetween(min2, min1, max1) || isBetween(max2, min1, max1);
  }
  
  
  public static boolean overlapsNE(byte min1, byte max1, byte min2, byte max2) {
  	return ((min1 >= min2) && (min1 < max2)) || ((max1 > min2) && (max1 <= max2)) || 
           ((min2 >= min1) && (min2 < max1)) || ((max2 > min1) && (max2 <= max1));
  }
  
  
  public static boolean overlapsNE(int min1, int max1, int min2, int max2) {
  	return ((min1 >= min2) && (min1 < max2)) || ((max1 > min2) && (max1 <= max2)) || 
           ((min2 >= min1) && (min2 < max1)) || ((max2 > min1) && (max2 <= max1));
  }
  
  
  public static boolean overlapsNE(long min1, long max1, long min2, long max2) {
  	return ((min1 >= min2) && (min1 < max2)) || ((max1 > min2) && (max1 <= max2)) || 
           ((min2 >= min1) && (min2 < max1)) || ((max2 > min1) && (max2 <= max1));
  }
  
  
  public static boolean overlapsNE(float min1, float max1, float min2, float max2) {
  	return isBetweenNE(min1, min2, max2) || isBetweenNE(max1, min2, max2) || 
        isBetweenNE(min2, min1, max1) || isBetweenNE(max2, min1, max1);
  }
  
  
  public static boolean overlapsNE(double min1, double max1, double min2, double max2) {
  	return ((min1 >= min2) && (min1 < max2)) || ((max1 > min2) && (max1 <= max2)) || 
           ((min2 >= min1) && (min2 < max1)) || ((max2 > min1) && (max2 <= max1));
  }
  
  
  public static long roundUp(double value) {
  	long result = (long)value;
  	if ((double)result < value) {
  		result++;
  	}
  	return result;
  }
  
  
  public static int roundUp(float value) {
  	int result = (int)value;
  	if ((float)result < value) {
  		result++;
  	}
  	return result;
  }
  
  
  public static BigDecimal roundBigDecimal(BigDecimal value, RoundingMode roundingMode) {
  	return value.round(new MathContext(0, roundingMode));
  }
  
  
  public static BigDecimal floorBigDecimal(BigDecimal value) {
  	return roundBigDecimal(value, RoundingMode.FLOOR);
  }
  
  
  public static BigInteger roundBigDecimalToBigInteger(BigDecimal value, RoundingMode roundingMode) {
  	return new BigInteger(roundBigDecimal(value, roundingMode).toString());
  }
  
  
  public static BigInteger floorBigDecimalToBigInteger(BigDecimal value) {
  	return new BigInteger(roundBigDecimal(value, RoundingMode.FLOOR).toString());
  }
  
  
  /**
   * Rounds a double value at the given significant digit.<br />
   * <br />
   * <b>Examples:</b><br />
   * <ul>
   *   <li><code>roundSignificantDigit(12.4, 0)</code> would return <i>12</i></li>
   *   <li><code>roundSignificantDigit(12.4, 1)</code> would return <i>10</i></li>
   *   <li><code>roundSignificantDigit(0.03462, -3)</code> would return <i>0.035</i></li>
   * </ul>
   * 
   * @param value - the value to be rounded
   * @param digit - the decimal power to be rounded to
   * @return the rounded value
   */
  public static double roundSignificantDigit(double value, int digit) {
  	double digitValue = Math.pow(10, digit);
  	double rest = value % digitValue;
  	return value = value - rest + Math.round(rest / digitValue) * digitValue;
  }
  
  
  /**
   * Rounds a float value at the given significant digit.<br />
   * <br />
   * <b>Examples:</b><br />
   * <ul>
   *   <li><code>roundSignificantDigit(12.4, 0)</code> would return <i>12</i></li>
   *   <li><code>roundSignificantDigit(12.4, 1)</code> would return <i>10</i></li>
   *   <li><code>roundSignificantDigit(0.03462, -3)</code> would return <i>0.035</i></li>
   * </ul>
   * 
   * @param value - the value to be rounded
   * @param digit - the decimal power to be rounded to
   * @return the rounded value
   */
  public static float roundSignificantDigit(float value, int digit) {
  	float digitValue = (float)Math.pow(10, digit);
  	float rest = value % digitValue;
  	return value = value - rest + Math.round(rest / digitValue) * digitValue;
  }
  
  
  /**
   * Rounds a double value at the given first significant digit.<br />
   * <br />
   * <b>Examples:</b><br />
   * <ul>
   *   <li><code>roundSignificantDigit(79.0)</code> would return <i>80.0</i></li>
   *   <li><code>roundSignificantDigit(12.0)</code> would return <i>10.0</i></li>
   *   <li><code>roundSignificantDigit(0.03462)</code> would return <i>0.03</i></li>
   * </ul>
   * Calling this method is equivalent to 
   * <code>roundSignificantDigit(value, (int)Math.floor(Math.log10(value)))</code>.
   * 
   * @param value - the value to be rounded
   * @param digit - the decimal power to be rounded to
   * @return the rounded value
   */
  public static double roundFirstSignificantDigit(double value) {
  	return roundSignificantDigit(value, (int)Math.floor(Math.log10(value)));
  }
  
  
  /**
   * Rounds a float value at the given first significant digit.<br />
   * <br />
   * <b>Examples:</b><br />
   * <ul>
   *   <li><code>roundSignificantDigit(79.0)</code> would return <i>80.0</i></li>
   *   <li><code>roundSignificantDigit(12.0)</code> would return <i>10.0</i></li>
   *   <li><code>roundSignificantDigit(0.03462)</code> would return <i>0.03</i></li>
   * </ul>
   * Calling this method is equivalent to 
   * <code>roundSignificantDigit(value, (int)Math.floor(Math.log10(value)))</code>.
   * 
   * @param value - the value to be rounded
   * @param digit - the decimal power to be rounded to
   * @return the rounded value
   */
  public static float roundFirstSignificantDigit(float value) {
  	return roundSignificantDigit(value, (int)Math.floor(Math.log10(value)));
  }
  
  
  /**
   * Parses a float value which can have either "," or "." as its decimal separator. The
   * string must not contain thousand separators.
   * @param string the string to parse
   * @return the parsed float value
   */
  public static float parseFloat(String string) {
  	return Float.parseFloat(string.replace(',', '.'));
  }

  
  /**
   * Parses a double value which can have either "," or "." as its decimal separator. The
   * string must not contain thousand separators. (Distinguishing between "1,236" as 1236 in English and 
   * "1,236" as 1.236 in German is not possible.)
   * 
   * @param string - the string to parse
   * @return the parsed double value
   * @throws NumberFormatException - if the specified string does not represent a valid decimal value 
   */
  public static double parseDouble(String string) {
  	return Double.parseDouble(string.replace(',', '.'));
  }
  
  
  public static String DecimalToString(double decimal, int decimalPlaceCount) {
    if (decimalPlaceCount > 0) {
	  	decimal *= Math.pow(10, decimalPlaceCount);
	  	String result = "" + (long)Math.round(decimal);
	  	if (result.equals("0")) {
	  		result = "0.";
	  		for (int i = 1; i <= decimalPlaceCount; i++) {
					result += "0";
				}
	  		return result;
	  	}
	  	else {
	  		int separatorPos = result.length() - decimalPlaceCount;
	  		if (separatorPos == 0) {
	  			return "0." + result;
	  		}
	  		else {
	  			return result.substring(0, separatorPos) + "." + result.substring(separatorPos, result.length());
	  		}
	  	}
  	}
  	else {
	  	return "" + (long)Math.round(decimal);
  	}
  }
  
  
  /**
   * Returns the sum of all integers from 1 to the specified parameter. 
   * @param n - the value to sum up to (must be greater or equal to zero)
   * @throws ArithmeticException - if <code>n</code> is smaller than zero
   */
  public static int sum1ToN(int n) {
  	if (n < 0) {
  		throw new ArithmeticException("n must be greater of equal to zero (" + n + ")");
  	}
  	else {
  		return (n * (n + 1)) / 2;
  	}
  }
}
