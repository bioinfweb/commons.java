package info.bioinfweb.commons.bio.biojava3.core.sequence;


import static org.junit.Assert.*;
import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.junit.Test;



public class SequenceCompareUtilsTest {
  @Test
  public void test_sequencesEqual() {
  	CompoundSet<NucleotideCompound> set =  AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet();
  	
  	assertTrue(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-N", set), new DNASequence("AT-N", set)));

  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-T", set), new DNASequence("AT-U", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-T", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-U", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-N", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-A", set), new DNASequence("AT-B", set)));
  	
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-U", set), new DNASequence("AT-T", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-B", set), new DNASequence("AT-T", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-B", set), new DNASequence("AT-U", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-B", set), new DNASequence("AT-N", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-B", set), new DNASequence("AT-A", set)));
  	
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEqual(new DNASequence("AT-M", set), new DNASequence("AT-B", set)));
  }

  
  @Test
  public void test_sequencesEquivalent() {
  	CompoundSet<NucleotideCompound> set =  AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet();
  	
  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-T", set), new DNASequence("AT-B", set)));
  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-U", set), new DNASequence("AT-B", set)));
  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-N", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-A", set), new DNASequence("AT-B", set)));

  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-B", set), new DNASequence("AT-T", set)));
  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-B", set), new DNASequence("AT-U", set)));
  	assertTrue(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-B", set), new DNASequence("AT-N", set)));
  	assertFalse(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-B", set), new DNASequence("AT-A", set)));

  	assertFalse(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.sequencesEquivalent(set, new DNASequence("AT-M", set), new DNASequence("AT-B", set)));
  }

  
  @Test
  public void test_nucleotideSequencesEquivalent() {
  	CompoundSet<NucleotideCompound> set =  AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet();
  	
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-T", set), new DNASequence("AT-B", set)));
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-U", set), new DNASequence("AT-B", set)));
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-N", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-A", set), new DNASequence("AT-B", set)));
  	
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-B", set), new DNASequence("AT-T", set)));
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-B", set), new DNASequence("AT-U", set)));
  	assertTrue(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-B", set), new DNASequence("AT-N", set)));
  	assertFalse(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-B", set), new DNASequence("AT-A", set)));
  	
  	assertFalse(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-", set), new DNASequence("AT-B", set)));
  	assertFalse(SequenceCompareUtils.nucleotideSequencesEquivalent(new DNASequence("AT-M", set), new DNASequence("AT-B", set)));
  }
}
