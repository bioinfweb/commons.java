package info.bioinfweb.biojava3.alignment.io;


import info.bioinfweb.biojava3.alignment.template.Alignment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public abstract class AbstractAlignmentReader<S extends Sequence<C>, C extends Compound> 
    implements AlignmentReader<S, C> {
	
	@Override
	public Alignment<S, C> read(File file) throws Exception {
		return read(new BufferedInputStream(new FileInputStream(file)));
	}
}
