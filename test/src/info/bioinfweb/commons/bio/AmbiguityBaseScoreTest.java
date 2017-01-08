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
