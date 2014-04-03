package info.bioinfweb.commons.bio.biojava3.core.sequence.compound;



/**
 * Extension of {@link AlignmentAmbiguityRNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 * @since build 8
 */
public class AlignmentAmbiguityRNACompoundSet extends AmbiguityNoGapRNACompoundSet {
	private static AlignmentAmbiguityRNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityRNACompoundSet() {
		super();
		addNucleotideCompound("" + AlignmentAmbiguityNucleotideCompoundSet.GAP_CHARACTER, 
				"" + AlignmentAmbiguityNucleotideCompoundSet.GAP_CHARACTER);
		addNucleotideCompound("" + AlignmentAmbiguityNucleotideCompoundSet.UNKNOWN_CHARACTER, 
				"" + AlignmentAmbiguityNucleotideCompoundSet.UNKNOWN_CHARACTER);
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AlignmentAmbiguityRNACompoundSet getAlignmentAmbiguityRNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AlignmentAmbiguityRNACompoundSet();
		}
		return sharedInstance;
	}
}
