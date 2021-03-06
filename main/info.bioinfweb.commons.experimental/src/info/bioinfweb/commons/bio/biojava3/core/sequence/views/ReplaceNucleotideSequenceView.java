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


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;
import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AmbiguityNoGapNucleotideCompoundSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



/**
 * Sequence view that replaces some types of nucleotides with others according to a specified translation 
 * map. This implementation additionally provides some constant maps with should not be modified during runtime.
 *   
 * @author Ben St&ouml;ver
 */
public class ReplaceNucleotideSequenceView extends SequenceProxyView<NucleotideCompound> 
    implements SequenceView<NucleotideCompound>, CountableSequenceView {
	
	/** Replaces <i>U</i> with <i>T</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> RNA_TO_DNA_MAP = createRNAToDNAMap();
		
	/** Replaces <i>T/i> with <i>U</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> DNA_TO_RNA_MAP = createDNAToRNAMap();
	
	/** Replaces all other ambiguity codes with <i>N</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> AMBIGUITY_TO_N_MAP = createAmbiguityToNMap();
	
	/** Replaces all other ambiguity codes with <i>N</i> and <i>U</i> with <i>T</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> AMBIGUITY_RNA_TO_N_DNA_MAP = createAmbiguityRNAToNDNAMap();
	
	/** Replaces all other ambiguity codes with <i>N</i> and <i>T</i> with <i>U</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> AMBIGUITY_DNA_TO_N_RNA_MAP = createAmbiguityDNAToNRNAMap();
	
	/** Replaces <i>?</i> with <i>N</i> and <i>U</i> with <i>T</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> UNKNOWN_RNA_TO_N_DNA_MAP = createUnknownRNAToNDNAMap();
	
	/** Replaces <i>?</i> with <i>N</i> and <i>T</i> with <i>U</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> UNKNOWN_DNA_TO_N_RNA_MAP = createUnknownDNAToNRNAMap();
	
	/** Replaces all other ambiguity codes and <i>?</i> with <i>N</i> and <i>U</i> with <i>T</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> AMBIGUITY_UNKNOWN_RNA_TO_N_DNA_MAP = createAmbiguityUnknownRNAToNDNAMap();
	
	/** Replaces all other ambiguity codes and <i>?</i> with <i>N</i> and <i>T</i> with <i>U</i>. */
	public static final Map<NucleotideCompound, NucleotideCompound> AMBIGUITY_UNKNOWN_DNA_TO_N_RNA_MAP = createAmbiguityUnknownDNAToNRNAMap();
	
	
	private static Map<NucleotideCompound, NucleotideCompound> createRNAToDNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = new HashMap<NucleotideCompound, NucleotideCompound>();
		AmbiguityNoGapNucleotideCompoundSet cs = AmbiguityNoGapNucleotideCompoundSet.getAmbiguityNoGapNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("U"), cs.getCompoundForString("T"));
		return result;
	}

	
	private static Map<NucleotideCompound, NucleotideCompound> createDNAToRNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = new HashMap<NucleotideCompound, NucleotideCompound>();
		AmbiguityNoGapNucleotideCompoundSet cs = AmbiguityNoGapNucleotideCompoundSet.getAmbiguityNoGapNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("T"), cs.getCompoundForString("U"));
		return result;
	}

	
	private static Map<NucleotideCompound, NucleotideCompound> createAmbiguityToNMap() {
		Map<NucleotideCompound, NucleotideCompound> result = new HashMap<NucleotideCompound, NucleotideCompound>();
		AmbiguityNoGapNucleotideCompoundSet cs = AmbiguityNoGapNucleotideCompoundSet.getAmbiguityNoGapNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("Y"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("R"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("M"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("W"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("S"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("K"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("V"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("H"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("D"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("B"), cs.getCompoundForString("N"));
		result.put(cs.getCompoundForString("X"), cs.getCompoundForString("N"));
		return result;
	}

			
	private static Map<NucleotideCompound, NucleotideCompound> createAmbiguityRNAToNDNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createAmbiguityToNMap();
		AmbiguityNoGapNucleotideCompoundSet cs = AmbiguityNoGapNucleotideCompoundSet.getAmbiguityNoGapNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("U"), cs.getCompoundForString("T"));
		return result;
	}

			
	private static Map<NucleotideCompound, NucleotideCompound> createAmbiguityDNAToNRNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createAmbiguityToNMap();
		AmbiguityNoGapNucleotideCompoundSet cs = AmbiguityNoGapNucleotideCompoundSet.getAmbiguityNoGapNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("T"), cs.getCompoundForString("U"));
		return result;
	}

			
	private static Map<NucleotideCompound, NucleotideCompound> createUnknownRNAToNDNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createRNAToDNAMap();
		AlignmentAmbiguityNucleotideCompoundSet cs = AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("?"), cs.getCompoundForString("N"));
		return result;
	}

	
	private static Map<NucleotideCompound, NucleotideCompound> createUnknownDNAToNRNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createDNAToRNAMap();
		AlignmentAmbiguityNucleotideCompoundSet cs = AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("?"), cs.getCompoundForString("N"));
		return result;
	}

	
	private static Map<NucleotideCompound, NucleotideCompound> createAmbiguityUnknownRNAToNDNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createAmbiguityRNAToNDNAMap();
		AlignmentAmbiguityNucleotideCompoundSet cs = AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("?"), cs.getCompoundForString("N"));
		return result;
	}

	
	private static Map<NucleotideCompound, NucleotideCompound> createAmbiguityUnknownDNAToNRNAMap() {
		Map<NucleotideCompound, NucleotideCompound> result = createAmbiguityDNAToNRNAMap();
		AlignmentAmbiguityNucleotideCompoundSet cs = AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet(); 
		result.put(cs.getCompoundForString("?"), cs.getCompoundForString("N"));
		return result;
	}

	
	private Map<NucleotideCompound, NucleotideCompound> replacementMap;
	
	
	public ReplaceNucleotideSequenceView(Sequence<NucleotideCompound> sequence, 
			Map<NucleotideCompound, NucleotideCompound> replacementMap) {
		
		super(sequence);
		this.replacementMap = replacementMap;
	}

	
	public ReplaceNucleotideSequenceView(Sequence<NucleotideCompound> sequence, 
			Map<NucleotideCompound, NucleotideCompound> replacementMap,	Integer bioStart, Integer bioEnd) {

		super(sequence, bioStart, bioEnd);
		this.replacementMap = replacementMap;
	}


	@Override
	public NucleotideCompound getCompoundAt(int position) {
		NucleotideCompound compound = super.getCompoundAt(position);
		NucleotideCompound replacement = replacementMap.get(compound);
		if (replacement == null) {
			return compound;
		}
		else {
			return replacement;
		}
	}	
	
	
	@Override
	public int countChangedCompounds() {
		int result = 0;
		Iterator<NucleotideCompound> iterator = getViewedSequence().iterator();
		int pos = 1;  // BioJava sequences start with 1. 
		while (iterator.hasNext() && (pos <= getBioEnd())) {
			NucleotideCompound compound = iterator.next();
			if ((pos >= getBioStart()) && (replacementMap.get(compound) != null)) {
				result++;
			}
			pos++;
		}
		return result;
	}
}
