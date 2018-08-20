/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.swing;


import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;



/**
 * An implementation of {@link HyperlinkListener} that opens URLs in the system browser.
 *
 * @author Ben St&ouml;ver
 * @since 3.3.0
 */
public class SystemBrowserHyperlinkListener implements HyperlinkListener {
	private Component owner;


	public SystemBrowserHyperlinkListener(Component owner) {
		super();
		this.owner = owner;
	}


	public SystemBrowserHyperlinkListener() {
		this(null);
	}


	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
			try {
				Desktop.getDesktop().browse(e.getURL().toURI());
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(owner, "An error occurred when trying open the selected link.", 
						"Navigation failed,", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
