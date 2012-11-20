package info.bioinfweb.biojava3.core.sequence.views;


import java.util.Map;

import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public class ReplaceNucleotideSequenceView extends SequenceProxyView<NucleotideCompound> 
    implements SequenceView<NucleotideCompound> {
	
	private Map<NucleotideCompound, NucleotideCompound> replacementMap;
	
	
	public ReplaceNucleotideSequenceView(Sequence<NucleotideCompound> sequence, 
			Map<NucleotideCompound, NucleotideCompound> replacementMap) {
		
		super(sequence);
		this.replacementMap = replacementMap;
	}

	
	@Override
	public NucleotideCompound getCompoundAt(int position) {
		NucleotideCompound compound = super.getCompoundAt(position);
		NucleotideCompound replacement = replacementMap.get(compound);
		if (replacement == null) {
			return compound;
		}
		else {
			return replacement;
		}
	}		
}
