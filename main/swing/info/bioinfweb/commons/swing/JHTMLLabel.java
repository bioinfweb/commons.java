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
package info.bioinfweb.commons.swing;


import info.webinsel.wikihelp.client.WikiHelp;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.text.html.HTMLDocument;



/**
 * This components modifies a {@link JEditorPane} in a way that is looks exactly like a JLabel and provides a
 * {@link HyperlinkListener} that opens the systems default browser is the user clicks on any links. 
 * 
 * @author Ben St&ouml;ver
 */
public class JHTMLLabel extends JEditorPane {
	public static final String WIKI_HELP_PROTOCOL = "wikihelp://";
	
	private final JHTMLLabel THIS = this;
	
	private WikiHelp wikiHelp = null;
	
	
	public final HyperlinkListener HYPERLINK_LISTENER = 		
				  new javax.swing.event.HyperlinkListener() {
							public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
								if (e.getEventType().equals(EventType.ACTIVATED)) {
									try {
										if (e.getDescription().startsWith(WIKI_HELP_PROTOCOL)) {
											String text = e.getDescription().substring(WIKI_HELP_PROTOCOL.length());
											int topic = -1;
											try {
												topic = Integer.parseInt(text);
											}
											catch (NumberFormatException ex) {}
											if (topic > -1) {
												getWikiHelp().displayTopic(topic);
											}
											else {
												getWikiHelp().displayTopic(text);
											}
										}
										else {
											Desktop.getDesktop().browse(e.getURL().toURI());
										}
									}
									catch (Exception ex) {
										JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(THIS), 
												"An error occurred when trying open the selected link.", 
												"Navigation failed,", JOptionPane.ERROR_MESSAGE);
									}
								}
							}
						};


						
	public JHTMLLabel() {
	  super();
		setBackground(SystemColor.menu);
		setContentType("text/html");
		Font font = UIManager.getFont("Label.font");
    String bodyRule = "body { font-family: " + font.getFamily() + "; " +
            "font-size: " + font.getSize() + "pt; }";
    ((HTMLDocument)getDocument()).getStyleSheet().addRule(bodyRule);
    
		setEditable(false);
		setBorder(null);
		addHyperlinkListener(HYPERLINK_LISTENER);
  }
	
	
	public JHTMLLabel(WikiHelp wikiHelp) {
	  this();
	  this.wikiHelp = wikiHelp;
  }


	@Override
  public void setText(String t) {
	  super.setText(t);
		setCaretPosition(0);
  }


	public void setHTMLContent(String content) {
		setText("<html><body>" + content + "</body></html>");
	}


	public WikiHelp getWikiHelp() {
		return wikiHelp;
	}


	public void setWikiHelp(WikiHelp wikiHelp) {
		this.wikiHelp = wikiHelp;
	}
}
