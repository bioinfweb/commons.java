/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityDNACompoundSet;

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
