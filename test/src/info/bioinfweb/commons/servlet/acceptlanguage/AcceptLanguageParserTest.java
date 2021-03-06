/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.servlet.acceptlanguage;


import static org.junit.Assert.* ;


import info.bioinfweb.commons.testing.TestTools;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.junit.* ;



public class AcceptLanguageParserTest {
	/**
	 * Returns an accessible private method from {@link AcceptLanguageParser}. 
	 * @param name
	 * @param params
	 * @return
	 */
	public static Method getPrivateMethod(String name, Class... params) {
		return TestTools.getPrivateMethod(AcceptLanguageParser.class, name, params);
	}
	
	
  @Test
  public void test_parseSingleHeader() {
  	Method method = getPrivateMethod("parseSingleHeader", String.class);
		try {
			AcceptLanguageEntry entry = (AcceptLanguageEntry)method.invoke(null, "en-us;q=0.5");
			assertEquals("en", entry.getLanguage());
			assertEquals("us", entry.getCountry());
			assertEquals(0.5, entry.getQuality(), 0.0);
			
			entry = (AcceptLanguageEntry)method.invoke(null, "de-de");
			assertEquals("de", entry.getLanguage());
			assertEquals("de", entry.getCountry());
			assertEquals(1.0, entry.getQuality(), 0.0);
			
			entry = (AcceptLanguageEntry)method.invoke(null, "de;q=0.8");
			assertEquals("de", entry.getLanguage());
			assertEquals("*", entry.getCountry());
			assertEquals(0.8, entry.getQuality(), 0.0);
			
			entry = (AcceptLanguageEntry)method.invoke(null, "de");
			assertEquals("de", entry.getLanguage());
			assertEquals("*", entry.getCountry());
			assertEquals(1.0, entry.getQuality(), 0.0);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
  }
  
  
  @Test
  public void test_parseHeader() {
  	Iterator<AcceptLanguageEntry> iterator = 
  		AcceptLanguageParser.parseHeader("de;q=0.8,de-de,en-us;q=0.5,en;q=0.3").iterator();
  	
		AcceptLanguageEntry entry = iterator.next();
		assertEquals("de", entry.getLanguage());
		assertEquals("de", entry.getCountry());
		assertEquals(1.0, entry.getQuality(), 0.0);
		
		entry = iterator.next();
		assertEquals("de", entry.getLanguage());
		assertEquals("*", entry.getCountry());
		assertEquals(0.8, entry.getQuality(), 0.0);
		
		entry = iterator.next();
		assertEquals("en", entry.getLanguage());
		assertEquals("us", entry.getCountry());
		assertEquals(0.5, entry.getQuality(), 0.0);
		
		entry = iterator.next();
		assertEquals("en", entry.getLanguage());
		assertEquals("*", entry.getCountry());
		assertEquals(0.3, entry.getQuality(), 0.0);
  }
}
