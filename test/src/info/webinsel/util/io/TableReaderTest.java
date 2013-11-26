/*
 * TreeGraph 2 - A feature rich editor for phylogenetic trees
 * Copyright (C) 2007-2013  Ben Stöver, Kai Müller
 * <http://treegraph.bioinfweb.info/>
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
package info.webinsel.util.io;


import static org.junit.Assert.*;


import info.webinsel.util.SystemUtils;

import java.io.File;

import org.junit.Test;



public class TableReaderTest {
	private void testTable(String fileName) {
		try {
		  String[][] table = TableReader.readTable(new File("data" + SystemUtils.FILE_SEPARATOR + "io" + 
  	      SystemUtils.FILE_SEPARATOR + "tableReader" + SystemUtils.FILE_SEPARATOR + fileName), '\t');
  		
  		final int columnCount = 2;
  		final int rowCount = 4;
  		assertEquals(columnCount, table.length);
  		assertEquals(rowCount, table[0].length);
  		for (int column = 0; column < columnCount; column++) {
	      for (int row = 0; row < rowCount; row++) {
	        assertEquals("V" + column + row, table[column][row]);
        }
      }
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
	}
	
	
  @Test
  public void test_readTable_windowsNoTerminalBreak() {
  	testTable("TableWindows_noTerminalBreak.txt");
  }
	
	
  @Test
  public void test_readTable_windowsterminalBreak() {
  	testTable("TableWindows_terminalBreak.txt");
  }
	
	
  @Test
  public void test_readTable_linuxNoTerminalBreak() {
  	testTable("TableLinux_noTerminalBreak.txt");
  }
	
	
  @Test
  public void test_readTable_linuxterminalBreak() {
  	testTable("TableLinux_terminalBreak.txt");
  }
	
	
  @Test
  public void test_readTable_macNoTerminalBreak() {
  	testTable("TableMac_noTerminalBreak.txt");
  }
	
	
  @Test
  public void test_readTable_macterminalBreak() {
  	testTable("TableMac_terminalBreak.txt");
  }
}
