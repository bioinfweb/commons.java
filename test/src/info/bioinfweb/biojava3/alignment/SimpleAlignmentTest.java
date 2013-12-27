package info.bioinfweb.biojava3.alignment;


import static org.junit.Assert.*;
import info.webinsel.util.text.UniqueNameMapParameters;
import info.webinsel.util.text.UniqueNameMap;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.junit.Test;



public class SimpleAlignmentTest {
  @Test(expected=IllegalArgumentException.class)
  public void test_renameSequence1() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment();
  	alignment.add("A", new DNASequence("ATCG"));
  	alignment.add("B", new DNASequence("ATCC"));
  	alignment.add("C", new DNASequence("ATCT"));
  	
  	alignment.renameSequence("A", "B");
  }
  
  
  @Test()
  public void test_renameSequence2() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment();
  	alignment.add("A", new DNASequence("ATCG"));
  	alignment.add("B", new DNASequence("ATCC"));
  	alignment.add("C", new DNASequence("ATCT"));
  	
  	alignment.renameSequence("A", "D");
  	assertEquals(3, alignment.size());
  	assertEquals("ATCG", alignment.getSequence("D").getSequenceAsString());
  	assertEquals("ATCG", alignment.getSequence(0).getSequenceAsString());
  }
  
  
  @Test()
  public void test_renameSequences() {
  	SimpleAlignment<DNASequence, NucleotideCompound> alignment = new SimpleAlignment();
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
}
