package info.bioinfweb.biojava3.alignment.io.fasta;


import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;

import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.biojava3.alignment.template.Alignment;



/**
 * Writes an alignment to a FASTA file. (Note that no character set information will be writte because the 
 * FASTA format does not support this.)
 * 
 * @author BenStoever
 */
public class FastaWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final int LINE_LENGTH = 80;
	
	
	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)
			throws Exception {
		
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
	      out.write('>');
	      out.write(name.getBytes());
	      final byte[] SEPARATOR = System.getProperty("line.separator").getBytes();
	      out.write(SEPARATOR);
	
	      int compoundCount = 0;
	      String seq = alignment.getSequence(name).getSequenceAsString();
	      for (int i = 0; i < seq.length(); i++) {
	          out.write(seq.charAt(i));
	          compoundCount++;
	          if (compoundCount == LINE_LENGTH) {
	              out.write(SEPARATOR);
	              compoundCount = 0;
	          }
	      }
	      if ((seq.length() % LINE_LENGTH) != 0) {
	          out.write(SEPARATOR);
	      }
			}
		}
		finally {
			out.close();
		}
	}
}
