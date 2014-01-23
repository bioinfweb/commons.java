package info.bioinfweb.biojava3.core.sequence.views;



/**
 * Should be implemented by all sequence views that e.g. replace, delete or add a certain number of compounds to/from
 * the underlying sequence.
 *  
 * @author Ben St&ouml;ver
 */
public interface CountableSequenceView {
	public int countChangedCompounds();
}
