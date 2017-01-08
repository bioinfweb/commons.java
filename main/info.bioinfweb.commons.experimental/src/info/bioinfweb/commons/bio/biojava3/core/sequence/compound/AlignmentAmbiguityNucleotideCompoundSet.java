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



/**
 * Extension of {@link AlignmentAmbiguityRNACompoundSet} that also includes the <code>?</code> character.
 * 
 * @author Ben St&ouml;ver
 */
public class AlignmentAmbiguityNucleotideCompoundSet extends AmbiguityNoGapNucleotideCompoundSet {
	public static final char GAP_CHARACTER = '-'; 
	public static final char UNKNOWN_CHARACTER = '?'; 
	
	private static AlignmentAmbiguityNucleotideCompoundSet sharedInstance = null;
	
	
	/**
	 * Returns a new instance of this class.
	 */
	public AlignmentAmbiguityNucleotideCompoundSet() {
		super();
		addNucleotideCompound("" + GAP_CHARACTER, "" + GAP_CHARACTER);
		addNucleotideCompound("" + UNKNOWN_CHARACTER, "" + UNKNOWN_CHARACTER);
	}

	
	/**
	 * Returns a shared instance of this class.
	 */
	public static AlignmentAmbiguityNucleotideCompoundSet getAlignmentAmbiguityNucleotideCompoundSet() {
		if (sharedInstance == null) {
			sharedInstance = new AlignmentAmbiguityNucleotideCompoundSet();
		}
		return sharedInstance;
	}
}
