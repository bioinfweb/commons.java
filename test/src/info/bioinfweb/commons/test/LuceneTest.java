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
package info.bioinfweb.commons.test;


import org.apache.lucene.util.packed.PackedInts;
import org.apache.lucene.util.packed.PackedInts.Mutable;



public class LuceneTest {
  public static void main(String[] args) {
		Mutable mutable = PackedInts.getMutable(100, 6, PackedInts.Format.PACKED);
		mutable.set(56, 8);
		mutable.set(60, 63);
		mutable.set(61, 64);
		mutable.set(62, 7);
		
		System.out.print(mutable.get(56) + " ");
		System.out.print(mutable.get(60) + " ");
		System.out.print(mutable.get(61) + " ");
		System.out.print(mutable.get(62) + " ");
		System.out.println(mutable.size());
		System.out.println();

  
		mutable = PackedInts.getMutable(100, 8, PackedInts.Format.PACKED);
		mutable.set(56, 8);
		mutable.set(60, 255);
		mutable.set(61, 256);
		mutable.set(62, 7);
		
		System.out.println(mutable.get(56));
		System.out.println(mutable.get(60));
		System.out.println(mutable.get(61));
		System.out.println(mutable.get(62));
}	
}
