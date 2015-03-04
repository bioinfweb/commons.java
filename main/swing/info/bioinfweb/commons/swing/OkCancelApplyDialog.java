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


import javax.swing.JPanel;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;



public abstract class OkCancelApplyDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	
	private boolean canceled = true;
	private JPanel buttonsPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private JButton applyButton = null;


	public OkCancelApplyDialog() {
		super();
		init(false);
	}


	public OkCancelApplyDialog(Frame owner) {
		super(owner);
		init(false);
	}


	public OkCancelApplyDialog(Frame owner, boolean modal) {
		super(owner, modal);
		init(false);
	}
	
	
	public OkCancelApplyDialog(Dialog owner, boolean modal, boolean closeOnEnter) {
		super(owner, modal);
		init(closeOnEnter);
	}


	public OkCancelApplyDialog(Frame owner, boolean modal, boolean closeOnEnter) {
		super(owner, modal);
		init(closeOnEnter);
	}
	
	
	public OkCancelApplyDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		init(false);
	}


	public OkCancelApplyDialog(Dialog owner) {
		super(owner);
		init(false);
	}


	public OkCancelApplyDialog(Window owner) {
		super(owner);
		init(false);
	}


	private void init(boolean closeOnEnter) {
		getRootPane().setDefaultButton(getOkButton());
    getRootPane().registerKeyboardAction(new ActionListener() {
		      public final void actionPerformed(final ActionEvent e) {
		        canceled = true;
		        setVisible(false);
		      }
		    }, 
		    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true),
	      JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    
    if (closeOnEnter) {
	    getRootPane().registerKeyboardAction(new ActionListener() {
	          public final void actionPerformed(final ActionEvent e) {
	  					if (apply()) {
	  						canceled = false;
		  					setVisible(false);
	  					}
	          }
	        }, 
	        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
	        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
	}


	public boolean execute() {
		setVisible(true);
		return !canceled;
	}
	
	
	protected void addMoreButtons(JPanel buttonPanel) {
		// Grundimplemetierung leer
	}
	

	/**
	 * This method is called if the user clicks <i>OK</i> or <i>Apply</i>. 
	 * @return <code>true</code> should be returned if the dialog can be closed (when <i>OK</i>
	 *         was clicked, <code>false</code> if an error accured and the dialog shall remain
	 *         open
	 */
	protected abstract boolean apply();


	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout());
			buttonsPanel.add(getOkButton(), null);
			buttonsPanel.add(getCancelButton(), null);
			buttonsPanel.add(getApplyButton(), null);
			addMoreButtons(buttonsPanel);
		}
		return buttonsPanel;
	}


	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (apply()) {
						canceled = false;
						setVisible(false);
					}
				}
			});
		}
		return okButton;
	}


	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					canceled = true;
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}


	/**
	 * This method initializes applyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	protected JButton getApplyButton() {
		if (applyButton == null) {
			applyButton = new JButton();
			applyButton.setText("Apply");
			applyButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					apply();
				}
			});
		}
		return applyButton;
	}
}
