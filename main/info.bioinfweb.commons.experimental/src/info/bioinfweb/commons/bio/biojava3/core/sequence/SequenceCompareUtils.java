/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.core.sequence;


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

import java.util.Iterator;

import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.AbstractCompoundSet;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Helper class that allows to compare sequences by taking ambiguity codes into account or not.
 * 
 * @author Ben St&ouml;ver
 */
public class SequenceCompareUtils {
  /**
   * Tests if the two symbols at each according position of the two sequences are exactly equal. 
   * (If ambiguity codes are contained they have to be identical at the according positions. 
   * Example: {@code N == N}, but {@code N != B}) <i>T</i> and <i>U</i> are not considered 
   * equal. 
   * 
   * @param sequence1
   * @param sequence2
   * @return
   */
  public static boolean sequencesEqual(Sequence<? extends Compound> sequence1, Sequence<? extends Compound> sequence2) {
  	boolean result = sequence1.getLength() == sequence2.getLength();
  	if (result) {
    	Iterator<? extends Compound> iterator1 = sequence1.iterator();
    	Iterator<? extends Compound> iterator2 = sequence2.iterator();
    	while (result && iterator1.hasNext()) {
    		result = result && iterator1.next().equals(iterator2.next());
    	}
  	}
  	return result;
  }

	
  
  /**
   * Uses the {@link AbstractCompoundSet#compoundsEquivalent(Compound, Compound)} method of the specified compound set
   * to check if the values at each position of the two sequences are equal. 
   * 
   * @param set
   * @param sequence1
   * @param sequence2
   * @return
   */
  public static <C extends Compound, S extends Sequence<C>> boolean sequencesEquivalent(CompoundSet<? super C> set, 
  		S sequence1, S sequence2) {
  	
  	boolean result = sequence1.getLength() == sequence2.getLength();
  	if (result) {
    	Iterator<C> iterator1 = sequence1.iterator();
    	Iterator<C> iterator2 = sequence2.iterator();
    	while (result && iterator1.hasNext()) {
    		result = result && set.compoundsEquivalent(iterator1.next(), iterator2.next());
    	}
  	}
  	return result;
  }


  /**
   * The two sequences are considered equivalent of the symbols on each position match. If ambiguity codes are contained
   * they are considered equal if one set is completely contained in the other. <i>T</i> and <i>U</i> are considered 
   * equivalent. 
   * This method uses {@link AlignmentAmbiguityNucleotideCompoundSet} internally.
   * 
   * @param sequence1
   * @param sequence2
   * @return
   */
  public static <C extends NucleotideCompound, S extends Sequence<C>> boolean nucleotideSequencesEquivalent(
  		S sequence1, S sequence2) {

  	return sequencesEquivalent(AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet(), 
  			sequence1, sequence2);
  }
}
