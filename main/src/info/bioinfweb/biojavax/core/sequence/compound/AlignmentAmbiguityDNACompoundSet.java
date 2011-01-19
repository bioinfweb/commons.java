package info.bioinfweb.biojavax.core.sequence.compound;


import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;



/**
 * Extension of {@link AlignmentAmbiguityDNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 */
public class AlignmentAmbiguityDNACompoundSet extends AmbiguityDNACompoundSet {
	private static AlignmentAmbiguityDNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityDNACompoundSet() {
		super();
		addNucleotideCompound("?", "?");
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
