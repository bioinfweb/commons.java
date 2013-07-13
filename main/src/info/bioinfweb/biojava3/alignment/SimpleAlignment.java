package info.bioinfweb.biojava3.alignment;


import info.bioinfweb.biojava3.alignment.template.Alignment;
import info.bioinfweb.biojavax.bio.phylo.io.nexus.CharSet;

import java.util.ArrayList;
import java.util.Collection;
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
		return names.iterator();
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
}
