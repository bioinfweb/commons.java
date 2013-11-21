package info.bioinfweb.biojava3.alignment.io.mega;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import info.bioinfweb.biojava3.alignment.io.NameMapWriter;
import info.bioinfweb.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Writes an alignment to a MEGA file. (Note that no character set information will be written.)
 * 
 * @author Ben St&ouml;ver
 */
public class MEGAWriter<S extends Sequence<C>, C extends Compound> extends NameMapWriter<S, C> {
	public static final String DATATYPE_DNA = "DNA";
	public static final String DATATYPE_PROTEIN = "Protein";
	public static final String DEFAULT_TITLE = "Alignment generated with bioinfweb AlignmentIO";

	public static final int LINE_LENGTH = 80;
	public static final byte[] LINE_SEPARATOR = "\r\n".getBytes();  // Always use windows separator because there are only windows versions of MEGA.
	
	
	private String datatype;
	private char gapCharacter = '-';
	
	
	public MEGAWriter(String datatype, char gapCharacter) {
		super();
		this.datatype = datatype;
		this.gapCharacter = gapCharacter;
	}


	public MEGAWriter(String datatype) {
		super();
		this.datatype = datatype;
	}


	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)	throws Exception {
		write(alignment, stream, DEFAULT_TITLE);
	}
	
	
	public void write(Alignment<S, C> alignment, File file, String title)	throws Exception {
		OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			write(alignment, stream, DEFAULT_TITLE);
		}
		finally {
			stream.close();
		}
	}
	
	
	public void write(Alignment<S, C> alignment, OutputStream stream, String title)	throws Exception {
		getNameMap().clear();
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			out.write("#mega".getBytes());
      out.write(LINE_SEPARATOR);
			out.write(("!Title " + title + ";").getBytes());
      out.write(LINE_SEPARATOR);
			out.write(("!Format DataType=" + datatype + " indel=" + gapCharacter + ";").getBytes());
      out.write(LINE_SEPARATOR);
      out.write(LINE_SEPARATOR);
			
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String modifiedName = formatSequenceName(name, false);
	      out.write('#');
	      out.write(modifiedName.getBytes());
	      out.write(LINE_SEPARATOR);
	
	      int compoundCount = 0;
	      String seq = alignment.getSequence(name).getSequenceAsString();
	      for (int i = 0; i < seq.length(); i++) {
	          out.write(seq.charAt(i));
	          compoundCount++;
	          if (compoundCount == LINE_LENGTH) {
	              out.write(LINE_SEPARATOR);
	              compoundCount = 0;
	          }
	      }
	      if ((seq.length() % LINE_LENGTH) != 0) {
	          out.write(LINE_SEPARATOR);
	      }
			}
		}
		finally {
			out.close();
		}
	}
}
