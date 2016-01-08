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
 * 
 * 
 * Basic implementation is comming from:
 * 
 * $Id: ScrollPaneSelector.java,v 1.1 2006/07/29 01:56:05 weebib Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package info.bioinfweb.commons.swing.scrollpaneselector;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.HashMap;
import org.jdesktop.swingx.scrollpaneselector.TweakedScrollPaneLayout;



/**
 * <code>ExtendedScrollPaneSelector</code> is an extended implementation of 
 * <code>ScrollPaneSelector</code> which is is a little utility class that provides a means to quickly 
 * scroll both vertically and horizontally on a single mouse click, by dragging a selection rectangle 
 * over a "thumbnail" of the scrollPane's viewport view.
 * <p>
 * Once the selector is installed on a given JScrollPane instance, a little button appears as soon as at least
 * one of its scroll bars is made visible.
 * <p>
 * In addition to <code>org.jdesktop.swingx.scrollpaneselector.ScrollPaneSelector</code> this class
 * provides the opportunity for the component inside the <code>JScrollPane</code> to draw their
 * thubnail by themselves by implementing <code>ScrollPaneSelectable</code>.
 * @author weebib, Ben St&ouml;ver
 */
public class ExtendedScrollPaneSelector extends JComponent {
	private static final double MAX_SIZE = 200;
	private static final String COMPONENT_ORIENTATION = "componentOrientation";
	private static final Icon LAUNCH_SELECTOR_ICON = new Icon() {
				public void paintIcon(Component c, Graphics g, int x, int y) {
					Color tmpColor = g.getColor();
					g.setColor(Color.BLACK);
					g.drawRect(2, 2, 10, 10);
					g.drawRect(4, 5, 6, 4);
					g.setColor(tmpColor);
				}
		
				public int getIconWidth() {
					return 15;
				}
		
				public int getIconHeight() {
					return 15;
				}
			};
			
