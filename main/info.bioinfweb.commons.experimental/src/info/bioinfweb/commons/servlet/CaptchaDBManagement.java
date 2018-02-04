/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.servlet;


import java.sql.ResultSet;
import java.sql.SQLException;

import info.bioinfweb.commons.RandomValues;



public class CaptchaDBManagement {
	//TODO codes Table durch ein Singleton ersetzen, dass entsprechende Daten speichert.
	public static final String NAME_ID_COLUMN = "id";
	public static final String NAME_CODE_COLUMN = "code";
	public static final String NAME_FONT_NAME = "name";
	
	
  private SQLManipulator sqlManipilator = null;
  private String codesTable = null;
  private String fontsTable = null;
  private int noIDBits = 0;
  private String codeChars = null;
  private int codeLength = 0;
  
  
  public CaptchaDBManagement(SQLManipulator sqlManipilator, String codesTable, String fontsTable, int noIDBits, String codeChars, int codeLength) {
		super();
		this.sqlManipilator = sqlManipilator;
		this.codesTable = codesTable;
		this.fontsTable = fontsTable;
		this.noIDBits = noIDBits;
		this.codeChars = codeChars;
		this.codeLength = codeLength;
	}


	public String codeToID(String id) throws SQLException {
		ResultSet rs = sqlManipilator.readFromDB(NAME_CODE_COLUMN, codesTable, NAME_ID_COLUMN, id);
		if (rs.next()) {
			return rs.getString(NAME_CODE_COLUMN);
		}
		else {
			return null;
		}
	}
	
  
  private boolean idExists(String id) throws SQLException {
   	return codeToID(id) != null;
  }
	
	
	private String newID() throws SQLException {
		String id;
		do {
			id = RandomValues.randHexForBits(noIDBits);
		} while (idExists(id));
		
		return id;
	}
	
	
	public String createEntry() throws SQLException {
		String[] values = new String[2];
		values[0] = newID();
		values[1] = RandomValues.randChars(codeChars, codeLength);
		sqlManipilator.insertIntoDB(codesTable, values);
		return values[0];
	}
	
	
	public boolean codeCorrect(String id, String code) throws SQLException {
		return code.equals(codeToID(id));
	}
	
	
	public void deleteEntry(String id) throws SQLException {
		sqlManipilator.deleteFromDB(codesTable, NAME_ID_COLUMN, id);
	}
	
	
	public String fetchFontName(int entryNo) throws SQLException {
		ResultSet rs = sqlManipilator.readFromDB(fontsTable, entryNo);
		if (rs.next()) {
			return rs.getString(NAME_FONT_NAME);
		}
		else {
			return null;
		}
	}
}

