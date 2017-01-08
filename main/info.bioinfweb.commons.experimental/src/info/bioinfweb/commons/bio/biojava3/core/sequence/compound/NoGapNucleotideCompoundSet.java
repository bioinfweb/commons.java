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
package info.bioinfweb.commons.bio.biojava3.core.sequence.compound;


import info.bioinfweb.commons.bio.biojava3.core.sequence.template.AbstractNucleotideCompoundSet2;



/**
 * Nucleotide compound set that contains all DNA and RNA compounds but no gap <i>-</i> or ambiguity character.
 * 
 * @author Ben St&ouml;ver
 */
public class NoGapNucleotideCompoundSet extends AbstractNucleotideCompoundSet2 {
	private static NoGapNucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public NoGapNucleotideCompoundSet() {
		super();
    addNucleotideCompound("A", "T");
    addNucleotideCompound("T", "A");
    addNucleotideCompound("G", "C");
    addNucleotideCompound("C", "G");
    addNucleotideCompound("U", "A");
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static NoGapNucleotideCompoundSet getNoGapNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new NoGapNucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
