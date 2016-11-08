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
package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import java.util.Arrays;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public abstract class RemoveSequenceView<C extends Compound> extends SequenceProxyView<C> implements SequenceView<C> {
	private int[] remainingPositions = null;
	
	
	public RemoveSequenceView(Sequence<C> sequence, Integer bioStart,	Integer bioEnd) {
		super(sequence, bioStart, bioEnd);
	}


	public RemoveSequenceView(Sequence<C> sequence) {
		super(sequence);
	}

	
	protected abstract boolean keepPosition(int viewedPos);
	

	private void createPosTraslation() {
		remainingPositions = new int[getViewedSequence().getLength()];
		int arrayPos = 0;
		for (int viewedPos = 1; viewedPos <= getViewedSequence().getLength(); viewedPos++) {
			if (keepPosition(viewedPos)) {
				remainingPositions[arrayPos] = viewedPos;
				arrayPos++;
			}
		}
		remainingPositions = Arrays.copyOf(remainingPositions, arrayPos);  // Shorten array to the necessary length
	}


	protected int[] getRemainingPositions() {
		if (remainingPositions == null) {
			createPosTraslation();
		}
		return remainingPositions;
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getCompoundAt(int)
	 */
	@Override
	public C getCompoundAt(int position) {
		return super.getCompoundAt(getRemainingPositions()[position - 1]);  // The super method takes account of bioStart
	}


	/* (non-Javadoc)
	 * @see org.biojava3.core.sequence.template.SequenceProxyView#getLength()
	 */
	@Override
	public int getLength() {
		return getRemainingPositions().length;
	}
}
