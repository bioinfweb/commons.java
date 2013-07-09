package info.bioinfweb.biojava3.core.sequence.views;


import java.util.Arrays;

import info.webinsel.util.collections.NonOverlappingIntervalList;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public class RemovedCharSetSequenceView<C extends Compound> extends SequenceProxyView<C> implements SequenceView<C> {
	private int[] remainingPositions;
	
	
	public RemovedCharSetSequenceView(Sequence<C> sequence, Integer bioStart,	Integer bioEnd, NonOverlappingIntervalList charSet) {
		super(sequence, bioStart, bioEnd);
		createPosTraslation(charSet);
	}


	public RemovedCharSetSequenceView(Sequence<C> sequence, NonOverlappingIntervalList charSet) {
		super(sequence);
		createPosTraslation(charSet);
	}


	private void createPosTraslation(NonOverlappingIntervalList charSet) {
		remainingPositions = new int[getViewedSequence().getLength()];
		int arrayPos = 0;
		for (int viewedPos = 1; viewedPos <= getViewedSequence().getLength(); viewedPos++) {
			if (!charSet.contains(viewedPos)) {
				remainingPositions[arrayPos] = viewedPos;
				arrayPos++;
			}
		}
		remainingPositions = Arrays.copyOf(remainingPositions, arrayPos);  // Shorten array to the necessary length
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getCompoundAt(int)
	 */
	@Override
	public C getCompoundAt(int position) {
		return super.getCompoundAt(remainingPositions[position - 1]);  // The super method takes account of bioStart
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getLength()
	 */
	@Override
	public int getLength() {
		return remainingPositions.length;
	}
}
