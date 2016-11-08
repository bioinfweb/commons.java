/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio;


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
