package info.bioinfweb.biojava3.core.sequence.compound;


import info.bioinfweb.biojava3.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Nucleotide compound set that contains all DNA compounds but no gap <i>-</i> character (and without <i>N</i>).
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public class NoGapDNACompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NoGapDNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NoGapDNACompoundSet() {
		super();
    addNucleotideCompound("A", "T");
    addNucleotideCompound("T", "A");
    addNucleotideCompound("G", "C");
    addNucleotideCompound("C", "G");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NoGapDNACompoundSet getNoGapDNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NoGapDNACompoundSet();
		}
		return sharedInstance;
	}
}
