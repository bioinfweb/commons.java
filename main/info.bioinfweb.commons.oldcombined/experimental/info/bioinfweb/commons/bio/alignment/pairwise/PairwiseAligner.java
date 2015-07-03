package info.bioinfweb.commons.bio.alignment.pairwise;



public interface PairwiseAligner {
	public CharSequence[] align(CharSequence seqA, CharSequence seqB);
}
