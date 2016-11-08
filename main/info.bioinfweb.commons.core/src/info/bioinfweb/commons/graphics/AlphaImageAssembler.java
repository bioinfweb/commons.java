/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.graphics;


import java.awt.Color;
import java.awt.image.*;



public class AlphaImageAssembler {
	private static Color extractRGB(int argb) {
		return new Color((argb >> 16) & 0xff, (argb >> 8) & 0xff, argb & 0xff);
	}
	
	
	private static int packARGB(int alpha, int red, int green, int blue) {
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}
	
	
	private static int originalColor(int black, int alpha) {
		if ((alpha == 0) || (alpha == 255)) {
			return black;
		}
		else {
			return Math.min(255, Math.max(0, (black * 255) / alpha));
		}
	}
		
	
	/**
	 * @param black
	 * @param white
	 * @return
	 */
	public static BufferedImage assemble(BufferedImage black, BufferedImage white) {
		if ((black.getWidth() != white.getWidth()) || (black.getHeight() != white.getHeight())) {
			return null;
		}
		else {
			BufferedImage result = new BufferedImage(black.getWidth(), black.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			for (int x = 0; x < result.getWidth(); x++) {
				for (int y = 0; y < result.getHeight(); y++) {
					Color pixelBlack = extractRGB(black.getRGB(x, y));				
					Color pixelWhite = extractRGB(white.getRGB(x, y));
					
					int alpha;
					if (pixelBlack.getRed() != pixelWhite.getRed()) {
						alpha = pixelBlack.getRed() - pixelWhite.getRed() + 255;
					}
					else if (pixelBlack.getGreen() != pixelWhite.getGreen()) {
						alpha = pixelBlack.getGreen() - pixelWhite.getGreen() + 255;
					}
					else {  // Falls es auch in Rot keinen Unterschied gibt, wird die Transparenz hier mit Null berechnet.
						alpha = pixelBlack.getBlue() - pixelWhite.getBlue() + 255;
					}
					alpha = Math.max(0, Math.min(255, alpha));  // Rundungsfehler verschieben den Wert um bis zu 10

					result.setRGB(x, y, packARGB(alpha, originalColor(pixelBlack.getRed(), alpha), originalColor(pixelBlack.getGreen(), alpha), originalColor(pixelBlack.getBlue(), alpha)));
				}
				
			}
			
			return result;
		}
	}
}
