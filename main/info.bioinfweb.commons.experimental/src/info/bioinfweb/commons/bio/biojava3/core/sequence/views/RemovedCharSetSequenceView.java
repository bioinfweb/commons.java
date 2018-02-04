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
package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import info.bioinfweb.commons.collections.NonOverlappingIntervalList;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceView;



public class RemovedCharSetSequenceView<C extends Compound> extends RemoveSequenceView<C> implements SequenceView<C> {
	private NonOverlappingIntervalList charSet;
	
	
	public RemovedCharSetSequenceView(Sequence<C> sequence, Integer bioStart,	Integer bioEnd, 
			NonOverlappingIntervalList charSet) {
		
		super(sequence, bioStart, bioEnd);
		this.charSet = charSet;
	}


	public RemovedCharSetSequenceView(Sequence<C> sequence, NonOverlappingIntervalList charSet) {
		super(sequence);
		this.charSet = charSet;
	}


	@Override
	protected boolean keepPosition(int viewedPos) {
		return !charSet.contains(viewedPos);
	}
}
