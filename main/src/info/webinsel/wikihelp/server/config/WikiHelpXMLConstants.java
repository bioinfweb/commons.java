package info.webinsel.wikihelp.server.config;


import info.bioinfweb.commons.sql.sqlproperties.SQLXMLConstants;

import javax.xml.namespace.QName;



public interface WikiHelpXMLConstants extends SQLXMLConstants {
  public static final QName TAG_ROOT = new QName("WikiHelpProperties");
  public static final QName TAG_WIKI_BASE_URL = new QName("WikiBaseURL");
  public static final QName TAG_INVALID_CODE_URL = new QName("InvalidCodeURL");
  public static final QName TAG_ERROR_URL = new QName("ErrorURL");
}
