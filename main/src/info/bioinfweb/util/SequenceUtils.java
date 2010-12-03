package info.bioinfweb.util;


import java.util.HashMap;

import info.webinsel.util.Math2;
import info.webinsel.util.StringUtils;



/**
 * Useful tools to manipulate DNA and RNA sequences stored as strings. 
 * @author Ben St&ouml;ver
 */
public class SequenceUtils {
	public static final char GAP_CHAR = '-';	
	public static final String DNA_CHARS = "CGAT";  // Order is relevant for randSequence() 	
	public static final String RNA_CHARS = "CGAAU";  // Order is relevant for randSequence()	
	
	private static final HashMap<Character, Character> complementMap = createComplementMap();
	
	
	private static HashMap<Character, Character> createComplementMap() {
		HashMap<Character, Character> result = new HashMap<Character, Character>();
		result.put('A', 'T');
		result.put('T', 'A');
		result.put('U', 'A');
		result.put('C', 'G');
		result.put('G', 'C');
		result.put('Y', 'R');  // Y = C v T (Pyrimidin)
		result.put('R', 'Y');  // R = A v G (Purin)
		result.put('K', 'M');  // K = G v T (Ketogruppe)
		result.put('M', 'K');  // M = A v C (Aminogruppe)
		result.put('B', 'V');  // B = C v G v T
		result.put('V', 'B');  // V = A v C v G
		result.put('D', 'H');  // D = A v G v T
		result.put('H', 'D');  // H = A v C v T
		// W (weak, A v T), S (strong, C v G), N oder "-" bleiben unverändert.
		return result;
	}
	
	
	public static String reverse(CharSequence sequence) {
  	return StringUtils.invert(sequence);
  }
  
  
  /**
   * Returns the complement of the specified character. If an undefined character is specified
   * (e.g. "-") it is returned unchanged.
   * @param c
   * @return
   */
  public static char complement(char c) {
  	Character result = complementMap.get(Character.toUpperCase(c));
  	if (result != null) {
  		return result;
  	}
  	else {
  		return c;
  	}
  }
	
	
	/**
   * Returns the complementing sequence. Ambiguity codes are supported.
   * @param sequence - the sequence to be complemented
   * @return always a DNA sequence (no matter if the input was RNA or not)
   */
  public static String complement(CharSequence sequence) {
  	StringBuffer result = new StringBuffer(sequence.length());
  	for (int i = 0; i < sequence.length(); i++) {
			result.append(complement(sequence.charAt(i)));
		}
  	return result.toString();
  }
  
  
  /**
   * Returns the reverse complemented sequence. Ambiguity codes are supported.
   * @param sequence - the sequence to be reverse complemented
   * @return always a DNA sequence (no matter if the input was RNA or not)
   */
  public static String reverseComplement(String sequence) {
  	return reverse(complement(sequence));
  }
  
  
  public static String rnaToDNA(String rna) {
  	return rna.toUpperCase().replaceAll("U", "T");
  }
  
  
  public static String dnaToRNA(String dna) {
  	return dna.toUpperCase().replaceAll("T", "U");
  }
  
  
  public static int lengthWOGaps(CharSequence sequence) {
  	int result = 0;
  	for (int i = 0; i < sequence.length(); i++) {
			if (sequence.charAt(i) != GAP_CHAR) {
				result++;
			}
		}
  	return result;
  }
  
  
  /**
   * Returns the left subsequence with the specified length (which does not include gaps).<br />
   * If there are less than <code>count</code> characters in <code>seq</code> the whole sequence is 
   * returned.<br />
   * <br />
   * <b>Example:</b> <code>leftSubsequence("AT-A-GCCTG-CTG", 5)</code> would return <i>AT-A-GC</i>
   *  
   * @param seq - the source sequence
   * @param count - the number characters that shall be contained in the returned subsequence (not 
   *        including gaps)
   * @return the left subsequence containing the specified number of characters and possibly additional 
   *         gaps
   */
  public static String leftSubsequence(String seq, int count) {
  	int charCount = 0;
  	int pos = 0;
  	while ((pos < seq.length()) && (charCount < count)) {
  		if (seq.charAt(pos) != GAP_CHAR) {
  			charCount++;
  		}
  		pos++;
  	}
  	
  	return seq.substring(0, pos);
  }
  
  
  /**
   * Returns the right subsequence with the specified length (which does not include gaps).<br />
   * If there are less than <code>count</code> characters in <code>seq</code> the whole sequence is 
   * returned.<br />
   * <br />
   * <b>Example:</b> <code>rightSubsequence("AT-A-GCCTG-CTG", 5)</code> would return <i>TG-CTG</i>
   *  
   * @param seq - the source sequence
   * @param count - the number characters that shall be contained in the returned subsequence (not 
   *        including gaps)
   * @return the right subsequence containing the specified number of characters and possibly additional 
   *         gaps
   */
  public static String rightSubsequence(String seq, int count) {
  	int charCount = 0;
  	int pos = seq.length() - 1;
  	while ((pos >= 0) && (charCount < count)) {
  		if (seq.charAt(pos) != GAP_CHAR) {
  			charCount++;
  		}
  		pos--;
  	}
  	
  	return seq.substring(pos + 1);
  }
  
  
  /**
   * Deletes <code>count</code> characters from the left side of the sequence which are not gaps
   * ("-").
   * @param seq - the original sequence
   * @param count - the number of characters to be removed
   * @return the sequence without the deleted characters and gaps
   */
  public static String deleteFromLeft(String seq, int count) {
  	int deleted = 0;
  	int pos = 0;
  	while ((pos < seq.length()) && (deleted < count)) {
  		if (seq.charAt(pos) != GAP_CHAR) {
  			deleted++;
  		}
  		pos++;
  	}
  	
  	if (deleted < count) {
  		throw new IllegalArgumentException("The specified sequence does not contains enough non-gap characters (" + count + ").");
  	}
  	else {
  		return seq.substring(pos);
  	}
  }
  
  
  /**
   * Deletes <code>count</code> characters from the left side of the sequence which are not gaps
   * ("-").
   * @param seq - the original sequence
   * @param count - the number of characters to be removed
   * @return the sequence without the deleted characters and gaps
   */
  public static String deleteFromRight(String seq, int count) {
  	int deleted = 0;
  	int pos = seq.length() - 1;
  	while ((pos >= 0) && (deleted < count)) {
  		if (seq.charAt(pos) != GAP_CHAR) {
  			deleted++;
  		}
  		pos--;
  	}
  	
  	if (deleted < count) {
  		throw new IllegalArgumentException("The specified sequence does not contains enough non-gap characters (" + count + ").");
  	}
  	else {
  		return seq.substring(0, pos + 1);
  	}
  }
  
  
  /**
   * Deletes all leading gaps from a sequence.
   * @param seq
   * @return
   */
  public static String deleteLeadingGaps(String seq) {
  	int pos = 0;
		while ((pos < seq.length()) && (seq.charAt(pos) == GAP_CHAR)) {
			pos++;
		}
		return seq.substring(pos);
  }
  
  
  /**
   * Deletes all trailing gaps from a sequence.
   * @param seq
   * @return
   */
  public static String deleteTrailingGaps(String seq) {
  	int pos = seq.length() - 1;
		while ((pos >= 0) && (seq.charAt(pos) == GAP_CHAR)) {
			pos--;
		}
		return seq.substring(0, pos + 1);
  }
  
  
	/**
	 * Deletes the specified number of gaps from the sequence starting on the left side.
	 * If the sequence contains less than the specified number of gaps a sequence without gaps is 
	 * returned.
	 * @param seq - the sequence that contains the gaps
	 * @param count - the number of gaps to be removed
	 * @return the sequence with less gaps
	 */
	public static String deleteGapsFromLeft(String seq, int count) {
		StringBuilder result = new StringBuilder(seq.length());
		int deletedCount = 0;
		int pos = 0;
		while (pos < seq.length()) {
			if ((seq.charAt(pos) == SequenceUtils.GAP_CHAR) && (deletedCount < count)) {
				deletedCount++;
			}
			else {
				result.append(seq.charAt(pos));
			}
			pos++;
		}
		return result.toString();
	}
	
	
	/**
	 * Deletes the specified number of gaps from the sequence starting on the right side.
	 * If the sequence contains less than the specified number of gaps a sequence without gaps is 
	 * returned.
	 * @param seq - the sequence that contains the gaps
	 * @param count - the number of gaps to be removed
	 * @return the sequence with less gaps
	 */
	public static String deleteGapsFromRight(String seq, int count) {
		StringBuilder result = new StringBuilder(seq.length());
		int deletedCount = 0;
		int pos = seq.length() - 1;
		while (pos >= 0) {
			if ((seq.charAt(pos) == SequenceUtils.GAP_CHAR) && (deletedCount < count)) {
				deletedCount++;
			}
			else {
				result.append(seq.charAt(pos));
			}
			pos--;
		}
		return StringUtils.invert(result);
	}
	
	
  /**
   * Deletes all leading and trailing gaps of the specified sequence.
   * @param seq
   * @return
   */
  public static String deleteLeadingTrailingGaps(String seq) {
  	return deleteLeadingGaps(deleteTrailingGaps(seq));
  }
  
  
  /**
   * Returns the specified sequence without any gaps.
   * @param seq
   * @return
   */
  public static String deleteAllGaps(CharSequence seq) {
  	StringBuffer result = new StringBuffer(seq.length());
  	for (int i = 0; i < seq.length(); i++) {
			if (seq.charAt(i) != GAP_CHAR) {
				result.append(seq.charAt(i));
			}
		}
  	return result.toString();
  }
  
  
  /**
   * Returns a sequence of random DNA or RNA characters. (The three rates together have to be lower 
   * than 1. The rate for Thymidine or Uracil is determined from the others.)
   * @param dna - determines whether a DNA sequence shall be returned (RNA id <code>false</code>)
   * @param length - the length of the sequence
   * @param rateC - the rate for Cytosine
   * @param rateG - the rate for Guanine
   * @param rateA - the rate for Adenine
   * @return the random sequence
   */
  public static String randSequence(boolean dna, int length, double rateC,  double rateG, double rateA) {
  	double[] borders = new double[5];
  	borders[0] = 0.0;
  	borders[1] = rateC;
  	borders[2] = rateC + rateG;
  	borders[3] = borders[2] + rateA;
  	borders[4] = 1.0;
  	
  	String chars = DNA_CHARS;
  	if (!dna) {
  		chars = RNA_CHARS;
  	}
  	
		StringBuffer result = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			double value = Math.random();
			int index = 0;
			while (!Math2.isBetween(value, borders[index], borders[index + 1])) {
				index++;
			}
			result.append(chars.charAt(index));
		}
		return result.toString();
  }
  
  
  /**
   * Returns a sequence of random DNA or RNA characters.
   * @param dna - determines whether a DNA sequence shall be returned (RNA id <code>false</code>)
   * @param length - the length of the sequence
   * @param rateC - the rate for Cytosine and Guanine (must lower than 1)
   * @return the random sequence
   */
  public static String randSequence(boolean dna, int length, double rateCG) {
  	double half = rateCG / 2.0;
  	return randSequence(dna, length, half, half, (1.0 - rateCG) / 2.0);
  }
  
}
	