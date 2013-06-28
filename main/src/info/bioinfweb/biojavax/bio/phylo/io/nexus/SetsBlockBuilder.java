package info.bioinfweb.biojavax.bio.phylo.io.nexus;


import org.biojavax.bio.phylo.io.nexus.NexusBlock;
import org.biojavax.bio.phylo.io.nexus.NexusBlockBuilder;
import org.biojavax.bio.phylo.io.nexus.NexusBlockListener;
import org.biojavax.bio.phylo.io.nexus.NexusComment;



/**
 * Builds a <code>SETS</code> Nexus block by listening to events.
 * 
 * @author Ben St&ouml;ver
 */
public class SetsBlockBuilder extends NexusBlockBuilder.Abstract 
    implements SetsBlockListener, NexusBlockBuilder, NexusBlockListener {
	
	private SetsBlock block = new SetsBlock();
	

	public void addCharSet(String name) {
		block.addCharSet(name);
	}

	
	public void addCharSetInterval(String name, int start, int end) {
		block.addCharSetInterval(name, start, end);
	}

	
	/**
	 * The implementation of this method is currently empty since HIR Finder does not process
	 * any information included in Nexus comments.
	 * @see org.biojavax.bio.phylo.io.nexus.NexusBlockBuilder$Abstract#addComment(org.biojavax.bio.phylo.io.nexus.NexusComment)
	 */
	@Override
	protected void addComment(NexusComment comment) {}

	
	@Override
	protected NexusBlock startBlockObject() {
		block = new SetsBlock();
		return block;
	}

	
	public void endBlock() {
	}

	
	public void endTokenGroup() {
	}
}
