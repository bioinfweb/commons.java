/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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
package info.bioinfweb.commons.collections;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.map.MultiValueMap;



/**
 * A tree map that allows to have more than one element for the same key.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <K> the type of the keys used
 * @param <V> the type of the values (elements) used
 */
public class MultiTreeMap<K, V> {  //TODO Impl. von entsprechenden Interfaces lösen
	private TreeMap<K, ArrayList<V>> treeMap;
	private MultiValueMap multiValueMap;
	
	
	/**
	 * Creates a new instance of this class using the default key comparator.
	 */
	public MultiTreeMap() {
		super();
		treeMap = new TreeMap<K, ArrayList<V>>();
		multiValueMap = MultiValueMap.decorate(treeMap);
	}


	/**
	 * Creates a new instance of this class using the specified comparator.
	 * 
	 * @param comparator - the comparator used to sort the keys in the tree map
	 */
	public MultiTreeMap(Comparator<? super K> comparator) {
		super();
		treeMap = new TreeMap<K, ArrayList<V>>(comparator);
		multiValueMap = MultiValueMap.decorate(treeMap);
	}


	public Entry<K, ? extends Collection<V>> ceilingEntry(K key) {
		return treeMap.ceilingEntry(key);
	}


	public K ceilingKey(K key) {
		return treeMap.ceilingKey(key);
	}


	public Comparator<? super K> comparator() {
		return treeMap.comparator();
	}


	public NavigableSet<K> descendingKeySet() {
		return treeMap.descendingKeySet();
	}


	public Entry<K, ? extends Collection<V>> firstEntry() {
		return treeMap.firstEntry();
	}


	public K firstKey() {
		return treeMap.firstKey();
	}


	public Entry<K, ? extends Collection<V>> floorEntry(K key) {
		return treeMap.floorEntry(key);
	}


	public K floorKey(K key) {
		return treeMap.floorKey(key);
	}


	public Entry<K, ? extends Collection<V>> higherEntry(K key) {
		return treeMap.higherEntry(key);
	}


	public K higherKey(K key) {
		return treeMap.higherKey(key);
	}


	public Entry<K, ? extends Collection<V>> lastEntry() {
		return treeMap.lastEntry();
	}


	public K lastKey() {
		return treeMap.lastKey();
	}


	public Entry<K, ? extends Collection<V>> lowerEntry(K key) {
		return treeMap.lowerEntry(key);
	}


	public K lowerKey(K key) {
		return treeMap.lowerKey(key);
	}


	public void clear() {
		multiValueMap.clear();
	}


	public boolean containsKey(K key) {
		return multiValueMap.containsKey(key);
	}


	public boolean containsValue(K key, V value) {
		return multiValueMap.containsValue(key, value);
	}


	public boolean containsValue(V value) {
		return multiValueMap.containsValue(value);
	}


	public Set entrySet() {
		return multiValueMap.entrySet();
	}


	public Collection<V> get(K key) {
		return (Collection<V>)multiValueMap.get(key);
	}


	public Collection<? extends Collection<V>> getCollection(K key) {
		return multiValueMap.getCollection(key);
	}


	public boolean isEmpty() {
		return multiValueMap.isEmpty();
	}


	public Iterator<V> iterator(K key) {
		return multiValueMap.iterator(key);
	}


	public NavigableMap<K, ArrayList<V>> headMap(K toKey, boolean inclusive) {
		return treeMap.headMap(toKey, inclusive);
	}


	public SortedMap<K, ArrayList<V>> headMap(K toKey) {
		return treeMap.headMap(toKey);
	}


	public NavigableSet<K> navigableKeySet() {
		return treeMap.navigableKeySet();
	}


	public NavigableMap<K, ArrayList<V>> subMap(K fromKey, boolean fromInclusive,
			K toKey, boolean toInclusive) {
		return treeMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
	}


	public SortedMap<K, ArrayList<V>> subMap(K fromKey, K toKey) {
		return treeMap.subMap(fromKey, toKey);
	}


	public NavigableMap<K, ArrayList<V>> tailMap(K fromKey, boolean inclusive) {
		return treeMap.tailMap(fromKey, inclusive);
	}


	public SortedMap<K, ArrayList<V>> tailMap(K fromKey) {
		return treeMap.tailMap(fromKey);
	}


	public Set<K> keySet() {
		return multiValueMap.keySet();
	}


	public Object put(K key, V value) {
		return multiValueMap.put(key, value);
	}


	public void putAll(Map map) {
		multiValueMap.putAll(map);
	}


	public boolean putAll(K key, Collection<V> values) {
		return multiValueMap.putAll(key, values);
	}


	public V remove(K key, V value) {
		return (V)multiValueMap.remove(key, value);
	}


	public Collection<V> remove(K key) {
		return (Collection<V>)multiValueMap.remove(key);
	}


	public int size() {
		return multiValueMap.size();
	}


	public int size(K key) {
		return multiValueMap.size(key);
	}


	public int totalSize() {
		return multiValueMap.totalSize();
	}


	public Collection<V> values() {
		return multiValueMap.values();
	}

	
	public Iterator<V> valueIterator() {
		return new Iterator<V>() {
			private Iterator<K> keyIterator = keySet().iterator();
			private Iterator<V> collectionIterator = null;
			
			
			@Override
			public boolean hasNext() {
				if ((collectionIterator != null) && collectionIterator.hasNext()) {
					return true;
				}
				else {
					return keyIterator.hasNext();
				}
			}
			

			@Override
			public V next() {
				if ((collectionIterator == null) || !collectionIterator.hasNext()) {
					collectionIterator = get(keyIterator.next()).iterator();  //TODO Könnte zu einem key eine leere Liste zurückgegeben werden? Dann würde der Aufruf von next() zu einem Fehler führen.
				}
				return collectionIterator.next();
			}
			

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	//@Override
	public Object[] toArray() {
		return toArray(new Object[totalSize()]);
	}


	//@Override
	public <T> T[] toArray(T[] array) {
		if (array.length < size()) {
			array = (T[])Array.newInstance(array.getClass(), totalSize());
		}
		Iterator<V> iterator = valueIterator();
		int index = 0;
		while (iterator.hasNext()) {
			array[index] = (T)iterator.next();
			index++;
		}
		return array;
	}
}
