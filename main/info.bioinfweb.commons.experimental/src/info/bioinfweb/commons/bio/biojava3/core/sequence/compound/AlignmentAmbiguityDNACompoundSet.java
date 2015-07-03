package info.bioinfweb.commons.bio.biojava3.core.sequence.compound;



/**
 * Extension of {@link AlignmentAmbiguityDNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 * @since build 8
 */
public class AlignmentAmbiguityDNACompoundSet extends AmbiguityNoGapDNACompoundSet {
	private static AlignmentAmbiguityDNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityDNACompoundSet() {
		super();
		addNucleotideCompound("" + AlignmentAmbiguityNucleotideCompoundSet.GAP_CHARACTER, 
				"" + AlignmentAmbiguityNucleotideCompoundSet.GAP_CHARACTER);
		addNucleotideCompound("" + AlignmentAmbiguityNucleotideCompoundSet.UNKNOWN_CHARACTER, 
				"" + AlignmentAmbiguityNucleotideCompoundSet.UNKNOWN_CHARACTER);
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AlignmentAmbiguityDNACompoundSet getAlignmentAmbiguityDNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AlignmentAmbiguityDNACompoundSet();
		}
		return sharedInstance;
	}
}
