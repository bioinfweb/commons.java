package info.bioinfweb.biojavax.core.sequence.compound;


import info.bioinfweb.biojavax.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Same as {@link NucleotideCompoundSet} but also includes the gap character("-").
 * 
 * @author Ben St&ouml;ver
 */
public class NucleotideCompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NucleotideCompoundSet() {
		super();
    addNucleotideCompound("-", "-");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NucleotideCompoundSet getNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
