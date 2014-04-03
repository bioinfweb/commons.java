package info.bioinfweb.commons.junit;


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
}
