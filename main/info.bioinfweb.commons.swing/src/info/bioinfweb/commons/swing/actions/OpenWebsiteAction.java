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


import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import info.bioinfweb.commons.swing.ExtendedAbstractAction;



/**
 * Action object to be used with <i>Swing</i> GUIs that opens the specified URL in the default browser.
 * 
 * @author Ben Stöver
 * @since 3.1.0
 */
public class OpenWebsiteAction extends ExtendedAbstractAction {
	private URI url;

	
	protected static URI createURI(String uri) {
		try {
			return new URI(uri);
		}
		catch (URISyntaxException e) {
			throw new InternalError(e);
		}
	}
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param url the URL to be opened
	 * @param pathPrefix the prefix of the resource path to load icons from
	 */
	public OpenWebsiteAction(String url, String pathPrefix) {
		this(createURI(url), pathPrefix);
	}
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param url the URL to be opened
	 */
	public OpenWebsiteAction(String url) {
		this(createURI(url));
	}
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param url the URL to be opened
	 * @param pathPrefix the prefix of the resource path to load icons from
	 */
	public OpenWebsiteAction(URI url, String pathPrefix) {
		super(pathPrefix);
		this.url = url;
	}

	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param url the URL to be opened
	 */
	public OpenWebsiteAction(URI url) {
		super();
		this.url = url;
	}

	
	public URI getURL() {
		return url;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Desktop.getDesktop().browse(getURL());
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "The website could not be opened due to the following error: " +
					ex.getLocalizedMessage(), "Could not open website", JOptionPane.ERROR_MESSAGE);
		}
	}
}
