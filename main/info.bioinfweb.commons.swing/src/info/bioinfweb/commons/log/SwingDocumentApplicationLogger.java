/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.log;


import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;



/**
 * This implementation of {@link ApplicationLogger} appends all messages to a {@link Document}. It 
 * can be used to print log messages to <i>Swing</i> text components (e.g. {@link JEditorPane}).
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.commons.swing
 */
public class SwingDocumentApplicationLogger extends AbstractApplicationLogger {
  private Document document = null;
  
  
	public SwingDocumentApplicationLogger(Document document) {
		super();
		this.document = document;
	}


	public Document getDocument() {
		return document;
	}


	@Override
	public void addMessage(ApplicationLoggerMessage message) {
		try {
			getDocument().insertString(getDocument().getLength(), message.toString() + System.lineSeparator(), null);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
			throw new InternalError("The following unexpected exception occurred: \"" + e.toString() + "\"");
		}
	}
}
