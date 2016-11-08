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
package info.bioinfweb.commons.sql.sqlproperties;



/**
 * Default implementation of the {@link SQLProperties} interface.
 * 
 * @author Ben St&ouml;ver
 */
public class ConcreteSQLProperties implements SQLProperties {
  private String dbHost = "";
  private String dbName = "";
  private String dbUser = "";
  private String dbPassword = "";
  private String dbDriver = "";
  private String tablePrefix = "";


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getDBDriver()
	 */
	public String getDBDriver() {
		return dbDriver;
	}
	
	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setDBDriver(java.lang.String)
	 */
	public void setDBDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getDBHost()
	 */
	public String getDBHost() {
		return dbHost;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setDBHost(java.lang.String)
	 */
	public void setDBHost(String dbHost) {
		this.dbHost = dbHost;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getDBName()
	 */
	public String getDBName() {
		return dbName;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setDBName(java.lang.String)
	 */
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getDBPassword()
	 */
	public String getDBPassword() {
		return dbPassword;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setDBPassword(java.lang.String)
	 */
	public void setDBPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getDBUser()
	 */
	public String getDBUser() {
		return dbUser;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setDBUser(java.lang.String)
	 */
	public void setDBUser(String dbUser) {
		this.dbUser = dbUser;
	}

	
	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#getTablePrefix()
	 */
	public String getTablePrefix() {
		return tablePrefix;
	}


	/* (non-Javadoc)
	 * @see info.bioinfweb.commons.servlet.sqlproperties.SQLProperties#setTablePrefix(java.lang.String)
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
}
