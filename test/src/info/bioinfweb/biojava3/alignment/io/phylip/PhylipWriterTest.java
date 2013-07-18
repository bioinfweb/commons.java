package info.bioinfweb.biojava3.alignment.io.phylip;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;


import info.bioinfweb.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.biojava3.alignment.template.Alignment;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.junit.Test;



public class PhylipWriterTest {
	private Alignment<DNASequence, NucleotideCompound> createAlignment() {
		Alignment<DNASequence, NucleotideCompound> result = new SimpleAlignment<DNASequence, NucleotideCompound>();
		result.add("ASequence",               new DNASequence("ATCGT"));
		result.add("SequenceWithManyAs", new DNASequence("AAAAA"));
		result.add("SequenceWithManyTs", new DNASequence("TTTTT"));
		result.add("SequenceWithManyCs", new DNASequence("CCCCC"));
		return result;
	}
	
	
  @Test
  public void test_write() {
  	final String LINE_SEP = System.getProperty("line.separator");
  	ByteArrayOutputStream stream = new ByteArrayOutputStream();
  	PhylipWriter<DNASequence, NucleotideCompound> writer = new PhylipWriter<DNASequence, NucleotideCompound>();
  	try {
  		writer.write(createAlignment(), stream);
  		assertEquals(" 3 5" + LINE_SEP + 
  				"ASequence  ATCGT" + LINE_SEP + 
  				"SequenceWi AAAAA" + LINE_SEP + 
  				"SequenceW1 TTTTT" + LINE_SEP +
  				"SequenceW2 CCCCC" + LINE_SEP,
  				stream.toString());
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
  }
}
