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


import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.KeyStroke;



public abstract class OkCancelApplyHelpDialog extends OkCancelApplyDialog {
	private JButton helpButton = null;  //  @jve:decl-index=0:visual-constraint="51,52"


	public OkCancelApplyHelpDialog() {
		super();
		init();
	}


	public OkCancelApplyHelpDialog(Frame owner) {
		super(owner);
		init();
	}


	public OkCancelApplyHelpDialog(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}
	
	
	public OkCancelApplyHelpDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		init();
	}


	public OkCancelApplyHelpDialog(Dialog owner, boolean modal, boolean closeOnEnter) {
		super(owner, modal, closeOnEnter);
		init();
	}


	public OkCancelApplyHelpDialog(Frame owner, boolean modal, boolean closeOnEnter) {
		super(owner, modal, closeOnEnter);
		init();
	}


	public OkCancelApplyHelpDialog(Dialog owner) {
		super(owner);
		init();
	}


	public OkCancelApplyHelpDialog(Window owner) {
		super(owner);
		init();
	}


	private void init() {
    getRootPane().registerKeyboardAction(new ActionListener() {
		      public final void actionPerformed(final ActionEvent e) {
		      	help();
		      }
		    }, 
		    KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false),
	      JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
    
	protected abstract void help();


	@Override
	protected void addMoreButtons(JPanel buttonPanel) {
		buttonPanel.add(getHelpButton());
	}


	/**
	 * This method initializes helpButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getHelpButton() {
		if (helpButton == null) {
			helpButton = new JButton();
			helpButton.setText("Help");
			helpButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					help();
				}
			});
		}
		return helpButton;
	}

}
