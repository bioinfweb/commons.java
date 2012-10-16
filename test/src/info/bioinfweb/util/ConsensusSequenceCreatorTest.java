package info.bioinfweb.util;


import static org.junit.Assert.*;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.RNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.junit.Test;



public class ConsensusSequenceCreatorTest {
	@Test
	public void test_majorityRuleConsensusAsString_DNA() {
		Sequence<NucleotideCompound>[] sequences = 
				new DNASequence[]{new DNASequence("ATCG"), new DNASequence("ATGG"), new DNASequence("ATCG")};
		assertEquals("ATCG", ConsensusSequenceCreator.getInstance().majorityRuleConsensusAsString(sequences));
  }
	
	
	@Test
	public void test_majorityRuleConsensusAsString_RNA() {
		Sequence<NucleotideCompound>[] sequences = 
				new RNASequence[]{new RNASequence("AUCG"), new RNASequence("AUGG"), new RNASequence("AUCG")};
		assertEquals("ATCG", ConsensusSequenceCreator.getInstance().majorityRuleConsensusAsString(sequences));
  }

	
	@Test
	public void test_majorityRuleConsensusAsString_Gap() {
		Sequence<NucleotideCompound>[] sequences = 
				new DNASequence[]{new DNASequence("A-CG"), new DNASequence("A-GG"), new DNASequence("A-CG")};
		assertEquals("A-CG", ConsensusSequenceCreator.getInstance().majorityRuleConsensusAsString(sequences));
  }
	
	
	@Test
	public void test_majorityRuleConsensus_Gap() {
		Sequence<NucleotideCompound>[] sequences = 
				new DNASequence[]{new DNASequence("A-CG"), new DNASequence("A-GG"), new DNASequence("A-CG")};
		Sequence<NucleotideCompound> result = ConsensusSequenceCreator.getInstance().majorityRuleConsensus(sequences);
		assertEquals("A", result.getCompoundAt(1).getUpperedBase());
		assertEquals("-", result.getCompoundAt(2).getUpperedBase());
		assertEquals("C", result.getCompoundAt(3).getUpperedBase());
		assertEquals("G", result.getCompoundAt(4).getUpperedBase());
  }
}
