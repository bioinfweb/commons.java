package info.webinsel.util.servlet.urltree.sitemap;


import info.webinsel.util.servlet.urltree.DBConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Sitemap implements DBConstants {
  private SitemapEntry home = null;

  
  
	private Sitemap() {
		super();
	}


	public SitemapEntry getHome() {
		return home;
	}
  
  
	private static void readSubentries(SitemapEntry root, Connection connection, String tablePrefix, 
			String lang) throws SQLException {
		
		ResultSet rs = connection.createStatement().executeQuery("SELECT s.*, r.* " +
				"FROM " + tablePrefix + TABLE_SITES + " s, " + 
				tablePrefix + TABLE_REPRESENTATIONS + " r " +
				"WHERE s.representationID = r.representationID " +
				"AND s." + COL_PARENT + " = '" + root.getSiteID() + "' " +
				"AND s.languages LIKE '%" + lang + "%' " +
				"ORDER BY r." + lang + COL_SUFFIX_NAME + " DESC");
		
		while (rs.next()) {
			SitemapEntry subentry = new SitemapEntry();
			subentry.readFromResultSet(rs);
			subentry.setParent(root);
			root.getSubentries().add(subentry);
		}
	}
	
	
	private static void readSubtree(SitemapEntry root, Connection connection, String tablePrefix, 
			String lang) throws SQLException {
		
		readSubentries(root, connection, tablePrefix, lang);
		for (int i = 0; i < root.getSubentries().size(); i++) {
			readSubtree(root.getSubentries().get(i), connection, tablePrefix, lang);
		}
	}
		
	
	public static Sitemap createInstance(Connection connection, String tablePrefix,
			String lang) throws SQLException {
		
		Sitemap result = new Sitemap();
		
		ResultSet rs = connection.createStatement().executeQuery("SELECT s.*, r.* " +
				"FROM " + tablePrefix + TABLE_SITES + " s, " + 
				tablePrefix + TABLE_REPRESENTATIONS + " r " +
				"WHERE s.representationID = r.representationID " +
				"AND s." + COL_PARENT + "='-1' " +
				"LIMIT 1");
		
		if (rs.next()) {
			SitemapEntry root = new SitemapEntry();
			root.readFromResultSet(rs);
			readSubtree(root, connection, tablePrefix, lang);
			result.home = root;
		}
		
		return result;
	}
}
