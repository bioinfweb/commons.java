/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.bio.biojava3.alignment.io.nexus;


import static org.junit.Assert.*;


import java.io.File;

import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.CharSet;
import info.bioinfweb.commons.bio.biojava3.alignment.SimpleAlignment;
import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

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
