package info.bioinfweb.biojavax.core.sequence.compound;


import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;



/**
 * Extension of {@link AlignmentAmbiguityRNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 */
public class AlignmentAmbiguityRNACompoundSet extends AmbiguityDNACompoundSet {
	private static AlignmentAmbiguityRNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityRNACompoundSet() {
		super();
		addNucleotideCompound("?", "?");
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
