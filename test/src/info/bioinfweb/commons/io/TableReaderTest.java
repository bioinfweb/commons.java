/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.io;


import static org.junit.Assert.*;


import info.bioinfweb.commons.SystemUtils;

import java.io.File;

import org.junit.Test;



public class TableReaderTest {
	private void testTable(String fileName) {
		try {
		  String[][] table = TableReader.readTable(new File("data" + SystemUtils.FILE_SEPARATOR + "io" + 
  	      SystemUtils.FILE_SEPARATOR + "tableReader" + SystemUtils.FILE_SEPARATOR + fileName), '\t');
  		
  		final int columnCount = 2;
  		final int rowCount = 4;
  		assertNotNull(table);
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
  public void test_readTable_linuxTerminalBreak() {
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
  
  
  @Test
  public void test_readTable_oneColumn() {
		try {
		  String[][] table = TableReader.readTable(new File("data" + SystemUtils.FILE_SEPARATOR + "io" + 
  	      SystemUtils.FILE_SEPARATOR + "tableReader" + SystemUtils.FILE_SEPARATOR + "Table_oneColumn.txt"), '\t');
  		
  		final int columnCount = 1;
  		final int rowCount = 4;
  		assertNotNull(table);
  		assertEquals(columnCount, table.length);
  		assertEquals(rowCount, table[0].length);
      for (int row = 0; row < rowCount; row++) {
        assertEquals("V0" + row, table[0][row]);
      }
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
	}


  @Test
  public void test_readTable_empty() {
		try {
		  String[][] table = TableReader.readTable(new File("data" + SystemUtils.FILE_SEPARATOR + "io" + 
  	      SystemUtils.FILE_SEPARATOR + "tableReader" + SystemUtils.FILE_SEPARATOR + "Table_empty.txt"), '\t');
  		
  		final int columnCount = 1;
  		final int rowCount = 1;
  		assertNotNull(table);
  		assertEquals(columnCount, table.length);
  		assertEquals(rowCount, table[0].length);
      assertEquals("", table[0][0]);
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  		fail(e.getMessage());
  	}
	}
}
