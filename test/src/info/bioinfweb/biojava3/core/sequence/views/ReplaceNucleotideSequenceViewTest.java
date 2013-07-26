package info.bioinfweb.biojava3.core.sequence.views;


import static org.junit.Assert.assertEquals;


import org.biojava3.core.sequence.DNASequence;
import org.junit.Test;



public class ReplaceNucleotideSequenceViewTest {
  @Test
  public void test_countReplacedCompounds() {
  	ReplaceNucleotideSequenceView view = new ReplaceNucleotideSequenceView(new DNASequence("AT?TCG??TGA"), 
  			ReplaceNucleotideSequenceView.AMBIGUITY_TO_N_MAP);
  	assertEquals(3, view.countReplacedCompounds());
  }
}
