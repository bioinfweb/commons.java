package info.bioinfweb.biojava3.core.sequence.views;


import java.util.Arrays;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public abstract class RemoveSequenceView<C extends Compound> extends SequenceProxyView<C> implements SequenceView<C> {
	private int[] remainingPositions = null;
	
	
	public RemoveSequenceView(Sequence<C> sequence, Integer bioStart,	Integer bioEnd) {
		super(sequence, bioStart, bioEnd);
	}


	public RemoveSequenceView(Sequence<C> sequence) {
		super(sequence);
	}

	
	protected abstract boolean keepPosition(int viewedPos);
	

	private void createPosTraslation() {
		remainingPositions = new int[getViewedSequence().getLength()];
		int arrayPos = 0;
		for (int viewedPos = 1; viewedPos <= getViewedSequence().getLength(); viewedPos++) {
			if (keepPosition(viewedPos)) {
				remainingPositions[arrayPos] = viewedPos;
				arrayPos++;
			}
		}
		remainingPositions = Arrays.copyOf(remainingPositions, arrayPos);  // Shorten array to the necessary length
	}


	protected int[] getRemainingPositions() {
		if (remainingPositions == null) {
			createPosTraslation();
		}
		return remainingPositions;
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getCompoundAt(int)
	 */
	@Override
	public C getCompoundAt(int position) {
		return super.getCompoundAt(getRemainingPositions()[position - 1]);  // The super method takes account of bioStart
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getLength()
	 */
	@Override
	public int getLength() {
		return getRemainingPositions().length;
	}
}
