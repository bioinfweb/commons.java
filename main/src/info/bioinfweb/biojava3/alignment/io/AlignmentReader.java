package info.bioinfweb.biojava3.alignment.io;


import java.io.File;
import java.io.InputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;

import info.bioinfweb.biojava3.alignment.template.Alignment;



public interface AlignmentReader<S extends Sequence<C>, C extends Compound> {
  public Alignment<S, C> read(InputStream stream) throws Exception;

  public Alignment<S, C> read(File file) throws Exception;
}
