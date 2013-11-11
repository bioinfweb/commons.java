package info.bioinfweb.biojava3.alignment.io.nexus;


import static org.junit.Assert.*;


import java.io.File;

import info.bioinfweb.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.bioinfweb.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;
import info.bioinfweb.biojavax.bio.phylo.io.nexus.CharSet;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.DNASequenceCreator;
import org.junit.Test;


public class NexusReaderWriterTest {
  @Test
  public void testReadingWriting() {
  	Alignment<DNASequence, NucleotideCompound> originalAlignment = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	originalAlignment.add("A", new DNASequence("ATCGT"));
  	originalAlignment.add("B", new DNASequence("A-CGT"));
  	originalAlignment.add("C", new DNASequence("ATGGT"));
  	CharSet charSet = new CharSet("S");
  	charSet.add(1, 2);
  	charSet.add(4);
  	originalAlignment.addCharSet(charSet);
  	
  	try {
  		File file = File.createTempFile("NexusReaderWriterTest", ".nex");
  		//System.out.println(file);
  		new NexusWriter<DNASequence, NucleotideCompound>("DNA").write(originalAlignment, file);
  		try {
  	  	Alignment<DNASequence, NucleotideCompound> readAlignment = 
  	  			new NexusReader<DNASequence, NucleotideCompound>(new DNASequenceCreator(
  	  					new AlignmentAmbiguityNucleotideCompoundSet())).read(file);
  	  	
  	  	assertEquals(originalAlignment.size(), readAlignment.size());
  	  	for (int i = 0; i < originalAlignment.size(); i++) {
  	  		assertEquals(originalAlignment.nameByIndex(i), readAlignment.nameByIndex(i));
    	  	assertEquals(originalAlignment.getSequence(i).getSequenceAsString(), 
    	  			readAlignment.getSequence(i).getSequenceAsString());
				}
  	  	
  	  	assertEquals(originalAlignment.getCharSets().get("S"), readAlignment.getCharSets().get("S"));
  		}
  		finally {
  			file.delete();
  		}
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail();
  	}
  }
}
