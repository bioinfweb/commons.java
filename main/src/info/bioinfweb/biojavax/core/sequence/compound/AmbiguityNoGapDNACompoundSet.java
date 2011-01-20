package info.bioinfweb.biojavax.core.sequence.compound;


import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;



/**
 * Nucleotide compound set that contains all DNA compounds and ambiguity codes but no gap <i>-</i> character.
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public class AmbiguityNoGapDNACompoundSet extends NoGapDNACompoundSet {
	private static AmbiguityNoGapDNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class. (Method copied from {@link AmbiguityDNACompoundSet}.)
	 */
	public AmbiguityNoGapDNACompoundSet() {
		super();
		addAmbiguityCompounds();
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AmbiguityNoGapDNACompoundSet getAmbiguityNoGapDNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AmbiguityNoGapDNACompoundSet();
		}
		return sharedInstance;
	}
}
