package info.bioinfweb.biojava3.core.sequence.views;


import static org.junit.Assert.*;


import info.bioinfweb.biojava3.core.sequence.compound.AlignmentAmbiguityDNACompoundSet;

import org.biojava3.core.sequence.DNASequence;
import org.junit.Test;



public class ReplaceNucleotideSequenceViewTest {
  @Test
  public void test_countReplacedCompounds() {
  	ReplaceNucleotideSequenceView view = new ReplaceNucleotideSequenceView(
  			new DNASequence("AT?TCG??TGA", AlignmentAmbiguityDNACompoundSet.getAlignmentAmbiguityDNACompoundSet()), 
  			ReplaceNucleotideSequenceView.AMBIGUITY_UNKNOWN_RNA_TO_N_DNA_MAP);
  	assertEquals(3, view.countChangedCompounds());
  }
  
  
  @Test
  public void test_countReplacedCompounds_cut() {
  	ReplaceNucleotideSequenceView view = new ReplaceNucleotideSequenceView(
  			new DNASequence("????AT?TCG??TGA???", AlignmentAmbiguityDNACompoundSet.getAlignmentAmbiguityDNACompoundSet()), 
  			ReplaceNucleotideSequenceView.AMBIGUITY_UNKNOWN_RNA_TO_N_DNA_MAP, 5, 15);
  	assertEquals(3, view.countChangedCompounds());
  }
}
