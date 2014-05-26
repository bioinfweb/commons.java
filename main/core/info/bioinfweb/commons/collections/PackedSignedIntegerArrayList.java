/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
 * <http://bioinfweb.info/Commons>
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



public class PackedSignedIntegerArrayList extends PackedIntegerArrayList {
	private long minValue;
	
	
	public PackedSignedIntegerArrayList(int bitsPerValue, long minValue, long initialCapacity) {
		super(bitsPerValue, initialCapacity);
		this.minValue = minValue;
	}

	
	protected long shiftValue(long value) {
		return value - minValue;
	}
	
	
	@Override
	public long get(long index) {
		return super.get(index) + minValue;
	}

	
	@Override
	public void set(long index, long value) {  // This method is also called by add(). Therefore these method must not be overwritten to shift the value in there as well!
		super.set(index, shiftValue(value));
	}
}
