package info.bioinfweb.biojava3.alignment.io;


import java.io.File;
import java.io.InputStream;

import info.bioinfweb.biojava3.alignment.Alignment;



public interface AlignmentReader {
  public Alignment read(InputStream stream) throws Exception;

  public Alignment read(File file) throws Exception;
}
