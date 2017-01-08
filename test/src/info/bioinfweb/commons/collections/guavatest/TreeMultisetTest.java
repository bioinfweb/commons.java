/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.collections.guavatest;


import java.util.Comparator;
import java.util.Iterator;

import info.bioinfweb.commons.collections.IntervalElement;

import com.google.common.collect.TreeMultiset;



public class TreeMultisetTest {
	private static void printSet(TreeMultiset<IntervalElement> set) {
	  Iterator<IntervalElement> iterator = set.iterator();
	  while (iterator.hasNext()) {
	  	System.out.println(iterator.next());
	  }
	}
	
	
	private static void testEqualElements() {
		TreeMultiset<IntervalElement> set = TreeMultiset.create(new Comparator<IntervalElement>() {
					@Override
					public int compare(IntervalElement e1, IntervalElement e2) {
						return e1.firstPos - e2.firstPos;
					}
				}); 
		
		IntervalElement e1 = new IntervalElement(5, 10, "a");
		IntervalElement e2 = new IntervalElement(5, 10, "b");
		IntervalElement e3 = new IntervalElement(5, 12, "c");
		IntervalElement e4 = new IntervalElement(7, 15, "d");
		
		set.add(e1);
		set.add(e2);
		set.add(e3);
		set.add(e4);
		
		printSet(set);
	}
	
	
	private static void testUnequalElements() {
		TreeMultiset<IntervalElement> set = TreeMultiset.create(new Comparator<IntervalElement>() {
					@Override
					public int compare(IntervalElement e1, IntervalElement e2) {
						int result = e1.firstPos - e2.firstPos;
						if (result == 0) {
							result = e1.lastPos - e2.lastPos;
						}
						if (result == 0) {
							result = e1.text.hashCode() - e2.text.hashCode();
						}
						return result;
					}
				}); 
		
		IntervalElement e1 = new IntervalElement(5, 10, "a");
		IntervalElement e2 = new IntervalElement(5, 10, "b");
		IntervalElement e2a = new IntervalElement(5, 10, "b");
		IntervalElement e3 = new IntervalElement(5, 12, "c");
		IntervalElement e4 = new IntervalElement(7, 15, "d");
		
		set.add(e1);
		set.add(e2);
		set.add(e2a);
		set.add(e3);
		set.add(e4);
		
		System.out.println(e2.equals(e2a));
		printSet(set);
		System.out.println();
	}
	
	
  public static void main(String[] args) {
  	testEqualElements();
	  //testUnequalElements();
	}
}
