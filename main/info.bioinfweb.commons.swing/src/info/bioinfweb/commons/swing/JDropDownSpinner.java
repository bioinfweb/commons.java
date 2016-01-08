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


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;

import javax.swing.ComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;



/**
 * A Swing component consisting of a {@link JComboBox} with a up and down buttons on the left, like they are
 * found in a {@link JSpinner}.
 * 
 * @author Ben St&ouml;ver
 *
 * @param <I> the item type of the combo box
 */
public class JDropDownSpinner<I> extends JPanel {
	private class Button extends JButton {
		private boolean up;
		
		
		public Button(boolean up) {
			super();
			this.up = up;
		}

		
		@Override
		public Dimension getPreferredSize() {
			int height = getComboBox().getPreferredSize().height;
			return new Dimension(Math.round(.75f * height), height / 2);
		}


		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
			
			Dimension size = getPreferredSize();
			double width2 = size.getWidth() / 2;
			double height2 = size.getHeight() / 2;
			double width4 = width2 / 2;
			double height4 = height2 / 2;
			double y1;
			double y2;
			if (up) {
				y1 = height2 + height4;
				y2 = height4;
			}
			else {
				y2 = height2 + height4;
				y1 = height4;
			}
			
			Path2D arrow = new Path2D.Double();
			arrow.moveTo(width4, y1);
			arrow.lineTo(width2, y2);
			arrow.lineTo(width2 + width4, y1);
			arrow.closePath();
			
			g2d.setColor(SystemColor.controlText);
			g2d.fill(arrow);
		}
	}

	
	private JComboBox<I> comboBox = null;
	
	
	public JDropDownSpinner(JComboBox<I> comboBox) {
		super();
		this.comboBox = comboBox;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridheight = 2;
		gbc_comboBox.insets = new Insets(0, 0, 0, 1);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		add(getComboBox(), gbc_comboBox);
		
		Button upButton = new Button(true);
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getComboBox().getSelectedIndex() > 0) {
					getComboBox().setSelectedIndex(getComboBox().getSelectedIndex() - 1);
				}
			}
		});
		GridBagConstraints gbc_upButton = new GridBagConstraints();
		gbc_upButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_upButton.insets = new Insets(0, 0, 0, 0);
		gbc_upButton.gridx = 1;
		gbc_upButton.gridy = 0;
		add(upButton, gbc_upButton);
		
		Button downButton = new Button(false);
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getComboBox().getSelectedIndex() < getComboBox().getItemCount() - 1) {
					getComboBox().setSelectedIndex(getComboBox().getSelectedIndex() + 1);
				}
			}
		});
		GridBagConstraints gbc_downButton = new GridBagConstraints();
		gbc_downButton.insets = new Insets(0, 0, 0, 0);
		gbc_downButton.gridx = 1;
		gbc_downButton.gridy = 1;
		add(downButton, gbc_downButton);
	}


	public JDropDownSpinner() {
		this(new JComboBox<I>());
	}

	
	public JDropDownSpinner(ComboBoxModel<I> model) {
		this(new JComboBox<I>(model));
	}
	
	
	public JDropDownSpinner(I[] elements) {
		this(new JComboBox<I>(elements));
	}
	
	
	/**
	 * The combo box used inside this component.
	 * 
	 * @return the combo box instance used
	 */
	public JComboBox<I> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<I>();
		}
		return comboBox;
	}
	
	
	/**
	 * Delegates to {@link JComboBox#getSelectedItem()} of {@link #getComboBox()}.
	 * 
	 * @return the item that is currently selected
	 */
	@SuppressWarnings("unchecked")
	public I getSelectedItem() {
		return (I)getComboBox().getSelectedItem();
	}
}
