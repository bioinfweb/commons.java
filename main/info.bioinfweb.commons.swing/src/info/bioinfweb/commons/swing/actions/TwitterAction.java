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
package info.bioinfweb.commons.swing.actions;


import java.awt.event.KeyEvent;

import javax.swing.Action;



public class TwitterAction extends OpenWebsiteAction {
	public TwitterAction() {
		super("http://r.bioinfweb.info/Twitter");
		
		putValue(Action.NAME, "bioinfweb news on Twitter"); 
	  putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
	  loadSymbols("Twitter");
	}
}
