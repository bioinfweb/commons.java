/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
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
package info.bioinfweb.commons.servlet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class SQLManipulator {
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	
	private Connection connection = null;
	private String dbHost = null;
	private String dbName = null;
	private String dbUser = null;
	private String dbPassword = null;
	
	
	public SQLManipulator(String dbHost, String dbName, String dbUser, String dbPassword) {
		super();
		this.dbHost = dbHost;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}


	public boolean isConntected() {
		boolean result = connection != null;
		if (result) {
			try {
					result = !connection.isClosed();
			}
			catch (SQLException e) {
				e.printStackTrace();
				result = false;
			}
		}
		
		return result;
	}
	
	
	public Statement getStatement() throws SQLException {
   	return getConnection().createStatement();
	}
	
	
	public Connection getConnection() {
		if ((!isConntected())) { //TODO Hier tritt nach einer Weile, die Tomcat läuft, eine NullPointerException auf. Ggf. wird das Connection-objetc von gc gelöscht. Eigenen Zeiger darauf versuchen.
		  try {
		    Class.forName(MYSQL_DRIVER);
		  }
		  catch (ClassNotFoundException e) {  // Fehler sollte nicht auftreten, da Package mitgegeben wird.
	    	connection = null;
		  }
		  
	    try {
	    	connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName + "?user=" + dbUser + "&password=" + dbPassword);
	    	//connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName + "?user=" + dbUser + "&password=" + dbPassword + "&jdcbCompliantTruncation=false");
	    }
	    catch (SQLException e) {
	    	connection = null;
	    }
		}
		return connection;
	}


	public void disconnect() throws SQLException {
		connection.close();
		connection = null;
	}
	
	
	/**
	 * Inserts a new entry in a databse table.
	 * @param table - the table to insert the new entry
	 * @param values - the values of the entry (the values have to have the same order as 
	 *        their columns have in the table)
	 * @return
	 * @throws SQLException
	 */
	public int insertIntoDB(String table, Object[] values) throws SQLException {
		String insert = "INSERT INTO " + table + " VALUES (";
		for (int i = 0; i < values.length - 1; i++) {
			if (values[i] == null) {
				insert += "NULL, ";
			}
			else {
				insert += "'" + values[i].toString() + "', ";
			}
		}
		if (values[values.length - 1] == null) {
			insert += "NULL)";
		}
		else {
			insert += "'" + values[values.length - 1].toString() + "')";
		}
		
		return getStatement().executeUpdate(insert);
	}
	
	
	/**
	 * Inserts a new entry in a database table
	 * @param table - the table to insert the new entry
	 * @param names - the column names
	 * @param values - the values for the columns
	 * @return
	 * @throws SQLException
	 */
	public int insertIntoDB(String table, String[] names, Object[] values) throws SQLException {
		if ((names.length == values.length) && (names.length > 0)) {
			String insert = "INSERT INTO " + table + " (";
			
			for (int i = 0; i < names.length - 1; i++) {
				insert += names[i] + ", ";
			}
			insert += names[names.length - 1] + ") VALUES (";
			
			for (int i = 0; i < values.length - 1; i++) {
				insert += "'" + values[i] + "', ";
			}
			insert += "'" + values[values.length - 1] + "')";
			
			return getStatement().executeUpdate(insert);
		}
		else {
			throw new IllegalArgumentException("There are a different number of names and values or the fields have both the length 0.");
		}
	}
	
	
	/**
	 * Updates one column in the specified database entries.
	 * @param table - the table which contains the entry
	 * @param columnName - the name of the column by which the entry shall be identified
	 * @param columnValue - the value of the column by which the entry shall be identified
	 * @param name - the name of the column which shall be updated
	 * @param value - the value which shall be placed in the specified column
	 * @return the count of columns updated
	 * @throws SQLException
	 */
	public int updateDBEntry(String table, String columnName, String columnValue, String name, String value) throws SQLException {
		return updateDBEntry(table, columnName + " = '" + columnValue + "'", name, value);
	}
	
	
	/**
	 * Updates one column in the specified database entries.
	 * @param table - the table which contains the entry
	 * @param whereCond - the condition placed after the <code>WHERE</code>-keyword in the SQL-query
	 * @param name - the name of the column which shall be updated
	 * @param value - the value which shall be placed in the specified column
	 * @return the count of columns updated
	 * @throws SQLException
	 */
	public int updateDBEntry(String table, String whereCond, String name, String value) throws SQLException {
		String[] names = new String[1];
		names[0] = name;
		Object[] values = new Object[1];
		values[0] = value;
		
		return updateDBEntry(table, whereCond, names, values);
	}
	
	
	/**
	 * Updates several columns in the specified database entries.
	 * @param table - the table which contains the entry
	 * @param columnName - the name of the column by which the entry shall be identified
	 * @param columnValue - the value of the column by which the entry shall be identified
	 * @param names - a list of the names of the columns which shall be updated
	 * @param values - a list of the values which shall be placed in the specified columns
	 * @return the count of columns updated
	 * @throws SQLException
	 */
	public int updateDBEntry(String table, String columnName, String columnValue, String[] names, Object[] values) throws SQLException {
		return updateDBEntry(table, columnName + " = '" + columnValue + "'", names, values);
	}
	
	
	/**
	 * Updates several columns in the specified database entries.
	 * @param table - the table which contains the entry
	 * @param whereCond - the condition placed after the <code>WHERE</code>-keyword in the SQL-query
	 * @param names - a list of the names of the columns which shall be updated
	 * @param values - a list of the values which shall be placed in the specified columns
	 * @return the count of columns updated
	 * @throws SQLException
	 */
	public int updateDBEntry(String table, String whereCond, String[] names, Object[] values) throws SQLException {
		if ((names.length == values.length) && (names.length > 0)) {
			String update = "UPDATE " + table + " SET ";
			for (int i = 0; i < names.length - 1; i++) {
				update += names[i] + " = '" + values[i].toString() + "', ";
			}
			update += names[names.length - 1] + " = '" + values[values.length - 1].toString() + "' "
			          + "WHERE " + whereCond;
			
			return getStatement().executeUpdate(update);
		}
		else {
			throw new IllegalArgumentException("There are a different number of names and values or the fields have both the length 0.");
		}
	}

	
	
	public int deleteFromDB(String table, String column, String value) throws SQLException {
		return getStatement().executeUpdate("DELETE FROM " + table + " WHERE " + column + " = '" + value + "'");
	}
	
	
	public ResultSet readFromDB(String table, String selectionColumn, String value) throws SQLException {
		return readFromDB("*", table, selectionColumn, value);
	}
	
	
	public ResultSet readFromDB(String returnColumn, String table, String selectionColumn, String value) throws SQLException {
		return getStatement().executeQuery("SELECT " + returnColumn + " FROM " + table + " WHERE " + selectionColumn + " = '" + value + "'");
	}
	
	
	public ResultSet readFromDB(String table, int entryNo) throws SQLException {
		return readFromDB("*", table, entryNo);
	}
	
	
	public ResultSet readFromDB(String returnColumn, String table, int entryNo) throws SQLException {
		return getStatement().executeQuery("SELECT " + returnColumn + " FROM " + table + " LIMIT " + entryNo + ", " + (entryNo + 1));
	}
}
