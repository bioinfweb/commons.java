package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;

import org.biojavax.bio.phylo.io.nexus.NexusBlock;



/**
 * Represents a part of the contents of a <code>SETS</code> block of a Nexus file. (Only 
 * <code>CharSet</code> commands are stored.)
 * 
 * @author Ben St&ouml;ver
 */
public class SetsBlock extends NexusBlock.Abstract {
	public static final String SETS_BLOCK = "SETS";

	
	private HashMap<String, CharSet> charSets = new HashMap<String, CharSet>(); 
	
	
	public SetsBlock() {
		super(SETS_BLOCK);
	}

	
	/**
	 * Adds a new character set with the specified name, if not already present.
	 * @param name
	 */
	public void addCharSet(String name) {
		if (charSets.get(name) == null) {
			charSets.put(name, new CharSet(name));
		}
	}
	
	
	/**
	 * Adds the specified interval to the specified character set.
	 * @param name
	 * @param start
	 * @param end
	 * @throws IllegalArgumentException - if there is no character set with the specified name 
	 *         present.
	 */
	public void addCharSetInterval(String name, int start, int end) {
		CharSet charSet = charSets.get(name);
		if (charSet == null) {
			throw new IllegalArgumentException("A character set with the name \"" + name + 
					"\" is not present in this object.");
		}
		else {
			charSet.addInterval(start, end);
		}
	}
	
	
	public CharSet getCharSet(String name) {
		return charSets.get(name);
	}
	
	
	public Collection<CharSet> values() {
		return charSets.values();
	}


	/**
	 * The implementation of this method is empty since it is not used in HIR-Finder.
	 * 
	 * @see org.biojavax.bio.phylo.io.nexus.NexusBlock$Abstract#writeBlockContents(java.io.Writer)
	 */
	@Override
	protected void writeBlockContents(Writer writer) throws IOException {}
}
