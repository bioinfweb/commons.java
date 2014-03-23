package info.bioinfweb.commons.bio;



public class AmbiguityBaseScore implements Comparable<AmbiguityBaseScore>, Cloneable {
	public static final int ADENINE_INDEX = 0;
	public static final int THYMINE_INDEX = 1;
	public static final int CYTOSINE_INDEX = 2;
	public static final int GUANINE_INDEX = 3;
	
	
  private double[] scores = null;

  
	/**
	 * Note that no deep copy of the array will be made.
	 * @param scores
	 */
	public AmbiguityBaseScore(double[] scores) {
		super();
		this.scores = scores;
	}


 	/**
 	 * Creates a new instance of this class.
 	 * 
 	 * @param adenine - the score for adenine
 	 * @param thymine - the score for thymin
 	 * @param cytosine - the score for cytosin
 	 * @param guanine - the score for guanine 
 	 */
 	public AmbiguityBaseScore(double adenine, double thymine, double cytosine, double guanine) {
		super();
		this.scores = new double[]{adenine, thymine, cytosine, guanine};
	}

 	
 	/**
 	 * Creates a new instance of this class and directly scales is to the specified sum.
 	 * 
 	 * @param adenine - the score for adenine
 	 * @param thymine - the score for thymin
 	 * @param cytosine - the score for cytosin
 	 * @param guanine - the score for guanine 
 	 * @param sum - the sum all scroes together shall have
 	 */
 	public AmbiguityBaseScore(double adenine, double thymine, double cytosine, double guanine, double sum) {
		this(adenine, thymine, cytosine, guanine);
		rescale(sum);
	}

 	
 	public double getAdenineScore() {
 		return scores[ADENINE_INDEX];
 	}
 	

 	public double getThymineScore() {
 		return scores[THYMINE_INDEX];
 	}
 	

 	public double getCytosineScore() {
 		return scores[CYTOSINE_INDEX];
 	}
 	

 	public double getGuanineScore() {
 		return scores[GUANINE_INDEX];
 	}

 	
 	public void setAdeninScore(double score) {
 		scores[ADENINE_INDEX] = score;
 	}
 	

 	public void setThyminScore(double score) {
 		scores[THYMINE_INDEX] = score;
 	}
 	

 	public void setCytosinScore(double score) {
 		scores[CYTOSINE_INDEX] = score;
 	}
 	

 	public void setGuaninScore(double score) {
 		scores[GUANINE_INDEX] = score;
 	}
 	
 	
 	public double getScoreByIndex(int index) {
 		return scores[index];
 	}
 	
 	
 	public void setScoreByIndex(int index, double score) {
 		scores[index] = score;
 	}
 	

  public void add(AmbiguityBaseScore other) {
		for (int i = 0; i < scores.length; i++) {
			scores[i] += other.scores[i];  
		}
  }
  
  
  public double getMaxScore() {
  	double result = scores[0];
  	for (int i = 1; i < scores.length; i++) {
			result = Math.max(result, scores[i]);
		}
  	return result;
  }
  
  
  /**
   * Returns the sum of all scores.
   */
  public double getSum() {
  	double result = scores[0];
  	for (int i = 1; i < scores.length; i++) {
			result += scores[i];
		}
  	return result;
  }
  
  
  /**
   * Scales all scores with the same linear factor so that the sum of all scores is equal to {@code sum}.
   * If the current sum of all scores is zero calling this method will have no effect. 
   * 
   * @param sum - the sum all scored together shall have
   */
  public void rescale(double sum) {
  	double previousSum = getSum();
  	if (previousSum > 0) {
	  	double factor = sum / previousSum;
	  	for (int i = 0; i < scores.length; i++) {
				scores[i] = factor * scores[i];
			}
  	}
  }
  
  
  @Override
	protected Object clone() throws CloneNotSupportedException {
		return new AmbiguityBaseScore(getAdenineScore(), getThymineScore(), getCytosineScore(), getGuanineScore());
	}


	@Override
	public boolean equals(Object other) {
		if (other instanceof AmbiguityBaseScore) {
			AmbiguityBaseScore otherScore = (AmbiguityBaseScore)other;
			for (int i = 0; i < scores.length; i++) {
				if (scores[i] != otherScore.scores[i]) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public int compareTo(AmbiguityBaseScore other) {
		for (int i = 0; i < scores.length; i++) {
			double diff = scores[i] - other.scores[i];
			if (diff != 0.0) {
				return (int)Math.signum(diff);
			}
		}
		return 0;
	}
}
