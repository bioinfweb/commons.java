package info.bioinfweb.commons.bio.biojava3.core.sequence.compound;


import info.bioinfweb.commons.bio.biojava3.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Nucleotide compound set that contains all DNA and RNA compounds but no gap <i>-</i> or ambiguity character.
 * 
 * @author Ben St&ouml;ver
 */
public class NoGapNucleotideCompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NoGapNucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NoGapNucleotideCompoundSet() {
		super();
    addNucleotideCompound("A", "T");
    addNucleotideCompound("T", "A");
    addNucleotideCompound("G", "C");
    addNucleotideCompound("C", "G");
    addNucleotideCompound("U", "A");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NoGapNucleotideCompoundSet getNoGapNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NoGapNucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
