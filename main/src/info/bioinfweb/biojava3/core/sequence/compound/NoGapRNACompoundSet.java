package info.bioinfweb.biojava3.core.sequence.compound;


import org.biojava3.core.sequence.compound.RNACompoundSet;

import info.bioinfweb.biojava3.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Nucleotide compound set that contains all RNA compounds but no gap <i>-</i> character (and without <i>N</i>).
 * ({@link RNACompoundSet} contains both these symbols.)
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public class NoGapRNACompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NoGapRNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NoGapRNACompoundSet() {
		super();
    addNucleotideCompound("A", "T");
    addNucleotideCompound("T", "A");
    addNucleotideCompound("G", "C");
    addNucleotideCompound("C", "G");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NoGapRNACompoundSet getNoGapRNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NoGapRNACompoundSet();
		}
		return sharedInstance;
	}
}
