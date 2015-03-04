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
package info.bioinfweb.commons;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;



/**
 * Tool class that offers a set of methods generating random numbers and strings or performing
 * random operations on lists.
 * 
 * @author Ben St&ouml;ver
 */
public class RandomValues {
	private static final SecureRandom RANDOM = new SecureRandom();
	
	
	/**
	 * Returns an equally distributed random integer value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static int randInt(int min, int max) {
		return ((int)(Math.random() * (max - min))) + min;
	}
	
	
	/**
	 * Returns an equally distributed random long value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static long randLong(long min, long max) {
		return ((long)(Math.random() * (max - min))) + min;
	}

	
	/**
	 * Returns an equally distributed random float value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static float randFloat(float min, float max) {
		return ((float)(Math.random() * (max - min))) + min;
	}
	
	
	/**
	 * Returns an equally distributed random double value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static double randDouble(double min, double max) {
		return Math.random() * (max - min) + min;
	}
	
	
	/**
	 * Returns an equally distributed random {@link BigDecimal} value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static BigDecimal randBigDecimal(BigDecimal min, BigDecimal max) {
		return new BigDecimal(Math.random()).multiply(max.subtract(min)).add(min);
	}
	
	
	/**
	 * Returns an equally distributed random {@link BigInteger} value.
	 * 
	 * @param min - the lower border of the returned value
	 * @param max - the upper border of the returned value
	 * @return a value that is greater of equal to {@code min} and lower than {@code max}
	 */
	public static BigInteger randBigInteger(BigInteger min, BigInteger max) {
		return Math2.floorBigDecimalToBigInteger(new BigDecimal(Math.random()).multiply(new BigDecimal(max.subtract(min).toString()))).add(min);
	}
	
	
	public static BigInteger randBigInteger(int numberOfBits) {  //TODO comment
	  return new BigInteger(numberOfBits, RANDOM);
	}
	
	
	public static String randHexForBits(int numberOfBits) {  //TODO possibly rename and comment
		return String.format("%x", randBigInteger(numberOfBits)).toUpperCase();
	}
	
	
	/**
	 * Returns the string representation of a random hexadecimal value.
	 * <p>
	 * 
	 * @param length
	 * @return
	 */
	public static String randHexForChars(int length) {  //TODO possibly rename and finish comment
		return randChars("0123456789ABCDEF", length);
	}
	
	
	/**
	 * Generates a string consisting of a random sequence of the specified characters.
	 * <p>
	 * Each character contained in {@code chars} can be selected with equal probability
	 * for each position of the returned string. One character may also occur several
	 * times in the same return value.
	 * 
	 * @param chars - a string containing the set of characters that are allowed elements for
	 *        the returned character sequence
	 * @param length - the number of characters the returned string shall have
	 * @return a random string with the specified length
	 */
	public static String randChars(String chars, int length) {
		StringBuffer result = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			result.append(chars.charAt((int)(Math.random() * chars.length())));
		}
		return result.toString();
	}
	
	
	/**
	 * Swaps the elements of the list to get a random order.
	 * <p>
	 * This method is only efficient for lists that allow random access.
	 * 
	 * @param list - the list to be reordered
	 * @param count - the number of swaps to perform 
	 */
	public static <E>void listSwap(List<E> list, int count) {
		for (int i = 0; i < count; i++) {
			int pos1 = randInt(0, list.size());
			int pos2 = randInt(0, list.size());
			E element1 = list.get(pos1);
			list.set(pos1, list.get(pos2));
			list.set(pos2, element1);
		}
	}
}
