/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.bio.biojava3.alignment;


import info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment;
import info.bioinfweb.commons.bio.biojava3.core.sequence.SequenceCompareUtils;
import info.bioinfweb.commons.bio.biojavax.bio.phylo.io.nexus.CharSet;
import info.bioinfweb.commons.text.UniqueNameMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Default implementation of {@link Alignment}.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleAlignment <S extends Sequence<C>, C extends Compound> implements Alignment<S, C> {
  private Map<String, CharSet> charSets = new TreeMap<String, CharSet>();
  
  /** Additional list used to store the order of the sequences in the alignment. */
  private ArrayList<String> names = new ArrayList<String>();
  
  private Map<String, S> sequences = new TreeMap<String, S>();
  
  
  public Map<String, CharSet> getCharSets() {
		return charSets;
	}
  
  
  public void addCharSet(CharSet charSet) {
  	getCharSets().put(charSet.getName(), charSet);
  }
  
  
  public void addAllCharSets(Collection<CharSet> collection) {
		Iterator<CharSet> iterator = collection.iterator();
		while (iterator.hasNext()) {
			CharSet current = iterator.next();
			getCharSets().put(current.getName(), current);
		}
  }


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#add(java.lang.String, S)
	 */
  @Override
	public void add(String name, S sequence) {
  	add(size(), name, sequence);
  }


  /* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#add(int, java.lang.String, S)
	 */
  @Override
	public void add(int index, String name, S sequence) {
  	if (getSequence(name) == null) {
  		names.add(index, name);
    	sequences.put(name, sequence);
  	}
  	else {
  		throw new IllegalArgumentException("A sequence with the name \"" + name + 
  				"\" is already contained in this alignment.");
  	}
  }


	@Override
	public void replace(String name, S sequence) {
		if (getSequence(name) != null) {
    	sequences.put(name, sequence);
		}
		else {
  		throw new IllegalArgumentException("A sequence with the name \"" + name + 
  				"\" is not contained in this alignment.");
		}
	}


	@Override
	public void replace(int index, S sequence) {
		replace(nameByIndex(index), sequence);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#clear()
	 */
	@Override
	public void clear() {
		names.clear();
		sequences.clear();
		charSets.clear();
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#containsName(java.lang.String)
	 */
	@Override
	public boolean containsName(String name) {
		return names.contains(name);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#containsAllNames(java.util.Collection)
	 */
	@Override
	public boolean containsAllNames(Collection<?> c) {
		return names.containsAll(c);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#getSequence(int)
	 */
	@Override
	public S getSequence(int index) {
		return sequences.get(names.get(index));
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#getSequence(java.lang.String)
	 */
	@Override
	public S getSequence(String name) {
		return sequences.get(name);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#indexOfName(java.lang.String)
	 */
	@Override
	public int indexByName(String name) {
		return names.indexOf(name);
	}


	@Override
	public String nameByIndex(int index) {
		return names.get(index);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return sequences.isEmpty();
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#nameIterator()
	 */
	@Override
	public Iterator<String> nameIterator() {
		final Iterator<String> iterator = names.iterator();
		return new Iterator<String>() {
			private String currentName;
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public String next() {
				currentName = iterator.next();
				return currentName;
			}

			@Override
			public void remove() {
				iterator.remove();
				sequences.remove(currentName);
			}
		};
	}
	
	
	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#remove(int)
	 */
	@Override
	public S remove(int index) {
		String name  = names.remove(index);
		return sequences.remove(name);
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#remove(java.lang.String)
	 */
	@Override
	public boolean remove(String name) {
		boolean result = names.remove(name);
		if (result) {
			sequences.remove(name);
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#size()
	 */
	@Override
	public int size() {
		return sequences.size();
	}


	@Override
	public int maxSequenceLength() {
		int result = 0;
		Iterator<String> iterator = nameIterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			result = Math.max(result, getSequence(name).getLength());
		}
		return result;
	}
	
	
	/**
	 * Moves all contained character sets by the specified offset.
	 * 
	 * @see CharSet#movePositions(int, int)
	 */
	@Override
	public void moveAllCharSetPositions(int start, int offset) {
		Iterator<CharSet> iterator = getCharSets().values().iterator();
		while (iterator.hasNext()) {
			iterator.next().movePositions(start, offset);
		}
	}
	
	
	@Override
	public void renameSequence(String currentName, String newName) {
		if (containsName(newName)) {
			throw new IllegalArgumentException("The sequence cannot be renamed, because another sequence with the name \"" +
					newName + "\" is already present.");
		}
		else {
			names.set(indexByName(currentName), newName);
			sequences.put(newName, sequences.get(currentName));
			sequences.remove(currentName);
		}
	}


	/**
	 * Changes the sequence names in this alignment according to the parameters of the specified name map
	 * and stores in the old and new names in map. (Previous contents of the map are deleted.)
	 */
	@Override
	public void renameSequences(UniqueNameMap nameMap) {
		nameMap.clear();
	  Map<String, S> newSequences = new TreeMap<String, S>();		// A new sequence map has to be created, some original names could be equal to the processed names of others.
		Iterator<String> iterator = nameIterator();
		while (iterator.hasNext()) {
			String originalName = iterator.next();
			String processedName = nameMap.addName(originalName);
			if (!originalName.equals(processedName)) {  // unchanged names have not been added to the list
				names.set(indexByName(originalName), processedName);
				newSequences.put(processedName, getSequence(originalName));
			}
			else {
				newSequences.put(originalName, getSequence(originalName));
			}
		}
		sequences = newSequences;
	}


	/**
	 * Returns this alignment as a map with sequence names as keys and sequences as values. Note that
	 * the returned map is read only. (It might throw an exception if modifying methods are called.)
	 * 
	 * @see info.bioinfweb.commons.bio.biojava3.alignment.template.Alignment#asMap()
	 */
	@Override
	public Map<String, S> asMap() {
		return Collections.unmodifiableMap(sequences);
	}

	
//TODO Method finished but untested. Commented out because sequences in the returned instance are only Views. 
// => Find BioJava method to clone sequences independently of their implementing class. (Only possible, if all implementing classes implement a clone method?)
// => AlignmentView API wäre Alternative, die evtl. zunächst verfolgt werden sollte. (Operationen wie in Preprocessor von AlignmentEvaluation.)
//	/* (non-Javadoc)
//	 * @see info.bioinfweb.biojava3.alignment.template.Alignment#subAlignment(int, int)
//	 */
//	@Override
//	public Alignment<S, C> subAlignment(int startColumn, int endColumn) {
//		SimpleAlignment<S, C> result = new SimpleAlignment<S, C>();
//
//		// Add character sets:
//		Iterator<String> iterator = getCharSets().keySet().iterator();
//		while (iterator.hasNext()) {
//			String name = iterator.next();
//			CharSet copy = new CharSet(name);
//			copy.addAll(getCharSets().get(name), startColumn, endColumn, true);
//			if (!copy.isEmpty()) {
//				result.addCharSet(copy);
//			}
//		}
//
//		// Add sequences
//		iterator = nameIterator();
//		while (iterator.hasNext()) {
//			String name =  iterator.next();
//			getSequence(name).getSubSequence(startColumn, endColumn - 1);
//		}
//		
//		return result;
//	}

	
	/** 
	 * Checks if this alignment contains the same sequences (name and sequence) as the specified one. (Ambiguity codes 
	 * are not checked for equivalence. Character sets are not taken into account.)
	 * 
	 * @param other
	 * @return
	 */
	public boolean sequncesEqual(Alignment<?, ?> other) {
	 	// Simple comparison of the maps is not enough, since BioJava sequence objects do not implement the equals method 
		// depending in the sequence. Otherwise result = sequences.equals(((Alignment)other).asMap()) would be enough.
		
		boolean result = size() == other.size();
		Iterator<String> iterator = nameIterator();
		while (result && iterator.hasNext()) {
			String name =  iterator.next();
			result =  result && other.containsName(name) && 
					SequenceCompareUtils.sequencesEqual(getSequence(name), other.getSequence(name));
		}
		return result;
	}
	

	/** 
	 * Checks if this alignment contains the same sequences (name and sequence) and the same character sets
	 * (names and positions) as the specified one. (Ambiguity codes are not checked for equivalence.)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean result = other instanceof Alignment<?, ?>;
		if (result) {
			Alignment<?, ?> otherAlignment = (Alignment<?, ?>)other;
			result = sequncesEqual(otherAlignment);
			if (result) {
				result = getCharSets().equals(otherAlignment.getCharSets());
			}
		}
		return result;
	}
}
