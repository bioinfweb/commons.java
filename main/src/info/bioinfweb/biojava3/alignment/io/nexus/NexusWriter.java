package info.bioinfweb.biojava3.alignment.io.nexus;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;

import info.bioinfweb.biojava3.alignment.io.AbstractAlignmentWriter;
import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.bioinfweb.biojavax.bio.phylo.io.nexus.CharSet;
import info.webinsel.util.collections.SimpleSequenceInterval;



/**
 * Writes an {@link Alignment} to a stream in Nexus format. The result contains a TAXA, a CHARACTERS and 
 * a SETS block (if the specified alignment contains any character set).
 * 
 * @author Ben St&ouml;ver
 */
public class NexusWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final String COMMAND_TERMINATOR = ";";
	public static final String COMMENT_START = "[";
	public static final String COMMENT_END = "]";

	public static final String DATATYPE_STANDARD = "STANDARD";
	public static final String DATATYPE_DNA = "DNA";
	public static final String DATATYPE_RNA = "RNA";
	public static final String DATATYPE_NUCLEOTIDE = "NUCLEOTIDE";
	public static final String DATATYPE_PROTEIN = "PROTEIN";
	public static final String DATATYPE_CONTINUOUS = "CONTINUOUS";
	
	
	private String matrixDataType = DATATYPE_STANDARD;
	private char gapCharacter = '-';
	private char missingCharacter = '?';
	private String indention = "";
	
	
	/**
	 * Creates a new instance of this class.
	 *  
	 * @param matrixDataType - the data type specified in the Nexus {@code FORMAT} command (Nexus allows 
	 * {@code STANDARD}, {@code DNA}, {@codeRDNA}, {@code NUCLEOTIDE}, {@code PROTEIN} or {@code CONTINUOUS}.)
	 * @param gapCharacter - the character coding a gap in the alignment
	 * @param missingCharacter - the character coding missing data in the alignment
	 */
	public NexusWriter(String matrixDataType, char gapCharacter, char missingCharacter) {
		super();
		this.matrixDataType = matrixDataType;
		this.gapCharacter = gapCharacter;
		this.missingCharacter = missingCharacter;
	}


	/**
	 * Creates a new instance of this class with "{@code -}" as gap data and "{@code ?} as missing data character.
	 *  
	 * @param matrixDataType - the data type specified in the Nexus {@code FORMAT} command (Nexus allows 
	 * {@code STANDARD}, {@code DNA}, {@codeRDNA}, {@code NUCLEOTIDE}, {@code PROTEIN} or {@code CONTINUOUS}.)
	 */
	public NexusWriter(String matrixDataType) {
		super();
		this.matrixDataType = matrixDataType;
	}


	private String maskSpaces(String name) {
		return name.replaceAll(" ", "_");
	}
	
	
	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)
			throws Exception {

		if (!alignment.isEmpty()) {
			PrintWriter writer = new PrintWriter(stream);
			try {
				writeFileStart(writer);
				
				writeBlockStart(writer, "TAXA");
				writeLine(writer, "DIMENSIONS NTAX=" + alignment.size() + COMMAND_TERMINATOR);
				writeLine(writer, "TAXLABELS ");
				increaseIndention();
				Iterator<String> nameIterator = alignment.nameIterator();
				while (nameIterator.hasNext()) {
					writeLine(writer, maskSpaces(nameIterator.next()));
				}
				writeLine(writer, COMMAND_TERMINATOR);
				decreaseIndention();
				writeBlockEnd(writer, "TAXA");
				
				writeBlockStart(writer, "CHARACTERS");
				writeLine(writer, "DIMENSIONS NCHAR=" + alignment.maxSequenceLength() + COMMAND_TERMINATOR);  //TODO Check is Nexus format allows sequences with different lengths. Otherwise shorter sequences have to be filled up with "?" or "-".
				writeLine(writer, "FORMAT DATATYPE=" + matrixDataType + " GAP=" + gapCharacter + 
						" MISSING=" + missingCharacter + COMMAND_TERMINATOR);  //TODO Add more parameters as defined in the Nexus standard.
				//TODO Use interleaved format in the future
				writeLine(writer, "MATRIX");
				increaseIndention();
				nameIterator = alignment.nameIterator();
				while (nameIterator.hasNext()) {
					String name = nameIterator.next();
					writeLine(writer, maskSpaces(name) + " " + alignment.getSequence(name).getSequenceAsString());
				}
				writeLine(writer, COMMAND_TERMINATOR);
				decreaseIndention();
				writeBlockEnd(writer, "CHARACTERS");
				
				if (!alignment.getCharSets().isEmpty()) {
					writeBlockStart(writer, "SETS");
					Iterator<CharSet> charSetIterator = alignment.getCharSets().values().iterator();
					while (charSetIterator.hasNext()) {
						CharSet charSet = charSetIterator.next();
						writeLineStart(writer, "CHARSET " + charSet.getName() + " =");
						Iterator<SimpleSequenceInterval> intervalIterator = charSet.iterator();
						while (intervalIterator.hasNext()) {
							SimpleSequenceInterval interval = intervalIterator.next();
							writer.write(" " + interval.getFirstPos());
							if (interval.getFirstPos() != interval.getLastPos()) {
								writer.write("-" + interval.getLastPos());
							}
						}
						writer.write(COMMAND_TERMINATOR);
						writer.println();
					}
					writeBlockEnd(writer, "SETS");
				}
			}
			finally {
				writer.close();  // Closing only the underlying stream does not make sure that all commands are written by this writer.
			}
		}
	}
	
	
	private void writeLineStart(PrintWriter writer, String text) throws IOException {
		writer.print(indention + text);
	}
	
	
	private void writeLine(PrintWriter writer, String text) throws IOException {
		writer.println(indention + text);
	}
	
	
	private void increaseIndention() {
		indention += "  ";
	}
	
	
	private void decreaseIndention() {
		if (indention.length() > 2) {
			indention = indention.substring(2);
		}
	}
	
	
	private void writeFileStart(PrintWriter writer) throws IOException {
		writer.println("#NEXUS");
	}
	
	
	private void writeBlockStart(PrintWriter writer, String name) throws IOException {
		writer.println("BEGIN " + name + COMMAND_TERMINATOR);
		increaseIndention();
	}
	
	
	private void writeBlockEnd(PrintWriter writer, String name) throws IOException {
		decreaseIndention();
		writer.println("END" + COMMAND_TERMINATOR + " " + COMMENT_START + name + COMMENT_END);
	}
}
