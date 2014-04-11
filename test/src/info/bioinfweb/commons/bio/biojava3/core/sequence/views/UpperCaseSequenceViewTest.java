package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import static org.junit.Assert.*;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.compound.DNACompoundSet;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.junit.Test;



public class UpperCaseSequenceViewTest {
  @Test
  public void testWithDNA() {
  	String sequence = "ATcGtaCgT";
  	UpperCaseSequenceView<NucleotideCompound> view = 
  			new UpperCaseSequenceView<NucleotideCompound>(new DNASequence(sequence), DNACompoundSet.getDNACompoundSet());
  	
  	assertEquals(sequence.toUpperCase(), view.getSequenceAsString());
  	assertEquals(4, view.countChangedCompounds());
  }


  // Test with proteins not necessary because AminoAcidCompoundSet does not include lower case characters anyway.
//  @Test
//  public void testWithProtein() {
//  	String sequence = "TQRnCLGgarGH";
//  	UpperCaseSequenceView<AminoAcidCompound> view = 
//  			new UpperCaseSequenceView<AminoAcidCompound>(new ProteinSequence(sequence), 
//  					AminoAcidCompoundSet.getAminoAcidCompoundSet());
//  	
//  	System.out.println(view.getSequenceAsString());
//  	assertEquals(sequence.toUpperCase(), view.getSequenceAsString());
//  	assertEquals(4, view.countChangedCompounds());
//  }
}
