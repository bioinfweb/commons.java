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
package info.bioinfweb.commons.bio.biojava3.alignment.template;


import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.CharSet;
import info.bioinfweb.commons.text.UniqueNameMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Classes implementing this interface represent an aligned set of sequences. Additionally the alignment
 * can be annotated by character sets as defined in the Nexus format.
 * <p>
 * Although this interface is based on BioJava 3, it differs from {@link Profile} since alignment gaps
 * are treated the same way as nucleotides here. (Note that a compound set allowing gaps must be use for
 * this to work.) In the future there might be an alternative version extending {@link Profile}.
 * 
 * @author Ben St&ouml;ver
 */
public interface Alignment<S extends Sequence<C>, C extends Compound> {
  public Map<String, CharSet> getCharSets();
  
  public void addCharSet(CharSet charSet);
  
  public void addAllCharSets(Collection<CharSet> collection);

  public void add(String name, S sequence);

	public void add(int index, String name, S sequence);
	
	public void replace(String name, S sequence);

	public void replace(int index, S sequence);

	public void clear();

	public boolean containsName(String name);

	public boolean containsAllNames(Collection<?> c);

	public S getSequence(int index);

	public S getSequence(String name);

	public int indexByName(String name);
	
	public String nameByIndex(int index);

	public boolean isEmpty();

	public Iterator<String> nameIterator();

	public S remove(int index);

	public boolean remove(String name);

	public int size();
	
	public int maxSequenceLength();

	public void moveAllCharSetPositions(int start, int offset);

	public void renameSequence(String currentName, String newName);

	public void renameSequences(UniqueNameMap nameMap);
	
	public Map<String, S> asMap();
	
	public boolean sequncesEqual(Alignment<?, ?> other);
	
//TODO See SimpleAlignment on why this method is currently commented out.
//	/**
//	 * Returns a new alignment that contains a subset of the columns of this alignment (including the character sets that
//	 * contain columns in the specified area. The subalignment begins at the specified {@code beginIndex} and extends to 
//	 * the column at index {@code endIndex - 1}. Thus the length of the substring is {@code endIndex - beginIndex}. Note
//	 * that the first column in the alignment has the index 1 due to BioJava conventions.
//	 * 
//	 * @param startIndex - the beginning index, inclusive
//	 * @param endIndex - the ending index, exclusive
//	 * @return the specified alignment
//	 */
//	public Alignment<S, C> subAlignment(int startIndex, int endIndex);
}
