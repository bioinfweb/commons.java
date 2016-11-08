/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
