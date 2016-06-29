package info.bioinfweb.commons.testing;


import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;



/**
 * Provides assert methods of general use.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class Assert2 {
  public static void assertCollectionElementTypes(Collection<?> expectedList, Collection<?> actualList) {
  	if (!((expectedList == null) && (actualList == null))) {
    	assertEquals(expectedList.size(), actualList.size());

    	Iterator<?> expectedIterator = expectedList.iterator();
    	Iterator<?> actualIterator = expectedList.iterator();
    	while (expectedIterator.hasNext()) {
    		assertEquals(expectedIterator.next().getClass(), actualIterator.next().getClass());
    	}
  	}
  }
}
