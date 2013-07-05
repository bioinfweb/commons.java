package info.bioinfweb.biojava3.alignment.template;


import java.util.Collection;
import java.util.Iterator;

import org.biojava3.alignment.template.Profile;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.Sequence;



/**
 * Classes implementing this interface represent an aligned set of sequences. Additionally the alignment
 * can be annotated by character sets as defined in the Nexus format.
 * 
 * Although this interface is based on BioJava 3, it differs from {@link Profile} since alignment gaps
 * are treated the same way as nucleotides here. In the future there might be an alternative version 
 * extending {@link Profile}.
 * 
 * @author Ben St&ouml;ver
 */
public interface Alignment<S extends Sequence<C>, C extends Compound> {
	public void addSequence(String name, S sequence);

	public void addSequence(int index, String name, S sequence);

	public void clear();

	public boolean containsName(String name);

	public boolean containsAllNames(Collection<?> c);

	public S getSequence(int index);

	public S getSequence(String name);

	public int indexOfName(String name);

	public boolean isEmpty();

	public Iterator<String> nameIterator();

	public S remove(int index);

	public boolean remove(String name);

	public int size();
}