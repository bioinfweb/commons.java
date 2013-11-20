package info.bioinfweb.biojava3.alignment.io.hennig86;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import info.bioinfweb.biojava3.alignment.io.NameMapWriter;
import info.bioinfweb.biojava3.alignment.io.nexus.NexusReader;
import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.bioinfweb.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;
import info.webinsel.util.SystemUtils;

import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.DNASequenceCreator;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Writes an alignment to a Hennig86 file. (Note that no character set information will be written, because this
 * file type does not support this.)
 * 
 * Optionally a {@code nstates} command for <i>TNT</i> can be written depending on the constructor that is used.
 * 
 * @author Ben St&ouml;ver
 */
public class Hennig86Writer<S extends Sequence<C>, C extends Compound> extends NameMapWriter<S, C> {
	public static final String COMMAND_TERMINATOR = ";";
	public static final String NO_OF_STATES_COMMAND = "nstates";
	public static final String MATRIX_COMMAND = "xread";
	public static final String DATATYPE_DNA = "dna";
	public static final String DATATYPE_PROTEIN = "prot";
	public static final String WHITESPACE_REPLACEMENT = "_";

	
	private String noOfStates = null;
	
	
	public Hennig86Writer() {
		super();
	}


	public Hennig86Writer(String noOfStates) {
		super();
		this.noOfStates = noOfStates;
	}


	public Hennig86Writer(int noOfStates) {
		super();
		this.noOfStates = Integer.toString(noOfStates);
	}


	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)	throws Exception {
		write(alignment, stream, null);
	}
	
	
	public void write(Alignment<S, C> alignment, File file, String title)	throws Exception {
		OutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		try {
			write(alignment, stream, title);
		}
		finally {
			stream.close();
		}
	}
	
	
	public void write(Alignment<S, C> alignment, OutputStream stream, String title)	throws Exception {
		getNameMap().clear();
		BufferedOutputStream out = new BufferedOutputStream(stream);
		try {
			final byte[] LINE_SEPARATOR = SystemUtils.LINE_SEPARATOR.getBytes();
			
			if (noOfStates != null) {
				out.write((NO_OF_STATES_COMMAND + " " + noOfStates + COMMAND_TERMINATOR).getBytes());
				out.write(LINE_SEPARATOR);
			}
			
			out.write(MATRIX_COMMAND.getBytes());
      out.write(LINE_SEPARATOR);
      if (title != null) {
      	out.write(("'" + title + "'").getBytes());
        out.write(LINE_SEPARATOR);
      }
      out.write((Integer.toString(alignment.maxSequenceLength()) + " " + Integer.toString(alignment.size())).getBytes());
      out.write(LINE_SEPARATOR);
			
			Iterator<String> iterator = alignment.nameIterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String modifiedName = replaceWhiteSpaces(name);
	      out.write((modifiedName + " ").getBytes());
        out.write(alignment.getSequence(name).getSequenceAsString().getBytes());
        out.write(LINE_SEPARATOR);
			}
      out.write(";".getBytes());
		}
		finally {
			out.close();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			final String folder = "C:\\Users\\BenStoever\\Documents\\Studium\\Projekte\\Promotion\\Alignment Evaluation\\Eigene Programme\\Alignment Evaluation\\Testdaten\\TestRepository\\ViscumITS";
	  	Alignment<Sequence<NucleotideCompound>, NucleotideCompound> alignment = 
	  			new NexusReader<Sequence<NucleotideCompound>, NucleotideCompound>(new DNASequenceCreator(
	  					new AlignmentAmbiguityNucleotideCompoundSet())).read(new File(folder + "\\Original.nex"));
	  	new Hennig86Writer<Sequence<NucleotideCompound>, NucleotideCompound>(DATATYPE_DNA).write(
	  			alignment, new File(folder + "\\Hennig86.txt"), "A title with spaces");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
