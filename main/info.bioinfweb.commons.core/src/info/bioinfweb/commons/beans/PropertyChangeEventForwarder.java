/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.beans;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;



/**
 * Delegates {@link PropertyChangeEvent}s to a collection of other {@link PropertyChangeListener}s and
 * optionally adds a prefix to the property name. The source of the delegated events will be identical
 * with the initial source.
 * <p>
 * One application of this class is delegating property change events from bean objects to their parent 
 * objects containing them with an according prefix.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class PropertyChangeEventForwarder implements PropertyChangeListener {
	private String prefix;
	private Collection<PropertyChangeListener> targets;
	
	
	/**
	 * Creates a new instance of this class.
	 * <p>
	 * The specified collection will be used directly, i.e. later changes to that collection will automatically
	 * be reflected by this instance. (Note that this may lead to a {@link ConcurrentModificationException} if 
	 * that collection is modified in another thread while {@link #propertyChange(PropertyChangeEvent)} is 
	 * executed, even if the collection is thread save.)
	 * 
	 * @param prefix the prefix to be added in front of the property name (Maybe {@code null} or {@code ""}.)
	 * @param targets the collection of property change listeners to delegate the events to
	 * @throws NullPointerException if {@code targets} is {@code null}
	 */
	public PropertyChangeEventForwarder(String prefix, Collection<PropertyChangeListener> targets) {
		super();
		if (prefix != null) {
			this.prefix = prefix;
		}
		else {
			this.prefix = "";
		}
		if (targets != null) {
			this.targets = targets;
		}
		else {
			throw new NullPointerException("The collection of targets must not be null.");
		}
	}


	/**
	 * Creates a new instance of this class.
	 * 
	 * @param prefix the prefix to be added in front of the property name (Maybe {@code null} or {@code ""}.)
	 * @param target a single property change listener to forward events to (More may be added later.)
	 */
	public PropertyChangeEventForwarder(String prefix, PropertyChangeListener target) {
		this(prefix, new ArrayList<PropertyChangeListener>(1));
		getTargets().add(target);
	}
	
	
	public String getPrefix() {
		return prefix;
	}


	public Collection<PropertyChangeListener> getTargets() {
		return targets;
	}


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		PropertyChangeEvent targetEvent = new PropertyChangeEvent(event.getSource(), prefix + event.getPropertyName(), 
				event.getOldValue(), event.getNewValue());
		
		for (PropertyChangeListener target : targets) {
			target.propertyChange(targetEvent);
		}
	}
	
}
