package info.bioinfweb.biojava3.core.sequence.io.alignment;


import java.io.File;
import java.io.InputStream;

import info.bioinfweb.biojava3.core.sequence.template.Alignment;



public interface AlignmentReader {
  public Alignment read(InputStream stream) throws Exception;

  public Alignment read(File file) throws Exception;
}
