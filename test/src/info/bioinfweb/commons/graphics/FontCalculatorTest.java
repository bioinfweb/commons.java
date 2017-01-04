package info.bioinfweb.commons.graphics;


import java.awt.Font;
import java.awt.font.FontRenderContext;

import org.junit.* ;

import static org.junit.Assert.* ;



public class FontCalculatorTest {
	@Test
	public void testFontSizeTextHeight() {
		final String fontName = "Arial Black";
		final int style = Font.PLAIN;
		
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Font font = new Font(fontName, style, 512);
		float expectedHeight = (float)font.getStringBounds("Ög", frc).getHeight();
		float expectedSize = font.getSize2D();
		System.out.println(expectedHeight + " " + expectedSize);
		
		assertEquals(512, expectedSize, 0.000001);
		assertEquals(expectedHeight, FontCalculator.getInstance().getTextHeightByFontSize(512, fontName, style), 0.000001);
		assertEquals(512, FontCalculator.getInstance().getFontSizeByTextHeight(expectedHeight, fontName, style), 0.000001);
	}
}
