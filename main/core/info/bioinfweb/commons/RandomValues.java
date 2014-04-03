package info.bioinfweb.commons;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;



public class RandomValues {
	private static final SecureRandom random = new SecureRandom();
	
	
	public static int randInt(int min, int max) {
		return ((int)(Math.random() * (max - min))) + min;
	}
	
	
	public static long randLong(long min, long max) {
		return ((long)(Math.random() * (max - min))) + min;
	}

	
	public static float randFloat(float min, float max) {
		return ((float)(Math.random() * (max - min))) + min;
	}
	
	
	public static double randDouble(double min, double max) {
		return Math.random() * (max - min) + min;
	}
	
	
	public static BigDecimal randBigDecimal(BigDecimal min, BigDecimal max) {
		return new BigDecimal(Math.random()).multiply(max.subtract(min)).add(min);
	}
	
	
	public static BigInteger randBigInteger(BigInteger min, BigInteger max) {
		return Math2.floorBigDecimalToBigInteger(new BigDecimal(Math.random()).multiply(new BigDecimal(max.subtract(min).toString()))).add(min);
	}
	
	
	public static BigInteger randBigInteger(int numberOfBits) {
	  return new BigInteger(numberOfBits, random);
	}
	
	
	public static String randHexForBits(int numberOfBits) {
		return String.format("%x", randBigInteger(numberOfBits)).toUpperCase();
	}
	
	
	public static String randHexForChars(int length) {
		return randChars("0123456789ABCDEF", length);
	}
	
	
	public static String randChars(String chars, int length) {
		StringBuffer result = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			result.append(chars.charAt((int)(Math.random() * chars.length())));
		}
		return result.toString();
	}
	
	
	/**
	 * Swaps the elements of the list to get a random order.
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
