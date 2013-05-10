package info.bioinfweb.biojava3.core.sequence.views;


import info.bioinfweb.biojava3.core.sequence.compound.AmbiguityNoGapNucleotideCompoundSet;

import java.util.HashMap;
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
    implements SequenceView<NucleotideCompound> {
	
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

			
	private Map<NucleotideCompound, NucleotideCompound> replacementMap;
	
	
	public ReplaceNucleotideSequenceView(Sequence<NucleotideCompound> sequence, 
			Map<NucleotideCompound, NucleotideCompound> replacementMap) {
		
		super(sequence);
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
}
