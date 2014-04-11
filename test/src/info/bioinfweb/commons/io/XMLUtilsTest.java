package info.bioinfweb.commons.io;


import java.util.regex.Matcher;

import org.junit.* ;


import static org.junit.Assert.* ;



public class XMLUtilsTest {
	@Test	
  public void test_XSD_FILE_NAME_PATTERN() {
		String value = "http://bioinfweb.info/xmlns/xtg http://bioinfweb.info/xmlns/xtg/1.1.xsd";
		Matcher matcher = XMLUtils.XSD_FILE_NAME_PATTERN.matcher(value);
		boolean result = matcher.matches(); 
		assertTrue("String not matched", result);
		if (result) {
			assertEquals("Extracted file name invalid", "1.1", matcher.group(1));
		}
	}
}
