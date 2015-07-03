package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import java.util.Map;

import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.AbstractSequence;



public class ReplaceAbstractSequenceView extends ReplaceNucleotideSequenceView {
	public ReplaceAbstractSequenceView(AbstractSequence<NucleotideCompound> sequence,
			Map<NucleotideCompound, NucleotideCompound> replacementMap) {
		
		super(sequence, replacementMap);
	}

	
	public String getOriginalHeader() {
		return ((AbstractSequence<NucleotideCompound>)getViewedSequence()).getOriginalHeader();
	}
}
