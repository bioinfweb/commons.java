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
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.HyperlinkListener;



/**
 * A customizable about dialog that contains information in mutliple tabs.
 * <p>
 * To Inherited classes should add their tabs using {@link #addTab(String, Icon, Component, String)}, 
 * {@link #addTab(String, Icon, String, String, String)} or {@link #addScrolledTab(String, Icon, Component, String)}.
 * 
 * @author Ben St&ouml;ver
 * @since 3.3.0
 */
public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	private JTabbedPane tabbedPane;
	private JPanel buttonPanel = null;
	private JButton closeButton = null;
	private HyperlinkListener hyperlinkListener = new SystemBrowserHyperlinkListener(this);
	
	
	public AboutDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		init();
	}


	public AboutDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}


	public AboutDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}


	public AboutDialog(Dialog owner, String title) {
		super(owner, title);
		init();
	}


	public AboutDialog(Dialog owner) {
		super(owner);
		init();
	}


	public AboutDialog(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}


	public AboutDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}


	public AboutDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}


	public AboutDialog(Frame owner, String title) {
		super(owner, title);
		init();
	}


	public AboutDialog(Frame owner) {
		super(owner);
		init();
	}


	public AboutDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		init();
	}


	public AboutDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		init();
	}


	public AboutDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		init();
	}


	public AboutDialog(Window owner, String title) {
		super(owner, title);
		init();
	}


	public AboutDialog(Window owner) {
		super(owner);
		init();
	}


	private void init() {
		setContentPane(getJContentPane());
		setTitle("About");
	}


	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.Y_AXIS));
			jContentPane.add(getTabbedPane(), null);
			jContentPane.add(getButtonPanel(), null);
		}
		return jContentPane;
	}


	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(3, 3, 3, 3);
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.weightx = 1.0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getCloseButton(), gridBagConstraints3);
		}
		return buttonPanel;
	}


	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("Close");
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return closeButton;
	}


	protected JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
		}
		return tabbedPane;
	}


	protected void addTab(String title, Icon icon, Component component, String tip) {
		getTabbedPane().addTab(title, icon, component, tip);
	}


	protected void addScrolledTab(String title, Icon icon, Component component, String tip) {
		addTab(title, icon, new JScrollPane(component), tip);
	}
	
	
	protected void addTab(String title, Icon icon, String contentType, String content, String tip) {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType(contentType);
		editorPane.setText(content);			
		editorPane.setCaretPosition(0);
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(hyperlinkListener);

		addScrolledTab(title, icon, editorPane, tip);
	}
}
