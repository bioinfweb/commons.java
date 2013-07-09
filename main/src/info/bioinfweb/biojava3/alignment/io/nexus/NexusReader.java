package info.bioinfweb.biojava3.alignment.io.nexus;


import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojavax.bio.phylo.io.nexus.DataBlock;
import org.biojavax.bio.phylo.io.nexus.NexusBlock;
import org.biojavax.bio.phylo.io.nexus.NexusFileFormat;

import info.bioinfweb.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentReader;
import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.bioinfweb.biojavax.bio.phylo.io.nexus.CharSetNexusFileBuilder;
import info.bioinfweb.biojavax.bio.phylo.io.nexus.SetsBlock;



public class NexusReader<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentReader<S, C> {
	public NexusReader(SequenceCreatorInterface<C> sequenceCreator) {
		super(sequenceCreator);
	}

	
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
			if (block instanceof DataBlock) {
				Iterator<String> labelIterator = ((DataBlock)block).getMatrixLabels().iterator();
				while (labelIterator.hasNext()) {
          String name = labelIterator.next();
  				List<String> data = ((DataBlock)block).getMatrixData(name);
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
			throw new IOException("The specified nexus file contains no DATA block.");
		}
	}
}
