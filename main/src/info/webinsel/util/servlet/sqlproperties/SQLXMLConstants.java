package info.webinsel.util.servlet.sqlproperties;


import info.webinsel.util.io.FormatVersion;

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
