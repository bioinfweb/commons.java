/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
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
package info.bioinfweb.commons.swt;


import java.util.Collection;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.ScrollBar;



/**
 * Selection listener that synchronizes {@link ScrolledComposite} components in horizontal or vertical direction.
 * Both the {@link ScrolledComposite}s as well as their content areas need to have the same lengths in the selected
 * direction.
 * 
 * @author Ben St&ouml;ver
 */
public class ScrolledCompositeSyncListener implements SelectionListener {
  private ScrolledComposite[] scrolledComposites;
  private boolean horizontal;
	
	
	/**
	 * Creates a new instance of this class and registers it as selection listeners at the according scroll bars
	 * of {@code scrolledComposites}.
	 * <p>
	 * Note that all {@link ScrolledComposite}s need to have a scroll bar in the according direction. Otherwise
	 * a {@link NullPointerException} will occur.
	 * 
	 * @param scrolledComposites - the scroll components to be synchronized
	 * @param horizontal - Specify {@code true} here of you want to synchronize horizontal scrolling or {@code false}
	 *        for vertical scrolling.
	 */
	public ScrolledCompositeSyncListener(Collection<ScrolledComposite> scrolledComposites, boolean horizontal) {
		this(scrolledComposites.toArray(new ScrolledComposite[scrolledComposites.size()]), horizontal);
	}

	
	/**
	 * Creates a new instance of this class.
	 * <p>
	 * Note that all {@link ScrolledComposite}s need to have a scroll bar in the according direction. Otherwise
	 * a {@link NullPointerException} will occur.
	 * 
	 * @param scrolledComposites - the scroll components to be synchronized
	 * @param horizontal - Specify {@code true} here of you want to synchronize horizontal scrolling or {@code false}
	 *        for vertical scrolling.
	 */
	public ScrolledCompositeSyncListener(ScrolledComposite[] scrolledComposites, boolean horizontal) {
		super();
		
		this.scrolledComposites = scrolledComposites;
		this.horizontal = horizontal;
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
	 */
	public void registerToAll() {
		for (int i = 0; i < scrolledComposites.length; i++) {
			if (horizontal) {
				scrolledComposites[i].getHorizontalBar().addSelectionListener(this);
			}
			else {
				scrolledComposites[i].getVerticalBar().addSelectionListener(this);
			}
		}
	}

	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	
	@Override
	public void widgetSelected(SelectionEvent e) {
		int value = ((ScrollBar)e.getSource()).getSelection();
		for (int i = 0; i < scrolledComposites.length; i++) {
			if (horizontal) {
				if (scrolledComposites[i].getHorizontalBar() != e.getSource()) {
					scrolledComposites[i].setOrigin(value, scrolledComposites[i].getOrigin().y);
				}
			}
			else {
				if (scrolledComposites[i].getVerticalBar() != e.getSource()) {
					scrolledComposites[i].setOrigin(scrolledComposites[i].getOrigin().x, value);
				}
			}
		}
	}	
}
