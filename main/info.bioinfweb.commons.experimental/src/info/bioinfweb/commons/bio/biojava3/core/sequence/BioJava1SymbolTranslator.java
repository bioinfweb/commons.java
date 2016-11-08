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
package info.bioinfweb.commons.bio.biojava3.core.sequence;


import info.bioinfweb.commons.bio.biojava3.core.sequence.compound.AlignmentAmbiguityNucleotideCompoundSet;

import org.biojava.bio.seq.DNATools;
import org.biojava.bio.symbol.AtomicSymbol;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.bio.symbol.Symbol;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Compound;



/**
 * Tool class that converts {@link AtomicSymbol} instances from BioJava 1 to {@link Compound} instances
 * used in BioJava 3.
 * <p>
 * This may be useful if you work with BioJava 3 but still need some functionality only implemented in 
 * BioJava 1. 
 * 
 * @author Ben St&ouml;ver
 */
public class BioJava1SymbolTranslator {
	public static NucleotideCompound symbolToNucleotideCompound(Symbol symbol) throws IllegalSymbolException {
		return AlignmentAmbiguityNucleotideCompoundSet.getAlignmentAmbiguityNucleotideCompoundSet().
				getCompoundForString("" + DNATools.dnaToken(symbol));
	}
	
	
	public static Symbol nucleotideCompoundToSymbol(NucleotideCompound compound) throws IllegalSymbolException {
		return DNATools.forSymbol(compound.getBase().charAt(0));
	}
	
	
  //TODO Implement according methods for codons and amino acids 
}
