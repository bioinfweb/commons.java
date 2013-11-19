package info.bioinfweb.biojava3.alignment.io.phylip;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import info.bioinfweb.biojava3.alignment.io.NameMapWriter;
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
public class PhylipWriter<S extends Sequence<C>, C extends Compound> extends NameMapWriter<S, C> {
  public static int DEFAULT_MAX_NAME_LENGTH = 10;
  public static char WHITESPACE_REPLACEMENT = '_'; 
  
  
  private int maxNameLength = DEFAULT_MAX_NAME_LENGTH;
  private boolean replaceWhiteSpace = false;
  
  
  public PhylipWriter() {
		super();
	}


  public PhylipWriter(boolean replaceWhiteSpace) {
		super();
		this.replaceWhiteSpace = replaceWhiteSpace;
	}


	public PhylipWriter(boolean replaceWhiteSpace, int maxNameLength) {
		super();
		this.maxNameLength = maxNameLength;
		this.replaceWhiteSpace = replaceWhiteSpace;
	}


  public int getMaxNameLength() {
		return maxNameLength;
	}


	public boolean getReplaceWhiteSpace() {
		return replaceWhiteSpace;
	}


	private String formatSequenceName(String name) {
  	String result = name;
  	if (getReplaceWhiteSpace()) {
  		result = name.replaceAll("\\s", "" + WHITESPACE_REPLACEMENT);
  	}  	
  	
    if (result.length() > maxNameLength) {
      result = result.substring(0, maxNameLength);
    } 
    else if (result.length() < maxNameLength) {
      StringBuffer buffer = new StringBuffer(maxNameLength);
      buffer.append(result);
      while (buffer.length() < maxNameLength) {
        buffer.append(" ");
      }
      result = buffer.toString();
    }
    
    int index = 1;
    while (getNameMap().containsKey(result)) {
    	String indexStr = "" + index;
    	result = result.substring(0, maxNameLength - indexStr.length()) + indexStr;
    	index++;
    }
    getNameMap().put(result, name);
    
    return result;
  }

  
  /**
   * Writes the specified alignment in sequential Phylip format.
   * 
   * @see info.bioinfweb.biojava3.alignment.io.AlignmentWriter#write(info.bioinfweb.biojava3.alignment.template.Alignment, java.io.OutputStream)
   */
  @Override
	public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception {
    getNameMap().clear();
    PrintWriter writer = new PrintWriter(stream);
    writer.println(" " + alignment.size() + " " + alignment.maxSequenceLength());
    Iterator<String> iterator = alignment.nameIterator();
    while (iterator.hasNext()) {
    	String name = iterator.next();
    	writer.println(formatSequenceName(name) + " " + alignment.getSequence(name).getSequenceAsString());
    }
    writer.flush();		
	}
}
