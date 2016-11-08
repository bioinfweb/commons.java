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
package info.bioinfweb.commons.collections;


import org.junit.* ;


import static org.junit.Assert.* ;



public class MultiTreeMapTest {
	public static void main(String[] args) {
  	MultiTreeMap<Integer, String> map = new MultiTreeMap<Integer, String>();
  	map.put(1, "1.1");
  	map.put(1, "1.2");
  	map.put(4, "4.1");
  	System.out.println(map.get(1));
  	System.out.println(map.get(4));
  	System.out.println(map.ceilingEntry(2));
  	System.out.println(map.floorEntry(2));
  	
	}
}
