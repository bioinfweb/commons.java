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
  
  
  public PhylipWriter() {
		super(true, DEFAULT_MAX_NAME_LENGTH);
	}


  public PhylipWriter(boolean replaceWhiteSpace) {
		super(replaceWhiteSpace, DEFAULT_MAX_NAME_LENGTH);
	}


	public PhylipWriter(boolean replaceWhiteSpace, int maxNameLength) {
		super(replaceWhiteSpace, maxNameLength);
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
    	writer.println(formatSequenceName(name, true) + " " + alignment.getSequence(name).getSequenceAsString());
    }
    writer.flush();		
	}
}
