/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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


import info.bioinfweb.commons.progress.ProgressMonitor;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



/**
 * A modal dialog displaying a progress bar that can be updated using the methods of {@link ProgressMonitor}.
 * <p>
 * This dialog is an alternative to {@link javax.swing.ProgressMonitor} in Swing, which cannot be displayed modal.
 * <h3><a name="UsageExample"></a>Usage example</h3>
 * <pre>
 * final ProgressDialog progressDialog = new ProgressDialog(getMainFrame(), "Doing something...");
 * new SwingWorker&lt;Void, Void&gt;() {
 *       {@literal @}Override
 *       protected Void doInBackground() throws Exception {
 *         doSomething();  // In here an update method (e.g. {@link #setProgressValue(double)}) should be called regularly.
 *         return null;
 *       }
 *
 *       {@literal @}Override
 *       protected void done() {
 *         progressDialog.dispose();
 *       }
 *     }.execute();  // Start processing in a separate thread.
 * progressDialog.setVisible(true);  // Show modal dialog.
 * </pre> 
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class ProgressDialog extends JDialog implements ProgressMonitor {	
	private static final long serialVersionUID = 1L;
	
	public static final DecimalFormat DEFAULT_PROGRESS_FORMAT = new DecimalFormat("0.00 %");
	private static final int MIN_DIALOG_WITH = 350;
	private static final int PROGRESS_BAR_LENGTH = 1000;
	private static final double MIN_DISPLAY_INTERVAL = 0.005;
	
	
	private double progress = 0;
	private double displayedProgress = 0;
	private String currentText = "";
	private boolean canceled = false;
	private boolean showText = true;
	private DecimalFormat progressFormat;
	
	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar = null;
	private JLabel label = null;
	private JButton cancelButton = null;

	
	/**
	 * Creates a new instance of this class with the default cancel button and an updatable progress text which uses
	 * {@link #DEFAULT_PROGRESS_FORMAT} to display the progrss value.
	 * 
	 * @param owner the {@link Window} from which the dialog is displayed or null if this dialog has no owner
	 * @param title the {@link String} to display in the dialog's title bar or null if the dialog has no title
	 */
	public ProgressDialog(Window owner, String title) {
		this(owner, title, DEFAULT_PROGRESS_FORMAT);
	}
	
	
	/**
	 * Creates a new instance of this class with the default cancel button and an updatable progress text.
	 * 
	 * @param owner the {@link Window} from which the dialog is displayed or null if this dialog has no owner
	 * @param title the {@link String} to display in the dialog's title bar or null if the dialog has no title
	 * @param progressFormat the decimal format to be used to display the progress value (If {@code null} is specified here,
	 *        {@link #DEFAULT_PROGRESS_FORMAT} will be used.)
	 */
	public ProgressDialog(Window owner, String title, DecimalFormat progressFormat) {
		this(owner, title, progressFormat, true);
	}
	
	
	/**
	 * Creates a new instance of this class with the default cancel button.
	 * 
	 * @param owner the {@link Window} from which the dialog is displayed or null if this dialog has no owner
	 * @param title the {@link String} to display in the dialog's title bar or null if the dialog has no title
	 * @param progressFormat the decimal format to be used to display the progress value (If {@code null} is specified here,
	 *        {@link #DEFAULT_PROGRESS_FORMAT} will be used.)
	 * @param showText Specify {@code true} here, if an additional text (describing the current operation status) shall be
	 *        displayed above the progress bar or {@code false} if only the progress bar shall be displayed.)
	 */
	public ProgressDialog(Window owner, String title, DecimalFormat progressFormat, boolean showText) {
		this(owner, title, progressFormat, showText, "Cancel");
	}
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param owner the {@link Window} from which the dialog is displayed or null if this dialog has no owner
	 * @param title the {@link String} to display in the dialog's title bar or null if the dialog has no title
	 * @param progressFormat the decimal format to be used to display the progress value (If {@code null} is specified here,
	 *        {@link #DEFAULT_PROGRESS_FORMAT} will be used.)
	 * @param showText Specify {@code true} here, if an additional text (describing the current operation status) shall be
	 *        displayed above the progress bar or {@code false} if only the progress bar shall be displayed.)
	 * @param buttonText the text to be displayed in the cancel button (If {@code null} is specified here, no cancel button
	 *        will be available.)
	 */
	public ProgressDialog(Window owner, String title, DecimalFormat progressFormat, boolean showText, String buttonText) {
		super(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
		
		this.showText = showText;
		if (progressFormat == null) {
			this.progressFormat = DEFAULT_PROGRESS_FORMAT;
		}
		else {
			this.progressFormat = progressFormat;
		}
		
		init(buttonText);
	}


	/**
	 * Create the dialog.
	 */
	private void init(String buttonText) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{146, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 19, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		if (showText) {
			label = getLabel();
			GridBagConstraints gbc_lblText = new GridBagConstraints();
			gbc_lblText.insets = new Insets(0, 0, 5, 0);
			gbc_lblText.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblText.anchor = GridBagConstraints.WEST;
			gbc_lblText.gridx = 0;
			gbc_lblText.gridy = 0;
			contentPanel.add(label, gbc_lblText);
		}
		
		progressBar = getProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.anchor = GridBagConstraints.NORTH;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 2;
		contentPanel.add(progressBar, gbc_progressBar);
		
		if (buttonText != null) {
			cancelButton = new JButton(buttonText);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canceled = true;
				}
			});
			GridBagConstraints gbc_btnCancel = new GridBagConstraints();
			gbc_btnCancel.gridx = 0;
			gbc_btnCancel.gridy = 3;
			contentPanel.add(cancelButton, gbc_btnCancel);
		}
		
		pack();
		setMinimumSize(getSize());
		setSize(Math.max(getSize().width, MIN_DIALOG_WITH), getSize().height);
		setLocationRelativeTo(getOwner());
	}
	
	
	protected JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setMinimum(0);
			progressBar.setStringPainted(true);
			progressBar.setMaximum(PROGRESS_BAR_LENGTH);
		}
		return progressBar;
	}
	
	
	protected JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Text");
		}
		return label;
	}
	
	
	protected JButton getCancelButton() {
		return cancelButton;
	}

	
	@Override
	public double getProgressValue() {
		return progress;
	}


	@Override
  public String getProgressText() {
	  return currentText;
  }


	@Override
	public void setProgressValue(double value) {
		setProgressValue(value, currentText);
	}
	
	
	@Override
	public synchronized void setProgressValue(double value, String text) {
		currentText = text;
		if (progress != value) {
			progress = Math.max(0, Math.min(1, value));
			if ((Math.abs(progress - displayedProgress) >= MIN_DISPLAY_INTERVAL) || (progress == 1)) {
				displayedProgress = progress;
		  	SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						getProgressBar().setValue((int)Math.round(progress * PROGRESS_BAR_LENGTH));
						getProgressBar().setString(progressFormat.format(progress));
						getLabel().setText(currentText);
					}
		  	});
			}
		}
	}


	@Override
	public void addToProgressValue(double addend) {
		setProgressValue(progress + addend);
	}


	@Override
	public void addToProgressValue(double addend, String text) {
		setProgressValue(progress + addend, text);
	}
	
	
	@Override
	public boolean isCanceled() {
		return canceled;
	}
}
