package info.webinsel.util.servlet.urltree.singlelang;


import info.webinsel.util.servlet.sqlproperties.SQLXMLConstants;

import javax.xml.namespace.QName;



public interface SingleLangXMLConstants extends SQLXMLConstants {
  public static final QName TAG_ROOT = new QName("SingleLangContentDispatchPropterties");
  
  public static final QName TAG_HOST_NAME = new QName("HostName");
  public static final QName TAG_MAIN_JSP = new QName("MainJSP");
  
  public static final QName TAG_404 = new QName("Error404");
  public static final QName TAG_HEAD = new QName("Head");
  public static final QName TAG_ON_LOAD = new QName("OnLoad");
  public static final QName TAG_BODY = new QName("Body");
}
