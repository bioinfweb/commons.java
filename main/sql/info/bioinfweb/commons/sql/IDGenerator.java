/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2014  Ben Stöver
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
package info.bioinfweb.commons.sql;


import info.bioinfweb.commons.RandomValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class IDGenerator {
	/**
	 * Returns whether an ID already exists is the specified column of the specified table.
	 * @param connection
	 * @param table
	 * @param column
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static boolean idExists(Connection connection, String table, String column, 
			long id) throws SQLException {

		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM " + table + " WHERE " + column + " = ?");
		statement.setLong(1, id);
		return statement.executeQuery().next();
	}
	
	
	public static boolean idExists(Connection connection, String table, String column, 
			String id) throws SQLException {

		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM " + table + " WHERE " + column + " = ?");
		statement.setString(1, id);
		return statement.executeQuery().next();
	}
	
	
  /**
   * Returns a random ID which is not contained in the specified column of the specified 
   * table by the time this method is called. There are no changes made to the database.
   * @param connection
   * @param table
   * @param column
   * @param min
   * @param max
   * @return
   * @throws SQLException
   */
  public static int createID(Connection connection, String table, String column, int min, 
  		int max) throws SQLException {
  	
		int result = 0;
		do {
			result = RandomValues.randInt(min, max);
		} while (idExists(connection, table, column, result));
		return result;
  }
	
	
  /**
   * Returns a random ID which is not contained in the specified column of the specified 
   * table by the time this method is called. There are no changes made to the database.
   * @param connection
   * @param table
   * @param column
   * @param min
   * @param max
   * @return
   * @throws SQLException
   */
  public static long createID(Connection connection, String table, String column, long min, 
  		long max) throws SQLException {
  	
		long result = 0;
		do {
			result = RandomValues.randLong(min, max);
		} while (idExists(connection, table, column, result));
		return result;
  }
	
	
  /**
   * Returns a random ID which is not contained in the specified column of the specified 
   * table by the time this method is called. There are no changes made to the database.
   * @param connection
   * @param table
   * @param column
   * @param chars
   * @param length
   * @return
   * @throws SQLException
   */
  public static String createID(Connection connection, String table, String column, 
  		String chars, int length) throws SQLException {
  	
  	String result;
		do {
			result = RandomValues.randChars(chars, length);
		} while (idExists(connection, table, column, result));
		return result;
  }
  
  
  /**
   * Returns the next ID which is not already present in the specified table. If the
   * table is empty <code>start</code> is returned. There are no changes made to the 
   * database.
   * @param connection
   * @param table
   * @param column
   * @param higher - specifies if an ID greater than the greatest or smaller than the 
   *        smallest value value in the table shall be returned
   * @param start - the value to be returned if the table is empty
   * @return
   * @throws SQLException
   */
  public static int createNextID(Connection connection, String table, String column, 
  		boolean higher, int start) throws SQLException {
  	
		String order = "ASC";
		if (higher) {
			order = "DESC";
		}
		
    ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + 
    		table + " ORDER BY " + column + " " + order + " LIMIT 1");
    if (rs.next()) {
    	int pos = rs.getInt(column);
    	if (higher) {
    		return pos + 1;
    	}
    	else {
    		return pos - 1;    	
    	}
    }
    else {
    	return start;  // Es war noch kein Eintrag vorhanden.
    }
  }
  
  
  /**
   * Returns the next ID which is not already present in the specified table. If the
   * table is empty <code>start</code> is returned. There are no changes made to the 
   * database.
   * @param connection
   * @param table
   * @param column
   * @param higher - specifies if an ID greater than the greatest or smaller than the 
   *        smallest value value in the table shall be returned
   * @param start - the value to be returned if the table is empty
   * @return
   * @throws SQLException
   */
  public static long createNextID(Connection connection, String table, String column, 
  		boolean higher, long start) throws SQLException {
  	
		String order = "ASC";
		if (higher) {
			order = "DESC";
		}
		
    ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + 
    		table + " ORDER BY " + column + " " + order + " LIMIT 1");
    if (rs.next()) {
    	long pos = rs.getLong(column);
    	if (higher) {
    		return pos + 1;
    	}
    	else {
    		return pos - 1;    	
    	}
    }
    else {
    	return start;  // Es war noch kein Eintrag vorhanden.
    }
  }
}
