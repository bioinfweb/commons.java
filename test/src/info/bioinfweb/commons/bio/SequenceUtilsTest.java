/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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


import static org.junit.Assert.*;

import java.util.Map;

import info.bioinfweb.commons.Math2;



import org.junit.Test;



/**
 * Test class for {@link SequenceUtils}.
 * 
 * @author Ben St&ouml;ver
 */
public class SequenceUtilsTest {
	@Test
	public void test_nucleotideConstituents() {
		assertArrayEquals(new char[]{'A'}, SequenceUtils.nucleotideConstituents('A'));
		assertArrayEquals(new char[]{'T'}, SequenceUtils.nucleotideConstituents('t'));
		assertArrayEquals(new char[]{'-'}, SequenceUtils.nucleotideConstituents('-'));
		assertArrayEquals(new char[]{'U'}, SequenceUtils.nucleotideConstituents('U'));
		assertArrayEquals(new char[]{'A', 'T', 'C', 'G'}, SequenceUtils.nucleotideConstituents('N'));
		assertArrayEquals(new char[]{'A', 'T', 'C', 'G'}, SequenceUtils.nucleotideConstituents('x'));
	}
	
	
	@Test
	public void test_isNucleotideAmbuguityCode() {
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('A'));
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('C'));
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('G'));
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('T'));
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('U'));
		assertFalse(SequenceUtils.isNucleotideAmbuguityCode('-'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('N'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('X'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('Y'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('R'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('M'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('K'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('W'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('S'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('V'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('B'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('H'));
		assertTrue(SequenceUtils.isNucleotideAmbuguityCode('D'));
	}
	
	
	@Test
	public void test_oneLetterAminoAcidByThreeLetter() {
		assertEquals('A', SequenceUtils.oneLetterAminoAcidByThreeLetter("Ala"));
		assertEquals('B', SequenceUtils.oneLetterAminoAcidByThreeLetter("Asx"));
		assertEquals('-', SequenceUtils.oneLetterAminoAcidByThreeLetter("---"));
		assertEquals('O', SequenceUtils.oneLetterAminoAcidByThreeLetter("Pyl"));
		assertEquals('U', SequenceUtils.oneLetterAminoAcidByThreeLetter("Sec"));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_oneLetterAminoAcidByThreeLetterException1() {
		SequenceUtils.oneLetterAminoAcidByThreeLetter("A");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_oneLetterAminoAcidByThreeLetterException2() {
		SequenceUtils.oneLetterAminoAcidByThreeLetter("--");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_oneLetterAminoAcidByThreeLetterException3() {
		SequenceUtils.oneLetterAminoAcidByThreeLetter("----");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_oneLetterAminoAcidByThreeLetterException4() {
		SequenceUtils.oneLetterAminoAcidByThreeLetter("Mom");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_oneLetterAminoAcidByThreeLetterException5() {
		SequenceUtils.oneLetterAminoAcidByThreeLetter("-.-");
	}
	
	
	@Test
	public void test_threeLetterAminoAcidByOneLetter() {
		assertEquals("Met", SequenceUtils.threeLetterAminoAcidByOneLetter('M'));
		assertEquals("Leu", SequenceUtils.threeLetterAminoAcidByOneLetter('L'));
		assertEquals("---", SequenceUtils.threeLetterAminoAcidByOneLetter('-'));
	}
	
	@Test
	public void test_oneLetterAminoAcidConstituents() {
		assertArrayEquals(new char[]{'P'}, SequenceUtils.oneLetterAminoAcidConstituents("Pro"));
		assertArrayEquals(new char[]{'N', 'D'}, SequenceUtils.oneLetterAminoAcidConstituents("B"));
		assertArrayEquals(new char[]{'N', 'D'}, SequenceUtils.oneLetterAminoAcidConstituents("Asx"));
		assertArrayEquals(new char[]{'Q', 'E'}, SequenceUtils.oneLetterAminoAcidConstituents("Z"));
		assertArrayEquals(new char[]{'Q', 'E'}, SequenceUtils.oneLetterAminoAcidConstituents("glx"));
		assertArrayEquals(new char[]{'I', 'L'}, SequenceUtils.oneLetterAminoAcidConstituents("J"));
		assertArrayEquals(new char[]{'I', 'L'}, SequenceUtils.oneLetterAminoAcidConstituents("XLE"));
	}
	
	
	@Test
	public void test_threeLetterAminoAcidConstituents() {
		assertArrayEquals(new String[]{"Pro"}, SequenceUtils.threeLetterAminoAcidConstituents("Pro"));
		assertArrayEquals(new String[]{"Asn", "Asp"}, SequenceUtils.threeLetterAminoAcidConstituents("B"));
		assertArrayEquals(new String[]{"Asn", "Asp"}, SequenceUtils.threeLetterAminoAcidConstituents("Asx"));
		assertArrayEquals(new String[]{"Gln", "Glu"}, SequenceUtils.threeLetterAminoAcidConstituents("Z"));
		assertArrayEquals(new String[]{"Gln", "Glu"}, SequenceUtils.threeLetterAminoAcidConstituents("glx"));
		assertArrayEquals(new String[]{"Ile", "Leu"}, SequenceUtils.threeLetterAminoAcidConstituents("J"));
		assertArrayEquals(new String[]{"Ile", "Leu"}, SequenceUtils.threeLetterAminoAcidConstituents("XLE"));
	}
	
	
	@Test
	public void test_isAminoAcidAmbiguityCode() {
		assertFalse(SequenceUtils.isAminoAcidAmbiguityCode(""));
		assertFalse(SequenceUtils.isAminoAcidAmbiguityCode("Asp"));
		assertFalse(SequenceUtils.isAminoAcidAmbiguityCode("pro"));
		assertFalse(SequenceUtils.isAminoAcidAmbiguityCode("P"));
		assertFalse(SequenceUtils.isAminoAcidAmbiguityCode("-"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("b"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("asx"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("Z"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("Glx"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("j"));
		assertTrue(SequenceUtils.isAminoAcidAmbiguityCode("XLE"));
	}
	
	
	@Test
	public void test_deleteGapsFromLeft() {
    assertEquals("ATCG-TTAGT-TGA", SequenceUtils.deleteGapsFromLeft("-AT-CG--TTAGT-TGA", 3));
    assertEquals("ATCGTTAGTTGA", SequenceUtils.deleteGapsFromLeft("-AT-CG--TTAGT-TGA", 20));
	}
	
	
	@Test
	public void test_deleteGapsFromRight() {
    assertEquals("-AT-CGTTAGTTGA", SequenceUtils.deleteGapsFromRight("-AT-CG--TTAGT-TGA", 3));
    assertEquals("ATCGTTAGTTGA", SequenceUtils.deleteGapsFromRight("-AT-CG--TTAGT-TGA", 20));
	}
	
	
	/**
	 * Warning: This is test may fail from time to time due to statistical effects, even if the tested
	 * method is working correctly. It should not fail in most cases.
	 */
	@Test
	public void test_randSeq() {
		String seq = SequenceUtils.randSequence(true, 10000, .3);
		int cgCount = 0;
		for (int i = 0; i < seq.length(); i++) {
			if ((seq.charAt(i) == 'C') || (seq.charAt(i) == 'G')) {
				cgCount++;
			}
		}
		//System.out.println(cgCount);
		assertTrue(Math2.isBetween(cgCount, 2900, 3100));
	}
	
	
	@Test
	public void test_isNonAmbiguityAminoAcid() {
		assertTrue(SequenceUtils.isNonAmbiguityAminoAcid("A"));
		assertFalse(SequenceUtils.isNonAmbiguityAminoAcid("B"));
		assertFalse(SequenceUtils.isNonAmbiguityAminoAcid("-"));
		assertTrue(SequenceUtils.isNonAmbiguityAminoAcid("Ala"));
		assertFalse(SequenceUtils.isNonAmbiguityAminoAcid("Asx"));
	}
	
	
	@Test
	public void test_nucleotideFrequencies() {
		Map<Character, Double> frequencies = SequenceUtils.nucleotideFrequencies("ACAT-CGGWNA".toCharArray());
		assertEquals(0.375, frequencies.get('A').doubleValue(), 0.0000000001);
		assertEquals(0.175, frequencies.get('T').doubleValue(), 0.0000000001);
		assertEquals(0.225, frequencies.get('C').doubleValue(), 0.0000000001);
		assertEquals(0.225, frequencies.get('G').doubleValue(), 0.0000000001);
	}
	
	
	@Test
	public void test_nucleotideConsensus() {
		assertEquals('A', SequenceUtils.nucleotideConsensus("ACAT-CGGWNA".toCharArray()));
		assertEquals('T', SequenceUtils.nucleotideConsensus("TCTA-CGGWNT".toCharArray()));
	}
	
	
	@Test
	public void test_aminoAcidFrequencies() {                                                         // N v D
		Map<Character, Double> frequencies = SequenceUtils.aminoAcidFrequencies(new String[]{"A", "C", "D", "B", "X", "-"});
		assertEquals(0.25, frequencies.get('A').doubleValue(), 0.0000000001);
		assertEquals(0.25, frequencies.get('C').doubleValue(), 0.0000000001);
		assertEquals(0.375, frequencies.get('D').doubleValue(), 0.0000000001);
		assertEquals(0.125, frequencies.get('N').doubleValue(), 0.0000000001);
	}


	@Test
	public void test_aminoAcidConsensus() {                                      // N v D
		assertEquals('C', SequenceUtils.aminoAcidConsensus(new String[]{"A", "C", "D", "B", "X", "-", "C"}));
	}
}
