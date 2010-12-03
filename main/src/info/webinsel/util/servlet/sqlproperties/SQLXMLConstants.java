package info.webinsel.util.servlet.sqlproperties;


import javax.xml.namespace.QName;



public interface SQLXMLConstants {
  public static final QName TAG_DB = new QName("Database");
  public static final QName TAG_DB_HOST = new QName("Host");
  public static final QName TAG_DB_NAME = new QName("Name");
  public static final QName TAG_DB_USER = new QName("User");
  public static final QName TAG_DB_PASSWORD = new QName("Password");
  public static final QName TAG_DB_DRIVER = new QName("Driver");
  public static final QName TAG_TABLE_PREFIX = new QName("TablePrefix");
}
