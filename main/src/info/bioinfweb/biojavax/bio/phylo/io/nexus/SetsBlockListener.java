package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import org.biojavax.bio.phylo.io.nexus.NexusBlockListener;



/**
 * @author Ben St&ouml;ver
 */
public interface SetsBlockListener extends NexusBlockListener {
	/**
	 * Adds a new character set with the specified name, if not already present.
	 * @param name
	 */
	public void addCharSet(String name);
		
	/**
	 * Adds the specified interval to the specified character set.
	 * @param name
	 * @param start
	 * @param end
	 */
	public void addCharSetInterval(String name, int start, int end);
}
