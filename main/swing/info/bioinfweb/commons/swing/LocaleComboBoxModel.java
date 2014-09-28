/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2014  Ben Stöver
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
package info.bioinfweb.commons.swing;


import java.util.Collections;
import java.util.Locale;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;



public class LocaleComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private class ComparableLocale implements Comparable<ComparableLocale> {
		private Locale locale;

		
		public ComparableLocale(Locale locale) {
			super();
			this.locale = locale;
		}

		
		public Locale getLocale() {
			return locale;
		}
		
		
		public String getName() {
			return getLocale().getDisplayName();
		}


		public int compareTo(ComparableLocale other) {
			return getName().compareTo(other.getName());
		}
	}
	
	
	private Vector<ComparableLocale> items = new Vector<ComparableLocale>();
	private int selected = 0;
	
	
	public LocaleComboBoxModel() {
		super();
		fillList(Locale.getAvailableLocales());
	}
	
	
	public LocaleComboBoxModel(Locale[] locales) {
		super();
		fillList(locales);
	}
	
	
	private void fillList(Locale[] locales) {
		for (Locale item: locales) {
			items.add(new ComparableLocale(item));
		}
		Collections.sort(items);
		fireContentsChanged(this, 0, items.size() - 1);
	}


	public String getElementAt(int pos) {
		return getLocaleAt(pos).getDisplayName();
	}

	
	public int getSize() {
		return items.size();
	}

	
	public String getSelectedItem() {
		return getSelectedLocale().getDisplayName();
	}

	
	/**
	 * Sets the selected item. Any <code>Locale</code>-object which equals one of the ojects 
	 * present in this model can be passed here.
	 * @throws IllegalArgumentException if the passed object does not equal one object in the
	 *         list
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object item) {
		String string = item.toString();
		int pos = 0; 
		while ((pos < items.size()) && !items.get(pos).getName().equals(string)) {
			pos++;
		}
		
		if (pos < items.size()) {
			selected = pos;
			fireContentsChanged(this, 0, items.size() - 1);  //TODO Interval verkleinern?
		}
		else {
			throw new IllegalArgumentException("The specified element \"" + string + "\"is not in the list.");
    }
	}
	
	
	public Locale getLocaleAt(int pos) {
		return items.get(pos).getLocale();
	}
	
	
	public Locale getSelectedLocale() {
		if (items.size() == 0) {
			return null;
		}
		else {
			return items.get(selected).getLocale();
		}
	}
	
	
	public void setSelectedLocale(Locale locale) {
		int pos = 0; 
		while ((pos < items.size()) && !items.get(pos).equals(locale)) {
			pos++;
		}
		
		if (pos < items.size()) {
			selected = pos;
			fireContentsChanged(this, 0, items.size() - 1);  //TODO Interval verkleinern?
		}
		else {
			throw new IllegalArgumentException("The specified element is not in the list.");
    }
	}
}
