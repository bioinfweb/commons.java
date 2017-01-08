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
package info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus;


import info.bioinfweb.commons.collections.SimpleSequenceInterval;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import org.biojavax.bio.phylo.io.nexus.NexusBlock;
import org.biojavax.bio.phylo.io.nexus.NexusFileFormat;



/**
 * Represents a part of the contents of a <code>SETS</code> block of a Nexus file. (Only 
 * <code>CharSet</code> commands are stored.)
 * 
 * @author Ben St&ouml;ver
 */
public class SetsBlock extends NexusBlock.Abstract {
	public static final String SETS_BLOCK = "SETS";

	
	private TreeMap<String, CharSet> charSets = new TreeMap<String, CharSet>(); 
	
	
	public SetsBlock() {
		super(SETS_BLOCK);
	}

	
	/**
	 * Adds a new character set with the specified name, if not already present.
	 * @param name
	 */
	public void addCharSet(String name) {
		if (charSets.get(name) == null) {
			charSets.put(name, new CharSet(name));
		}
	}
	
	
	/**
	 * Adds the specified interval to the specified character set.
	 * @param name
	 * @param start
	 * @param end
	 * @throws IllegalArgumentException - if there is no character set with the specified name 
	 *         present.
	 */
	public void addCharSetInterval(String name, int start, int end) {
		CharSet charSet = charSets.get(name);
		if (charSet == null) {
			throw new IllegalArgumentException("A character set with the name \"" + name + 
					"\" is not present in this object.");
		}
		else {
			charSet.add(start, end);
		}
	}
	
	
	public CharSet getCharSet(String name) {
		return charSets.get(name);
	}
	
	
	public Collection<CharSet> values() {
		return charSets.values();
	}


	/**
	 * The implementation of this method is empty since it is not used in HIR-Finder.
	 * 
	 * @see org.biojavax.bio.phylo.io.nexus.NexusBlock$Abstract#writeBlockContents(java.io.Writer)
	 */
	@Override
	protected void writeBlockContents(Writer writer) throws IOException {
		//TODO Müssen BEGIN und END hier geschrieben werden?
		Iterator<String> nameIterator = charSets.keySet().iterator();
		while (nameIterator.hasNext()) {
			String name = nameIterator.next();
			writer.write(SetsBlockParser.CHAR_SET_COMMAND.toUpperCase() + " " + name + " =");
			CharSet charSet = charSets.get(name);
			Iterator<SimpleSequenceInterval> intervalIterator = charSet.iterator();
			while (intervalIterator.hasNext()) {
				SimpleSequenceInterval interval = intervalIterator.next();
				writer.write(" " + interval.getFirstPos());
				if (interval.getFirstPos() != interval.getLastPos()) {
					writer.write(SetsBlockParser.START_END_SEPARATER + interval.getLastPos());
				}
			}
		}
		writer.write(";" + NexusFileFormat.NEW_LINE);
	}
}
