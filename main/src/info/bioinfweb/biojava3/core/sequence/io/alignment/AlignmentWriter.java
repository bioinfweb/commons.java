package info.bioinfweb.biojava3.core.sequence.io.alignment;


import info.bioinfweb.biojava3.core.sequence.template.Alignment;

import java.io.File;
import java.io.OutputStream;



public interface AlignmentWriter {
  public void write(Alignment alignment, OutputStream stream) throws Exception;

  public void write(Alignment alignment, File file) throws Exception;
}
