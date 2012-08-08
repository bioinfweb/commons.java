package info.bioinfweb.util;


import info.bioinfweb.biojava3.core.sequence.compound.AmbiguityNoGapNucleotideCompoundSet;
import info.webinsel.util.Math2;

import java.util.Iterator;
import java.util.TreeMap;

import org.biojava3.core.sequence.BasicSequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;



public class ConsensusSequenceCreator {
  private static ConsensusSequenceCreator firstInstance = null; 
  	
  
	private TreeMap<String, int[]> mapByBase;
	private TreeMap<int[], String> mapByScore;
	
	
	private ConsensusSequenceCreator() {
		super();
		createMaps();
	}
	
	
	public static ConsensusSequenceCreator getInstance() {
		if (firstInstance == null) {
			firstInstance = new ConsensusSequenceCreator();
		}
		return firstInstance;
	}


	private int[] createScoreMapEntry(int... addends) {
		return addends;
	}
	
	
	private void createMaps() {
		mapByBase = new TreeMap<String, int[]>();
		mapByBase.put("A", createScoreMapEntry(1, 0, 0, 0));
		mapByBase.put("T", createScoreMapEntry(0, 1, 0, 0));
		mapByBase.put("C", createScoreMapEntry(0, 0, 1, 0));
		mapByBase.put("G", createScoreMapEntry(0, 0, 0, 1));
		mapByBase.put("U", createScoreMapEntry(0, 1, 0, 0));
		mapByBase.put("Y", createScoreMapEntry(0, 1, 1, 0));
		mapByBase.put("R", createScoreMapEntry(1, 0, 0, 1));
		mapByBase.put("W", createScoreMapEntry(1, 1, 0, 0));
		mapByBase.put("S", createScoreMapEntry(0, 0, 1, 1));
		mapByBase.put("K", createScoreMapEntry(0, 1, 0, 1));
		mapByBase.put("M", createScoreMapEntry(1, 0, 1, 0));
		mapByBase.put("B", createScoreMapEntry(0, 1, 1, 1));
		mapByBase.put("D", createScoreMapEntry(0, 1, 0, 0));
		mapByBase.put("H", createScoreMapEntry(1, 1, 0, 1));
		mapByBase.put("V", createScoreMapEntry(1, 0, 1, 1));
		mapByBase.put("N", createScoreMapEntry(1, 1, 1, 1));
		
		mapByScore = new TreeMap<int[], String>();
		Iterator<String> iterator = mapByBase.keySet().iterator();
		while (iterator.hasNext()) {
			String base = iterator.next();
			mapByScore.put(mapByBase.get(base), base);
		}
		mapByScore.put(createScoreMapEntry(0, 0, 0, 0), "-");
		
		mapByBase.put("X", createScoreMapEntry(1, 1, 1, 1));  // has to be added after the creation of mapByScore
	}
	
	
	/**
	 * Adds the values of <code>b</code> to <code>a</code>.
	 * @param a
	 * @param b
	 */
	private void addConsensusCounts(int[] a, int[] b) {
	  if (b != null) {
			for (int i = 0; i < a.length; i++) {
				a[i] += b[i];  
			}
	  }
	}
	
	
	private String getMajorityBase(int[] counts) {
		int max = Math2.maxInt(counts);
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == max) {
				counts[i] = 1;
			}
			else {
				counts[i] = 0;
			}
		}
		return mapByScore.get(counts);
	}
	
	
  /**
   * Constructs a majority rule consensus sequence
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public Sequence<NucleotideCompound> majorityRuleConsensus(Sequence<NucleotideCompound>[] sequences) {
  	return new BasicSequence<NucleotideCompound>(majorityRuleConsensusAsString(sequences), 
  			new AmbiguityNoGapNucleotideCompoundSet());
  }
  
  
  /**
   * Constructs a majority rule consensus sequence
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public String majorityRuleConsensusAsString(Sequence<NucleotideCompound>[] sequences) {
  	StringBuffer result = new StringBuffer(sequences[0].getLength()); 
		for (int position = 0; position < sequences[0].getLength(); position++) {
    	int[] counts = {0, 0, 0, 0};
	  	for (int sequenceIndex = 0; sequenceIndex < sequences.length; sequenceIndex++) {
	  		addConsensusCounts(counts, 
	  				mapByBase.get(sequences[sequenceIndex].getCompoundAt(position).getUpperedBase()));
			}
	  	result.append(getMajorityBase(counts));
		}
		return result.toString();
  }
}
