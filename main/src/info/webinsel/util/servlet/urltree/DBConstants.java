package info.webinsel.util.servlet.urltree;



/**
 * Constances used to access the database tables that store the URL tree.
 * @author Ben St&ouml;ver
 */
public interface DBConstants {
  public static final String TABLE_SITES = "sites";
  public static final String TABLE_REPRESENTATIONS = "representations";
  
  public static final String COL_SITE_ID = "siteID"; 
  public static final String COL_REPRESENTATION_ID = "representationID"; 
  public static final String COL_PARENT = "parent"; 
  public static final String COL_HEAD = "includeHead"; 
  public static final String COL_ON_LOAD = "onLoad"; 
  public static final String COL_BODY = "includeBody"; 
  public static final String COL_LANGUAGES = "languages"; 

  public static final String COL_SUFFIX_URL = "URL"; 
  public static final String COL_SUFFIX_NAME = "Name";
  
  public static final char LANGUAGES_SEPARATOR = ','; 
}
