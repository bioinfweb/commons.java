package info.bioinfweb.biojava3.core.sequence.compound;



/**
 * Extension of {@link AlignmentAmbiguityRNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 */
public class AlignmentAmbiguityNucleotideCompoundSet extends AmbiguityNoGapNucleotideCompoundSet {
	public static final char GAP_CHARACTER = '-'; 
	public static final char UNKNOWN_CHARACTER = '?'; 
	
	private static AlignmentAmbiguityNucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityNucleotideCompoundSet() {
		super();
		addNucleotideCompound("" + GAP_CHARACTER, "" + GAP_CHARACTER);
		addNucleotideCompound("" + UNKNOWN_CHARACTER, "" + UNKNOWN_CHARACTER);
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AlignmentAmbiguityNucleotideCompoundSet getAlignmentAmbiguityNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AlignmentAmbiguityNucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
