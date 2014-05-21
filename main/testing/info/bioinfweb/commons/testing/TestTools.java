package info.bioinfweb.commons.testing;


import static org.junit.Assert.* ;


import java.lang.reflect.Method;



/**
 * Class that provides tools for JUnit testing.
 * 
 * @author Ben St&ouml;ver
 */
public class TestTools {
	/**
	 * Returns an accessible private method.
	 * @param objectClass 
	 * @param name
	 * @param params
	 * @return
	 */
	public static Method getPrivateMethod(Class<? extends Object> objectClass, String name, Class... params) {
  	try {
  		Method method = objectClass.getDeclaredMethod(name, params);
      method.setAccessible(true);
      return method;
  	}
  	catch (NoSuchMethodException e) {
  		e.printStackTrace();
  		fail("Private method " + name + " not found.");
  		return null;
  	}
	}
	
	
  /**
   * Returns a string buffer containing the two's complement representation (consisting of 8 bits) of
   * a {@code byte} value
   * 
   * @param value - the value to be displayed
   * @return a string buffer with the length 8
   */
  public static StringBuffer toBinaryRepresentation(byte value) {
  	StringBuffer result = new StringBuffer(8);
  	for (int bit = 0; bit < 8; bit++) {
			result.append(value << bit >>> 7);
		}
  	return result;
  }
	
	
  /**
   * Returns a string buffer containing the two's complement representation (consisting of 16 bits) of
   * a {@code short} value
   * 
   * @param value - the value to be displayed
   * @return a string buffer with the length 16
   */
  public static StringBuffer toBinaryRepresentation(short value) {
  	StringBuffer result = new StringBuffer(16);
  	for (int bit = 0; bit < 16; bit++) {
			result.append(value << bit >>> 15);
		}
  	return result;
  }
	
	
  /**
   * Returns a string buffer containing the two's complement representation (consisting of 32 bits) of
   * an {@code int} value
   * 
   * @param value - the value to be displayed
   * @return a string buffer with the length 32
   */
  public static StringBuffer toBinaryRepresentation(int value) {
  	StringBuffer result = new StringBuffer(32);
  	for (int bit = 0; bit < 32; bit++) {
			result.append(value << bit >>> 31);
		}
  	return result;
  }
	
	
  /**
   * Returns a string buffer containing the two's complement representation (consisting of 64 bits) of
   * a {@code long} value
   * 
   * @param value - the value to be displayed
   * @return a string buffer with the length 64
   */
  public static StringBuffer toBinaryRepresentation(long value) {
  	StringBuffer result = new StringBuffer(64);
  	for (int bit = 0; bit < 64; bit++) {
			result.append(value << bit >>> 63);
		}
  	return result;
  }
}
