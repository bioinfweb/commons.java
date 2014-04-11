package info.bioinfweb.commons.bio.biojava3.alignment.io.fasta;


import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.DNASequenceCreator;
import org.junit.Test;


public class FastaReaderTest {
  @Test
  public void test_read_valid() {
  	try {
  		Alignment<DNASequence, NucleotideCompound> aligment = new FastaReader<DNASequence, NucleotideCompound>(
  				new DNASequenceCreator(new AlignmentAmbiguityNucleotideCompoundSet())).read(new File("data\\alignmentIO\\valid.fasta"));
  		assertEquals(3, aligment.size());
  		Iterator<String> iterator = aligment.nameIterator();
  		
  		String name = iterator.next();
  		assertEquals("ASequence", name);
  		assertEquals("ATCGT", aligment.getSequence(name).getSequenceAsString());
  		
  		name = iterator.next();
  		assertEquals("SequenceWithManyAs", name);
  		assertEquals("AAAAA", aligment.getSequence(name).getSequenceAsString());
  		
  		name = iterator.next();
  		assertEquals("SequenceWithManyTs", name);
  		assertEquals("TTTTT", aligment.getSequence(name).getSequenceAsString());
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getLocalizedMessage());
  	}
  }

  
  @Test(expected=IOException.class)
  public void test_read_invalid() throws Exception {
		new FastaReader<DNASequence, NucleotideCompound>(
				new DNASequenceCreator(new AlignmentAmbiguityNucleotideCompoundSet())).read(new File("data\\alignmentIO\\invalid.fasta"));
  }
}
