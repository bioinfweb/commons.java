package info.webinsel.util.servlet.urltree;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;



public class URLTreeElement implements DBConstants {
  private int siteID = -1;
  private int representationID = -1;
  private String head = "";
  private String onLoad = "";
  private String body = "";
  private HashMap<String, String> urlParts = new HashMap<String, String>();
  private HashMap<String, String> names = new HashMap<String, String>();
  
  
	public URLTreeElement() {
		super();
	}


	/**
	 * Creates an instance of this class. All data is read from the current position of the given
	 * <code>ResultSet</code>. (<code>next()</code> is never called inside this method.)
	 * @param rs - the <code>ResultSet</code> to read the data from
	 * @return the new instance
	 * @throws SQLException
	 */
	public void readFromResultSet(ResultSet rs) throws SQLException {
		setSiteID(rs.getInt(COL_SITE_ID));
		setRepresentationID(rs.getInt(COL_REPRESENTATION_ID));
		setHead(rs.getString(COL_HEAD));
		setOnLoad(rs.getString(COL_ON_LOAD));
		setBody(rs.getString(COL_BODY));
		
		String[] languages = rs.getString(COL_LANGUAGES).split("" + LANGUAGES_SEPARATOR);
		for (int i = 0; i < languages.length; i++) {
			if (!languages[i].equals("")) {
				getNames().put(languages[i], rs.getString(languages[i] + COL_SUFFIX_NAME));
				getUrlParts().put(languages[i], rs.getString(languages[i] + COL_SUFFIX_URL));
			}
		}
	}


	public String getBody() {
		return body;
	}
  
  
	public void setBody(String body) {
		this.body = body;
	}
  
  
	public String getHead() {
		return head;
	}
  
  
	public void setHead(String head) {
		this.head = head;
	}
  
  
	public String getOnLoad() {
		return onLoad;
	}
  
  
	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}
  
  
	public int getRepresentationID() {
		return representationID;
	}
  
  
	public void setRepresentationID(int representationID) {
		this.representationID = representationID;
	}
  
  
	public int getSiteID() {
		return siteID;
	}
  
  
	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}
  
  
	public HashMap<String, String> getNames() {
		return names;
	}
  
  
	public HashMap<String, String> getUrlParts() {
		return urlParts;
	}  
}
