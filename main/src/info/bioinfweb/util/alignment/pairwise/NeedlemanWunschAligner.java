package info.bioinfweb.util.alignment.pairwise;



/**
 * Performs a global Needleman Wunsch pairwise alignment. This class is based on the implementation of Daniel Himmelein
 * available <a href="https://code.google.com/p/himmele/source/browse/trunk/Bioinformatics/NeedlemanWunsch/src/NeedlemanWunsch.java">here</a>
 * in the version from 7.11.2012 under the terms of Apache License 2.0.
 * 
 * @author Daniel Himmelein, Ben St&ouml;ver
 */
public class NeedlemanWunschAligner implements PairwiseAligner {
	public static int MATCH_SCORE = 1;
	public static int MISMATCH_SCORE = -1;
	public static int GAP_SCORE = -1;
	
	
	private char gapChar = '-';
  private int score = Integer.MIN_VALUE;
  private CharSequence seqA;
  private CharSequence seqB;
  private int[][] matrix;
  private StringBuffer alignmentSeqA = null;
  private StringBuffer alignmentSeqB = null;
 
  
  public char getGapChar() {
		return gapChar;
	}


	public void setGapChar(char gapChar) {
		this.gapChar = gapChar;
	}


	public int getScore() {
		return score;
	}


	public CharSequence[] align(CharSequence seqA, CharSequence seqB) {
  	this.seqA = seqA;
  	this.seqB = seqB;
  	int length = seqA.length() + seqB.length();
  	alignmentSeqA = new StringBuffer(length);
  	alignmentSeqB = new StringBuffer(length);
  	
  	init();
  	process();
  	backtrack();
  	
  	return new CharSequence[]{alignmentSeqA, alignmentSeqB};
  }
  
  
  private void init() {
    matrix = new int[seqA.length() + 1][seqB.length() + 1];
    for (int i = 0; i <= seqA.length(); i++) {
      for (int j = 0; j <= seqB.length(); j++) {
        if (i == 0) {
          matrix[i][j] = -j;
        } else if (j == 0) {
          matrix[i][j] = -i;
        } else {
          matrix[i][j] = 0;
        }
      }
    }
  }
 
  
  void process() {
    for (int i = 1; i <= seqA.length(); i++) {
      for (int j = 1; j <= seqB.length(); j++) {
        matrix[i][j] = Math.max(Math.max(
        		matrix[i - 1][j - 1] + weight(i, j),  // diagonal 
        		matrix[i][j - 1] + GAP_SCORE),  // left
        		matrix[i - 1][j] + GAP_SCORE);  // up
      }
    }
  }
  
 
  void backtrack() {
    int i = seqA.length();
    int j = seqB.length();
    score = matrix[i][j];
    while (i > 0 && j > 0) {                        
      if (matrix[i][j] == matrix[i-1][j-1] + weight(i, j)) {                          
        alignmentSeqA.append(seqA.charAt(i - 1));
        alignmentSeqB.append(seqB.charAt(j - 1));
        i--;
        j--;                            
        //continue;
      } 
      else if (matrix[i][j] == matrix[i][j-1] - 1) {
        alignmentSeqA.append(getGapChar());
        alignmentSeqB.append(seqB.charAt(j - 1));
        j--;
        //continue;
      } 
      else {
        alignmentSeqA.append(seqA.charAt(i - 1));
        alignmentSeqB.append(getGapChar());
        i--;
        //continue;
      }
    }
    alignmentSeqA = alignmentSeqA.reverse();
    alignmentSeqB = alignmentSeqB.reverse();
  }
 
  
  private int weight(int i, int j) {
    if (seqA.charAt(i - 1) == seqB.charAt(j - 1)) {
      return MATCH_SCORE;
    } else {
      return MISMATCH_SCORE;
    }
  }
}
