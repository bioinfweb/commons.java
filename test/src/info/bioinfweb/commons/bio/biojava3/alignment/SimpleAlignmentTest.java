/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.alignment;


import static org.junit.Assert.*;

import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.CharSet;
import info.bioinfweb.commons.text.UniqueNameMapParameters;
import info.bioinfweb.commons.text.UniqueNameMap;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.junit.Test;



public class SimpleAlignmentTest {
  @Test(expected=IllegalArgumentException.class)
  public void test_renameSequence1() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment.add("A", new DNASequence("ATCG"));
  	alignment.add("B", new DNASequence("ATCC"));
  	alignment.add("C", new DNASequence("ATCT"));
  	
  	alignment.renameSequence("A", "B");
  }
  
  
  @Test
  public void test_renameSequence2() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment.add("A", new DNASequence("ATCG"));
  	alignment.add("B", new DNASequence("ATCC"));
  	alignment.add("C", new DNASequence("ATCT"));
  	
  	alignment.renameSequence("A", "D");
  	assertEquals(3, alignment.size());
  	assertEquals("ATCG", alignment.getSequence("D").getSequenceAsString());
  	assertEquals("ATCG", alignment.getSequence(0).getSequenceAsString());
  }
  
  
  @Test
  public void test_renameSequences() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment.add("Name 1", new DNASequence("ATCG"));
  	alignment.add("Name 2", new DNASequence("ATCC"));
  	alignment.add("Name 3 Text", new DNASequence("ATCT"));
  	
  	UniqueNameMapParameters params = new UniqueNameMapParameters();
  	params.getReplacements().put("\\s", "_");
  	params.setMaxNameLength(6);
    alignment.renameSequences(new UniqueNameMap(params));
  	
  	assertEquals(3, alignment.size());
  	assertEquals("Name_1", alignment.nameByIndex(0));
  	assertEquals("Name_2", alignment.nameByIndex(1));
  	assertEquals("Name_3", alignment.nameByIndex(2));
  }

  
  @Test(expected=UnsupportedOperationException.class)
  public void asMap() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment.add("Name 1", new DNASequence("ATCG"));
  	alignment.add("Name 2", new DNASequence("ATCC"));
  	alignment.add("Name 3 Text", new DNASequence("ATCT"));

  	alignment.asMap().remove("Name 1");
  }

  
  @Test
  public void test_equals() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment1 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment1.add("Name 1", new DNASequence("ATCG"));
  	alignment1.add("Name 2", new DNASequence("ATCC"));
  	alignment1.add("Name 3 Text", new DNASequence("ATCT"));

  	SimpleAlignment<DNASequence, NucleotideCompound> alignment2 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment2.add("Name 1", new DNASequence("ATCG"));
  	alignment2.add("Name 2", new DNASequence("ATCC"));
  	alignment2.add("Name 3 Text", new DNASequence("ATCT"));
  	
  	assertTrue("Equal alignments not considered equal.", alignment1.equals(alignment2));
  	assertTrue("equals() method is not symmetric.", alignment2.equals(alignment1));
  	
  	alignment2 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment2.add("Name 1", new DNASequence("ATCG"));
  	alignment2.add("Name 2", new DNASequence("ATCN"));
  	alignment2.add("Name 3 Text", new DNASequence("ATCT"));
  	
  	assertFalse(alignment1.equals(alignment2));
  	assertFalse(alignment2.equals(alignment1));

  	alignment2 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment2.add("Name 1", new DNASequence("ATCG"));
  	alignment2.add("Name 4", new DNASequence("ATCC"));
  	alignment2.add("Name 3 Text", new DNASequence("ATCT"));
  	
  	assertFalse(alignment1.equals(alignment2));
  	assertFalse(alignment2.equals(alignment1));
  }

  
  @Test
  public void test_equals_charSet() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment1 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment1.add("Name 1", new DNASequence("ATCG"));
  	alignment1.add("Name 2", new DNASequence("ATCC"));
  	alignment1.add("Name 3 Text", new DNASequence("ATCT"));
  	alignment1.addCharSet(new CharSet("A"));
  	alignment1.getCharSets().get("A").add(1, 2);

  	SimpleAlignment<DNASequence, NucleotideCompound> alignment2 = new SimpleAlignment<DNASequence, NucleotideCompound>();
  	alignment2.add("Name 1", new DNASequence("ATCG"));
  	alignment2.add("Name 2", new DNASequence("ATCC"));
  	alignment2.add("Name 3 Text", new DNASequence("ATCT"));
  	alignment2.addCharSet(new CharSet("A"));
  	alignment2.getCharSets().get("A").add(1, 2);
  	
  	assertTrue("Equal alignments not considered equal.", alignment1.equals(alignment2));
  	assertTrue("equals() method is not symmetric.", alignment2.equals(alignment1));
  	
  	alignment2.getCharSets().get("A").clear();
  	
  	assertFalse(alignment1.equals(alignment2));
  	assertFalse(alignment2.equals(alignment1));
  }
}
