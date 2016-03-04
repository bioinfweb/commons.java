/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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



/**
 * A modal dialog displaying a progress bar that can be updated using the methods of {@link ProgressMonitor}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class ProgressDialog extends JDialog implements ProgressMonitor {	
	//TODO Add optional cancel functionality
	
	private static final int MIN_DIALOG_WITH = 250;
	private static final int PROGRESS_BAR_LENGTH = 1000;
	private static final double MIN_DISPLAY_INTERVAL = 0.005;
	private static final DecimalFormat PROGRESS_FORMAT = new DecimalFormat("0.00");
	
	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar = null;
	private double progress = 0;
	private double displayedProgress = 0;

	
	public ProgressDialog(Window owner, String title) {
		super(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
		init();
	}


	/**
	 * Create the dialog.
	 */
	private void init() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		progressBar = getProgressBar();
		contentPanel.add(progressBar);
		
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
	
	
	@Override
	public double getProgressValue() {
		return progress;
	}


	@Override
	public synchronized void setProgressValue(double value) {
		if (progress != value) {
			progress = Math.max(0, Math.min(1, value));
			if ((Math.abs(progress - displayedProgress) >= MIN_DISPLAY_INTERVAL) || (progress == 1)) {
				displayedProgress = progress;
		  	SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						getProgressBar().setValue((int)Math.round(progress * PROGRESS_BAR_LENGTH));
						getProgressBar().setString(PROGRESS_FORMAT.format(progress * 100) + "%");
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
	public boolean isCanceled() {
		return false;
	}
}
