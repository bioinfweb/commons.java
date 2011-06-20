package info.webinsel.util.servlet.acceptlanguage;


import java.util.Comparator;



/**
 * This class can be used to sort lists of {@link AcceptLanguageEntry} objects by their quality value.
 * The highest value will be the first in the list.
 * 
 * @author Ben St&ouml;ver
 */
public class QualityComparator implements Comparator<AcceptLanguageEntry> {
	private static QualityComparator firstInstance = null;
	
	
	private QualityComparator() {
		super();
	}


  public static QualityComparator getInstance() {
  	if (firstInstance == null) {
  		firstInstance = new QualityComparator();
  	}
  	return firstInstance;
  }
	

	/**
	 * Returns -1 if <code>o1.getQuality()</code> is less than <code>o2.getQuality()</code>, 0 is they are 
	 * equal and 1 if <code>o1.getQuality()</code> is greater than <code>o2.getQuality()</code>. (Note that lists
	 * sorted with this method will the contains the highest quality values at first.)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(AcceptLanguageEntry o1, AcceptLanguageEntry o2) {
		return (int)Math.round(Math.signum(o2.getQuality() - o1.getQuality()));
	}
}
