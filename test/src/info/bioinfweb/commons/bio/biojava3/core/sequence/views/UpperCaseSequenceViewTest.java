/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
