package info.webinsel.util.servlet.urltree;


import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;



public class URLPath implements DBConstants {
	private Vector<URLTreeElement> elements = new Vector<URLTreeElement>();
	private boolean error404 = false;
	
	
	/**
	 * Reads data from the sites and the representations table. 
	 * @param statement
	 * @param tablePrefix
	 * @param lang
	 * @param urlPart
	 * @param parentID
	 * @return the URL tree element or <code>null</code> if the specified entry is not
	 *         in the database.
	 * @throws SQLException
	 */
	private static URLTreeElement readElement(Statement statement, String tablePrefix, 
			String lang, String urlPart, int parentID) throws SQLException {
		
		ResultSet rs = statement.executeQuery("SELECT s.*, r.* " +
				"FROM " + tablePrefix + TABLE_SITES + " s, " + 
				tablePrefix + TABLE_REPRESENTATIONS + " r " +
				"WHERE r." + lang + COL_SUFFIX_URL + "='" + urlPart + "' " +
				"AND r." + COL_REPRESENTATION_ID + "=s." + COL_REPRESENTATION_ID + " " +
				"AND s." + COL_PARENT + "='" + parentID + "' " +
				"LIMIT 1");
		
		if (rs.next()) {
			URLTreeElement result = new URLTreeElement();
			result.readFromResultSet(rs);
			return result;
		}
		else {
			return null;
		}
	}
	
	
	private URLPath () {
		super();		
	}
	
	
	private static void set404(URLPath path, ErrorPageData page404) {
		path.elements.clear();
		URLTreeElement element = new URLTreeElement();
		element.setHead(page404.getHead());
		element.setOnLoad(page404.getOnLoad());
		element.setBody(page404.getBody());
		path.elements.add(element);
		path.error404 = true;
	}
	
	
	/**
	 * Creates a <code>URLPath</code> object. 
	 * @param statement - the statement that should be used to obtain data from the 
	 *        database
	 * @param tablePrefix - the prefix of the tables in the database (Can be 
	 *        <code>""</code> but not <code>null</code>.) 
	 * @param lang - the language of the specified path
	 * @param path - the path requested by the user
	 * @return the new instance (linking to the specified error page if the specified path does not
	 *         match the database entries)
	 * @throws SQLException
	 */
	public static URLPath createInstance(Statement statement, String tablePrefix, 
			String lang, String path, ErrorPageData page404) throws SQLException {
		
		URLPath result = new URLPath();
		
		URLTreeElement element = readElement(statement, tablePrefix, lang, "", -1);
		if (element == null) {
			set404(result, page404);
			return result;
		}
		result.elements.add(element);  // Add root
		
		if (!path.equals("")) {  // not Home
			String[] parts = path.split("/");
			for (int i = 0; i < parts.length; i++) {
				element = readElement(statement, tablePrefix, lang, parts[i], 
						result.elements.get(i).getSiteID());
				if (element == null) {
					set404(result, page404);
					return result;
				}
				result.elements.add(element);
			}
		}
		return result;
	}


	/**
	 * Returns a site associated with the specified URL part
	 * @param index - the index of the URL part (Home is 0)
	 * @return
	 */
	public URLTreeElement get(int index) {
		return elements.get(index);
	}


	public URLTreeElement getLastElement() {
		if (size() > 0) {
			return elements.get(size() - 1); 
		}
		else {
			return null;
		}
	}
	
	
	public boolean isError404() {
		return error404;
	}


	/**
	 * The number of URL parts contained in this object. (1 means that Home is the only
	 * containes element.)
	 * @return
	 */
	public int size() {
		return elements.size();
	}
	
	
	/**
	 * Creates the URL path for the given language.
	 * @param lang - the language the path should be returned in
	 * @return the path without a leading "/"
	 * @throws InvalidParameterException if no values are stored fot the specified 
	 *         language
	 */
	public String createPath(String lang) {
		return createPath(lang, elements.size() - 1);
	}
	
	
	/**
	 * Creates the URL path for the given language.
	 * @param lang - the language the path should be returned in
	 * @param element - the index of the last element of the path
	 * @return the path without a leading "/"
	 * @throws InvalidParameterException if no values are stored fot the specified 
	 *         language
	 */
	public String createPath(String lang, int element) {
		String result = "";
		if (element >= 1) {
			try {
				result = elements.get(1).getUrlParts().get(lang);
				for (int i = 2; i <= element; i++) {
					result += "/" + elements.get(i).getUrlParts().get(lang);
				}
			}
			catch (NullPointerException e) {
				throw new InvalidParameterException("Language \"" + lang + "\" not found.");
			}
		}
		return result;
	}
}
