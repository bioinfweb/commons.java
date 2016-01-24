/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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
package info.bioinfweb.commons.bio;


/**
 * Enumerates defined meanings of single character states in sequence alignments. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public enum CharacterStateMeaning {
	/** Indicates that the string representation of a token is the gap character or token (e.g. '-'). */
	GAP,
	
	/** Indicates that the string representation of a token is the missing data character or token (e.g. '?'). */
	MISSING,
	
	/** Indicates that the string representation of a token is the match character or token (e.g. '.'). */
	MATCH,

	/** 
	 * Indicates that a token is a representation of a character state (e.g. a nucleotide, an amino acid, 
	 * an ambiguity code, ...). 
	 */
	CHARACTER_STATE,
	
	/** Indicates that a token has some other meaning which is not enumerated by this class. */
	OTHER;
}