	private static Map<JScrollPane, ExtendedScrollPaneSelector> theInstalledScrollPaneSelectors
			= new HashMap<JScrollPane, ExtendedScrollPaneSelector>();
	
	
	private LayoutManager theFormerLayoutManager;
	private JScrollPane theScrollPane;
	private JComponent theComponent;
	private JPopupMenu thePopupMenu;
	private JComponent theButton;
	private BufferedImage theImage;
	private Rectangle theStartRectangle;
	private Rectangle theRectangle;
	private Point theStartPoint;
	private double theScale;
	private PropertyChangeListener theComponentOrientationListener;
	private ContainerAdapter theViewPortViewListener;

	
	/**
	 * Installs a ScrollPaneSelector to the given JScrollPane instance.
	 * @param aScrollPane
	 */
	public synchronized static void installScrollPaneSelector(JScrollPane aScrollPane) {
		if (aScrollPane == null) return;
		if (theInstalledScrollPaneSelectors.containsKey(aScrollPane)) return;
		ExtendedScrollPaneSelector scrollPaneSelector = new ExtendedScrollPaneSelector();
		scrollPaneSelector.installOnScrollPane(aScrollPane);
		theInstalledScrollPaneSelectors.put(aScrollPane, scrollPaneSelector);
	}

	
	/**
	 * Removes the ScrollPaneSelector from the given JScrollPane.
	 * @param aScrollPane
	 */
	public synchronized static void uninstallScrollPaneSelector(JScrollPane aScrollPane) {
		if (aScrollPane == null) return;
		ExtendedScrollPaneSelector scrollPaneSelector = theInstalledScrollPaneSelectors.get(aScrollPane);
		if (scrollPaneSelector == null) return;
		scrollPaneSelector.uninstallFromScrollPane();
		theInstalledScrollPaneSelectors.remove(aScrollPane);
	}

	
	private ExtendedScrollPaneSelector() {
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		theScrollPane = null;
		theImage = null;
		theStartRectangle = null;
		theRectangle = null;
		theStartPoint = null;
		theScale = 0.0;
		theButton = new JLabel(LAUNCH_SELECTOR_ICON);
		MouseInputListener mil = new MouseInputAdapter() {
			public void mousePressed(MouseEvent e) {
				if ((theComponent.getWidth() > 0) && (theComponent.getHeight() > 0)) {  // Added to avoid exceptions with e.g. empty tables
					Point p = e.getPoint();
					SwingUtilities.convertPointToScreen(p, theButton);
					display(p);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (theStartPoint != null) {
					Point newPoint = e.getPoint();
					SwingUtilities.convertPointToScreen(newPoint, (Component)e.getSource());
					int deltaX = (int)((newPoint.x - theStartPoint.x) / theScale);
					int deltaY = (int)((newPoint.y - theStartPoint.y) / theScale);
					scroll(deltaX, deltaY);
				}
				theStartPoint = null;
				theStartRectangle = theRectangle;
			}

			public void mouseDragged(MouseEvent e) {
				if (theStartPoint == null) return;
				Point newPoint = e.getPoint();
				SwingUtilities.convertPointToScreen(newPoint, (Component)e.getSource());
				moveRectangle(newPoint.x - theStartPoint.x, newPoint.y - theStartPoint.y);
			}
		};
		theButton.addMouseListener(mil);
		theButton.addMouseMotionListener(mil);
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		thePopupMenu = new JPopupMenu();
		thePopupMenu.setLayout(new BorderLayout());
		thePopupMenu.add(this, BorderLayout.CENTER);
		theComponentOrientationListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (theScrollPane == null) return;
				theScrollPane.setCorner(JScrollPane.LOWER_LEADING_CORNER, null);
				theScrollPane.setCorner(JScrollPane.LOWER_TRAILING_CORNER, theButton);
			}
		};
		theViewPortViewListener = new ContainerAdapter() {
			public void componentAdded(ContainerEvent e) {
				if (thePopupMenu.isVisible()) thePopupMenu.setVisible(false);
				Component comp = theScrollPane.getViewport().getView();
				theComponent = (comp instanceof JComponent)? (JComponent)comp : null;
			}
		};
	}

	
	public Dimension getPreferredSize() {
		if (theImage == null || theRectangle == null) return new Dimension();
		Insets insets = getInsets();
		return new Dimension(theImage.getWidth(null) + insets.left + insets.right,
							 theImage.getHeight(null) + insets.top + insets.bottom);
	}

	
	protected void paintComponent(Graphics g1D) {
		if (theImage == null || theRectangle == null) return;
		Graphics2D g = (Graphics2D)g1D;
		Insets insets = getInsets();
		int xOffset = insets.left;
		int yOffset = insets.top;
		int availableWidth = getWidth() - insets.left - insets.right;
		int availableHeight = getHeight() - insets.top - insets.bottom;
		g.drawImage(theImage, xOffset, yOffset, null);
		Color tmpColor = g.getColor();
		Area area = new Area(new Rectangle(xOffset, yOffset, availableWidth, availableHeight));
		area.subtract(new Area(theRectangle));
		g.setColor(new Color(200, 200, 200, 128));
		g.fill(area);
		g.setColor(Color.BLACK);
		g.draw(theRectangle);
		g.setColor(tmpColor);
	}


	private void installOnScrollPane(JScrollPane aScrollPane) {
		if (theScrollPane != null) uninstallFromScrollPane();
		theScrollPane = aScrollPane;
		theFormerLayoutManager = theScrollPane.getLayout();
		theScrollPane.setLayout(new TweakedScrollPaneLayout ());
		theScrollPane.addPropertyChangeListener(COMPONENT_ORIENTATION, theComponentOrientationListener);
		theScrollPane.getViewport().addContainerListener(theViewPortViewListener);
		theScrollPane.setCorner(JScrollPane.LOWER_TRAILING_CORNER, theButton);
		Component comp = theScrollPane.getViewport().getView();
		theComponent = (comp instanceof JComponent)? (JComponent)comp : null;
	}

	
	private void uninstallFromScrollPane() {
		if (theScrollPane == null) return;
		if (thePopupMenu.isVisible()) thePopupMenu.setVisible(false);
		theScrollPane.setCorner(JScrollPane.LOWER_TRAILING_CORNER, null);
		theScrollPane.removePropertyChangeListener(COMPONENT_ORIENTATION, theComponentOrientationListener);
		theScrollPane.getViewport().removeContainerListener(theViewPortViewListener);
		theScrollPane.setLayout(theFormerLayoutManager);
		theScrollPane = null;
	}

	
	private void display(Point aPointOnScreen) {
		if (theComponent == null) return;
		double compWidth = theComponent.getWidth();
		double compHeight = theComponent.getHeight();
		double scaleX = MAX_SIZE / compWidth;
		double scaleY = MAX_SIZE / compHeight;
		theScale = Math.min(scaleX, scaleY);
		theImage = new BufferedImage(Math.max(1, (int)(theComponent.getWidth() * theScale)),  // Verhindern, dass bei sehr groöen Seitenverhölntnissen eine Seite zu 0 gerundet wird.
				Math.max(1, (int)(theComponent.getHeight() * theScale)), BufferedImage.TYPE_INT_RGB);

		Graphics2D g = theImage.createGraphics();
		
		// Changes have been made here:
		if (theComponent instanceof ScrollPaneSelectable) {
			((ScrollPaneSelectable)theComponent).paintPreview(g, theScale);
		}
		else {
			g.scale(theScale, theScale);
			theComponent.paint(g);  
		}
		
		theStartRectangle = theComponent.getVisibleRect();
		Insets insets = getInsets();
		theStartRectangle.x = (int)(theScale * theStartRectangle.x + insets.left);
		theStartRectangle.y = (int)(theScale * theStartRectangle.y + insets.right);
		theStartRectangle.width *= theScale;
		theStartRectangle.height *= theScale;
		theRectangle = theStartRectangle;

		Dimension pref = thePopupMenu.getPreferredSize();
		Point buttonLocation = theButton.getLocationOnScreen();
		Point popupLocation = new Point((theButton.getWidth() - pref.width) / 2,
										(theButton.getHeight() - pref.height) / 2);
		Point centerPoint = new Point(buttonLocation.x + popupLocation.x + theRectangle.x + theRectangle.width / 2,
									  buttonLocation.y + popupLocation.y + theRectangle.y + theRectangle.height / 2);
		try {
			// Attempt to move the mouse pointer to the center of the selector's rectangle.
			new Robot().mouseMove(centerPoint.x, centerPoint.y);
			theStartPoint = centerPoint;
		} catch (Exception e) {
			// Since we cannot move the cursor, we'll move the popup instead.
			theStartPoint = aPointOnScreen;
			popupLocation.x += theStartPoint.x - centerPoint.x;
			popupLocation.y += theStartPoint.y - centerPoint.y;
		}
		thePopupMenu.show(theButton, popupLocation.x, popupLocation.y);
	}

	
	private void moveRectangle(int aDeltaX, int aDeltaY) {
		if (theStartRectangle == null) return;

		Insets insets = getInsets();
		Rectangle newRect = new Rectangle(theStartRectangle);
		newRect.x += aDeltaX;
		newRect.y += aDeltaY;
		newRect.x = Math.min(Math.max(newRect.x, insets.left), getWidth() - insets.right - newRect.width);
		newRect.y = Math.min(Math.max(newRect.y, insets.right), getHeight() - insets.bottom - newRect.height);
		Rectangle clip = new Rectangle();
		Rectangle.union(theRectangle, newRect, clip);
		clip.grow(2, 2);
		theRectangle = newRect;
		paintImmediately(clip);
	}

	
	private void scroll(int aDeltaX, int aDeltaY) {
		if (theComponent == null) return;
		Rectangle rect = theComponent.getVisibleRect();
		rect.x += aDeltaX;
		rect.y += aDeltaY;
		theComponent.scrollRectToVisible(rect);
		thePopupMenu.setVisible(false);
	}
}
