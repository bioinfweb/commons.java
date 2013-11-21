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
  public static int UNLIMITTED_LENGTH = -1;
	public static final String WHITESPACE_REPLACEMENT = "_";

	
	private Map<String, String> nameMap = new TreeMap<String, String>();
  private int maxNameLength = UNLIMITTED_LENGTH;
  private boolean replaceWhiteSpace = false;
  
  
  public NameMapWriter() {
		super();
	}


	public NameMapWriter(boolean replaceWhiteSpace) {
		super();
		this.replaceWhiteSpace = replaceWhiteSpace;
	}


	public NameMapWriter(boolean replaceWhiteSpace, int maxNameLength) {
		super();
		this.maxNameLength = maxNameLength;
		this.replaceWhiteSpace = replaceWhiteSpace;
	}


  /**
   * Returns the name map that resulted from the last call of {@link #write(Alignment, OutputStream)}.
   * The new names (written to the alignment file) are the keys and the old names are the values in this map.
   */
  public Map<String, String> getNameMap() {
  	return nameMap;
  }
  
  
  public int getMaxNameLength() {
		return maxNameLength;
	}


	public void setMaxNameLength(int maxNameLength) {
		this.maxNameLength = maxNameLength;
	}


	public boolean getReplaceWhiteSpace() {
		return replaceWhiteSpace;
	}


	public void setReplaceWhiteSpace(boolean replaceWhiteSpace) {
		this.replaceWhiteSpace = replaceWhiteSpace;
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
  
  
  /**
   * Replaces all whitespaces in the specified name by {@link NameMapWriter#WHITESPACE_REPLACEMENT} and truncates 
   * them to the length of {@link #getMaxNameLength()}. The old and new name are than added to the name map. 
   * If identical names occur during this process they will be changed accordingly.
   * 
   * @param name - the taxon name containing spaces
   * @param fillUp - If {@code true} is specified here, all names will be filled up with spaces until a length
   *        of {@link #getMaxNameLength()} is reached.
   * @return a new taxon name without spaces
   */
	protected String formatSequenceName(String name, boolean fillUp) {
  	String result = name;
  	if (getReplaceWhiteSpace()) {
  		result = name.replaceAll("\\s", "" + WHITESPACE_REPLACEMENT);
  	}  	
  	
  	if (getMaxNameLength() != UNLIMITTED_LENGTH) {
	    if (result.length() > getMaxNameLength()) {
	      result = result.substring(0, getMaxNameLength());
	    } 
	    else if ((result.length() < getMaxNameLength()) && fillUp) {
	      StringBuffer buffer = new StringBuffer(maxNameLength);
	      buffer.append(result);
	      while (buffer.length() < getMaxNameLength()) {
	        buffer.append(" ");
	      }
	      result = buffer.toString();
	    }
  	}
    
    int index = 1;
    while (getNameMap().containsKey(result)) {
    	String indexStr = "" + index;
    	result = result.substring(0, getMaxNameLength() - indexStr.length()) + indexStr;
    	index++;
    }
    getNameMap().put(result, name);
    
    return result;
  }
}
