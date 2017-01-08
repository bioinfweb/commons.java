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
 * Enumerates the types of basic changes that could be applied to a list or a sequence.
 * 
 * @author Ben St&ouml;ver
 */
public enum ListChangeType {
	/** Indicates that an element has been inserted into the list. */
	INSERTION,
	
	/** Indicates that an element has been removed from the list. */
	DELETION,

	/** 
	 * Indicates that an element in the list has been replaced by another one 
	 * (or edited, depending on the implementation). 
	 */
	REPLACEMENT;
}
