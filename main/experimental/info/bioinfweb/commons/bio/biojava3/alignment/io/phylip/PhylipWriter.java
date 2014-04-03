package info.bioinfweb.commons.bio.biojava3.alignment.io.phylip;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.text.UniqueNameMap;

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
  public static int DEFAULT_MAX_NAME_LENGTH = 10;
  
  
  public PhylipWriter() {
		this(true, DEFAULT_MAX_NAME_LENGTH);
	}


  public PhylipWriter(boolean replaceWhiteSpace) {
		this(replaceWhiteSpace, DEFAULT_MAX_NAME_LENGTH);
	}


	public PhylipWriter(boolean replaceWhiteSpace, int maxNameLength) {
		super();
		getNameMap().getParameters().setMaxNameLength(maxNameLength);
		if (replaceWhiteSpace) {
			getNameMap().getParameters().getReplacements().put("\\s", Character.toString(WHITESPACE_REPLACEMENT));
		}
		getNameMap().getParameters().setFillUp(true);
	}

	
	/**
   * Note that certain parameter sets for the specified name map might lead to an invalid output of this class. 
   * 
   * @param nameMap
   */
  public PhylipWriter(UniqueNameMap nameMap) {
		super(nameMap);
	}


	/**
   * Writes the specified alignment in sequential Phylip format.
   * 
   * @see info.bioinfweb.commons.bio.biojava3.alignment.io.AlignmentWriter#write(info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment, java.io.OutputStream)
   */
  @Override
	public void write(Alignment<S, C> alignment, OutputStream stream) throws Exception {
    getNameMap().clear();
    PrintWriter writer = new PrintWriter(stream);
    writer.println(" " + alignment.size() + " " + alignment.maxSequenceLength());
    Iterator<String> iterator = alignment.nameIterator();
    while (iterator.hasNext()) {
    	String name = iterator.next();
    	writer.println(getNameMap().addName(name) + " " + alignment.getSequence(name).getSequenceAsString());
    }
    writer.flush();		
	}
}
