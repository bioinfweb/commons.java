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
package info.bioinfweb.commons.bio.alignment.pairwise;


import static org.junit.Assert.*;


import org.junit.Test;



/**
 * Test class for {@link NeedlemanWunschAligner}.
 * 
 * @author Ben St&ouml;ver
 */
public class NeedlemanWunschAlignerTest {
  @Test
  public void test_align() {
  	CharSequence[] result = new NeedlemanWunschAligner().align("ATCGATTATTATTATTACGTTGAC", "ATCGATTATTATTACGTTGAC");
  	assertEquals("ATCGATTATTATTATTACGTTGAC", result[0].toString());
  	assertEquals("ATCG---ATTATTATTACGTTGAC", result[1].toString());
  }
}
