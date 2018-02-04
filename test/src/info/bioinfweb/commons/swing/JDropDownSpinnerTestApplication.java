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
package info.bioinfweb.commons.swing;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



public class JDropDownSpinnerTestApplication {
	private JFrame frame;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JDropDownSpinnerTestApplication window = new JDropDownSpinnerTestApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public JDropDownSpinnerTestApplication() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JDropDownSpinner<String> spinner = new JDropDownSpinner<String>();
		spinner.getComboBox().addItem("A");
		spinner.getComboBox().addItem("B");
		spinner.getComboBox().addItem("C");
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.BOTH;
		gbc_spinner.gridx = 0;
		gbc_spinner.gridy = 0;
		frame.getContentPane().add(spinner, gbc_spinner);
	}
}
