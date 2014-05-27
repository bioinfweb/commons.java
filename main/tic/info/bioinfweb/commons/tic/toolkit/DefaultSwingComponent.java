/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014  Ben Stöver
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
package info.bioinfweb.commons.tic.toolkit;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.beans.Transient;

import info.bioinfweb.commons.tic.TICComponent;
import info.bioinfweb.commons.tic.TICPaintEvent;



/**
 * The default Swing component implementation to be associated with a {@link TICComponent}.
 * <p>
 * It overwrites {@link #paint(Graphics)} using {@link TICComponent#paint(TICPaintEvent)} to paint the component.
 * 
 * @author Ben St&ouml;ver
 */
public class DefaultSwingComponent extends AbstractSwingComponent {
	public DefaultSwingComponent(TICComponent ticComponent) {
		super(ticComponent);
	}
	

	@Override
	public void paint(Graphics graphics) {
		getIndependentComponent().paint(new TICPaintEvent(this, (Graphics2D)graphics, getVisibleRect()));
	}


	@Override
	@Transient
	public Dimension getPreferredSize() {
		Dimension2D size = getIndependentComponent().getSize();
		return new Dimension((int)Math.round(size.getWidth()), (int)Math.round(size.getHeight()));
	}
}
