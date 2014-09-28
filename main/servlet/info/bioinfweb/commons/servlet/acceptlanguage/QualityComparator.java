/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2014  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.servlet.acceptlanguage;


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
