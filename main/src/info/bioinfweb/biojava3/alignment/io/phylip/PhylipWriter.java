package info.bioinfweb.biojava3.alignment.io.phylip;


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

import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojavax.bio.phylo.io.phylip.PHYLIPFileFormat;



/**
 * Writes an alignment to a file in PHYLIP format.
 * (This class is based on the implementation of the BioJava 1.8 class {@link PHYLIPFileFormat} written by
 * Richard Holland, Tobias Thierer and Jim Balhoff, who provided the source code under LGPL.) 
 * 
 * @author Ben St&ouml;ver
 */
public class PhylipWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
  public static int MAX_NAME_LENGTH = 10;
  
  
  private Map<String, String> nameMap = new TreeMap<String, String>();
  
  
  private String formatSequenceName(String name) {
  	String result;
    if (name.length() > MAX_NAME_LENGTH) {
      result = name.substring(0, MAX_NAME_LENGTH);
    } 
    else if (name.length() < MAX_NAME_LENGTH) {
      StringBuffer buffer = new StringBuffer(MAX_NAME_LENGTH);
      buffer.append(name);
      while (buffer.length() < MAX_NAME_LENGTH) {
        buffer.append(" ");
      }
      result = buffer.toString();
    }
    else {
      result = name;
    }
    
    int index = 1;
    while (nameMap.containsKey(result)) {
    	String indexStr = "" + index;
    	result = result.substring(0, MAX_NAME_LENGTH - indexStr.length()) + indexStr;
    }
    nameMap.put(result, name);
    
    return result;
  }

  
  /**
   * Writes the specified alignment in sequential Phylip format.
   * 
   * @see info.bioinfweb.biojava3.alignment.io.AlignmentWriter#write(info.bioinfweb.biojava3.alignment.template.Alignment, java.io.OutputStream)
   */
  @Override
	public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception {
    nameMap.clear();
    PrintWriter writer = new PrintWriter(stream);
    writer.println(" " + alignment.size() + " " + alignment.maxSequenceLength());
    Iterator<String> iterator = alignment.nameIterator();
    while (iterator.hasNext()) {
    	String name = iterator.next();
    	writer.println(formatSequenceName(name) + " " + alignment.getSequence(name).getSequenceAsString());
    }
    writer.flush();		
	}

	
  /**
   * Returns the name map that resulted from the last call of {@link #write(Alignment, OutputStream)}.
   * The shortened Phylip names are the keys and the complete names are the values in this map.
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
