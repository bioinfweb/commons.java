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


import static org.junit.Assert.assertEquals;


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AmbiguityNoGapDNACompoundSet;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.junit.Test;



/**
 * Test for {@link UnalignedSequenceView}.
 *  
 * @author Ben St&ouml;ver
 */
public class UnalignedSequenceViewTest {
	@Test
	public void test_getCompoundAt() {
		assertEquals("G", new UnalignedSequenceView<NucleotideCompound>(
				new DNASequence("AT--GC-TA"), new AmbiguityNoGapDNACompoundSet()).getCompoundAt(3).getBase());
	}
	
	
	@Test
	public void test_getCompoundAt_bioStart() {
		assertEquals("C", new UnalignedSequenceView<NucleotideCompound>(
				new DNASequence("AT--GC-TA"), new AmbiguityNoGapDNACompoundSet(), 2, 7).getCompoundAt(3).getBase());
	}
	
	
	@Test
	public void test_getIndexOf() {
		AmbiguityNoGapDNACompoundSet set = new AmbiguityNoGapDNACompoundSet();
		UnalignedSequenceView<NucleotideCompound> view = 
			  new UnalignedSequenceView<NucleotideCompound>(new DNASequence("AT--GC-TA"), set);
		
		assertEquals(3, view.getIndexOf(set.getCompoundForString("G")));
	}
	
	
	@Test
	public void test_getLength() {
		assertEquals(6, new UnalignedSequenceView<NucleotideCompound>(
				new DNASequence("AT--GC-TA"), new AmbiguityNoGapDNACompoundSet()).getLength());
	}
	
	
	@Test
	public void test_getSequenceAsString() {
		assertEquals("ATGCTA", new UnalignedSequenceView<NucleotideCompound>(
				new DNASequence("AT--GC-TA"), new AmbiguityNoGapDNACompoundSet()).getSequenceAsString());
	}
}
