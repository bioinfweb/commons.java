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


import info.bioinfweb.commons.Math2;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;



/**
 * Swing input for decimal values using a text field and a slider. Has to be inserted in to a 
 * <code>JPanel</code> which used <code>GridBagLayout</code>.
 *  
 * @author Ben St&ouml;ver
 */
public class DecimalInput {
	public static final String DOUBLE_FORMAT = "#.##########"; 
	public static final String FLOAT_FORMAT = "#.#######";  // Bei zu vielen Nachkommastellen entstehen Rundungsfehler, da DecimalFormat double verwendet und nur float gespeichert wird. 
	public static final String INTEGER_FORMAT = "#"; 
	
	
	private JLabel label = null;
  private JFormattedTextField textField = null;
  private JSlider slider = null;
  private DecimalFormat decimalFormat = null;
  private double valuesPerTick = 1;
  private SwingChangeMonitor changeMonitor = new SwingChangeMonitor();
  
  
  public DecimalInput(String labelText, JPanel panel, int y, String format) {
  	this(labelText, panel, 0, y, format, true);
  }
  
  
  public DecimalInput(String labelText, JPanel panel, int x, int y, String format) {
  	this(labelText, panel, x, y, format, true);
  }
  
  
  public DecimalInput(String labelText, JPanel panel, int x, int y, String format, boolean showSlider) {
  	decimalFormat = new DecimalFormat(format);
  	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 4, 0);
  	label = new JLabel(labelText);
		panel.add(getLabel(), gbc);
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = x + 1;
		gbc.gridy = y;
		gbc.weightx = 4.0;
		panel.add(getTextField(), gbc);
		
		if (showSlider) {
			gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridy = y + 1;
			gbc.weightx = 4.0;
			gbc.gridwidth = 2;
			gbc.gridx = x + 1;
			panel.add(getSlider(), gbc);
		}
		
		setValue(1);  // Es muss ein von Null verschiedener Wert gesetzt werden um ein sinnvolles Maximum zu berechnen.
		setValue(0);
		
		getTextField().getDocument().addDocumentListener(changeMonitor);
  }
  
  
	public JLabel getLabel() {
		return label;
	}


	public JFormattedTextField getTextField() {
		if (textField == null) {
			textField = new JFormattedTextField(decimalFormat);
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (Character.isDigit(e.getKeyChar()) || 
							e.getKeyCode() == KeyEvent.VK_DELETE || 
							e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						try {
							setSliderValue(parseDouble());
						}
						catch (NumberFormatException ex) {}  // do nothing						
					}
				}
			});
		}
		return textField;
	}
	
	
	/**
	 * This method initializes slider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setPaintLabels(true);
			slider.setPaintTicks(true);
			slider.setMinorTickSpacing(1);
			slider.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					setValue(getSlider().getValue() * valuesPerTick);
				}
			});
		}
		return slider;
	}
	
	
	public SwingChangeMonitor getChangeMonitor() {
		return changeMonitor;
	}


	public void setEnabled(boolean enabled) {
		getLabel().setEnabled(enabled);
		getTextField().setEnabled(enabled);
	}
	
	
  public DecimalFormat getDecimalFormat() {
		return decimalFormat;
	}
  
  
	public void setDecimalFormatStr(String format) {
		decimalFormat = new DecimalFormat(format);
	}

	
	/**
   * Parses the current value of the text field. "," or "." are allowed as decimal 
   * seperators. Thousend separators must not be used. Note that the value depends on the 
   * currently selected unit and is not converted.
   * @return the parsed value
   */
  public float parseFloat() {
  	return Math2.parseFloat(getTextField().getText());
  }
  
  
  /**
   * Parses the current value of the text field. "," or "." are allowed as decimal 
   * seperators. Thousend separators must not be used. Note that the value depends on the 
   * currently selected unit and is not converted.
   * @return the parsed value
   */
  public double parseDouble() {
  	return Math2.parseDouble(getTextField().getText());
  }
  
  
  /**
   * Parses the current value as an <code>int</code>.
   * @return the parsed value
   */
  public int parseInt() {
  	return Integer.parseInt(getTextField().getText());
  }
  
  
  /**
   * Parses the current value as an <code>long</code>.
   * @return the parsed value
   */
  public long parseLong() {
  	return Long.parseLong(getTextField().getText());
  }
  
  
  protected Hashtable<Integer, JLabel> createLabelTable(int sliderMax, 
  		int sliderStep, double valueMax) {
  	
  	Hashtable<Integer, JLabel> result = new Hashtable<Integer, JLabel>();
  	for (int i = 0; i <= sliderMax; i += sliderStep) {
			result.put(new Integer(i), new JLabel(getDecimalFormat().format(
					(((double)i) / (double)sliderMax) * valueMax)));  //TODO Formatierung
		}
  	return result;
  }
  
  
  private void setSliderValue(double currentValue) {
  	//TODO Lösung finden, die positive und negative Werte zulösst, keine krummen Schrittlöngen anzeigt und einen ausreichenden Bereich darstellt. => Scala mösste sich immer bei öberschreiten einer Zehnerpotenz öndern (ggf. auch Zwischenschritte wie 1, 5, 10, 50, ...)
  	
  	getSlider().setPaintLabels(false);
  	double max = Math.pow(10, Math.ceil(Math.log10(10 * currentValue)));
  	if (max == 0) {
  		max = getSlider().getMaximum() * valuesPerTick;  // leaf unchanged
  	}
  	else {
	  	if (10 * currentValue <= 0.3 * max) {
	  		max *= 0.3;
	  		getSlider().setMaximum(300);
	  		getSlider().setMinorTickSpacing(10);
	  		getSlider().setMajorTickSpacing(50);
	  	}
	  	else {
	  		getSlider().setMaximum(400);
	  		getSlider().setMinorTickSpacing(4);
	  		getSlider().setMajorTickSpacing(40);
	  	}
	  	valuesPerTick = max / (double)getSlider().getMaximum();
	  	getSlider().setLabelTable(createLabelTable(
	  			getSlider().getMaximum(), getSlider().getMajorTickSpacing(), max));
  	}
  	getSlider().setPaintLabels(true);
  	
  	getSlider().setValue((int)Math.max(0, Math.min(getSlider().getMaximum(), 
  			Math.round((currentValue / max) * getSlider().getMaximum()))));
  }
  
  
  public void setValue(float value) {
  	getTextField().setText(getDecimalFormat().format(value));
  	setSliderValue(value);
  }


  public void setValue(double value) {
  	getTextField().setText(getDecimalFormat().format(value));
  	setSliderValue(value);
  }


  public void setValue(int value) {
  	getTextField().setText(getDecimalFormat().format(value));
  	setSliderValue(value);
  }


  public void setValue(long value) {
  	getTextField().setText(getDecimalFormat().format(value));
  	setSliderValue(value);
  }
}
