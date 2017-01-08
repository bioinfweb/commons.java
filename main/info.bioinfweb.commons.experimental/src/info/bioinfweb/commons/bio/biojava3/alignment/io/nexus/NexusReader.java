/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.alignment.io.nexus;


import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojavax.bio.phylo.io.nexus.CharactersBlock;
import org.biojavax.bio.phylo.io.nexus.NexusBlock;
import org.biojavax.bio.phylo.io.nexus.NexusFileFormat;

import info.bioinfweb.commons.bio.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.commons.bio.biojava3.alignment.io.AbstractAlignmentReader;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.CharSetNexusFileBuilder;
import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.SetsBlock;



public class NexusReader<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentReader<S, C> {
	public NexusReader(SequenceCreatorInterface<C> sequenceCreator) {
		super(sequenceCreator);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Alignment<S, C> read(InputStream stream) throws Exception {
		CharSetNexusFileBuilder builder = new CharSetNexusFileBuilder();
		builder.setDefaultBlockParsers();
		NexusFileFormat.parseInputStream(builder, stream);
		
		Alignment<S, C> result = new SimpleAlignment<S, C>();
		boolean hasCharacters = false;
		Iterator<NexusBlock> blockIterator = builder.getNexusFile().blockIterator();
		while (blockIterator.hasNext()) {
			NexusBlock block = blockIterator.next();
			if (block instanceof CharactersBlock) {  // also true for instances of DataBlock
				Iterator<String> labelIterator = ((CharactersBlock)block).getMatrixLabels().iterator();
				while (labelIterator.hasNext()) {
          String name = labelIterator.next();
  				List<String> data = ((CharactersBlock)block).getMatrixData(name);
  				StringBuffer sequenceStr = new StringBuffer(data.size());
  				for (int i = 0; i < data.size(); i++) {
  					sequenceStr.append(data.get(i));
  				}
          S sequence = (S)getSequenceCreator().getSequence(sequenceStr.toString(), 0);
          result.add(name, sequence);
				}
				hasCharacters = true;
			}
			else if (block instanceof SetsBlock) {
				result.addAllCharSets(((SetsBlock)block).values());
			}
		}
		
		if (hasCharacters) {
			return result;
		}
		else {
			throw new IOException("The specified nexus file contains no CHARACTERS or DATA block.");
		}
	}
}
