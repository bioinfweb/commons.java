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
package info.bioinfweb.commons.bio.biojava3.core.sequence.compound;


import org.biojava3.core.sequence.compound.RNACompoundSet;

import info.bioinfweb.commons.bio.biojava3.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Nucleotide compound set that contains all RNA compounds but no gap <i>-</i> character (and without <i>N</i>).
 * ({@link RNACompoundSet} contains both these symbols.)
 * 
 * @author Ben St&ouml;ver
 * @since build 10
 */
public class NoGapRNACompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NoGapRNACompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NoGapRNACompoundSet() {
		super();
    addNucleotideCompound("A", "T");
    addNucleotideCompound("T", "A");
    addNucleotideCompound("G", "C");
    addNucleotideCompound("C", "G");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NoGapRNACompoundSet getNoGapRNACompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NoGapRNACompoundSet();
		}
		return sharedInstance;
	}
}
