/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons;


import java.util.ArrayList;
import java.util.List;

import org.junit.* ;


import static org.junit.Assert.* ;



public class RandomValuesTest {
	@Test
	public void test_listSwap() {
		List<Integer> list = new ArrayList<Integer>(10);
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		RandomValues.listSwap(list, 10);
		
		int changeCount = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != i) {
				changeCount++;
			}
		}
		assertTrue("Changes: " + changeCount + " (Note that this test might ramdomly fail in very few cases.)", changeCount > 0);
		
//		System.out.print("listSwap: ");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println();
	}
}
