package info.bioinfweb.biojava3.core.sequence.views;


import java.util.Iterator;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public class UpperCaseSequenceView<C extends Compound> extends SequenceProxyView<C> 
    implements SequenceView<C>, CountableSequenceView {
	
	private CompoundSet<? extends C> compoundSet;


	public UpperCaseSequenceView(Sequence<C> sequence, Integer bioStart, Integer bioEnd, CompoundSet<? extends C> compoundSet) {
		super(sequence, bioStart, bioEnd);
		this.compoundSet = compoundSet;
	}


	public UpperCaseSequenceView(Sequence<C> sequence, CompoundSet<? extends C> compoundSet) {
		super(sequence);
		this.compoundSet = compoundSet;
	}


	@Override
	public C getCompoundAt(int position) {
		C compound = super.getCompoundAt(position);
		String upperCase = compound.getShortName().toUpperCase();
		if (compound.getShortName().equals(upperCase)) {
			return compound;
		}
		else {
			return compoundSet.getCompoundForString(upperCase);
		}
	}	
	
	
	public int countChangedCompounds() {
		int result = 0;
		Iterator<C> iterator = getViewedSequence().iterator();
		int pos = 1;  // BioJava sequences start with 1. 
		while (iterator.hasNext() && (pos <= getBioEnd())) {
			C compound = iterator.next();
			if ((pos >= getBioStart()) && (compound.getShortName() != compound.getShortName().toUpperCase())) {
				result++;
			}
			pos++;
		}
		return result;
	}
}
