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
package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import java.util.Iterator;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



public class UpperCaseSequenceView<C extends Compound> extends SequenceProxyView<C> 
    implements SequenceView<C>, CountableSequenceView {
	
	private CompoundSet<? extends C> compoundSet;


	public UpperCaseSequenceView(Sequence<C> sequence, Integer bioStart, Integer bioEnd, CompoundSet<? extends C> compoundSet) {
		super(sequence, bioStart, bioEnd);
		this.compoundSet = compoundSet;
	}


	public UpperCaseSequenceView(Sequence<C> sequence, CompoundSet<? extends C> compoundSet) {
		super(sequence);
		this.compoundSet = compoundSet;
	}


	@Override
	public C getCompoundAt(int position) {
		C compound = super.getCompoundAt(position);
		String upperCase = compound.getShortName().toUpperCase();
		if (compound.getShortName().equals(upperCase)) {
			return compound;
		}
		else {
			return compoundSet.getCompoundForString(upperCase);
		}
	}	
	
	
	public int countChangedCompounds() {
		int result = 0;
		Iterator<C> iterator = getViewedSequence().iterator();
		int pos = 1;  // BioJava sequences start with 1. 
		while (iterator.hasNext() && (pos <= getBioEnd())) {
			C compound = iterator.next();
			if ((pos >= getBioStart()) && (compound.getShortName() != compound.getShortName().toUpperCase())) {
				result++;
			}
			pos++;
		}
		return result;
	}
}
