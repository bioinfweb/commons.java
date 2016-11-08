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
package info.bioinfweb.commons.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SQLConnector {
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	
	public static Connection getConnection(String host, String name, String user, 
			String password, String driver) throws ClassNotFoundException, SQLException {
		
    Class.forName(driver);
    return DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?user=" +
    		user + "&password=" + password);
	}
}
