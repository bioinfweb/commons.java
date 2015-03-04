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
package info.bioinfweb.commons.bio;


import static org.junit.Assert.*;
import info.bioinfweb.commons.Math2;


import org.junit.Test;



/**
 * Test class for {@link SequenceUtils}.
 * 
 * @author Ben St&ouml;ver
 */
public class SequenceUtilsTest {
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
		System.out.println(cgCount);
		assertTrue(Math2.isBetween(cgCount, 2900, 3100));
	}
}
