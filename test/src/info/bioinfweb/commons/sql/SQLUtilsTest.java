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
package info.bioinfweb.commons.sql;


import java.util.EnumSet;

import org.junit.* ;


import static org.junit.Assert.* ;



public class SQLUtilsTest {
	private static enum TestEnum {
		A, B_1, B_2;
	}
	
	
  @Test
  public void test_readEnumSet() {
  	EnumSet<TestEnum> set = SQLUtils.readEnumSet("A,B_1", TestEnum.class);
  	assertTrue(set.contains(TestEnum.A));
  	assertTrue(set.contains(TestEnum.B_1));
  	assertFalse(set.contains(TestEnum.B_2));

  	set = SQLUtils.readEnumSet("A, B_1", TestEnum.class);
  	assertTrue(set.contains(TestEnum.A));
  	assertTrue(set.contains(TestEnum.B_1));
  	assertFalse(set.contains(TestEnum.B_2));
  	
  	set = SQLUtils.readEnumSet("", TestEnum.class);
  	assertTrue(set.isEmpty());
  	
  	assertNull(SQLUtils.readEnumSet(null, TestEnum.class));
  }
}
