/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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
package info.bioinfweb.commons.text;


import org.junit.* ;


import static org.junit.Assert.* ;



public class StringUtilsTest {
  @Test
  public void test_firstCharToLowerCase() {
  	assertEquals("content", StringUtils.firstCharToLowerCase("Content"));
  	assertEquals("", StringUtils.firstCharToLowerCase(""));
  	assertEquals("c", StringUtils.firstCharToLowerCase("C"));
  }
  
  
  @Test
  public void test_firstCharToUpperCase() {
  	assertEquals("Content", StringUtils.firstCharToUpperCase("content"));
  	assertEquals("", StringUtils.firstCharToUpperCase(""));
  	assertEquals("C", StringUtils.firstCharToUpperCase("c"));
  }
  
  
  @Test
  public void test_convertCamelCase() {
  	assertEquals("an-example-keyword", StringUtils.convertCamelCase("anExampleKeyword", "-"));
  	assertEquals("", StringUtils.convertCamelCase("", "-"));
  	assertEquals("a", StringUtils.convertCamelCase("A", "-"));
  }
}
