/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.bio;


import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import info.bioinfweb.commons.Math2;
import info.bioinfweb.commons.text.StringUtils;



/**
 * Useful tools to manipulate nucleotide and amino acid sequences stored as character sequences. 
 * 
 * @author Ben St&ouml;ver
 */
public class SequenceUtils {
	private static class NucleotideInfo {
		public char complement;
		public char[] constituents;
		
		
		public NucleotideInfo(char complement, char[] constituents) {
			super();
			this.complement = complement;
			this.constituents = constituents;
		}
	}
	
	
	private static class AminoAcidInfo {
		private char oneLetterCode;
		private String threeLetterCode;
		private char[] constituents;
		
		
		public AminoAcidInfo(char oneLetterCode, String threeLetterCode,
				char[] constituents) {
			super();
			this.oneLetterCode = oneLetterCode;
			this.threeLetterCode = threeLetterCode;
			this.constituents = constituents;
		}
	}
	
	
	public static final char GAP_CHAR = '-';
	public static final char MISSING_DATA_CHAR = '?';
	public static final char MATCH_CHAR = '.';
	public static final char STOP_CODON_CHAR = '*';
	public static final String DNA_CHARS = "CGAT";  // Order is relevant for randSequence() 	
	public static final String ALL_DNA_CHARS = "CGATYRKMBVDHN"; 	
	public static final String RNA_CHARS = "CGAU";  // Order is relevant for randSequence()	
	public static final String ALL_RNA_CHARS = "CGAUYRKMBVDHN"; 	
	
