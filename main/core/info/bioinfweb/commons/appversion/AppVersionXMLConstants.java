package info.bioinfweb.commons.appversion;


import javax.xml.namespace.QName;



/**
 * Constants used for the XML representation of an instance of {@link ApplicationVersion} as they are
 * used by {@link AppVersionXMLReadWrite}.
 * 
 * @author Ben St&ouml;ver
 */
public class AppVersionXMLConstants {
  public static final QName TAG_APP_VERSION = new QName("AppVersion");
  public static final QName ATTR_MAJOR_APP_VERSION = new QName("Major");
  public static final QName ATTR_MINOR_APP_VERSION = new QName("Minor");
  public static final QName ATTR_PATCH_LEVEL = new QName("Patch");
  public static final QName ATTR_BUILD_NO = new QName("Build");
  public static final QName ATTR_APP_TYPE = new QName("type");  
}
