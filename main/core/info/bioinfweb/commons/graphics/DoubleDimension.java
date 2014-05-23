/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.graphics;


import java.awt.geom.Dimension2D;



public class DoubleDimension extends Dimension2D {
	private double width;
	private double height;
	

	public DoubleDimension(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	
	@Override
	public double getHeight() {
		return height;
	}
	

	@Override
	public double getWidth() {
		return width;
	}

	
	@Override
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}
}
