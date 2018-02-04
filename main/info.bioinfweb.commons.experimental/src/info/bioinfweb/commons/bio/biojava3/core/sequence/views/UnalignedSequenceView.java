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


import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceView;



/**
 * Implementation of the {@link SequenceView} interface which provides access 
 * to an aligned sequence (with gaps) as if it would be unaligned (without 
 * gaps). 
 * 
 * @author Ben St&ouml;ver
 */
public class UnalignedSequenceView<C extends Compound> extends RemoveSequenceView<C> implements SequenceView<C> {
	private CompoundSet<C> nonGapCompoundSet;
	

	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 * @param bioStart - start index, cannot be less than 1
	 * @param bioEnd - end index, cannot be greater than the sequence length
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet, 
			Integer bioStart, Integer bioEnd) {
		
		super(sequence, bioStart, bioEnd);
		this.nonGapCompoundSet = nonGapCompoundSet;
	}

	
	/**
	 * Creates a new instance of this class.
	 * @param sequence - the underlying aligned sequence
	 * @param nonGapCompoundSet - the set of compounds representing non-gap positions in <code>sequence</code> 
	 */
	public UnalignedSequenceView(Sequence<C> sequence, CompoundSet<C> nonGapCompoundSet) {
		super(sequence);
		this.nonGapCompoundSet = nonGapCompoundSet;
	}
	
	
	@Override
	protected boolean keepPosition(int viewedPos) {
		return nonGapCompoundSet.hasCompound(getViewedSequence().getCompoundAt(viewedPos));
	}


	/**
	 * Returns the set of compounds representing non-gap positions in the underlying sequence.
	 */
	public CompoundSet<C> getNonGapCompoundSet() {
		return nonGapCompoundSet;
	}
}
