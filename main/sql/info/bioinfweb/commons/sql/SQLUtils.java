/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;



/**
 * Utility class for common tasks related to SQL operations.
 * 
 * @author Ben St&ouml;ver
 */
public class SQLUtils {
	public static final String SQL_SET_SEPARATOR = ",";
	
	
  public static String createIntListCond(String columnName, List<Integer> values, String operator) {
  	String result = "";
  	if (values.size() > 0) {
	  	for (int i = 0; i < values.size(); i++) {
				result += columnName + " = " + values.get(i) + " " + operator + " ";
			}
	  	result = "(" + result.substring(0, result.length() - 2 - operator.length()) + ")";
  	}
  	return result;
  }
  
  
	public static String addLeadingKeyword(String cond, String keyword) {
		if (!"".equals(cond)) {
			cond = " " + keyword + " " + cond;
		}
		return cond;
	}
  
  
	public static String addTrailingKeyword(String cond, String keyword) {
		if (!"".equals(cond)) {
			cond += " " + keyword + " ";
		}
		return cond;
	}
	
	
	/**
	 * Reads a value of an SQL {@code SET} column into an {@link EnumSet}.
	 * <p>
	 * Note that the names of the enumeration constants used in SQL and in Java need to be identical
	 * (case sensitive match).
	 * <p>
	 * If {@code null} is read from the database the returned set will also be {@code null}.
	 * If an empty string (representation of an empty {@code SET}) is read an empty set will be returned.
	 * 
	 * @param rs - the result set to read the data from
	 * @param columnLabel - the label of the column containing the {@code SET} value
	 * @param enumClass - the {@code enum} class that represents the SQL {@code SET} in Java
	 * @return an {@code enum} set corresponding the value in the database
	 * @throws SQLException
	 */
	public static <E extends Enum<E>> EnumSet<E> readEnumSet(ResultSet rs, String columnLabel, Class<E> enumClass) 
			throws SQLException {
		
		return readEnumSet(rs.getString(columnLabel), enumClass);
	}

	
	/**
	 * Reads a value of an SQL {@code SET} column into an {@link EnumSet}.
	 * <p>
	 * Note that the names of the enumeration constants used in SQL and in Java need to be identical
	 * (case sensitive match).
	 * <p>
	 * If {@code null} is specified as {@code value} the returned set will also be {@code null}.
	 * If an empty string is specified as {@code value} an empty set will be returned.
	 * 
	 * @param value - the string representation of the SQL {@code SET} value
	 * @param enumClass - the {@code enum} class that represents the SQL {@code SET} in Java
	 * @return an {@code enum} set corresponding the value in the database
	 */
	public static <E extends Enum<E>> EnumSet<E> readEnumSet(String value, Class<E> enumClass) {
		if (value == null) {
			return null;
		}
		else {
			EnumSet<E> result = EnumSet.noneOf(enumClass);
			if (!"".equals(value)) {
				String[] values = value.split(SQL_SET_SEPARATOR);
				for (int i = 0; i < values.length; i++) {
					result.add(Enum.valueOf(enumClass, values[i].trim()));
				}
			}
			return result;
		}
	}
}
