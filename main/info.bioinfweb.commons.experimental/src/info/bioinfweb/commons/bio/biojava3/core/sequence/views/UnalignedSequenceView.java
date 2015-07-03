package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceView;



/**
 * Implementation of the {@link SequenceView} interface which provides access 
 * to an aligned sequence (with gaps) as if it would be unaligned (without 
 * gaps). 
 * 
 * @author Ben St&ouml;ver
 */
public class UnalignedSequenceView<C extends Compound> extends RemoveSequenceView<C> implements SequenceView<C> {
	private CompoundSet<C> nonGapCompoundSet;
	

	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 * @param bioStart - start index, cannot be less than 1
	 * @param bioEnd - end index, cannot be greater than the sequence length
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet, 
			Integer bioStart, Integer bioEnd) {
		
		super(sequence, bioStart, bioEnd);
		this.nonGapCompoundSet = nonGapCompoundSet;
	}

	
	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet) {
		super(sequence);
		this.nonGapCompoundSet = nonGapCompoundSet;
	}
	
	
	@Override
	protected boolean keepPosition(int viewedPos) {
		return nonGapCompoundSet.hasCompound(getViewedSequence().getCompoundAt(viewedPos));
	}


	/**
	 * Returns the set of compounds representing non-gap positions in the underlying sequence.
	 */
	public CompoundSet<C> getNonGapCompoundSet() {
		return nonGapCompoundSet;
	}
}
