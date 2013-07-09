package info.bioinfweb.biojava3.alignment.io;


import info.bioinfweb.biojava3.alignment.template.Alignment;

import java.io.File;
import java.io.OutputStream;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public interface AlignmentWriter<S extends Sequence<C>, C extends Compound> {
  public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception;

  public void write(Alignment<S, C> alignment, File file) throws Exception;
}
