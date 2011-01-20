package info.bioinfweb.biojava3.core.sequence.compound;


import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;



/**
 * Nucleotide compound set that contains all DNA and RNA compounds and ambiguity codes 
 * but no gap <i>-</i> character.
 * 
 * @author Ben St&ouml;ver
 */
public class AmbiguityNoGapNucleotideCompoundSet extends NoGapNucleotideCompoundSet {
	private static AmbiguityNoGapNucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class. (Method copied from {@link AmbiguityDNACompoundSet}.)
	 */
	public AmbiguityNoGapNucleotideCompoundSet() {
		super();
		addAmbiguityCompounds();
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AmbiguityNoGapNucleotideCompoundSet getAmbiguityNoGapNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AmbiguityNoGapNucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
