package info.bioinfweb.biojava3.alignment.template;


import info.bioinfweb.biojavax.bio.phylo.io.nexus.CharSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Classes implementing this interface represent an aligned set of sequences. Additionally the alignment
 * can be annotated by character sets as defined in the Nexus format.
 * 
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
}