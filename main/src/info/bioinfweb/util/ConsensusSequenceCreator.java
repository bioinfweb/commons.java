package info.bioinfweb.util;


import info.bioinfweb.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.plaf.basic.BasicArrowButton;

import org.biojava3.core.sequence.BasicSequence;
import org.biojava3.core.sequence.DNASequence;
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
		//mapByScore.put(new AmbiguityBaseScore(0, 0, 0, 0), "-");
		
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
//		if (result == null) {
//			System.out.println(score.getAdeninScore() + " " + score.getThyminScore() + " " + score.getCytosinScore() + " " + score.getGuaninScore());
//		}
		return result;
	}
	
	
  /**
   * Constructs a majority rule consensus sequence. The returned sequence always contains T instead of U, even if RNA sequences 
   * have been specified. 
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public String majorityRuleConsensusAsString(Sequence<NucleotideCompound>[] sequences) {
  	StringBuffer result = new StringBuffer(sequences[0].getLength()); 
  	//BasicSequence<NucleotideCompound> result = new BasicSequence<NucleotideCompound>(sequence, compoundSet);
  	
		for (int position = 1; position <= sequences[0].getLength(); position++) {  // "biological index" [...]
    	AmbiguityBaseScore counts = new AmbiguityBaseScore(0, 0, 0, 0);
	  	for (int sequenceIndex = 0; sequenceIndex < sequences.length; sequenceIndex++) {
	  		counts.add(mapByBase.get(sequences[sequenceIndex].getCompoundAt(position).getUpperedBase()));
			}
	  	String base = getMajorityBase(counts);
	  	if (!base.equals("A") && !base.equals("T") && !base.equals("C") && !base.equals("G")) {
	  		System.out.print(base);
	  	}
	  	result.append(base);
		}
//		System.out.println();
//		System.out.println(result.length() + ", " + result.toString());
		return result.toString();
  }

  
  /**
   * Constructs a majority rule consensus sequence
   * @param sequences - a set of sequences with equal length
   * @return the consensus sequence
   */
  public Sequence<NucleotideCompound> majorityRuleConsensus(Sequence<NucleotideCompound>[] sequences) {
  	String seq = majorityRuleConsensusAsString(sequences);
//  	System.out.println("\"" + seq + "\"");
  	BasicSequence<NucleotideCompound> result = new BasicSequence<NucleotideCompound>(seq, 
  			AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet());
//  	DNASequence result = new DNASequence(seq, 
//  			AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet());
//  	System.out.println(result.getLength());
//  	for (int i = 1; i <= result.getLength(); i++) {
//  		System.out.print("|" + seq.charAt(i - 1) + result.getCompoundAt(i).getBase());
//		}
//  	System.out.println();
//  	System.out.println("Seq: " + result.getSequenceAsString());
  	return result;
  }
}
