package info.bioinfweb.commons.bio.biojava3.alignment.io.phylip;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;


import info.bioinfweb.commons.bio.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;

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
		result.add("Seq with spaces", new DNASequence("CGTTA"));
		return result;
	}
	
	
  @Test
  public void test_write_space() {
  	final String LINE_SEP = System.getProperty("line.separator");
  	ByteArrayOutputStream stream = new ByteArrayOutputStream();
  	PhylipWriter<DNASequence, NucleotideCompound> writer = new PhylipWriter<DNASequence, NucleotideCompound>(false);
  	try {
  		writer.write(createAlignment(), stream);
  		assertEquals(" 5 5" + LINE_SEP + 
  				"ASequence  ATCGT" + LINE_SEP + 
  				"SequenceWi AAAAA" + LINE_SEP + 
  				"SequenceW1 TTTTT" + LINE_SEP +
  				"SequenceW2 CCCCC" + LINE_SEP +
  				"Seq with s CGTTA" + LINE_SEP,
  				stream.toString());
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
  }
  
  
  @Test
  public void test_write_noSpace() {
  	final String LINE_SEP = System.getProperty("line.separator");
  	ByteArrayOutputStream stream = new ByteArrayOutputStream();
  	PhylipWriter<DNASequence, NucleotideCompound> writer = new PhylipWriter<DNASequence, NucleotideCompound>(true);
  	try {
  		writer.write(createAlignment(), stream);
  		assertEquals(" 5 5" + LINE_SEP + 
  				"ASequence  ATCGT" + LINE_SEP + 
  				"SequenceWi AAAAA" + LINE_SEP + 
  				"SequenceW1 TTTTT" + LINE_SEP +
  				"SequenceW2 CCCCC" + LINE_SEP +
  				"Seq_with_s CGTTA" + LINE_SEP,
  				stream.toString());
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
  }
  
  
  @Test
  public void test_write_noSpaceShorter() {
  	final String LINE_SEP = System.getProperty("line.separator");
  	ByteArrayOutputStream stream = new ByteArrayOutputStream();
  	PhylipWriter<DNASequence, NucleotideCompound> writer = new PhylipWriter<DNASequence, NucleotideCompound>(true, 8);
  	try {
  		writer.write(createAlignment(), stream);
  		assertEquals(" 5 5" + LINE_SEP + 
  				"ASequenc ATCGT" + LINE_SEP + 
  				"Sequence AAAAA" + LINE_SEP + 
  				"Sequenc1 TTTTT" + LINE_SEP +
  				"Sequenc2 CCCCC" + LINE_SEP +
  				"Seq_with CGTTA" + LINE_SEP,
  				stream.toString());
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
  }
}
