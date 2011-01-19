package info.bioinfweb.biojavax.core.sequence.views;


import java.util.Arrays;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



/**
 * Implementation of the {@link SequenceView} interface which provides access 
 * to an aligned sequence (with gaps) as if it would be unaligned (without 
 * gaps). 
 * 
 * @author Ben St&ouml;ver
 */
public class UnalignedSequenceView<C extends Compound> extends SequenceProxyView<C> implements SequenceView<C> {
	private int[] alignedPositions;
	private CompoundSet<C> nonGapCompoundSet;
	

	//TODO Overwrite more necessary methods
	
	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 * @param bioStart - start index, cannot be less than 1
	 * @param bioEnd - end index, cannot be greater than the sequence length
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet, Integer bioStart, Integer bioEnd) {
		super(sequence, bioStart, bioEnd);
		this.nonGapCompoundSet = nonGapCompoundSet;
		init();
	}

	
	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet) {
		super(sequence);
		this.nonGapCompoundSet = nonGapCompoundSet;
		init();
	}
	
	
	private void init() {
		createPosTraslation();
	}

	
	private void createPosTraslation() {
		alignedPositions = new int[getViewedSequence().getLength()];
		
		int unalignedPos = 0;
		for (int alignedPos = 1; alignedPos <= getViewedSequence().getLength(); alignedPos++) {
			if (nonGapCompoundSet.hasCompound(getViewedSequence().getCompoundAt(alignedPos))) {
				alignedPositions[unalignedPos] = alignedPos;
				unalignedPos++;
			}
		}
		alignedPositions = Arrays.copyOf(alignedPositions, unalignedPos);  // Shorten array to the necessary length
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getCompoundAt(int)
	 */
	@Override
	public C getCompoundAt(int position) {
		return getViewedSequence().getCompoundAt(alignedPositions[position - 1]);
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getLength()
	 */
	@Override
	public int getLength() {
		return alignedPositions.length;
	}


	/**
	 * Returns the set of compounds representing non-gap positions in the underlying sequence.
	 */
	public CompoundSet<C> getNonGapCompoundSet() {
		return nonGapCompoundSet;
	}
}
