package info.bioinfweb.biojava3.alignment.io;


import info.bioinfweb.biojava3.alignment.template.Alignment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Implements functionality for all writers that may have to modify their taxon names according to the limitations
 * of their format.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <S> - the sequence type to be written
 * @param <C> - the compound type to be written
 */
public abstract class NameMapWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	private Map<String, String> nameMap = new TreeMap<String, String>();

	
  /**
   * Returns the name map that resulted from the last call of {@link #write(Alignment, OutputStream)}.
   * The new names (written to the alignment file) are the keys and the old names are the values in this map.
   */
  public Map<String, String> getNameMap() {
  	return nameMap;
  }
  
  
  /**
   * Writes the contents of the current name map in text format as tab separated values. Every line
   * contains one name pair, where the first column contains the Phylip name and the second column 
   * the full name.
   * 
   * @param stream - the stream to write the table to
   */
  public void writeNameMap(OutputStream stream) {
  	PrintWriter writer = new PrintWriter(stream);
  	Iterator<String> iterator = nameMap.keySet().iterator();
  	while (iterator.hasNext()) {
  		String phylipName = iterator.next();
  		writer.println(phylipName + "\t" + nameMap.get(phylipName));
  	}
  	writer.flush();
  }
  
  
  /**
   * Writes the contents of the current name map in text format as tab separated values. Every line
   * contains one name pair, where the first column contains the Phylip name and the second column 
   * the full name.
   * 
   * @param file - the file to write the table to
   * @throws FileNotFoundException - if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
   * @throws SecurityException - if a security manager exists and its checkWrite method denies write access to the file.
   */
  public void writeNameMap(File file) throws FileNotFoundException, IOException {
  	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file)); 
  	try {
  		writeNameMap(stream);
  	}
  	finally {
  		stream.close();
  	}
  }
}
