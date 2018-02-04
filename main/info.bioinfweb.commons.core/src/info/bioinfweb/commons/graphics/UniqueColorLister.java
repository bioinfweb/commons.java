/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.graphics;


import java.awt.Color;



/**
 * Allows to subsequently generate a list of unique colors that are equally distributed along the spectrum.
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class UniqueColorLister {
	private float currentHue = 0;
	private float stepLength = 1;
	
	
	/**
	 * Returns a new color which differs from all colors previously returned by this method.
	 * 
	 * @return a new color which is unique for this instance 
	 */
	public Color generateNext() {
		Color result = Color.getHSBColor(currentHue, 1, 1);
		do {
			currentHue += stepLength;
		} while (currentHue % (2 * stepLength) == 0f);
		if (currentHue >= 1f) {
			stepLength /= 2;
			currentHue = stepLength;
		}
		return result;
	}
}
