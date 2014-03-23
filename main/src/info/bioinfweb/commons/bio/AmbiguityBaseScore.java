package info.bioinfweb.commons.bio;



public class AmbiguityBaseScore implements Comparable<AmbiguityBaseScore>, Cloneable {
	public static final int ADENIN_INDEX = 0;
	public static final int THYMIN_INDEX = 1;
	public static final int CYTOSIN_INDEX = 2;
	public static final int GUANIN_INDEX = 3;
	
	
  private double[] scores = null;

  
	/**
	 * Note that no deep copy of the array will be made.
	 * @param scores
	 */
	public AmbiguityBaseScore(double[] scores) {
		super();
		this.scores = scores;
	}


 	public AmbiguityBaseScore(double adenin, double thymin, double cytosin, double guanin) {
		super();
		this.scores = new double[]{adenin, thymin, cytosin, guanin};
	}

 	
 	public double getAdeninScore() {
 		return scores[ADENIN_INDEX];
 	}
 	

 	public double getThyminScore() {
 		return scores[THYMIN_INDEX];
 	}
 	

 	public double getCytosinScore() {
 		return scores[CYTOSIN_INDEX];
 	}
 	

 	public double getGuaninScore() {
 		return scores[GUANIN_INDEX];
 	}

 	
 	public void setAdeninScore(double score) {
 		scores[ADENIN_INDEX] = score;
 	}
 	

 	public void setThyminScore(double score) {
 		scores[THYMIN_INDEX] = score;
 	}
 	

 	public void setCytosinScore(double score) {
 		scores[CYTOSIN_INDEX] = score;
 	}
 	

 	public void setGuaninScore(double score) {
 		scores[GUANIN_INDEX] = score;
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
  
  
  @Override
	protected Object clone() throws CloneNotSupportedException {
		return new AmbiguityBaseScore(getAdeninScore(), getThyminScore(), getCytosinScore(), getGuaninScore());
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
