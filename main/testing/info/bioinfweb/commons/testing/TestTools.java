/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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
package info.bioinfweb.commons.testing;


import static org.junit.Assert.* ;

import java.lang.reflect.Field;
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
  		return null;  // unreachable code
  	}
	}
	
	
	public static Field getPrivateField(Class<? extends Object> objectClass, String name) {
		try {
			Field field = objectClass.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
			fail("Private field " + name + " not found.");
			return null;  // unreachable code
		}
	}
	
	
	public static Object getPrivateFieldValue(Object object, String name) {
		try {
			Field field = getPrivateField(object.getClass(), name);
			return field.get(object);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("The private field " + name + " is not accesible.");
			return null;  // unreachable code
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
