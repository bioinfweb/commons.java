package info.bioinfweb.biojava3.alignment.io.fasta;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import info.bioinfweb.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentReader;
import info.bioinfweb.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



public class FastaReader<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentReader<S, C> {
	public FastaReader(SequenceCreatorInterface<C> sequenceCreator) {
		super(sequenceCreator);
	}

	
	@Override
	public Alignment<S, C> read(InputStream stream) throws Exception {
		Alignment<S, C> result = new SimpleAlignment<S, C>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line = reader.readLine();
		while (line != null) {
			if (line.startsWith(">")) {
				String name = line.substring(1);
				StringBuffer sequence = new StringBuffer();
				line = reader.readLine();
				while ((line != null) && !line.startsWith(">")) {
					sequence.append(line);
					line = reader.readLine();					
				}
				result.add(name, (S)getSequenceCreator().getSequence(sequence.toString(), 0));
			}
			else {
				throw new IOException("A fasta file has to start with \">\".");
			}
		}
		return result;
	}
}
