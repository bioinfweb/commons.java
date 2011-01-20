package info.bioinfweb.biojavax.core.sequence.compound;


import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;



/**
 * Nucleotide compound set that contains all RNA compounds and ambiguity codes but no gap <i>-</i> character.
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public class AmbiguityNoGapRNACompoundSet extends NoGapDNACompoundSet {
	private static AmbiguityNoGapRNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class. (Method copied from {@link AmbiguityDNACompoundSet}.)
	 */
	public AmbiguityNoGapRNACompoundSet() {
		super();
		addAmbiguityCompounds();
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AmbiguityNoGapRNACompoundSet getAmbiguityNoGapRNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AmbiguityNoGapRNACompoundSet();
		}
		return sharedInstance;
	}
}
