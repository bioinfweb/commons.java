/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.swt;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Control;



/**
 * Selection listener that synchronizes {@link ScrolledComposite} components in horizontal or vertical direction.
 * Both the {@link ScrolledComposite}s as well as their content areas need to have the same lengths in the selected
 * direction.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.commons.swt
 */
public class ScrolledCompositeSyncListener {
  private Iterable<ScrolledComposite> scrolledComposites;
  private boolean horizontal;
  
  private final ControlListener SCROLL_LISTENER = new ControlAdapter() {
		@Override
		public void controlMoved(ControlEvent e) {
			ScrolledComposite source = (ScrolledComposite)((Control)e.getSource()).getParent();
			int value;
			if (horizontal) {
				value = source.getHorizontalBar().getSelection();
			}
			else {
				value = source.getVerticalBar().getSelection();
			}
			
			for (Iterator<ScrolledComposite> iterator = scrolledComposites.iterator(); iterator.hasNext();) {
				ScrolledComposite scrolledComposite = iterator.next();
				if (horizontal) {
					if (scrolledComposite.getHorizontalBar() != e.getSource()) {
						scrolledComposite.setOrigin(value, scrolledComposite.getOrigin().y);
					}
				}
				else {
					if (scrolledComposite.getVerticalBar() != e.getSource()) {
						scrolledComposite.setOrigin(scrolledComposite.getOrigin().x, value);
					}
				}
			}
		}
	};
	
	
	/**
	 * Creates a new instance of this class which uses a deep copy of {@code scrolledComposites}. That means future changes
	 * to {@code scrolledComposites} will not be reflected by this instance. If you need this functionality use
	 * {@link #newLinkedInstance(Iterable, boolean)} instead.
	 * <p>
	 * Note that all {@link ScrolledComposite}s need to have a scroll bar in the according direction. Otherwise
	 * a {@link NullPointerException} will occur.
	 * 
	 * @param scrolledComposites the scroll components to be synchronized
	 * @param horizontal Specify {@code true} here of you want to synchronize horizontal scrolling or {@code false}
	 *        for vertical scrolling.
	 */
	public ScrolledCompositeSyncListener(Collection<ScrolledComposite> scrolledComposites, boolean horizontal) {
		this(scrolledComposites.toArray(new ScrolledComposite[scrolledComposites.size()]), horizontal);
	}

	
	/**
	 * Creates a new instance of this class which uses a deep copy of {@code scrolledComposites}. That means future changes
	 * to {@code scrolledComposites} will not be reflected by this instance. If you need this functionality use
	 * {@link #newLinkedInstance(Iterable, boolean)} instead.
	 * <p>
	 * Note that all {@link ScrolledComposite}s need to have a scroll bar in the according direction. Otherwise
	 * a {@link NullPointerException} will occur.
	 * 
	 * @param scrolledComposites the scroll components to be synchronized
	 * @param horizontal Specify {@code true} here of you want to synchronize horizontal scrolling or {@code false}
	 *        for vertical scrolling.
	 */
	public ScrolledCompositeSyncListener(ScrolledComposite[] scrolledComposites, boolean horizontal) {
		super();
		
		this.scrolledComposites = Arrays.asList(scrolledComposites);
		this.horizontal = horizontal;
	}
	
	
	/**
	 * For internal use in {@link #newLinkedInstance(Collection, boolean)} only.
	 * 
	 * @param scrolledComposites
	 * @param horizontal
	 */
	private ScrolledCompositeSyncListener(Iterable<ScrolledComposite> scrolledComposites, 
			boolean horizontal) {
		
		this.scrolledComposites = scrolledComposites;
		this.horizontal = horizontal;
	}
	
	
	/**
	 * Creates a new instance of this class that uses the specified collection as the source. Future changes to that
	 * collection will be reflected in the behavior of the returned instance. If you do not want that use 
	 * {@link #ScrolledCompositeSyncListener(Collection, boolean)} instead.
	 * 
	 * @param scrolledComposites
	 * @param horizontal
	 * @return
	 */
	public static ScrolledCompositeSyncListener newLinkedInstance(Iterable<ScrolledComposite> scrolledComposites, 
			boolean horizontal) {
		
		return new ScrolledCompositeSyncListener(scrolledComposites, horizontal);
	}
	
	
	public boolean isHorizontal() {
		return horizontal;
	}


	public boolean isVertical() {
		return !horizontal;
	}


	/**
	 * Registers this object as the selection listener at all according scroll bars of the specified 
	 * {@link ScrolledComposite}s.
	 * <p>
	 * This method should only be called once to avoid that this instance is registered several times as a 
	 * listener for some components.
	 */
	public void registerToAll() {
	  // Adding a selection listener to the scroll bar does not work if the component scrolls due to insertion 
		// of new tokens at the end. Therefore the movement of the content component is used as the event instead.
		for (Iterator<ScrolledComposite> iterator = scrolledComposites.iterator(); iterator.hasNext();) {
			if (horizontal) {
				iterator.next().getContent().addControlListener(SCROLL_LISTENER);
				//iterator.next().getHorizontalBar().addSelectionListener(this);
			}
			else {
				iterator.next().getContent().addControlListener(SCROLL_LISTENER);
				//iterator.next().getVerticalBar().addSelectionListener(this);
			}
		}
	}
}
