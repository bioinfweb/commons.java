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



public class NexusWriter<S extends Sequence<C>, C extends Compound> extends AbstractAlignmentWriter<S, C> {
	public static final String COMMAND_TERMINATOR = ";";
	public static final String COMMENT_START = "[";
	public static final String COMMENT_END = "]";
	
	
	private String indention = "";
	
	
	@Override
	public void write(Alignment<S, C> alignment, OutputStream stream)
			throws Exception {

		if (!alignment.isEmpty()) {
			PrintWriter writer = new PrintWriter(stream);
			writeFileStart(writer);
			
			writeBlockStart(writer, "TAXA");
			writeLine(writer, "DIMENSIONS NTAX=" + alignment.size() + COMMAND_TERMINATOR);
			writeLine(writer, "TAXLABELS ");
			increaseIndention();
			Iterator<String> nameIterator = alignment.nameIterator();
			while (nameIterator.hasNext()) {
				writeLine(writer, nameIterator.next());
			}
			writeLine(writer, COMMAND_TERMINATOR);
			decreaseIndention();
			writeBlockEnd(writer, "TAXA");
			
			writeBlockStart(writer, "CHARACTERS");
			writeLine(writer, "DIMENSIONS NCHAR=" + alignment.maxSequenceLength() + COMMAND_TERMINATOR);
			//TODO Insert format command depending on character set
			//TODO Use interleaved format in the future
			writeLine(writer, "MATRIX");
			increaseIndention();
			nameIterator = alignment.nameIterator();
			while (nameIterator.hasNext()) {
				String name = nameIterator.next();
				writeLine(writer, name + " " + alignment.getSequence(name).getSequenceAsString());
			}
			writeLine(writer, COMMAND_TERMINATOR);
			decreaseIndention();
			writeBlockEnd(writer, "CHARACTERS");
			
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
			}
			writeBlockEnd(writer, "SETS");
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
