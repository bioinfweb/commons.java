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
package info.bioinfweb.commons.collections;



/**
 * Implementation of {@link SequenceIntervalPositionAdapter} for {@link SimpleSequenceInterval}.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleSequenceIntervalPositionAdapter implements SequenceIntervalPositionAdapter<SimpleSequenceInterval> {
	@Override
	public int compare(SimpleSequenceInterval o1, SimpleSequenceInterval o2) {
		return o1.compareTo(o2);
	}

	
	@Override
	public int getFirstPos(SimpleSequenceInterval o) {
		return o.getFirstPos();
	}
	

	@Override
	public int getLastPos(SimpleSequenceInterval o) {
		return o.getLastPos();
	}

	
	@Override
	public void setFirstPos(SimpleSequenceInterval o, int newFirstPos) {
		o.setFirstPos(newFirstPos);
	}

	
	@Override
	public void setLastPos(SimpleSequenceInterval o, int newLastPos) {
		o.setLastPos(newLastPos);
	}
}
