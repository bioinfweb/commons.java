package info.bioinfweb.util;


import static org.junit.Assert.*;


import org.junit.Test;



public class AmbiguityBaseScoreTest {
	@Test
	public void test_compareTo() {
    assertTrue(new AmbiguityBaseScore(0, 1, 1, 1).compareTo(new AmbiguityBaseScore(1, 0, 0, 0)) < 0);
    assertTrue(new AmbiguityBaseScore(1, 0, 0, 0).compareTo(new AmbiguityBaseScore(0, 1, 1, 1)) > 0);
    assertTrue(new AmbiguityBaseScore(0, 1, 1, 1).compareTo(new AmbiguityBaseScore(0, 1, 0, 1)) > 0);
    assertTrue(new AmbiguityBaseScore(0, 0, 0, 0.25).compareTo(new AmbiguityBaseScore(0, 0, 0, 0.75)) < 0);
	}
}
