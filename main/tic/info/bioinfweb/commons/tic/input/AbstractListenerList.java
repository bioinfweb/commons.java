/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014 - 2015  Ben Stöver
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
package info.bioinfweb.commons.tic.input;


import info.bioinfweb.commons.tic.TICComponent;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;



public abstract class AbstractListenerList<L extends EventListener> {
	private TICComponent owner;
	private List<L> listeners = new ArrayList<L>();
	
	
	public AbstractListenerList(TICComponent owner) {
		super();
		this.owner = owner;
	}


	public TICComponent getOwner() {
		return owner;
	}


	public List<L> getListeners() {
		return listeners;
	}
}