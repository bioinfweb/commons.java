package info.bioinfweb.commons.bio;


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
	
	
	@Test
	public void test_rescale() {
		AmbiguityBaseScore score = new AmbiguityBaseScore(1, 0, 1, 0, 1);
		assertEquals(score.getAdenineScore(), .5, 0.0);
		assertEquals(score.getThymineScore(), 0.0, 0.0);
		assertEquals(score.getCytosineScore(), .5, 0.0);
		assertEquals(score.getGuanineScore(), 0.0, 0.0);

		score = new AmbiguityBaseScore(0, 0, 0, 0, 1);
		assertEquals(score.getAdenineScore(), 0.0, 0.0);
		assertEquals(score.getThymineScore(), 0.0, 0.0);
		assertEquals(score.getCytosineScore(), 0.0, 0.0);
		assertEquals(score.getGuanineScore(), 0.0, 0.0);
	}
}
