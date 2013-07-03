package info.bioinfweb.biojava3.alignment;


import info.webinsel.util.collections.SequenceIntervalList;
import info.webinsel.util.collections.SimpleSequenceInterval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Default implementation of {@link Profile}.
 * 
 * @author Ben St&ouml;ver
 */
public class SimpleAlignment <S extends Sequence<C>, C extends Compound> implements Alignment<S, C> {
  private Map<String, SequenceIntervalList<SimpleSequenceInterval>> charSets = 
  		new TreeMap<String, SequenceIntervalList<SimpleSequenceInterval>>();
  
  /** Additional list used to store the order of the sequences in the alignment. */
  private ArrayList<String> names = new ArrayList<String>();
  
  private Map<String, S> sequences = new TreeMap<String, S>();
  
  
  /* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#addSequence(java.lang.String, S)
	 */
  @Override
	public void addSequence(String name, S sequence) {
  	addSequence(size(), name, sequence);
  }


  /* (non-Javadoc)
	 * @see info.bioinfweb.biojava3.alignment.Alignment#addSequence(int, java.lang.String, S)
	 */
  @Override
	public void addSequence(int index, String name, S sequence) {
  	if (getSequence(name) == null) {
  		names.add(index, name);
    	sequences.put(name, sequence);
  	}
  	else {
  		throw new IllegalArgumentException("A sequence with the name \"" + name + 
  				"\" is already contained in this alignment.");
  	}
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
	public int indexOfName(String name) {
		return names.indexOf(name);
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
}
