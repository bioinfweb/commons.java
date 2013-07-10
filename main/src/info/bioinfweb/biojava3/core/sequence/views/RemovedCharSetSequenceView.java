package info.bioinfweb.biojava3.core.sequence.views;


import info.webinsel.util.collections.NonOverlappingIntervalList;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceView;



public class RemovedCharSetSequenceView<C extends Compound> extends RemoveSequenceView<C> implements SequenceView<C> {
	private NonOverlappingIntervalList charSet;
	
	
	public RemovedCharSetSequenceView(Sequence<C> sequence, Integer bioStart,	Integer bioEnd, 
			NonOverlappingIntervalList charSet) {
		
		super(sequence, bioStart, bioEnd);
		this.charSet = charSet;
	}


	public RemovedCharSetSequenceView(Sequence<C> sequence, NonOverlappingIntervalList charSet) {
		super(sequence);
		this.charSet = charSet;
	}


	@Override
	protected boolean keepPosition(int viewedPos) {
		return !charSet.contains(viewedPos);
	}
}
