package info.bioinfweb.biojavax.core.sequence.views;


import static org.junit.Assert.assertEquals;
import info.bioinfweb.biojavax.core.sequence.compound.AmbiguityNoGapDNACompoundSet;

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
