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
package info.bioinfweb.commons.bio.biojava3.core.sequence.views;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.template.Compound;
import org.biojava3.core.sequence.template.CompoundSet;
import org.biojava3.core.sequence.template.Sequence;
import org.biojava3.core.sequence.template.SequenceMixin;
import org.biojava3.core.sequence.template.SequenceProxyView;
import org.biojava3.core.sequence.template.SequenceView;



/**
 * Allows to view multiple concatenated sequences as a single sequence.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <C>
 */
public class ConcatenatedSequenceView<C extends Compound> implements SequenceView<C> {
	//TODO Where is the difference to the according sequence view existing in BioJava?
	public class ViewedSequence {
		private Sequence<C> sequence;
		private int bioStart = -1;
		
		
		public ViewedSequence(Sequence<C> sequence) {
			super();
			this.sequence = sequence;
		}


		public Sequence<C> getSequence() {
			return sequence;
		}


		public int getBioStart() {
			return bioStart;
		}
	}
	
	
	private class SequenceList implements List<Sequence<C>>{
		@Override
		public void add(int index, Sequence<C> sequence) {
			if (sequence != null) {
	  		viewedSequences.add(index, new ViewedSequence(sequence));
  			recalculateStartPositions();
			}
			else {
				throw new NullPointerException("Adding null to this list is not allowed.");
			}
		}


		@Override
		public boolean add(Sequence<C> sequence) {
			if (sequence != null) {
				boolean result = viewedSequences.add(new ViewedSequence(sequence));
				recalculateStartPositions();
				return result;
			}
			else {
				throw new NullPointerException("Adding null to this list is not allowed.");
			}
		}


		@Override
		public boolean addAll(Collection<? extends Sequence<C>> collection) {
			Iterator<? extends Sequence<C>> iterator = collection.iterator();
			boolean result = true;
			while (iterator.hasNext()) {
				result = add(iterator.next()) && result;
			}
			return result;
		}


		@Override
		public boolean addAll(int index, Collection<? extends Sequence<C>> collection) {
			Iterator<? extends Sequence<C>> iterator = collection.iterator();
			while (iterator.hasNext()) {
				add(index, iterator.next());
				index++;
			}
			return true;
		}


		@Override
		public void clear() {
			viewedSequences.clear();
		}


