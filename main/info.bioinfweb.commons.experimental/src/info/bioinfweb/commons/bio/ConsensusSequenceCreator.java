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
package info.bioinfweb.commons.bio;


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.biojava3.core.sequence.BasicSequence;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Sequence;



public class ConsensusSequenceCreator {
  private static ConsensusSequenceCreator firstInstance = null; 
  	
  
	private TreeMap<String, AmbiguityBaseScore> mapByBase;
	private TreeMap<AmbiguityBaseScore, String> mapByScore;
	
	
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


	private void createMaps() {
		mapByBase = new TreeMap<String, AmbiguityBaseScore>();
		mapByBase.put("A", new AmbiguityBaseScore(1, 0, 0, 0));
		mapByBase.put("T", new AmbiguityBaseScore(0, 1, 0, 0));
		mapByBase.put("C", new AmbiguityBaseScore(0, 0, 1, 0));
		mapByBase.put("G", new AmbiguityBaseScore(0, 0, 0, 1));
		mapByBase.put("Y", new AmbiguityBaseScore(0, 1, 1, 0));  //TODO Wäre hier 0, 0.5, 0.5, 0 besser? (Dann müssten auch auf 1 normierte Schlüssel im Algorithmus erzeugt werden.)
		mapByBase.put("R", new AmbiguityBaseScore(1, 0, 0, 1));
		mapByBase.put("W", new AmbiguityBaseScore(1, 1, 0, 0));
		mapByBase.put("S", new AmbiguityBaseScore(0, 0, 1, 1));
		mapByBase.put("K", new AmbiguityBaseScore(0, 1, 0, 1));
		mapByBase.put("M", new AmbiguityBaseScore(1, 0, 1, 0));
		mapByBase.put("B", new AmbiguityBaseScore(0, 1, 1, 1));
		mapByBase.put("D", new AmbiguityBaseScore(1, 1, 0, 1));
		mapByBase.put("H", new AmbiguityBaseScore(1, 1, 1, 0));
		mapByBase.put("V", new AmbiguityBaseScore(1, 0, 1, 1));
		mapByBase.put("N", new AmbiguityBaseScore(1, 1, 1, 1));
		mapByBase.put("-", new AmbiguityBaseScore(0, 0, 0, 0));
		
		mapByScore = new TreeMap<AmbiguityBaseScore, String>();
		Iterator<String> iterator = mapByBase.keySet().iterator();
		while (iterator.hasNext()) {
			String base = iterator.next();
			mapByScore.put(mapByBase.get(base), base);
		}
		
		mapByBase.put("U", new AmbiguityBaseScore(0, 1, 0, 0));  // has to be added after the creation of mapByScore
		mapByBase.put("X", new AmbiguityBaseScore(1, 1, 1, 1));
	}
	
	
	private String getMajorityBase(AmbiguityBaseScore score) {
		double max = score.getMaxScore();
		if (max > 0) {  // Avoid turning "-" to "N"
			for (int i = 0; i < 4; i++) {
				if (score.getScoreByIndex(i) == max) {
					score.setScoreByIndex(i, 1);
				}
				else {
					score.setScoreByIndex(i, 0);
				}
			}
		}
		String result = mapByScore.get(score);
		return result;
	}

	
	public String majorityRuleConsensusAsString(List<? extends Sequence<NucleotideCompound>> sequences) {
		return majorityRuleConsensusAsString(sequences.toArray(new Sequence[sequences.size()]));
	}
	
	
  /**
   * Constructs a majority rule consensus sequence. The returned sequence always contains T instead of U, even if RNA sequences 
   * have been specified.
   * 
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public String majorityRuleConsensusAsString(Sequence<NucleotideCompound>[] sequences) {
  	StringBuffer result = new StringBuffer(sequences[0].getLength()); 
  	
		for (int position = 1; position <= sequences[0].getLength(); position++) {  // "biological index" [...]
    	AmbiguityBaseScore counts = new AmbiguityBaseScore(0, 0, 0, 0);
	  	for (int sequenceIndex = 0; sequenceIndex < sequences.length; sequenceIndex++) {
	  		counts.add(mapByBase.get(sequences[sequenceIndex].getCompoundAt(position).getUpperedBase()));
			}
	  	result.append(getMajorityBase(counts));
		}
		return result.toString();
  }


  /**
   * Constructs a majority rule consensus sequence.
   * 
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public Sequence<NucleotideCompound> majorityRuleConsensus(Sequence<NucleotideCompound>[] sequences) {
  	String seq = majorityRuleConsensusAsString(sequences);
  	BasicSequence<NucleotideCompound> result = new BasicSequence<NucleotideCompound>(seq, 
  			AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet());
  	return result;
  }

  
  public Sequence<NucleotideCompound> majorityRuleConsensus(List<? extends Sequence<NucleotideCompound>> sequences) {
  	String seq = majorityRuleConsensusAsString(sequences);
  	BasicSequence<NucleotideCompound> result = new BasicSequence<NucleotideCompound>(seq, 
  			AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet());
  	return result;
  }
}
