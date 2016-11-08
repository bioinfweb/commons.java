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
package info.bioinfweb.commons.sql.sqlproperties;



/**
 * Classes implementing this interface provide a bean to store information to connect to a SQL database.
 * 
 * @author Ben St&ouml;ver
 */
public interface SQLProperties {
	public String getDBDriver();

	
	public void setDBDriver(String dbDriver);

	
	public String getDBHost();

	
	public void setDBHost(String dbHost);

	
	public String getDBName();

	
	public void setDBName(String dbName);

	
	public String getDBPassword();

	
	public void setDBPassword(String dbPassword);

	
	public String getDBUser();

	
	public void setDBUser(String dbUser);

	
	public String getTablePrefix();

	
	public void setTablePrefix(String tablePrefix);
}