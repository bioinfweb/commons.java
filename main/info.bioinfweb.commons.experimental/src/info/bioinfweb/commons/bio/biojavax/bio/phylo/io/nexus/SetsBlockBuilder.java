/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus;


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
