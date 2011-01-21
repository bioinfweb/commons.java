package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import org.biojavax.bio.phylo.io.nexus.NexusFileBuilder;



/**
 * Builds a Nexus file including information from the <code>SETS block</code> by listening to 
 * events.
 * 
 * @author Ben St&ouml;ver
 */
public class CharSetNexusFileBuilder extends NexusFileBuilder {
	public CharSetNexusFileBuilder() {
		super();
		
		setBlockParser(SetsBlock.SETS_BLOCK, new SetsBlockParser(new SetsBlockBuilder()));
	}
}
