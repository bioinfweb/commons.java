package info.bioinfweb.util;


import static org.junit.Assert.*;
import info.webinsel.util.Math2;

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
