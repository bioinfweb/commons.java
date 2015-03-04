/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