	private static final Map<Character, NucleotideInfo> nucleotideInfoMap = createNucleotideInfoMap();
	private static final Map<String, AminoAcidInfo> aminoAcidInfoMap = createAminoAcidInfoMap();
	
	
	private static Map<Character, NucleotideInfo> createNucleotideInfoMap() {
		Map<Character, NucleotideInfo> result = new TreeMap<Character, NucleotideInfo>();
		result.put('A', new NucleotideInfo('T', new char[]{'A'}));
		result.put('T', new NucleotideInfo('A', new char[]{'T'}));
		result.put('U', new NucleotideInfo('A', new char[]{'U'}));
		result.put('C', new NucleotideInfo('G', new char[]{'C'}));
		result.put('G', new NucleotideInfo('C', new char[]{'G'}));
		result.put('Y', new NucleotideInfo('R', new char[]{'C', 'T'}));  // Y = C v T (Pyrimidin)
		result.put('R', new NucleotideInfo('Y', new char[]{'A', 'G'}));  // R = A v G (Purin)
		result.put('K', new NucleotideInfo('M', new char[]{'G', 'T'}));  // K = G v T (Ketogruppe)
		result.put('M', new NucleotideInfo('K', new char[]{'A', 'C'}));  // M = A v C (Aminogruppe)
		result.put('W', new NucleotideInfo('W', new char[]{'A', 'T'}));  // W (weak) = A v T
		result.put('S', new NucleotideInfo('S', new char[]{'C', 'G'}));  // S (strong) = C v G
		result.put('B', new NucleotideInfo('V', new char[]{'C', 'G', 'T'}));  // B = C v G v T
		result.put('V', new NucleotideInfo('B', new char[]{'A', 'C', 'G'}));  // V = A v C v G
		result.put('D', new NucleotideInfo('H', new char[]{'A', 'G', 'T'}));  // D = A v G v T
		result.put('H', new NucleotideInfo('D', new char[]{'A', 'C', 'T'}));  // H = A v C v T
		result.put('N', new NucleotideInfo('N', new char[]{'A', 'T', 'C', 'G'}));
		result.put('X', new NucleotideInfo('X', new char[]{'A', 'T', 'C', 'G'}));
		return result;
	}
	
	
	private static void putAminoAcidInfo(Map<String, AminoAcidInfo> map, char oneLetterCode, String threeLetterCode, 
			char... constituents) {
		
		if (constituents.length == 0) {
			constituents = new char[]{oneLetterCode};
		}
		AminoAcidInfo info = new AminoAcidInfo(oneLetterCode, threeLetterCode, constituents);
		map.put(Character.toString(oneLetterCode), info);
		map.put(threeLetterCode.toUpperCase(), info);
	}
	
	
	private static Map<String, AminoAcidInfo> createAminoAcidInfoMap() {
		Map<String, AminoAcidInfo> result = new TreeMap<String, AminoAcidInfo>();
		
		putAminoAcidInfo(result, 'A', "Ala");
		putAminoAcidInfo(result, 'C', "Cys");
		putAminoAcidInfo(result, 'D', "Asp");
		putAminoAcidInfo(result, 'E', "Glu");
		putAminoAcidInfo(result, 'F', "Phe");
		putAminoAcidInfo(result, 'G', "Gly");
		putAminoAcidInfo(result, 'H', "His");
		putAminoAcidInfo(result, 'I', "Ile");
		putAminoAcidInfo(result, 'K', "Lys");
		putAminoAcidInfo(result, 'L', "Leu");
		putAminoAcidInfo(result, 'M', "Met");
		putAminoAcidInfo(result, 'N', "Asn");
		putAminoAcidInfo(result, 'P', "Pro");
		putAminoAcidInfo(result, 'Q', "Gln");
		putAminoAcidInfo(result, 'R', "Arg");
		putAminoAcidInfo(result, 'S', "Ser");
		putAminoAcidInfo(result, 'T', "Thr");
		putAminoAcidInfo(result, 'V', "Val");
		putAminoAcidInfo(result, 'W', "Trp");
		putAminoAcidInfo(result, 'Y', "Tyr");
		
		putAminoAcidInfo(result, 'O', "Pyl");
		putAminoAcidInfo(result, 'U', "Sec");
		
		putAminoAcidInfo(result, 'B', "Asx", 'N', 'D');
		putAminoAcidInfo(result, 'Z', "Glx", 'Q', 'E');
		putAminoAcidInfo(result, 'J', "Xle", 'I', 'L');
		
		AminoAcidInfo info = new AminoAcidInfo('X', "Xaa", 
				new char[]{'A', 'C', 'D', 'E', 'F','G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y', 'O', 'U'});
		result.put("X", info);
		result.put("XAA", info);
		result.put("UNK", info);
		
		return result;
	}
	
	
	/**
	 * Returns a set of all nucleotide characters, including 'T' and 'U' as well as all IUPAC 
	 * ambiguity codes in upper case.
	 * 
	 * @return a new set with all nucleotide tokens
	 */
	public static Set<Character> getNucleotideCharacters() {
		Set<Character> result = new TreeSet<Character>();
		result.addAll(nucleotideInfoMap.keySet());
		return result;
	}
	
	
	/**
	 * If the specified nucleotide is an IUPAC ambiguity code this method returns an array
	 * containing all nucleotides that could be represented by the code. 
	 * <p>
	 * Constituents returned for ambiguity code are always DNA nucleotides (thymine is always used instead 
	 * of uracil). (Anyway, if {@code 'U'} is specified for {@code nucleotide} the returned array will contain 
	 * {@code 'U'} as the only element and not {@code 'T'}). 
	 * <p>
	 * If the specified character is not an ambiguity code character, an array containing 
	 * this character (in upper case) as the only element is returned.  
	 * 
	 * @param nucleotide the character that may be an ambiguity code
	 * @return an array of the nucleotides as upper case letters
	 */
	public static char[] nucleotideConstituents(char nucleotide) {
		nucleotide = Character.toUpperCase(nucleotide);
		NucleotideInfo result = nucleotideInfoMap.get(nucleotide);
		if (result == null) {  // e.g. for '-'
			return new char[]{nucleotide};
		}
		else {
			return Arrays.copyOf(result.constituents, result.constituents.length);  // Copy array to avoid modifications of the arrays in nucleotideInfoMap by external code.
		}
	}
	
	
	public static char[] rnaConstituents(char nucleotide) {
		char[] constituents = nucleotideConstituents(nucleotide);
		
		for (int i = 0; i < constituents.length; i++) {
			if (constituents[i] == 'T') {
				constituents[i] = 'U';
			}			
		}
		
		return constituents;
	}
	
	
	public static boolean isNonAmbiguityNucleotide(char c) {
		c = Character.toUpperCase(c);
		return (c == 'A') || (c == 'T') || (c == 'C') || (c == 'G') || (c == 'U');
	}
	
	
	/**
	 * Determines whether the specified character is an IUPAC ambiguity code.
	 * 
	 * @param nucleotide the character that may be an ambiguity code
	 * @return {@code true} of the specified character is a valid ambiguity code, {@code false} otherwise.
	 */
	public static boolean isNucleotideAmbuguityCode(char nucleotide) {
		return nucleotideConstituents(nucleotide).length > 1;
	}
	
	
	/**
	 * Returns a set of all amino acid one letter codes in upper case.
	 * 
	 * @param includeAmbiguity Specify {@code true} here if amino acid ambiguity codes shall also be
	 *        contained in the returned set or {@code false} if only unambiguous characters shall be
	 *        contained.
	 * @return a new set of amino acid codes
	 */
	public static Set<Character> getAminoAcidOneLetterCodes(boolean includeAmbiguity) {
		Set<Character> result = new TreeSet<Character>();
		for (String key : aminoAcidInfoMap.keySet()) {
			if (key.length() == 1) {
				result.add(key.charAt(0));
			}
		}
		
		if (!includeAmbiguity) {
			result.remove('B');
			result.remove('Z');
			result.remove('J');
			result.remove('X');
		}
		return result;
	}
	
	
	/**
	 * Returns a set of all amino acid three letter codes in upper case.
	 * 
	 * @param includeAmbiguity Specify {@code true} here if amino acid ambiguity codes shall also be
	 *        contained in the returned set or {@code false} if only unambiguous characters shall be
	 *        contained.
	 * @return a new set of amino acid codes
	 */
	public static Set<String> getAminoAcidThreeLetterCodes(boolean includeAmbiguity) {
		Set<String> result = new TreeSet<String>();
		for (String key : aminoAcidInfoMap.keySet()) {
			if (key.length() == 3) {
				result.add(key);
			}
		}
		
		if (!includeAmbiguity) {
			result.remove("ASX");
			result.remove("GLX");
			result.remove("XLE");
			result.remove("XAA");
			result.remove("UNK");
		}
		return result;
	}
	
	
	/**
	 * Converts the specified three letter amino acid code into a one letter representation. Besides the defined
	 * amino acid codes this method also accepts any string which consists of one character that is repeated three
	 * times. (E.g. {@code "---"} would be converted to {@code '-'}).
	 * 
	 * @param threeLetterCode the three letter code to be converted
	 * @return the according three letter code in upper case
	 * @throws IllegalArgumentException if the specified code is not a valid one letter amino acid code or a three 
	 *         character long repetition of the same character
	 */
	public static char oneLetterAminoAcidByThreeLetter(String threeLetterCode) {
		if (threeLetterCode.length() == 3) {
			AminoAcidInfo info = aminoAcidInfoMap.get(threeLetterCode.toUpperCase());
			if (info != null) {
				return info.oneLetterCode;
			}
			else if ((threeLetterCode.charAt(0) == threeLetterCode.charAt(1)) && (threeLetterCode.charAt(0) == threeLetterCode.charAt(2))) {
				return threeLetterCode.charAt(0);
			}
		}
		throw new IllegalArgumentException("The specified string \"" + threeLetterCode + 
				"\" is not a valid three letter amino acid code.");
	}
	
	
	/**
	 * Converts the specified one letter amino acid code into a three letter representation.
	 * 
	 * @param oneLetterCode the one letter code to be converted
	 * @return the according three letter code where the first letter is in upper case (e.g. {@code "Pro"})
	 *         if {@code oneLetterCode} was a valid amino acid representation or a string consisting of three
	 *         repetitions of the specified character otherwise (E.g. '-' would be converted to "---".) 
	 */
	public static String threeLetterAminoAcidByOneLetter(char oneLetterCode) {
		AminoAcidInfo info = aminoAcidInfoMap.get(Character.toString(oneLetterCode));
		if (info != null) {
			return info.threeLetterCode;
		}
		else {
			return StringUtils.repeat(Character.toString(oneLetterCode), 3);
		}
	}
	
	
	/**
	 * Returns the one letter amino acid representations of all compounds that could be represented by the
	 * specified ambiguity code.
	 * 
	 * @param code the ambiguity one or three letter ambiguity code to be converted 
	 * @return an array with the one letter representations of the according amino acids or {@code null}
	 *         if {@code code} was not a valid ambiguity code
	 */
	public static char[] oneLetterAminoAcidConstituents(String code) {
		AminoAcidInfo info = aminoAcidInfoMap.get(code.toUpperCase());
		if (info != null) {
			return Arrays.copyOf(info.constituents, info.constituents.length);
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * Returns the three letter amino acid representations of all compounds that could be represented by the
	 * specified ambiguity code.
	 * 
	 * @param code the ambiguity one or three letter ambiguity code to be converted 
	 * @return an array with the three letter representations of the according amino acids or {@code null}
	 *         if {@code code} was not a valid ambiguity code
	 */
	public static String[] threeLetterAminoAcidConstituents(String code) {
		char[] oneLetterConstituents = oneLetterAminoAcidConstituents(code);
		if (oneLetterConstituents == null) {
			return null;
		}
		else {
			String[] result = new String[oneLetterConstituents.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = threeLetterAminoAcidByOneLetter(oneLetterConstituents[i]);
			}
			return result;
		}
	}

	
	public static boolean isNonAmbiguityAminoAcid(String code) {
		AminoAcidInfo info = aminoAcidInfoMap.get(code.toUpperCase());
		return (info != null) && (info.constituents.length == 1);
	}
	
	
	/**
	 * Determines whether the specified character is an amino acid ambiguity code.
	 * 
	 * @param code the string that may be an ambiguity code (one and three letter codes are supported)
	 * @return {@code true} of the specified character is a valid amino acid ambiguity code, {@code false} otherwise.
	 */
	public static boolean isAminoAcidAmbiguityCode(String code) {
		char[] constituents = oneLetterAminoAcidConstituents(code);
		return (constituents != null) && (constituents.length > 1);
	}
	
	
	/**
	 * Returns the reverse of the specified sequence.
	 * 
	 * @param sequence the source sequence
	 * @return the inverse sequence
	 */
	public static String reverse(CharSequence sequence) {
  	return StringUtils.invert(sequence);
  }
  
  
  /**
   * Returns the complement of the specified character. (IUPAC ambiguity codes are also supported.) 
   * If an undefined character is specified (e.g. "-") it is returned unchanged. 
   * 
   * @param c the nucleotide to be complemented
   * @return the complemented nucleotide character in upper or lower case depending on {@code c}
   */
  public static char complement(char c) {
  	NucleotideInfo result = nucleotideInfoMap.get(Character.toUpperCase(c));
  	if (result != null) {
  		if (Character.isUpperCase(c)) {
  			return result.complement;
  		}
  		else {
  			return Character.toLowerCase(result.complement);
  		}
  	}
  	else {
  		return c;
  	}
  }
	
	
	/**
   * Returns the complementing sequence. Ambiguity codes are supported. Upper case and lower case
   * characters are replaced by their according complements.
   * 
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
   * Returns the reverse complemented sequence. Ambiguity codes are supported. Upper case and lower 
   * case characters are replaced by their according complements.
   * 
   * @param sequence - the sequence to be reverse complemented
   * @return always a DNA sequence (no matter if the input was RNA or not)
   */
  public static String reverseComplement(CharSequence sequence) {
  	return reverse(complement(sequence));
  }
  
  
  public static String rnaToDNA(String rna) {
  	return rna.replaceAll("U", "T").replaceAll("u", "t");
  }
  
  
  public static String dnaToRNA(String dna) {
  	return dna.replaceAll("T", "U").replaceAll("t", "u");
  }
  
  
  public static boolean isDNAChar(char c) {
  	return isInTokenSet(Character.toUpperCase(c), ALL_DNA_CHARS);
  }
  
  
  public static boolean isRNAChar(char c) {
  	return isInTokenSet(Character.toUpperCase(c), ALL_RNA_CHARS);
  }
  
  
  public static boolean isInTokenSet(char c, String tokens) {
  	for (int i = 0; i < tokens.length(); i++) {
			if (c == tokens.charAt(i)) {
				return true;
			}
		}
  	return false;
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
   * Returns the left subsequence with the specified length (which does not include gaps).
   * <p>
   * If there are less than <code>count</code> characters in <code>seq</code> the whole sequence is 
   * returned.
   * <p>
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
   * Returns the right subsequence with the specified length (which does not include gaps).
   * <p>
   * If there are less than <code>count</code> characters in <code>seq</code> the whole sequence is 
   * returned.
   * <p>
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
   * than 1. The rate for thymidine or uracil is determined from the others.)
   * 
   * @param dna - determines whether a DNA sequence shall be returned (RNA id <code>false</code>)
   * @param length - the length of the sequence
   * @param rateC - the rate for cytosine
   * @param rateG - the rate for guanine
   * @param rateA - the rate for adenine
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
   * 
   * @param dna determines whether a DNA sequence shall be returned (RNA id <code>false</code>)
   * @param length the length of the sequence
   * @param rateCG the rate for cytosine and guanine (must lower than 1)
   * @return the random sequence
   */
  public static String randSequence(boolean dna, int length, double rateCG) {
  	double half = rateCG / 2.0;
  	return randSequence(dna, length, half, half, (1.0 - rateCG) / 2.0);
  }
  
  
  /**
   * Counts the nucleotide frequencies in the specified alignment column.
   * <p>
   * IUPAC ambiguity codes are supported and counted accordingly for several nucleotides.
   * (Examples: N would be counted as 0.25 for each nucleotide, R would be counted as 0.5 for C and 0.5 for T.)
   * 
   * @param alignmentColumn the contents of the alignment column from which the consensus shall be calculated
   * @return a map with the nucleotides as keys and their frequencies as values
   */
  public static Map<Character, Double> nucleotideFrequencies(char[] alignmentColumn) {
  	// Initialize:
  	Map<Character, Double> result = new TreeMap<Character, Double>();
  	result.put('A', 0.0);
  	result.put('T', 0.0);
  	result.put('C', 0.0);
  	result.put('G', 0.0);
  	
  	// Calculate:
  	double sum = 0.0;
  	for (int i = 0; i < alignmentColumn.length; i++) {
  		char[] constituents = nucleotideConstituents(alignmentColumn[i]);
  		double addend = 1.0 / (double)constituents.length;
  		for (int j = 0; j < constituents.length; j++) {
  			if (isNonAmbiguityNucleotide(constituents[j])) {
  				result.put(constituents[j], result.get(constituents[j]) + addend);
  				sum += addend;
  			}
			}
		}
  	
  	// Normalize to 1:
  	for (Character c : result.keySet()) {
    	result.put(c, result.get(c) / sum);
		}
  	
  	return result;
  }
  
  
  /**
   * Returns the nucleotide that occurs most often in the specified alignment row. IUPAC
   * ambiguity codes are recognized and counted accordingly for several nucleotides. 
   * 
   * @param alignmentColumn the contents of the alignment column from which the consensus shall be calculated
   * @return the most frequent nucleotide (If several nucleotides occur equally often, one of them is chosen arbitrarily.)
   */
  public static char nucleotideConsensus(char[] alignmentColumn) {
  	Map<Character, Double> counts = nucleotideFrequencies(alignmentColumn);
  	char result = ' ';
  	double max = -1;
  	for (Character c : counts.keySet()) {
			if (counts.get(c) > max) {
				result = c;
				max = counts.get(c);
			}
		}
  	return result;
  }
  
  
  public static Map<Character, Double> aminoAcidFrequencies(String[] alignmentColumn) {
  	// Initialize:
  	Map<Character, Double> result = new TreeMap<Character, Double>();
  	for (Character aminoAcid : getAminoAcidOneLetterCodes(false)) {
			result.put(aminoAcid, 0.0);
		}
  	
  	// Calculate:
  	double sum = 0.0;
  	for (int i = 0; i < alignmentColumn.length; i++) {
  		char[] constituents = oneLetterAminoAcidConstituents(alignmentColumn[i]);
  		if ((constituents != null) && (constituents.length <= 2)) {  // Also ignore X because it counts equally for all amino acids anyway and its not clear if post-translational amino acids are included in the alphabet.
	  		double addend = 1.0 / (double)constituents.length;
	  		for (int j = 0; j < constituents.length; j++) {
	  			if (isNonAmbiguityAminoAcid(Character.toString(constituents[j]))) {
	  				result.put(constituents[j], result.get(constituents[j]) + addend);
	  				sum += addend;
	  			}
				}
  		}
		}
  	
  	// Normalize to 1:
  	for (Character c : result.keySet()) {
    	result.put(c, result.get(c) / sum);
		}
  	
  	return result;
  }


  public static char aminoAcidConsensus(String[] alignmentColumn) {
  	Map<Character, Double> counts = aminoAcidFrequencies(alignmentColumn);
  	char result = ' ';
  	double max = -1;
  	for (Character c : counts.keySet()) {
			if (counts.get(c) > max) {
				result = c;
				max = counts.get(c);
			}
		}
  	return result;
  }
}
	