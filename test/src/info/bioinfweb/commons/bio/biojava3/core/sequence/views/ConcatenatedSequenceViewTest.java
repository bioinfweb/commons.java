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
package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import static org.junit.Assert.*;

import java.util.Iterator;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.junit.Test;



public class ConcatenatedSequenceViewTest {
	public static final String SEQUENCE_0 = "ATAT";
	public static final String SEQUENCE_1 = "GCCGC";
	public static final String SEQUENCE_2 = "-ACAA";
	public static final String CONCATENATED_SEQUENCE = SEQUENCE_0 + SEQUENCE_1 + SEQUENCE_2;
	
	
	private ConcatenatedSequenceView<NucleotideCompound> createView() {
		return new ConcatenatedSequenceView(
				new Sequence[]{new DNASequence(SEQUENCE_0), new DNASequence(SEQUENCE_1), new DNASequence(SEQUENCE_2)});
	}
	
	
  @Test
  public void test_getLength_getCompoundAt() {
  	ConcatenatedSequenceView<NucleotideCompound> view = createView();
  	assertEquals(CONCATENATED_SEQUENCE.length(), view.getLength());
  	for (int i = 0; i < CONCATENATED_SEQUENCE.length(); i++) {
    	assertEquals("" + CONCATENATED_SEQUENCE.charAt(i), view.getCompoundAt(i + 1).getBase());
		}
  }

  
  @Test
  public void test_sequencesAsList_iterator() {
  	Iterator<Sequence<NucleotideCompound>> iterator = createView().sequencesAsList().iterator();
  	assertEquals(SEQUENCE_0, iterator.next().getSequenceAsString());
  	assertEquals(SEQUENCE_1, iterator.next().getSequenceAsString());
  	assertEquals(SEQUENCE_2, iterator.next().getSequenceAsString());
  	assertFalse(iterator.hasNext());
  }
}
