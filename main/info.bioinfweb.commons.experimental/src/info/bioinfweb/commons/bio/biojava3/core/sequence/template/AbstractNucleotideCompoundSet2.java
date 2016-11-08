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
package info.bioinfweb.commons.bio.biojava3.core.sequence.template;


import org.biojava3.core.sequence.compound.DNACompoundSet;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.AbstractNucleotideCompoundSet;



/**
 * Ancestor of the nucleotide compound sets in <code>info.bioinfweb.biojavax</code>. 
 * Implements {@link #newNucleotideCompound(String, String, String...)}.
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public abstract class AbstractNucleotideCompoundSet2 extends AbstractNucleotideCompoundSet<NucleotideCompound> {
  /**
   * Creates a new nucleotide compound. (Method copied from {@link DNACompoundSet}).
   * 
   * @param base - the name of the base (e.g. <i>A</i>)
   * @param complement - the name of the complementary base (e.g. <i>T</i>)
   * @param equivalents - the bases represented by <code>base</code> if <code>base</code> 
   *        and <code>complement</code> are ambiguity codes
   * @return the new nucleotide compound
   * @see org.biojava3.core.sequence.template.AbstractNucleotideCompoundSet#newNucleotideCompound(java.lang.String, java.lang.String, java.lang.String[])
   */
	@Override
  public NucleotideCompound newNucleotideCompound(String base, String complement, String... equivalents) {
    if(equivalents.length == 0) {
      return new NucleotideCompound(base, this, complement);
    }
    else {
      NucleotideCompound[] compounds = new NucleotideCompound[equivalents.length];
      for(int i = 0; i < compounds.length; i++) {
        compounds[i] = getCompoundForString(equivalents[i]);
      }
      return new NucleotideCompound(base, this, complement, compounds);
    }
  }
	
	
	protected void addAmbiguityDNACompounds() {
		addAmbiguityCompounds("T");
	}
	
	
	protected void addAmbiguityRNACompounds() {
		addAmbiguityCompounds("U");
	}
	
	
	/**
	 * Implementing classes can use this method to add all DNA or RNA ambiguity codes. (The four bases
	 * must have been added before calling this method.)  
	 */
	private void addAmbiguityCompounds(String symbolTU) {
    addNucleotideCompound("M", "K",
        "A", "C");
    addNucleotideCompound("R", "Y",
        "A", "G");
    addNucleotideCompound("W", "W",
        "A", symbolTU);
    addNucleotideCompound("S", "S",
        "C", "G");
    addNucleotideCompound("Y", "R",
        "C", symbolTU);
    addNucleotideCompound("K", "M",
        "G", symbolTU);
    addNucleotideCompound("V", "B",
        "A", "C", "G");
    addNucleotideCompound("H", "D",
        "A", "C", symbolTU);
    addNucleotideCompound("D", "H",
        "A", "G", symbolTU);
    addNucleotideCompound("B", "V",
        "C", "G", symbolTU);
    addNucleotideCompound("N", "N",
        "A", "C", "G", symbolTU);

    calculateIndirectAmbiguities();
	}
	
	
	protected void addAmbiguityDNARNACompounds() {
    addNucleotideCompound("M", "K",
        "A", "C");
    addNucleotideCompound("R", "Y",
        "A", "G");
    addNucleotideCompound("W", "W",
        "A", "T", "U");
    addNucleotideCompound("S", "S",
        "C", "G");
    addNucleotideCompound("Y", "R",
        "C", "T", "U");
    addNucleotideCompound("K", "M",
        "G", "T", "U");
    addNucleotideCompound("V", "B",
        "A", "C", "G");
    addNucleotideCompound("H", "D",
        "A", "C", "T", "U");
    addNucleotideCompound("D", "H",
        "A", "G", "T", "U");
    addNucleotideCompound("B", "V",
        "C", "G", "T", "U");
    addNucleotideCompound("N", "N",
        "A", "C", "G", "T", "U");

    calculateIndirectAmbiguities();
	}
}
