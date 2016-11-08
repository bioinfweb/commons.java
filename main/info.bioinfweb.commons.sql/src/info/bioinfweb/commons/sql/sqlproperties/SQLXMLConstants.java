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


import info.bioinfweb.commons.io.FormatVersion;

import javax.xml.namespace.QName;



/**
 * XML constants for the namespace <i>http://bioinfweb.info/xmlns/SQLConnection</i>.
 * 
 * @author Ben St&ouml;ver
 */
public interface SQLXMLConstants {
  public static final String NAMESPACE_URI = "http://bioinfweb.info/xmlns/SQLConnection";
  public static final FormatVersion VERSION = new FormatVersion(1, 0);
  public static final String FULL_SCHEMA_LOCATION = NAMESPACE_URI + " " + NAMESPACE_URI + "/" + VERSION + ".xsd";
  
  public static final QName TAG_DB = new QName(NAMESPACE_URI, "database");
  public static final QName TAG_DB_HOST = new QName(NAMESPACE_URI, "host");
  public static final QName TAG_DB_NAME = new QName(NAMESPACE_URI, "name");
  public static final QName TAG_DB_USER = new QName(NAMESPACE_URI, "user");
  public static final QName TAG_DB_PASSWORD = new QName(NAMESPACE_URI, "password");
  public static final QName TAG_DB_DRIVER = new QName(NAMESPACE_URI, "driver");
  public static final QName TAG_TABLE_PREFIX = new QName(NAMESPACE_URI, "table-prefix");
}
