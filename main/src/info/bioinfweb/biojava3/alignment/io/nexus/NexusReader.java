package info.bioinfweb.biojava3.alignment.io.nexus;


import java.io.InputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;

import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentReader;
import info.bioinfweb.biojava3.alignment.template.Alignment;



public class NexusReader<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentReader<S, C> {
	@Override
	public Alignment<S, C> read(InputStream stream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