		@Override
		public boolean contains(Object o) {
			Iterator<ViewedSequence> iterator = viewedSequences.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().sequence.equals(o)) {
					return true;
				}
			}
			return false;
		}


		@Override
		public boolean containsAll(Collection<?> collection) {
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				if (!contains(iterator.next())) {
					return false;
				}
			}
			return true;
		}


		@Override
		public Sequence<C> get(int index) {
			return viewedSequences.get(index).sequence;
		}


		@Override
		public int indexOf(Object o) {
			for (int i = 0; i < viewedSequences.size(); i++) {
				if (viewedSequences.get(i).sequence.equals(o)) {
					return i;
				}
			}
			return -1;
		}


		@Override
		public boolean isEmpty() {
			return viewedSequences.isEmpty();
		}


		@Override
		public int lastIndexOf(Object o) {
			for (int i = viewedSequences.size() - 1; i >= 0; i--) {
				if (viewedSequences.get(i).sequence.equals(o)) {
					return i;
				}
			}
			return -1;
		}


		@Override
		public Iterator<Sequence<C>> iterator() {
			return listIterator();
		}


		@Override
		public ListIterator<Sequence<C>> listIterator() {
			return listIterator(0);
		}


		@Override
		public ListIterator<Sequence<C>> listIterator(int index) {
			final ListIterator<ViewedSequence> iterator = viewedSequences.listIterator(index);
			return new ListIterator<Sequence<C>>() {

				@Override
				public void add(Sequence<C> e) {
					throw new UnsupportedOperationException();  // Impossible because start indices change.
				}

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public boolean hasPrevious() {
					return iterator.hasPrevious();
				}

				@Override
				public Sequence<C> next() {
					return iterator.next().sequence;
				}

				@Override
				public int nextIndex() {
					return iterator.nextIndex();
				}

				@Override
				public Sequence<C> previous() {
					return iterator.previous().sequence;
				}

				@Override
				public int previousIndex() {
					return iterator.previousIndex();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();  // Impossible because start indices change.
				}

				@Override
				public void set(Sequence<C> e) {
					throw new UnsupportedOperationException();  // Impossible because start indices change.
				}
			};
		}

		
		@Override
		public Sequence<C> remove(int index) {
			ViewedSequence result = viewedSequences.remove(index);
			recalculateStartPositions();
			return result.sequence;
		}


		@Override
		public boolean remove(Object o) {
			boolean result = viewedSequences.remove(o);
			recalculateStartPositions();
			return result;
		}


		@Override
		public boolean removeAll(Collection<?> collection) {
			Iterator<?> iterator = collection.iterator();
			boolean result = true;
			while (iterator.hasNext()) {
				result = remove(iterator.next()) && result; 
			}
			return result;  // Start positions do not have to be recalculated since the list is empty now.
		}


		@Override
		public boolean retainAll(Collection<?> collection) {
			Iterator<ViewedSequence> iterator = viewedSequences.iterator();
			boolean result = true;
			while (iterator.hasNext()) {
				ViewedSequence element = iterator.next();
				if (!collection.contains(element.sequence)) {
					result = viewedSequences.remove(element) && result;
				}
			}
			recalculateStartPositions();
			return result;
		}


		@Override
		public Sequence<C> set(int index, Sequence<C> element) {
			ViewedSequence result = viewedSequences.set(index, new ViewedSequence(element));
			recalculateStartPositions();
			return result.sequence;
		}


		@Override
		public int size() {
			return viewedSequences.size();
		}


		@Override
		public List<Sequence<C>> subList(int fromIndex, int toIndex) {
			List<ViewedSequence> list = viewedSequences.subList(fromIndex, toIndex);
			List<Sequence<C>> result = new ArrayList<Sequence<C>>(list.size());
			Iterator<ViewedSequence> iterator = list.iterator();
			while (iterator.hasNext()) {
				result.add(iterator.next().sequence);
			}
			return result;
		}


		@Override
		public Object[] toArray() {
			return toArray(new Object[size()]);
		}


		@Override
		public <T> T[] toArray(T[] array) {
			if (array.length < size()) {
				array = (T[])Array.newInstance(array.getClass().getComponentType(), size());
			}
			Iterator<Sequence<C>> iterator = iterator();
			int index = 0;
			while (iterator.hasNext()) {
				array[index] = (T)iterator.next();
				index++;
			}
			return array;
		}

	}
	
	
	private List<ViewedSequence> viewedSequences = new ArrayList<ViewedSequence>();
	private SequenceList sequenceList = new SequenceList();
		
	
	public ConcatenatedSequenceView() {
		super();
	}


	public ConcatenatedSequenceView(Collection<Sequence<C>> sequences) {
		super();
  	sequencesAsList().addAll(sequences);
	}


	public ConcatenatedSequenceView(Sequence<C>[] sequences) {
		super();
		for (int i = 0; i < sequences.length; i++) {
			sequencesAsList().add(sequences[i]);
		}
	}

	
	public ConcatenatedSequenceView(Sequence<C> sequence) {
		super();
		if (sequence != null) {
			sequencesAsList().add(sequence);
		}
		else {
			throw new NullPointerException();
		}
	}

	
	public int getViewedSequenceCount() {
		return viewedSequences.size();
	}
	

	/**
	 * Returns the first viewed sequence.
	 * 
	 * @see org.biojava3.core.sequence.template.SequenceView#getViewedSequence()
	 */
	@Override
	public Sequence<C> getViewedSequence() {
		return viewedSequences.get(0).sequence;
	}


	@Override
	public Iterator<C> iterator() {
		return new SequenceMixin.SequenceIterator<C>(this);
	}


	public ViewedSequence getViewedSequenceByPosition(int position) {
		if (position <= getLength()) {  // BioJava indices start with 1
			int seqIndex = 1;
			while ((seqIndex < getViewedSequenceCount()) && (viewedSequences.get(seqIndex).bioStart <= position)) {
				seqIndex++;
			}
			return viewedSequences.get(seqIndex - 1);
		}
		else {
			return null;
		}
	}
	
	
	@Override
	public C getCompoundAt(int position) {
		ViewedSequence seq = getViewedSequenceByPosition(position);
		if (seq != null) {
			return seq.sequence.getCompoundAt(position - seq.bioStart + 1); 
		}
		else {
			throw new ArrayIndexOutOfBoundsException(position);
		}
	}

	
	@Override
	public int getLength() {
		ViewedSequence seq = viewedSequences.get(getViewedSequenceCount() - 1); 
		return seq.bioStart + seq.sequence.getLength() - 1;
	}


	@Override
	public int countCompounds(C... compounds) {
		return SequenceMixin.countCompounds(this, compounds);	
	}


	@Override
	public List<C> getAsList() {
		return SequenceMixin.toList(this);
	}


	@Override
	public CompoundSet<C> getCompoundSet() {
		return getViewedSequence().getCompoundSet();
	}


	@Override
	public int getIndexOf(C compound) {
		return SequenceMixin.indexOf(this, compound);
	}


	@Override
	public SequenceView<C> getInverse() {
		return SequenceMixin.inverse(this);
	}


	@Override
	public int getLastIndexOf(C compound) {
		return SequenceMixin.lastIndexOf(this, compound);
	}


	@Override
	public String getSequenceAsString() {
		return SequenceMixin.toString(this);
	}


	@Override
	public SequenceView<C> getSubSequence(final Integer bioStart, final Integer bioEnd) {
    return new SequenceProxyView<C>(this, bioStart, bioEnd);
	}


	/**
	 * Returns the accession ID of the first viewed sequence.
	 * 
	 * @see org.biojava3.core.sequence.template.Accessioned#getAccession()
	 */
	@Override
	public AccessionID getAccession() {
		return getViewedSequence().getAccession();
	}


	@Override
	public Integer getBioEnd() {
		return getLength();
	}


	@Override
	public Integer getBioStart() {
		return 1;
	}


	private void recalculateStartPositions() {
		Iterator<ViewedSequence> iterator = viewedSequences.iterator();
		int bioStart = 1;
		while (iterator.hasNext()) {
			ViewedSequence seq = iterator.next();
			seq.bioStart = bioStart;
			bioStart += seq.sequence.getLength();
		}
	}
	
	
	/**
	 * Returns a list implementation that allows to edit the underlying list of viewed sequences.
	 */
	public List<Sequence<C>> sequencesAsList() {
		return sequenceList;
	}
}
