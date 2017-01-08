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
package info.bioinfweb.commons.swing;


import javax.swing.JPanel;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;



public class DecimalInputDialog extends OkCancelApplyDialog {
	private static final long serialVersionUID = 1L;
	
	
	private JPanel jContentPane = null;
	private JPanel inputPanel = null;
	private DecimalInput decimalInput = null;


	/**
	 * @param owner
	 */
	public DecimalInputDialog(Frame owner) {
		super(owner, true);
		initialize();
		setLocationRelativeTo(owner);
	}


	public DecimalInputDialog(Dialog owner) {
		super(owner, true);
		initialize();
		setLocationRelativeTo(owner);
	}


	@Override
	protected boolean apply() {
		return true;
	}
	
	
	private void initExecute(String message, String title, String format) {
		getDecimalInput().getLabel().setText(message);
		setTitle(title);
		getDecimalInput().setDecimalFormatStr(format);
	}
	
	
	public boolean execute(String message, String title, double value, 
			String format) {
		
		initExecute(message, title, format);
		getDecimalInput().setValue(value);
		return execute();
	}


	public boolean execute(String message, String title, long value, 
			String format) {
		
		initExecute(message, title, format);
		getDecimalInput().setValue(value);
		return execute();
	}
	
	
	public long getLongValue() {
		return getDecimalInput().parseLong();
	}


	public int getIntValue() {
		return getDecimalInput().parseInt();
	}


	public float getFloatValue() {
		return getDecimalInput().parseFloat();
	}


	public double getDoubleValue() {
		return getDecimalInput().parseDouble();
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.pack();
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
			jContentPane.add(getInputPanel(), null);
			jContentPane.add(getButtonsPanel(), null);
		}
		return jContentPane;
	}


	/**
	 * This method initializes inputPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getInputPanel() {
		if (inputPanel == null) {
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridBagLayout());
			decimalInput = new DecimalInput("Enter a value", inputPanel, 0, 
					DecimalInput.DOUBLE_FORMAT);
		}
		return inputPanel;
	}


	public DecimalInput getDecimalInput() {
		getInputPanel();
		return decimalInput;
	}
}
