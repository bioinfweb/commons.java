/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
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



public class PackedSignedIntegerArrayListTest {
	@Test
	public void test_get_set() {
		final int minValue = -10;
		final int size = 128;
		
		for (int bitsPerValue = 7; bitsPerValue <= 64; bitsPerValue++) {
			PackedSignedIntegerArrayList list = new PackedSignedIntegerArrayList(bitsPerValue, minValue, size);
			for (int i = minValue; i < size + minValue; i++) {
				list.add(i);
			}
			
			for (int i = 0; i < size; i++) {
				assertEquals(i + minValue, list.get(i));
			}
		}
	}
}
